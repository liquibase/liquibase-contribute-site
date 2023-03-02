---
title: Liquibase Code Overview
---
# Working with the Code

## Top-Level Modules

The Liquibase repository is divided into a series of submodules, including:

- `liquibase-standard` contains the Liquibase engine/API plus standard functionality
- Modules for extension code that builds on the engine/API that is better split out of the the
  monolotic `liquibase-standard`
    - `liquibase-snowflake` adds support for Snowflake
    - Further modules will be extracted from `liquibase-standard` in the future
- Modules for integrations
    - `liquibase-cli` contains the CLI integration
    - `liquibase-maven-plugin` contains the Maven integration
    - `liquibase-cdi` contains the CDI integration
- `liquibase-core` creates the shipped liquibase-core.jar which is made up of a combination of modules
  including `liquibase-standard`, `liquibase-cli`, and `liquibase-snowflake`
- `liquibase-dist` which contains the distributed CLI tar.gz package
- Modules for testing
    - `liquibase-integration-tests` which contains [tests that run against databases](integration-tests.md)
    - `liquibase-extension-testing` which contains the [test environment code](test-environments.md) as well as other
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

## Major Components

| Package                  | Interface                  | Category         | Notes                                                                                                                                                          |
|--------------------------|----------------------------|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| liquibase                | Liquibase                  | Infrastructure   | Legacy facade for calling commands                                                                                                                             |
|                          | Scope                      | Infrastructure   | Alternative to global variables                                                                                                                                |
|                          | ScopeManager               | Infrastructure   | Controls Scope creation                                                                                                                                        |
| liquibase.change         | Change                     | Change Types     | [Defines change types available to changelog files](../extensions/guides/change-types/index.md)                                                                |
| liquibase.changelog      | DatabaseChangeLog          | Changelog Files  | Format-independent changelog file object                                                                                                                       |
|                          | ChangeLogHistoryService    | Database Support | Tracks what changesets have been ran against a database                                                                                                        | 
| liquibase.command        | CommandStep                | Command & Config | Defines a step within a [command pipeline](../extensions/integrations/command-api.md)                                                                          |
|                          | CommandScope               | Command & Config | Facade for [calling commands](../extensions/integrations/command-api.md) from an integration                                                                   |
| liquibase.configuration  | LiquibaseConfiguration     | Command & Config | Access [configuration](../extensions/integrations/configuration-api.md) values                                                                                 |
|                          | ConfigurationValueProvider | Command & Config | [Defines new configuration stores](../extensions/guides/configuration-value-providers/index.md)                                                                |
|                          | ConfiguredValueModifer     | Command & Config | Modifies configuration values before returing them to code                                                                                                     |
| liquibase.database       | Database                   | Database Support | [Defines a database dialect](../extensions/guides/additional-databases/index.md)                                                                               |
|                          | DatabaseConnection         | Database Support | Wraps the underlying connection [to a database](../extensions/guides/additional-databases/index.md)                                                            |
| liquibase.datatype       | DataTypeFactory            | Database Support | Translates generic to [database specific](../extensions/guides/additional-databases/index.md) data types and vice versa                                        |
|                          | DatabaseDataType           | Database Support | Defines mappings for DataTypeFactory                                                                                                                           |
|                          | LiquibaseDataType          | Snapshot         | Defines generic data types                                                                                                                                     | 
| liquibase.diff           | DiffGeneratorFactory       | Diff Support     | Facade for creating diffs between existing databases                                                                                                           | 
|                          | DiffResult                 | Diff Support     | Holds differences from a database comparison                                                                                                                   |
|                          | DatabaseObjectComparator   | Diff Support     | Determines whether objects from different databases are the same object or different                                                                           | 
| liquibase.executor       | ExecutorService            | Infrastructure   | Facade for [executing statements](../extensions/guides/runwith-executors/index.md)                                                                             |
|                          | Executor                   | Database Support | [Defines a new RunWith Executor](../extensions/guides/runwith-executors/index.md)                                                                              |
| liquibase.lockservice    | LockServiceFactory         | Infrastructure   | Facade for finding the LockService                                                                                                                             |
|                          | LockService                | Database Support | Manages the Liquibase Lock                                                                                                                                     |
| liquibase.logging        | LogFactory                 | Infrastructure   | Facade for working with the Logger                                                                                                                             |
|                          | Logger                     | Infrastructure   | Wraps underlying logging systems in a common API                                                                                                               |  
| liquibase.parser         | ChangeLogParser            | Changelog Files  | [Defines changelog file formats](../extensions/guides/changelog-formats/index.md)                                                                              |
|                          | SnapshotParser             | Snapshot         | Parses a stored snapshot file                                                                                                                                  |
| liquibase.plugin         | PluginFactory              | Infrastructure   | Helper class for pluggable service infrastructure                                                                                                              | 
|                          | Plugin                     | Infrastructure   | Base class for pluggable services                                                                                                                              |
| liquibase.precondition   | Precondition               | Preconditions    | [Defines preconditions](../extensions/guides/preconditions/index.md)                                                                                           |
| liquibase.resource       | ResourceAccessor           | Resources        | [Defines resource accessors](../extensions/guides/resource-accessors/index.md)                                                                                 |
|                          | PathHandler                | Resources        | [Defines path handlers](../extensions/guides/path-handlers/index.md)                                                                                           |
|                          | Resource                   | Resources        | Objects returned from [resources accessors](../extensions/guides/resource-accessors/index.md) and [path handlers](../extensions/guides/path-handlers/index.md) |
| liquibase.serializer     | ChangeLogSerializer        | Changelog Files  | Allows [changelog file formats](../extensions/guides/changelog-formats/index.md) to be used in diffChangelog and generateChangelog operations                  |
|                          | SnapshotSerializer         | Snapshot         | Stores a snapshot of a database                                                                                                                                |
| liquibase.servicelocator | ServiceLocator             | Infrastructure   | Helper class for pluggable service infrastructure                                                                                                              |
| liquibase.snapshot       | SnapshotGeneratorFactory   | Snapshot         | Facade for [snapshotting a database](../extensions/guides/snapshot-generators/index.md)                                                                        |
|                          | SnapshotGenerator          | Database Support | [Defines how to snapshot a particular object type](../extensions/guides/snapshot-generators/index.md)                                                          | 
| liquibase.sql            | Sql                        | Change Types     | Interface for holding SQL to execute                                                                                                                           |
|                          | UnparsedSql                | Change Types     | Holds SQL to execute                                                                                                                                           |
| liquibase.sqlgenerator   | SqlGeneratorFactory        | Infrastructure   | Facade for converting SqlStatements database-specific SQL                                                                                                      | 
|                          | SqlGenerator               | Database Support | [Defines how to convert a cross-database SqlStatement to database-specific SQL](../extensions/guides/sql-generators/index.md)                                  |
| liquibase.statement      | SqlStatement               | Change Types     | Defines database-independent operations that can be performed                                                                                                  |
| liquibase.structure      | DatabaseObject             | Snapshot         | Base interface for all types of objects which can be snapshotted                                                                                               |
| liquibase.ui             | UIService                  | Infrastructure   | Wraps the underlying user interaction in a common API                                                                                                          |

## Further Reading

- Understand the [unit](unit-tests.md) and [integration](integration-tests.md) automated tests
- Read our [coding style guide](coding-style.md)

## Need help?

Visit our [Developer forum](https://forum.liquibase.org/c/liquibase-development/){:target="_blank"} or reach
out [on Discord](https://discord.gg/pDB5DfE){target="_blank"}.

## Next Steps

Ready to contribute your changes? [Send us a pull request!](create-pr.md)