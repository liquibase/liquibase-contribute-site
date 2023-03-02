---
title: Test Environments
---

# Liquibase SDK Test Environments

## Overview

To make it easy to spin up standardized test environments for testing, Liquibase has an abstraction around [testcontainers.org](https://testcontainers.org){:target="_blank"}
at [liquibase/extension/testing/testsystem](https://github.com/liquibase/liquibase/tree/master/liquibase-extension-testing/src/main/java/liquibase/extension/testing/testsystem){:target="_blank"}.

The wrapper around Test Containers allows us to provide a simple configuration system, plus support testing databases which do not have Docker containers such as in-memory and cloud databases.

!!! note

    The SDK Test Environment System is currently only available in the main Liquibase repository, not for extensions.

## Configuration

The default test environments and settings are configured in [liquibase-extension-testing/src/main/resources/liquibase.sdk.yaml](https://github.com/liquibase/liquibase/tree/master/liquibase-extension-testing/src/main/resources/liquibase.sdk.yaml){:target="_blank"}.

That file configures the various databases including docker image settings and users to create. Generally each test system will be created with:

- A `lbuser` user with a password `LiquibasePass1`
- A catalog/database called `lbcat`
- An alternate catalog/database named `lbcat2` 
- An alternate schema named `lbschem2`

Besides the environment configuration, the default setup also sets the `liquibase.sdk.testSystem.test` configuration to `h2,hsqldb,sqlite`. This setting controls which databases are tested against and which are skipped. Note that by default only in-memory databases will be used.

### Custom Configuration

The SDK Test Environment setup looks up configuration keys in:

1. System properties
1. Environment Variables
1. A `liquibase.sdk.local.yaml` file
1. A `liquibase.sdk.yaml` file

If a configuration is set in multiple locations, it will use the value it finds first given the above order.

For example, by default the MysqlIntegrationTest will skip all tests because `liquibase.sdk.testSystem.test` does not include "mysql". To customize that configuration, you could:

- Add a `-Dliquibase.sdk.testSystem.test=mysql` argument to your java call. This works well when running specific tests from your IDE's "test run" configuration which you don't always want to enable
- Add a `LIQUBASE_SDK_TEST_SYSTEM_TEST=mysql` environment variable. This works like a system property, but may be easier to set depending on how you are working
- Create a [liquibase.sdk.local.yaml](#example-liquibasesdklocalyaml-file) file in `liquibase-extension-testing/src/main/resources` next to `liquibase.sdk.yaml`. This file is not checked into git, and works well for more permanent/consistent changes you would like to make

### Configuration Structure

All settings for the test environments use the `liquibase.sdk.testSystem` prefix. The overall structure can be seen in the [liquibase-extension-testing/src/main/resources/liquibase.sdk.yaml](https://github.com/liquibase/liquibase/tree/master/liquibase-extension-testing/src/main/resources/liquibase.sdk.yaml){:target="_blank"} file.

For each test environment, there is a top-level "key" for it. For example, `liquibase.sdk.testSystem.mysql`. There is also a `liquibase.sdk.testSystem.default` section which contains default/standard settings.

When code is looking for a particular setting such as `liquibase.sdk.testSystem.mysql.username` it will first look for `liquibase.sdk.testSystem.mysql.username` in all the locations it could be set
and if none has that setting, it will look for `liquibase.sdk.testSystem.default.username` in those same locations.

This allows common settings such as "username" to be shared across environments, while keeping environment-specific settings like "imageName" separate. 

### Example liquibase.sdk.local.yaml File

Anything you set in `liquibase.sdk.local.yaml` file will override what is in the default `liquibase.sdk.yaml` file, but there is no need to duplicate settings from the `liquibase.sdk.yaml` file.
Copy/pasting settings from the default only risks unexpected drift between what you are testing against and what others use.

Example file:

```yaml
liquibase:
  sdk:
    testSystem:
      test: h2,mysql,postgresql

      mysql:
        version: 8.0
```

This changes what databases are tested against to be only h2, mysql, and postgresql without changing what is tested against. It also changes the mysql version to test to "8.0" regardless of what the default is.

## Starting Databases

### In Tests

Tests are able to auto-start databases on demand when they are ran. 

A call to 
```
Scope.getCurrentScope().getSingleton(TestSystemFactory.class).getTestSystem("mysql")
```

will either return the database connection after starting the database OR throw a `AssumptionViolatedException` exception which causes the test to be skipped.

The `liquibase.sdk.testSystem.test` setting controls which environments to auto-start and which to skip.

The `AbstractIntegrationTest` base class contains this logic for the [classic integration tests](integration-tests.md).

### On Demand

To start and stop test systems manually, you can use the `liquibase sdk system up` and `liquibase sdk system down` commands.

**_NOTE:_** these commands are part of the `liquibase-extension-testing` module and not currently shipped as published artifacts.

Therefore, to use them you must either:

- Run `mvn package` from the root of the Liquibase repository and copy `liquibase-extension-testing/target/liquibase-extension-testing-0-SNAPSHOT.jar` to your LIQUIBASE_HOME/lib directory 
- From your IDE, [run](env-setup.md) `liquibase.integration.commandline.LiquibaseLauncher` using the `liquibase-extension-testing` classpath 

The "up" and "down" commands let you start and top the test environments as needed. 

Any tests you run will use these manually started environments rather than creating their own.  