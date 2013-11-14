package com.simplicityitself.grails.plugin.delayeddatasource.context

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionRegistry

class DelayedDataSourceContextAnalyser {

  BeanDefinitionRegistry registry
  String dsName

  String getLobHandlerName() {
    "lobHandlerDetector${dataSourceSuffix}"
  }

  BeanDefinition getDataSource() {
    registry.getBeanDefinition("dataSource${dataSourceSuffix}")
  }

  BeanDefinition getSessionFactory() {
    registry.getBeanDefinition("sessionFactory${dataSourceSuffix}")
  }

  def getHibernateProperties() {
    registry.getBeanDefinition("hibernateProperties${dataSourceSuffix}")
  }

  String getDataSourceSuffix() {
    if(dsName.startsWith("dataSource_")) {
      return "_" + dsName[11..-1]
    }
    return ""
  }

  boolean isDataSourcePooled() {
    def ds = registry.getBeanDefinition(dsName)

    //TODO c3po, some other pooled DS we don't know about?  prob need a config option
    ["org.apache.tomcat.jdbc.pool.DataSource",
     "org.apache.commons.dbcp.BasicDataSource"].contains(ds.beanClassName)
  }

  String getDialect() {
    return hibernateProperties.propertyValues.propertyValueList[0].value["hibernate.dialect"]
  }

  boolean isDataSourceOracle() {
    dialect.contains("Oracle")
  }
}
