package com.simplicityitself.grails.plugin.delayeddatasource
import org.codehaus.groovy.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean
import org.hibernate.SessionFactory
import org.springframework.util.ReflectionUtils

import java.lang.reflect.Field

class DelayedSessionFactoryBean extends ConfigurableLocalSessionFactoryBean {

  private boolean initialized
  private SessionFactory realSessionFactory

  @Override
  void afterPropertiesSet() {

  }

  SessionFactory getObject() {
    connectDataSource()

    realSessionFactory
  }

  private synchronized void connectDataSource() {
    if (initialized) {
      return
    }

    realSessionFactory = wrapSessionFactoryIfNecessary(buildSessionFactory())

    Field field = ReflectionUtils.findField(getClass(), 'sessionFactory')
    field.accessible = true
    field.set(this, realSessionFactory)

    afterSessionFactoryCreation()

    initialized = true
  }
}