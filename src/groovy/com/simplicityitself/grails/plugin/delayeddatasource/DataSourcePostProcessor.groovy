package com.simplicityitself.grails.plugin.delayeddatasource

import com.simplicityitself.grails.plugin.delayeddatasource.context.DelayedDataSources
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor

class DataSourcePostProcessor implements BeanDefinitionRegistryPostProcessor {

  @Override
  void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    new DelayedDataSources(registry: beanDefinitionRegistry).preprocessBeans()
  }

  @Override
  void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    new DelayedDataSources(registry: (BeanDefinitionRegistry) configurableListableBeanFactory).postProcessBeans()
  }
}
