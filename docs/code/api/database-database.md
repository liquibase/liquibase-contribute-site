---
title: database.Database
---

# liquibase.database.Database Interface

## Overview

### Empty Constructor

Liquibase requires implementations to have an empty constructor.

### getPriority()

Generally you should return `liquibase.servicelocator.PrioritizedService.PRIORITY_DEFAULT`, but higher values can be used to replace the Database implementation
that would otherwise be chosen by Liquibase (see `isCorrectDatabaseImplementation`) below.

### getShortName()

The "short name" for the database is the unique, all-lowercase alphanumeric identifier for the database. For example `oracle` or `mysql`.
This is the key used in the `dbms` tag among other places.

### isCorrectDatabaseImplementation(DatabaseConnection)

This is the function used by Liquibase to determine if it is the correct Database class to use for a given connection.

You can check whatever information best identifies the database from the connection. For example, you can check `getDatabaseProductName()` on the `DatabaseConnection`.

!!! tip

    Some "compatible" databases may identify themselves as the "standard" database in `getDatabaseProductName()`. 
    In those cases, you may need to also check `DatabaseConnection.getDatabaseProductVersion()`, `getURL()`, or whatever other calls can most efficiently identify your database.

When Liquibase connects to a database, it will use the instance of `Database` which returns true from `isCorrectDatabaseImplementation()` AND has the highest
number returned from `getPriority()`.

### getDefaultDriver(String)

Return the class name of the default driver for the given URL string. Specifying your driver's class here allows users to not have to use the `driver` setting whenever they connect to your database.

### Other Dialect Settings

There are a few dialect settings that do not have a default implementation and therefore must be defined:

- **getDefaultPort()** Default port used to connect to the database
- **supportsInitiallyDeferrableColumns()** Does your database support initially deferrable constraints?
- **supportsTablespaces()** Does your database support tablespaces?

For other functions where your database differs than assumptions the base class makes, override the corresponding methods.

### Registration

Database classes are registered by adding it to  `META-INF/services/liquibase.database.Database`

## API Details

The complete javadocs for `liquibase.database.Database` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/database/Database.html){:target="_blank"}
