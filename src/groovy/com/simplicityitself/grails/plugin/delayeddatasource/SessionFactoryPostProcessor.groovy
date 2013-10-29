package com.simplicityitself.grails.plugin.delayeddatasource
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.jdbc.datasource.DelegatingDataSource

import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException

class SessionFactoryPostProcessor implements BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor {

  @Override
  void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    beanDefinitionRegistry.removeBeanDefinition("lobHandlerDetector")
    beanDefinitionRegistry.registerAlias("lobHandlerOverride", "lobHandlerDetector")

    makeDatasourceDefinitionsLazy(beanDefinitionRegistry)
  }

  private void makeDatasourceDefinitionsLazy(BeanDefinitionRegistry beanDefinitionRegistry) {

    //TODO, iterate all potential datasources

    def dsName = "dataSource"

    def beanDef = beanDefinitionRegistry.getBeanDefinition(dsName)
    beanDef.lazyInit = true

    beanDefinitionRegistry.registerBeanDefinition("${dsName}_lazy", beanDef)
    beanDef.clone()
    beanDefinitionRegistry.removeBeanDefinition(dsName)

    createLazyProxyDataSource(beanDefinitionRegistry, dsName)
  }

  private createLazyProxyDataSource(
      BeanDefinitionRegistry beanDefinitionRegistry, String dsName) {
    def dataSource = new GenericBeanDefinition()
    dataSource.lazyInit = false
    dataSource.beanClassName = LazyDataSource.name
    dataSource.getPropertyValues().add("dsName", dsName)

    beanDefinitionRegistry.registerBeanDefinition(dsName, dataSource)
  }

  @Override
  void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    //TODO, iterate all potential session factories.

    def sessionFactoryName = "sessionFactory"

    configurableListableBeanFactory.getBeanDefinition(sessionFactoryName).beanClassName =
      DelayedSessionFactoryBean.name

  }
}


class LazyDataSource extends DelegatingDataSource implements ApplicationContextAware {

  private boolean initialized

  String dsName

  ApplicationContext applicationContext

  @Override
  Connection getConnection() throws SQLException {
    initialize()
    return super.getConnection()
  }

  @Override
  void afterPropertiesSet() {
    // override to not check for targetDataSource since it's lazily created
  }

  private synchronized void initialize() {
    if (initialized) {
      return
    }

    DataSource target = applicationContext.getBean("${dsName}_lazy")

    targetDataSource = target

    initialized = true
  }
}

