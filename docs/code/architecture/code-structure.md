# Code Structure

## Top-Level Modules

The Liquibase repository is divided into a series of submodules, including:

- `liquibase-core` contains the Liquibase engine/API plus standard functionality
- Modules for extension code that builds on the engine/API that is better split out of the monolithic `liquibase-dist`
    - `liquibase-snowflake` adds support for Snowflake
    - Further modules will be extracted from `liquibase-dist` in the future
- Modules for integrations
    - `liquibase-cli` contains the CLI integration
    - `liquibase-maven-plugin` contains the Maven integration
    - `liquibase-cdi` contains the CDI integration
- `liquibase-dist` creates the shipped liquibase-core.jar which is made up of a combination of modules
  including `liquibase-core`, `liquibase-cli`, and `liquibase-snowflake`
- `liquibase-dist` which contains the distributed CLI tar.gz package
- Modules for testing
    - `liquibase-integration-tests` which contains [tests that run against databases](../test-your-code/integration-tests.md)
    - `liquibase-extension-testing` which contains the [test environment code](../test-your-code/test-environments.md) as well as other
      environmental testing base classes

## Module Structure

Within each submodule, the code is structured following the Maven standard layout, with:

- `src` containing all the source code
- `src/main` containing the "production" code
- `src/main/java` containing the "production" Java code
- `src/main/resources` containing static files for the "production" artifacts
- `src/test` containing the "test" code
- `src/test/java` and `src/test/groovy` containing the "test" java and groovy code
- `src/test/resources`containing static files for use in tests


## Further Reading

- Learn about [the major components](../api/index.md)
- Read our [coding style guide](../get-started/coding-style.md)
