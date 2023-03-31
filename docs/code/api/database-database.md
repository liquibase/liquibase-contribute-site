---
title: database.Database
---

# liquibase.database.Database

## Overview

The `liquibase.database.Database` interface acts as the "dialect" definition and facade for interacting with the database. It defines methods for:

- Reading metadata about the database (`getDatabaseMajorVersion()`, `getDefaultPort()`, `getDefaultSchema()`, etc.)
- Checking capabilities of the database (`isCaseSensitive()`, `supportsInitiallyDeferrableColumns`, `supportsSchemas()`, etc.)
- Performing common logic (`escapeObjectName()`, `commit()`, `rollback()`, etc.)

Each supported database within Liquibase will have its own Database implementation, such as `liquibase.database.core.MySQLDatabase`.

Databases that are generally compatible with another database should have their Database implementation extend the other Database class. 
For example, Reshift is PostgreSQL compatible and so the `RedshiftDatabase` class extends `PostgresDatabase`. 

Any code that has to behave differently for different database types, will use a `if (database instanceof PostgresDatabase)` pattern to determine the "dialect". 
By having compatible databases such as Redshift extend the base class like `PostgresqlDatabase`, all the existing "instanceof PostgresDatabase" checks will continue to work
and in any cases where Redshift **_does_** work differently, a new `if (database instanceof RedshiftDatabase)` check can be added.

## Database Selection

Liquibase uses the [isCorrectDatabaseImplementation](#iscorrectdatabaseimplementation) method to determine which Database implementation are valid for a given connection.

Generally, the logic in that method will check `getDatabaseProductName()`, but depending on the database other connection metadata may need to be inspected as well.

For all the Database instances that are valid, Liquibase will use the one with the highest [priority](../architecture/service-discovery.md) value. 

## API Highlights

### Auto-Registration

Changes are [dynamically discovered](../architecture/service-discovery.md), so must have a no-arg constructor and be registered in `META-INF/services/liquibase.database.Database`.

### getPriority()

Used in [selecting the instance to use](#database-selection).

### getShortName()

The "short name" for the database is the unique, all-lowercase alphanumeric identifier for the database. For example `oracle` or `mysql`.
This is the key used in the `dbms` tag among other places.

### isCorrectDatabaseImplementation()

Used in [selecting the instance to use](#database-selection) for a given connection.

This function can check whatever information best identifies the database from the connection. For example, you can check `getDatabaseProductName()` on the `DatabaseConnection`.

!!! tip

    Some "compatible" databases may identify themselves as the "standard" database in `getDatabaseProductName()`. 
    In those cases, it may need to also check `DatabaseConnection.getDatabaseProductVersion()`, `getURL()`, or whatever other calls can most efficiently identify the database.

### getDefaultDriver

Return the class name of the default driver for the given URL string. Specifying your driver's class here allows users to not have to use the `driver` setting whenever they connect to your database.

### Other Dialect Settings

There are a few dialect settings that do not have a default implementation and therefore must be defined:

- **getDefaultPort()** Default port used to connect to the database
- **supportsInitiallyDeferrableColumns()** Does your database support initially deferrable constraints?
- **supportsTablespaces()** Does your database support tablespaces?

For other functions where your database differs than assumptions the base class makes, override the corresponding methods.

## API Details

The complete javadocs for `liquibase.database.Database` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/database/Database.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Database](../../extensions-integrations/extension-guides/add-a-database/index.md)