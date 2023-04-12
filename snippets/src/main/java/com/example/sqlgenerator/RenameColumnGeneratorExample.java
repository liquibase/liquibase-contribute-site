package com.example.sqlgenerator;

import com.example.database.ExampleDatabase;
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