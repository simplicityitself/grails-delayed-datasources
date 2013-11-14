import com.simplicityitself.grails.plugin.delayeddatasource.DataSourcePostProcessor
import com.simplicityitself.grails.plugin.delayeddatasource.proxies.LazyDataSource
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.context.ApplicationContext
import org.springframework.jdbc.support.lob.DefaultLobHandler
import org.springframework.jdbc.support.lob.OracleLobHandler
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor
import org.springframework.jmx.export.MBeanExporter
import org.springframework.jmx.export.assembler.MethodNameBasedMBeanInfoAssembler
import org.springframework.jmx.support.MBeanServerFactoryBean

class DelayedDatasourcesGrailsPlugin {
  def version = "0.3"
  def grailsVersion = "1.3.7 > *"

  def title = "Delayed Datasources Plugin"
  def author = "David Dawson"
  def authorEmail = "david.dawson@simplicityitself.com"
  def description = '''\
Reconfigures and replaces datasource handling with versions that do not require a database on startup. 
Requires some changes to datasource config, which this plugin will attempt to apply automatically and fail fast if this doesn't work
'''

  def documentation = "https://github.com/simplicityitself/grails-delayed-datasources"

  def license = "APACHE"

  def organization = [name: "Simplicity Itself", url: "http://www.simplicityitself.com/"]

  def issueManagement = [system: "GitHub", url: "https://github.com/simplicityitself/grails-delayed-datasources/issues"]

  def scm = [url: "https://github.com/simplicityitself/grails-delayed-datasources"]

  def doWithSpring = {
    if (pluginEnabled) {
      log.info "Delayed Datasources Activated"
      datasourcePostProcessor(DataSourcePostProcessor)

      lobHandlerOverrideOracle(OracleLobHandler)
      lobHandlerOverrideOraclePooled(OracleLobHandler) {
        nativeJdbcExtractor = new CommonsDbcpNativeJdbcExtractor()
      }

      lobHandlerOverride(DefaultLobHandler)

      mbeanServer(MBeanServerFactoryBean) {
        locateExistingServerIfPossible = true
      }

      delayedDataSourceJmxAssembler(MethodNameBasedMBeanInfoAssembler) {
        managedMethods = [
            "testConnection",
            "isCreated",
            "isCurrentlyAvailable",
            "getLastError"
        ]
      }

      delayedDataSourceExporter(MBeanExporter) {
        server = mbeanServer
        assembler = ref("delayedDataSourceJmxAssembler")
        beans = [:]
      }
    } else {
      log.info "Delayed Datasources Disabled"
    }

  }

  def doWithApplicationContext = { applicationContext ->
    if (pluginEnabled) {
      exportDatasourcesAsMBeans(applicationContext)
    }
  }

  def exportDatasourcesAsMBeans(ApplicationContext applicationContext) {
    def exporter = applicationContext.getBean("delayedDataSourceExporter")
    def datasources = applicationContext.getBeansOfType(LazyDataSource)
    datasources.each { name, ds ->

      def mbeanName = "Data Sources:type=datasource,datasource=${name}"
      println "Exporting : $mbeanName"
      exporter.beans."${mbeanName}" = ds
    }
  }

  boolean isPluginEnabled() {
    ConfigurationHolder.config.delayed_datasource.enabled == "true" || ConfigurationHolder.config.delayed_datasource.enabled == true
  }
}
