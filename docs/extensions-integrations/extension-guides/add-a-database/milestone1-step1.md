---
title: "Milestone 1: New Database"
---

# Defining a New Database

## Overview

When adding support for a new database, the first class to create is a new [liquibase.database.Database](../../../code/api/database-database.md) implementation. This class acts as the "dialect" definition and the facade to your database.

The Database interface defines methods for:

- Reading metadata about the database (`getDatabaseMajorVersion()`, `getDefaultPort()`, `getDefaultSchema()`, etc.)
- Checking capabilities of the database (`isCaseSensitive()`, `supportsInitiallyDeferrableColumns`, `supportsSchemas()`, etc.)
- Performing common logic (`escapeObjectName()`, `commit()`, `rollback()`, etc.)

## Subclassing an existing Database Class

If your database generally attempts to be compatible with another database, your new Database implementation can extend the existing Database class.

For example, many databases strive for PostgreSQL compatibility. Those databases can extend [`liquibase.database.core.PostgresDatabase`](https://javadocs.liquibase.com/liquibase-core/liquibase/database/core/PostgresDatabase.html){:target="_blank"} 
and only override what is special about the new database.

!!! note
    To correctly differentiate your database from the "standard" version, you must always implement:

    - getPriority()
    - getShortName()
    - getDatabaseProductName()

NATHAN - please give guidance on what values to use for the above 3 methods.

### Example Code

```java
--8<-- "src/main/java/com/example/database/ExamplePostgresDatabase.java"
```

## Extending AbstractJdbcDatabase

If your database is more unique, you will likely want to extend from [liquibase.database.AbstractJdbcDatabase](https://javadocs.liquibase.com/liquibase-core/liquibase/database/AbstractJdbcDatabase.html){:target="_blank"}
which provides default logic that follows SQL standards.

Depending on your base class you will have more or less abstract methods which must be implemented.

NATHAN - could you clarify the above sentence? The statement "you will have more or less abstract methods" is unclear.

### Example Code

```java
--8<-- "src/main/java/com/example/database/ExampleDatabase.java"
```

## API Documentation

A complete description of the API, including what methods must be implemented and how, is available on the [liquibase.database.Database API page](../../../code/api/database-database.md).

## Next Step

After you have created your Database class, it's time to [test it out](milestone1-step2.md)