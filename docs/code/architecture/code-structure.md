# Code Structure

## Top-Level Modules

The Liquibase repository is divided into a series of submodules, each with a specific purpose.

### Code shipped as part of liquibase-core.jar

The liquibase-core.jar file is made up of several submodules:

- `liquibase-standard` which contains the Liquibase engine/API plus standard functionality
- `liquibase-snowflake` adds contains support for Snowflake 
- `liquibase-cli` adds contains the CLI integration
- `liquibase-core` which has no code itself, but combines the other modules into the single, final jar

By separating blocks of functionality within liquibase-core.jar into independent submodules, we can better control internal code dependencies. 

### Independently shipped artifacts

Other independently shipped artifacts are also submodules:

- `liquibase-maven-plugin` contains the Maven integration
- `liquibase-cdi` contains the CDI 2.0 integration
- `liquibase-cdi-jakarta` contains the CDI 3.0+ integration
- `liquibase-dist` contains the distributable CLI zip/tar.gz/installer package

### Utility modules

The remaining submodules are for testing and other purposes, including:

- `liquibase-integration-tests` which contains [tests that run against databases](../test-your-code/integration-tests.md)
- `liquibase-extension-testing` which contains the [test environment code](../test-your-code/test-environments.md) as well as other environmental testing base classes

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
