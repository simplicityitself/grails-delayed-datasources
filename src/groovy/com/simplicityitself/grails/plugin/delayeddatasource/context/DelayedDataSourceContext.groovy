package com.simplicityitself.grails.plugin.delayeddatasource.context

import com.simplicityitself.grails.plugin.delayeddatasource.proxies.DelayedSessionFactoryBean
import com.simplicityitself.grails.plugin.delayeddatasource.proxies.LazyDataSource
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.GenericBeanDefinition

class DelayedDataSourceContext {

  BeanDefinitionRegistry registry
  String dsName
  DelayedDataSourceContextAnalyser analyser

  public updateBeanDefinitions() {
    analyser.ensureDialectIsSet()

    createLobHandler()
    makeDatasourceDefinitionLazy()
  }

  public postProcessBeans() {
    updateSessionFactoryClass()
  }

  private createLobHandler() {

    String lobOverride = "lobHandlerOverride"

    if(analyser.dataSourceOracle) {
      if (analyser.dataSourcePooled) {
        println "Datasource ${dsName} is deemed to be Oracle (Pooled)"
        lobOverride = "lobHandlerOverrideOraclePooled"
      } else {
        println "Datasource ${dsName} is deemed to be Oracle (Single Connection)"
        lobOverride = "lobHandlerOverrideOracle"
      }
    }

    if (registry.containsBeanDefinition(analyser.lobHandlerName)) {
      registry.removeBeanDefinition(analyser.lobHandlerName)
    }
    registry.registerAlias(lobOverride, analyser.lobHandlerName)
  }

  private makeDatasourceDefinitionLazy() {
    def beanDef = registry.getBeanDefinition(dsName)
    beanDef.lazyInit=true

    registry.registerBeanDefinition("${dsName}_lazy", beanDef)

    registry.removeBeanDefinition(dsName)

    createLazyProxyDataSource()

  }

  private createLazyProxyDataSource() {
    def dataSource = new GenericBeanDefinition()

    dataSource.lazyInit = true
    dataSource.beanClassName = LazyDataSource.name
    dataSource.getPropertyValues().add("dsName", dsName)

    registry.registerBeanDefinition(dsName, dataSource)
  }

  private updateSessionFactoryClass() {
    analyser.sessionFactory.beanClassName = DelayedSessionFactoryBean.name
  }
}
