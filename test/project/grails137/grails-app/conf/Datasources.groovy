import org.hibernate.dialect.HSQLDialect

def dbDialect = HSQLDialect
def dbDriver = "org.hsqldb.jdbcDriver"

datasources = {

  datasource(name: "first") {
    driverClassName(dbDriver)
    dialect(dbDialect)
    pooled(true)
    url("jdbc:hsqldb:mem:firstDb")
  }


  datasource(name: "second") {
    driverClassName(dbDriver)
    dialect(dbDialect)
    pooled(false)
    url("jdbc:hsqldb:mem:secondDb")
  }

  datasource(name: "third") {
    driverClassName(dbDriver)
    dialect(dbDialect)
    pooled(true)
    url("jdbc:hsqldb:mem:thirdDb")
  }
}

