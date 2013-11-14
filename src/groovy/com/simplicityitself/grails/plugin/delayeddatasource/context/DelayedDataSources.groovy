package com.simplicityitself.grails.plugin.delayeddatasource.context

import org.springframework.beans.factory.support.BeanDefinitionRegistry

class DelayedDataSources {

  BeanDefinitionRegistry registry

  List<DelayedDataSourceContext> getDatasources() {
    registry.beanDefinitionNames.findAll {
      it.startsWith("dataSource") && !it.endsWith("lazy")
    }.collect {
      new DelayedDataSourceContext(
          dsName:it,
          registry: registry,
          analyser: new DelayedDataSourceContextAnalyser(
              dsName: it, registry:registry))

    }
  }

  def preprocessBeans() {
    datasources.each { DelayedDataSourceContext ctx ->
      ctx.updateBeanDefinitions()
    }
  }

  def postProcessBeans() {
    datasources.each { DelayedDataSourceContext ctx ->
      ctx.postProcessBeans()
    }
  }
}
