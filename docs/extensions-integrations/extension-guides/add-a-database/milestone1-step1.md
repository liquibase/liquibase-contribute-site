---
title: "Milestone 1: New Database"
---

# Defining a New Database

## Overview

When adding support for a new database, the first class to create is a new [liquibase.database.Database](../../../code/api/database-database.md) implementation.
Your Database implementation acts as the "dialect" definition and the facade to your database.

The Database interface defines methods for:

- Reading metadata about the database (`getDatabaseMajorVersion()`, `getDefaultPort()`, `getDefaultSchema()`, etc.)
- Checking capabilities of the database (`isCaseSensitive()`, `supportsInitiallyDeferrableColumns`, `supportsSchemas()`, etc.)
- Performing common logic (`escapeObjectName()`, `commit()`, `rollback()`, etc.)

If your database generally attempts to be compatible with another database, your new Database implementation can extend the existing Database class.
For example, if your database is PostgreSQL-compatible you can extend `liquibase.database.core.PostgresDatabase` and only override what is special about your database.

If your database is unique, you will likely want to extend from [liquibase.database.AbstractJdbcDatabase](https://javadocs.liquibase.com/liquibase-core/liquibase/database/AbstractJdbcDatabase.html){:target="_blank"}
which provides default logic that follows SQL standards.

Depending on your base class you will have more or less abstract methods which must be implemented.

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