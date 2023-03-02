# Create an Executor

## Overview

When adding support for a new `runWith` option or replacing the default, the interface you are going to implement is [liquibase.executor.Executor](https://javadocs.liquibase.com/liquibase-core/liquibase/executor/Executor.html){:target="_blank"}.

!!! tip

    There is an [liquibase.executor.AbstractExecutor](https://javadocs.liquibase.com/liquibase-core/liquibase/executor/AbstractExecutor.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement. 

    If you are wrapping custom logic around a JDBC connection, you can subclass `liquibase.executor.jvm.JdbcExecutor` to further limit what you need to implement.

## Implementing

### Empty Constructor

Like most Liquibase extensions, yourdatabase must have an empty constructor.

### getName()

Returns the "name" of the executor to be used, as described in [the overview](index.md) under "Executor Selection". 

Names should be lowercase with no spaces or special characters.

### supports()

Return true if your executor can run statements against the given database. Only executors that return true from this method will be sorted by priority. 

### getPriority()

Returns the priority of the executor, as described in [the overview](index.md) under "Executor Selection".

### validate()

If your executor has unique logic on what it can and cannot support in SqlStatements, validate them in this function. The default implementation does no validation. 

### updatesDatabase()

If your executor does not actually apply changes to the database return `false`. This is used to determine if the current state of the database can be trusted in preconditions during `update` operations.

### execute()

These methods are where the real logic for running the `SqlStatements` go.
You can rely on `liquibase.sqlgenerator.SqlGeneratorFactory` to generate SQL from the `SqlStatements` if that is something your Executor requires.
Make sure you take the `sqlVistor` list into account.

!!! tip
    The two `execute` methods differ only in the arguments, so you can generally call the version that takes a `List<SqlVisitor>` from the other.  

### comment

Generally your executor doesn't have to care about comments because they don't impact your database. Simply sending the comment to FINE level logs is all you normally need to do.

### query and update methods

**_ALL_** database operations in Liquibase go through an executor, not just changeSets. To support all the needed database interactions, the `Executor` interface includes a set of 
methods that are only called against Executors with the name "jdbc":

- query* methods
- update*

If you are creating an extension purely for use in changeSets with `runWith`, you can `throw new UnsupportedOperationException()` from all these methods. 
 

## Register your Class

Like all extensions, your executor must be registered by adding your class name to `META-INF/services/liquibase.executor.Executor`

## Example Code

```java
package com.example;

import liquibase.Scope;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.executor.AbstractExecutor;
import liquibase.sql.Sql;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import java.util.List;
import java.util.Map;

public class ExampleExecutor extends AbstractExecutor {

    @Override
    public boolean supports(Database database) {
        return true;
    }

    @Override
    public String getName() {
        return "example";
    }

    @Override
    public int getPriority() {
        return PRIORITY_SPECIALIZED;
    }

    
    
    @Override
    public boolean updatesDatabase() {
        return true;
    }

    @Override
    public void comment(String message) throws DatabaseException {
        Scope.getCurrentScope().getLog(getClass()).fine(message);
    }

    @Override
    public void execute(SqlStatement sql) throws DatabaseException {
        this.execute(sql, null);
    }

    @Override
    public void execute(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        Scope.getCurrentScope().getLog(getClass()).info("Executing with the '" + getName() + "' executor");
        Sql[] sqls = SqlGeneratorFactory.getInstance().generateSql(sqlStatement, database);
        try {
            for (Sql sql : sqls) {
                String actualSqlString = sql.toSql();
                for (SqlVisitor visitor : sqlVisitors) {
                    visitor.modifySql(actualSqlString, database);
                }
                Scope.getCurrentScope().getLog(getClass()).info("Generated SQL for change is " + actualSqlString);

                //TODO: SEND `sql` TO YOUR DATABASE
            }
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    /// remaining methods are unused when this is only used in runWith changesets
    @Override
    public int update(SqlStatement sql) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        throw new UnsupportedOperationException();
    }


    @Override
    public <T> T queryForObject(SqlStatement sql, Class<T> requiredType) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T queryForObject(SqlStatement sql, Class<T> requiredType, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long queryForLong(SqlStatement sql) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long queryForLong(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int queryForInt(SqlStatement sql) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int queryForInt(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List queryForList(SqlStatement sql, Class elementType) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List queryForList(SqlStatement sql, Class elementType, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Map<String, ?>> queryForList(SqlStatement sql) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Map<String, ?>> queryForList(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        throw new UnsupportedOperationException();
    }
}

```