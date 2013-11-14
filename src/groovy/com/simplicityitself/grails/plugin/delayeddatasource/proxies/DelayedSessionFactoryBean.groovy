package com.simplicityitself.grails.plugin.delayeddatasource.proxies

import org.codehaus.groovy.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean
import org.hibernate.SessionFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.jdbc.datasource.DelegatingDataSource
import org.springframework.util.ReflectionUtils

import javax.sql.DataSource
import java.lang.reflect.Field
import java.sql.Connection
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.util.logging.Logger

class DelayedSessionFactoryBean extends ConfigurableLocalSessionFactoryBean {

  private boolean initialised
  private SessionFactory realSessionFactory

  void afterPropertiesSes() {

  }

  SessionFactory getObject() {
    connectDataSource()
    realSessionFactory
  }

  private synchronized void connectDataSource() {
    if(initialised) {
      return
    }

    realSessionFactory = wrapSessionFactoryIfNecessary(buildSessionFactory())

    Field field = ReflectionUtils.findField(getClass(), "sessionFactory")
    field.accessible=true
    field.set(this, realSessionFactory)

    afterSessionFactoryCreation()
    initialised=true
  }
}

class LazyDataSource extends DelegatingDataSource implements ApplicationContextAware {

  private boolean initialised

  String dsName

  ApplicationContext applicationContext
  boolean dataSourceConnectionOk = true
  String lastError

  public Map testConnection() {
    try {
      println "Connection test on $dsName"
      getConnection()?.close()
      println "Connection test SUCCESS on $dsName"
      return [success:true]
    } catch (Exception ex) {
      [success:false, message:ex.message]
    }
  }

  public boolean isCreated() {
    initialised
  }
  public boolean isCurrentlyAvailable() {
    dataSourceConnectionOk
  }

  Logger getParentLogger() throws SQLFeatureNotSupportedException {
    null
  }

  Connection getConnection() throws SQLException {
    try {
      initialise()
      def conn = super.getConnection()
      dataSourceConnectionOk = true
      lastError = "No Error"
      return conn
    } catch (Exception ex) {
      dataSourceConnectionOk = false
      lastError = ex.message
      throw ex
    }
  }

  void afterPropertiesSet() {

  }

  private synchronized void initialise() {
    if (initialised) {
      return
    }

    DataSource target = applicationContext.getBean("${dsName}_lazy")

    targetDataSource = target

    initialised = true
  }
}


