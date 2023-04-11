package com.example.parser;


import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.ChangeLogParser;
import liquibase.resource.ResourceAccessor;

import java.io.InputStream;

public class ExampleParser implements ChangeLogParser {

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }

    @Override
    public boolean supports(String changeLogFile, ResourceAccessor resourceAccessor) {
        return changeLogFile.toLowerCase().endsWith(".my");
    }

    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        try {
            DatabaseChangeLog changelog = new DatabaseChangeLog(physicalChangeLogLocation);
            try (InputStream inputStream = resourceAccessor.getExisting(physicalChangeLogLocation).openInputStream()) {
                //TODO: read the stream, update the changelog
            }

            return changelog;
        } catch (Exception e) {
            throw new ChangeLogParseException(e.getMessage(), e);
        }
    }
}