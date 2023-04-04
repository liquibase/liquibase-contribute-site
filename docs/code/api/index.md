# API Overview

## Overview

The Liquibase API consists of pluggable interfaces and the driving logic that surrounds them.

Even the main Liquibase code itself is layered between the API-level code which works purely with interfaces, and the
implementation-level code which is written and [auto-discovered](../architecture/service-discovery.md) exactly
like [external extensions](../../extensions-integrations/extensions-overview/index.md) are.

!!! tip

    The complete Javadocs for Liquibase are available at [https://javadoc.liquibase.com](https://javadoc.liquibase.com){:target="_blank"}.

## Environmental Setup

The following classes are used to connect the generic Liquibase logic to the environment it is running in.
They tend to be called from [integrations](../architecture/index.md#integrations-to-engine) prior to executing commands.

| Package                  | Interface                  | Notes                                                                                                   |
|--------------------------|----------------------------|---------------------------------------------------------------------------------------------------------|
| liquibase                | Scope                      | Alternative to global variables                                                                         |
|                          | ScopeManager               | Controls Scope creation                                                                                 |
| liquibase.configuration  | LiquibaseConfiguration     | Access [configuration](configuration-liquibaseconfiguration.md) values                                  |
|                          | ConfigurationDefinition    | [Defines new configuration options](configuration-configurationdefinition.md)                           |
|                          | ConfigurationValueProvider | [Defines new configuration stores](configuration-configurationvalueprovider.md)                         |
|                          | ConfiguredValueModifer     | [Modifies configuration](configuration-configuredvaluemodifier.md) values before returning them to code |
| liquibase.logging        | LogFactory                 | Facade for working with the Logger                                                                      |
|                          | Logger                     | Wraps underlying logging systems in a common API                                                        |  
| liquibase.resource       | ResourceAccessor           | [Defines resource accessors](resource-resourceaccessor.md)                                              |
|                          | PathHandler                | [Defines path handlers](resource-pathhandler.md)                                                        |
|                          | Resource                   | [Objects returned](resource-resource.md) from resources accessors                                       |
| liquibase.servicelocator | ServiceLocator             | Controls pluggable service infrastructure                                                               |
| liquibase.ui             | UIService                  | Wraps the underlying user interaction in a common API                                                   |

## Command Facade

The following classes are the main entry points for calling overall Liquibase commands.

| Package           | Interface    | Notes                                                                      |
|-------------------|--------------|----------------------------------------------------------------------------|
| liquibase         | Liquibase    | Legacy facade for calling commands                                         |
| liquibase.command | CommandScope | Facade for [calling commands](command-commandscope.md) from an integration |

## Business Logic and Data Models

The following classes contain or coordinate business logic or hold data for transfer between components.

| Package           | Interface                | Notes                                                                                |
|-------------------|--------------------------|--------------------------------------------------------------------------------------|
| liquibase.command | CommandStep              | Defines a step within a [command pipeline](command-commandscope.md)                  |
| liquibase.diff    | DiffGeneratorFactory     | Facade for creating diffs between existing databases                                 |
|                   | DiffResult               | Holds differences from a database comparison                                         |
|                   | DatabaseObjectComparator | Determines whether objects from different databases are the same object or different |

## Extensible Capabilities

The following classes define functionality available within Liquibase. They tend to be called
from [business logic](#business-logic-and-data-models), especially `CommandStep` implementations.

Liquibase ships with standard implementations of these interfaces, but they can be extended or replaced
by [external extensions](../../extensions-integrations/extensions-overview/index.md).

| Package                | Interface               | Notes                                                                                                                           |
|------------------------|-------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| liquibase.change       | Change                  | [Defines change types](change-change.md) available to changelog files                                                           |
| liquibase.changelog    | DatabaseChangeLog       | Format-independent changelog file object                                                                                        |
|                        | ChangeLogHistoryService | Tracks what changesets have been ran against a database                                                                         | 
| liquibase.database     | Database                | [Defines a database dialect](database-database.md)                                                                              |
|                        | DatabaseConnection      | Wraps the underlying connection [to a database](database-database.md)                                                           |
| liquibase.datatype     | DataTypeFactory         | Translates generic to database specific data types and vice versa                                                               |
|                        | DatabaseDataType        | Defines mappings for DataTypeFactory                                                                                            |
|                        | LiquibaseDataType       | Defines generic data types                                                                                                      | 
| liquibase.executor     | ExecutorService         | Facade for [executing statements](executor-executor.md)                                                                         |
|                        | Executor                | [Defines a new RunWith Executor](executor-executor.md)                                                                          |
| liquibase.lockservice  | LockServiceFactory      | Facade for finding the LockService                                                                                              |
|                        | LockService             | Manages the Liquibase Lock                                                                                                      |
| liquibase.parser       | ChangeLogParser         | [Defines changelog file formats](parser-changelogparser.md)                                                                     |
|                        | Parser                  | Parses a stored snapshot file                                                                                                   |
| liquibase.precondition | Precondition            | [Defines preconditions](precondition-precondition.md)                                                                           |
| liquibase.serializer   | ChangeLogSerializer     | Allows [changelog file formats](serializer-changelogserializer.md) to be used in diffChangelog and generateChangelog operations |
|                        | Serializer              | Stores a snapshot of a database                                                                                                 |
| liquibase.snapshot     | GeneratorFactory        | Facade for [snapshotting a database](snapshot-snapshotgenerator.md)                                                             |
|                        | SnapshotGenerator       | [Defines how to snapshot a particular object type](snapshot-snapshotgenerator.md)                                               |
| liquibase.sql          | Sql                     | Interface for holding SQL to execute                                                                                            |
|                        | UnparsedSql             | Holds SQL to execute                                                                                                            |
| liquibase.sqlgenerator | SqlGeneratorFactory     | Facade for converting SqlStatements database-specific SQL                                                                       | 
|                        | SqlGenerator            | Defines how to [convert a cross-database SqlStatement to database-specific SQL](sqlgenerator-sqlgenerator.md)                   |
| liquibase.statement    | SqlStatement            | Defines database-independent operations that can be performed                                                                   |
| liquibase.structure    | DatabaseObject          | Base interface for all types of objects which can be snapshotted                                                                |

## Next Steps

Ready to contribute your changes? [Send us a pull request!](../get-started/create-pr.md)

Looking to write an extension? [Learn how](../../extensions-integrations/extensions-overview/index.md)