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

    def organization = [ name: "Simplicity Itself", url: "http://www.simplicityitself.com/" ]

    def issueManagement = [ system: "GitHub", url: "https://github.com/simplicityitself/grails-delayed-datasources/issues" ]

    def scm = [ url: "https://github.com/simplicityitself/grails-delayed-datasources/issues" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
