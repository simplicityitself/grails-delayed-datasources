import com.simplicityitself.grails.plugin.delayeddatasource.LazyDataSource
import com.simplicityitself.grails.plugin.delayeddatasource.SessionFactoryPostProcessor
import org.springframework.jdbc.support.lob.DefaultLobHandler

class DelayedDatasourcesGrailsPlugin {
  def version = "0.1"
  def grailsVersion = "1.3.7 > *"

  def title = "Delayed Datasources Plugin"
  def author = "David Dawson"
  def authorEmail = "david.dawson@simplicityitself.com"
  def description = '''\
Reconfigures and replaces datasource handling with versions that do not require a database on startup. 
Requires some changes to datasource config, which this plugin will attempt to apply automatically and fail fast if this doesn't work
'''

  def documentation = "http://grails.org/plugin/delayed-datasources"

  def license = "APACHE"

  def organization = [name: "Simplicity Itself", url: "http://www.simplicityitself.com/"]

  def issueManagement = [system: "GitHub", url: "https://github.com/simplicityitself/grails-delayed-datasources/issues"]

  def scm = [url: "https://github.com/simplicityitself/grails-delayed-datasources/issues"]

  def doWithSpring = {
    sessionFactoryPostProcessor(SessionFactoryPostProcessor)

    //TODO, add support for oracle somehow.
    lobHandlerOverride(DefaultLobHandler)
  }
}
