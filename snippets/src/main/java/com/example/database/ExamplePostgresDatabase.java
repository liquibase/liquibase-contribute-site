package com.example.database;

import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.DatabaseException;

public class ExamplePostgresDatabase extends PostgresDatabase {

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