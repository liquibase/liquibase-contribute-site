---
title: "Milestone 1: New Database"
---

# Defining a New Database

## Overview

When adding support for a new database, the first class to create is a new [liquibase.database.Database](https://javadocs.liquibase.com/liquibase-core/liquibase/database/Database.html){:target="_blank"} implementation.
Your Database implementation acts as the "dialect" definition and the facade to your database.

The Database interface defines methods for:

- Reading metadata about the database (`getDatabaseMajorVersion()`, `getDefaultPort()`, `getDefaultSchema()`, etc.)
- Checking capabilities of the database (`isCaseSensitive()`, `supportsInitiallyDeferrableColumns`, `supportsSchemas()`, etc.)
- Performing common logic (`escapeObjectName()`, `commit()`, `rollback()`, etc.)

If your database generally attempts to be compatible with another database, your new Database implementation can extend the existing Database class.
For example, if your database is PostgreSQL-compatible you can extend `liquibase.database.core.PostgresDatabase` and only override what is special about your database.

If your database is unique, you will likely want to extend from [liquibase.database.AbstractJdbcDatabase](https://javadocs.liquibase.com/liquibase-core/liquibase/database/AbstractJdbcDatabase.html){:target="_blank"}
which provides default logic that follows SQL standards.

## Implementing

Depending on your base class you will have more or less abstract methods which must be implemented.

### Empty Constructor

Like most Liquibase extensions, yourdatabase must have an empty constructor.

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

## Register your Class

Like all extensions, your database must be registered by adding your class name to `META-INF/services/liquibase.database.Database`

## Example Code

```java
package com.example.database;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;

public class ExampleDatabase extends AbstractJdbcDatabase {

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection databaseConnection) throws DatabaseException {
        return databaseConnection.getDatabaseProductName().equals("ExampleDB");
    }

    @Override
    public String getShortName() {
        return "example";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Example Database";
    }

    @Override
    public String getDefaultDriver(String s) {
        return "com.example.db.Driver";
    }
    
    @Override
    public Integer getDefaultPort() {
        return 55555;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }
}
```

If your database is compatible with an existing database, your class would look more like this:

```java
package com.example.database;

import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.DatabaseException;

public class ExampleDatabase extends PostgresDatabase {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection databaseConnection) throws DatabaseException {
        return databaseConnection.getDatabaseProductName().equals("ExampleDB");
    }

    @Override
    public String getShortName() {
        return "example";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Example Database";
    }
}
```

## Next Step

After you have created your Database class, it's time to [test it out](milestone1-step2.md)