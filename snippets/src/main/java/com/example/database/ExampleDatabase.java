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