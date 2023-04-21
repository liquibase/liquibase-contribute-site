# Code Structure

## Top-Level Modules

The Liquibase repository is divided into a series of modules, each with a specific purpose.

### Modules shipped as part of liquibase-core.jar

The liquibase-core.jar file is made up of several submodules:

- `liquibase-standard` which contains the Liquibase engine/API plus standard functionality
- `liquibase-snowflake` adds contains support for Snowflake 
- `liquibase-cli` adds contains the CLI integration
- `liquibase-core` which has no code itself, but combines the other modules into the single, final jar

By separating blocks of functionality within liquibase-core.jar into independent submodules, we can [better control internal code dependencies](#module-dependencies) 

### Modules creating additional shipped artifacts

Beyond the modules that make up `liquibase-core.jar`, the following correspond to other shipped artifacts:

- `liquibase-dist` contains the distributable CLI zip/tar.gz/installer packages
- `liquibase-maven-plugin` contains the Maven integration
- `liquibase-cdi` contains the CDI 2.0 integration
- `liquibase-cdi-jakarta` contains the CDI 3.0+ integration

### Utility modules

The remaining submodules are for testing and other purposes, including:

- `liquibase-integration-tests` which contains [tests that run against databases](../test-your-code/integration-tests.md)
- `liquibase-extension-testing` which contains the [test environment code](../test-your-code/test-environments.md) as well as other environmental testing base classes

## Module Dependencies

While we ship `liquibase-core.jar` as a combined jar to simplify dependency management for users, we can be more granular in our dependencies internally when applicable.

Any modules that [ship as artifacts](#independently-shipped-artifacts) must depend on `liquibase-core` and not any "internal-only" modules such as `liquibase-standard`. 
Because they are shipped and therefore their dependencies are used outside our internal build, they can only reference artifacts that will be usable by external users.

For modules that do **_not_** ship as artifacts, the general rule is to limit its dependencies as much as possible in order to keep code more cleanly separated and avoid a tangle of dependencies.

For example, the `liquibase-standard` module defines the API and standard implementations without getting into "optional" functionality such as the CLI. 
By having modules like `liquibase-snowflake` depend on `liquibase-standard`, it cannot accidentally depend on the CLI-focused code.
However, even though `liquibase-integration-tests` is an internal-only module, in order to test Snowflake and the CLI functionality it depends on `liquibase-core` and not just `liquibase-standard`.
 

## Module Structure

Within each submodule, the code is structured following the Maven standard layout, with:

- `src` containing all the source code
- `src/main` containing the "production" code
- `src/main/java` containing the "production" Java code
- `src/main/resources` containing static files for the "production" artifacts
- `src/test` containing the "test" code
- `src/test/java` and `src/test/groovy` containing the "test" java and groovy code
- `src/test/resources` containing static files for use in tests


## Further Reading

- Learn about [the major components](../api/index.md)
- Read our [coding style guide](../get-started/coding-style.md)
