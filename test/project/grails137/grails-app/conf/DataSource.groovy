dataSource {
  pooled = true
  driverClassName = "com.mysql.jdbc.Driver"
  dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
  show_sql=true
}

hibernate {
  cache.use_second_level_cache = true
  cache.use_query_cache = true
  cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
  development {
    dataSource {
      dbCreate = "create-drop" // one of 'create', 'create-drop','update'
      url = "jdbc:mysql://localhost/delayeddb?useUnicode=yes&characterEncoding=UTF-8"
      username = "newuser"
      password = "password"
    }
  }
}
