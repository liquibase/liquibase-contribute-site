---
layout: default
---

## Defining a New liquibase.database.Database Implementation

### Overview

When adding support for a new database, the first class to create is a new [liquibase.database.Database](https://javadocs.liquibase.com/liquibase-core/liquibase/database/Database.html) implementation.
The Database class implementations act as "dialect" definitions and as the facade for interacting with the database.

The Database interface methods for:
- Reading metadata about the database (`getDatabaseMajorVersion()`, `getDefaultPort()`, `getDefaultSchema()`, etc.)
- Checking capabilities of the database (`isCaseSensitive()`, `supportsInitiallyDeferrableColumns`, `supportsSchemas()`, etc)
- Performing common logic (`escapeObjectName()`, `commit()`, `rollback()`, etc.)

If your database generally attempts to be compatible with another database, your new Database implementation can extend an existing class.
For example, if your database is PostgreSQL-compatible you can extend `liquibase.database.core.PostgresDatabase`

If your database is unique, you will likely want to extend from [liquibase.database.AbstractJdbcDatabase](https://javadocs.liquibase.com/liquibase-core/liquibase/database/AbstractJdbcDatabase.html)
which provides default method logic to follow SQL standards.

### Implementing

Depending on your base class you will have more or less abstract methods which must be implemented.

#### Empty Constructor

Like all Liquibase extensions, your database must have an empty constructor.

#### getPriority()

Generally you should return `liquibase.servicelocator.PrioritizedService.PRIORITY_DEFAULT`, but higher values can be used to replace the Database implementation
that would otherwise be chosen by Liquibase (see isCorrectDatabaseImplementation) below.

#### getShortName()

The "short name" for the database is the unique, all-lowercase alphanumeric identifier for the database. For example `oracle` or `mysql`.
This is the key used in the `dbms` tag among other places.

#### isCorrectDatabaseImplementation(DatabaseConnection)

This is the function used by Liquibase to determine if it is the correct Database class to use for a given connection.

You can check whatever information best identifies the database from the connection. For example, you can check `getDatabaseProductName()` on the DatabaseConnection.
Some "compatible" databases may identify themselves as a different database with `getDatabaseProductName()`, such as MariaDB returning "MySQL" from that call and
you may need to also check the DatabaseConnection's `getDatabaseProductVersion()` or even `getURL()`.

When Liquibase connects to a database, it will use the instance of `Database` which returns true from `isCorrectDatabaseImplementation()` AND has the highest
number returned from `getPriority()`.

#### getDefaultDriver(String)

Return the class name of the default driver for the given URL string. Specifying your driver's class here allows users to not have to use the `driver` setting whenever they connect to your database.

#### Dialect settings

There are a few dialect settings that do not have a default implementation and therefore must be defined:
- getDefaultPort() -- default port used to connect to the database
- supportsInitiallyDeferrableColumns() -- does your database support initially deferrable constraints
- supportsTablespaces() -- does your database support tablespaces

For other functions where your database differs than assumptions your base class makes, override the corresponding methods.

#### Register your Class

Like all extensions, your database must be registered by adding your class name to `META-INF/services/liquibase.database.Database`

### Example Code

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

