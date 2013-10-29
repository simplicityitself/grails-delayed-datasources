# Delayed Datasources Plugin

Adds support for starting an application succssfully without the databases being running.

The datasources and session factory are reconfigured such that they will only be created when the database is present, and will non fatally fail in other cases.

Once the session factory and datasources are created, they will act as normal and tolerate failure in the DB connections.


## Limitations

Currently tested against Grails 1.3.7 only.   Supports the single Grails 1.3.7 datasource only.

Dialects are required to be explicitly set, this is not yet enforced.
Pooling is required to have an initial size of 0, this is not yet enforced

## Installation

Add the latest version of the plugin to the BuildConfig.groovy

```groovy

  plugins {
    runtime ':delayed-datasources:0.1'
  }

```

## Roadmap

Support Grails 2 datasources and configurations
Support Grails 1.3.7 datasources plugin
Support enforcement of explicit dialect configuration
Enforce pool size for commons pool
Enforce pool size for C3P0
Enforce pool size for Tomcat JDBC Pool
Record failure and success to infer database connection status and expose via a user screen/ JSON endpoint
