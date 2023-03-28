# Add a SqlGenerator

--8<-- "extension-setup.md"

## Overview

When defining the execution logic for a particular `SqlGenerator`, the interface you are going to implement is [liquibase.sqlgenerator.SqlGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/sqlgenerator/SqlGenerator.html){:target="_blank"}.

!!! tip

    There is a [liquibase.sqlgenerator.core.AbstractSqlGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/sqlgenerator/core/AbstractSqlGenerator.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement.

!!! tip

    When adding a SqlGenerator that provides database-specific logic, it's often best (but not required) to subclass the default SqlGenerator for that SqlStatement
    and override and change only the specific parts that need to be different while preserving the rest of the logic. 


!!! tip

    Specifying database-specific functionality is best done with `if (database instanceof ExampleDatabase)` blocks around the database-specific logic. 

    Using "instanceof" rather than "equals" allows the logic to apply to any subclasses (variants) of the given database.

## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.sqlgenerator.SqlGenerator API page](../../code/api/sqlgenerator-sqlgenerator.md).

## Example Code

```java
package com.example;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.RenameColumnStatement;
import liquibase.structure.core.Column;

public class RenameColumnGeneratorExample extends AbstractSqlGenerator<RenameColumnStatement> {
    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean supports(RenameColumnStatement statement, Database database) {
        return database instanceof ExampleDatabase;
    }

    @Override
    public ValidationErrors validate(RenameColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors = new ValidationErrors();
        errors.checkRequiredField("tableName", statement.getTableName());
        errors.checkRequiredField("oldColumnName", statement.getOldColumnName());
        errors.checkRequiredField("newColumnName", statement.getNewColumnName());

        return errors;
    }

    @Override
    public Sql[] generateSql(RenameColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[]{
                new UnparsedSql("alter table " +
                        database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) +
                        " rename column " +
                        database.escapeObjectName(statement.getOldColumnName(), Column.class) +
                        " to " +
                        database.escapeObjectName(statement.getNewColumnName(), Column.class))
        };
    }
}
```