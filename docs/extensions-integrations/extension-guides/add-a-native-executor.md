# Add a Native Executor

--8<-- "extension-setup.md"

## Overview

When adding support for a new `runWith` option or replacing the default, the interface you are going to implement is [liquibase.executor.Executor](https://javadocs.liquibase.com/liquibase-core/liquibase/executor/Executor.html){:target="_blank"}.

!!! tip

    There is an [liquibase.executor.AbstractExecutor](https://javadocs.liquibase.com/liquibase-core/liquibase/executor/AbstractExecutor.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement. 

    If you are wrapping custom logic around a JDBC connection, you can subclass `liquibase.executor.jvm.JdbcExecutor` to further limit what you need to implement.

## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.executor.Executor API page](../../code/api/executor-executor.md).


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