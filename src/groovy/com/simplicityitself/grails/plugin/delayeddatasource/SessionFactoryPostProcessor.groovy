package com.simplicityitself.grails.plugin.delayeddatasource
import org.apache.commons.dbcp.BasicDataSource
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.jdbc.datasource.DelegatingDataSource

import java.sql.Connection
import java.sql.SQLException

class SessionFactoryPostProcessor implements BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor {

  @Override
  void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    beanDefinitionRegistry.removeBeanDefinition("lobHandlerDetector")
    beanDefinitionRegistry.registerAlias("lobHandlerOverride", "lobHandlerDetector")
  }

  @Override
  void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    configurableListableBeanFactory.getBeanDefinition("sessionFactory").beanClassName =
      DelayedSessionFactoryBean.name

    println "CALZZ FOR SESSION FACTOPRY=${configurableListableBeanFactory.getBeanDefinition("sessionFactory").beanClassName}"

  }
}

//TODO, replace this with some kind of lazy proxy from the existing data source
//This would preserve the pooling behaviour form other plugins (such as tomcat-jdbc)

class LazyDataSource extends DelegatingDataSource {

  private boolean _initialized

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
    if (_initialized) {
      return
    }

    def config = ConfigurationHolder.config.dataSource
    setTargetDataSource(new BasicDataSource(
        driverClassName: config.driverClassName, password: config.password,
        username: config.username, url: config.url))

    _initialized = true
  }
}

