# Add a Changelog Format

--8<-- "extension-setup.md"

## Overview

Liquibase ships with XML, YAML, JSON, and SQL parsers, but there is nothing special about those formats in the Liquibase internals.

Examples of custom parsers include:

- A parser for a custom Liquibase DSL
- A parser to read database changes stored in a 3rd party system

When adding support for a new changelog parser, the interface you are going to implement is [liquibase.parser.ChangeLogParser](https://javadocs.liquibase.com/liquibase-core/liquibase/parser/ChangeLogParser.html){:target="_blank"}.

If you would like to support generating changelogs in your custom format with `diffChangelog` or `generateChangelog` operations, you need to implement a [Changelog Serializer](../../code/api/parser-changelogparser.md).

## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.parser.ChangeLogParser API page](../../code/api/parser-changelogparser.md).

## Example Parser Code

```java
package com.example;

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


```

## Example Serializer Code

```java
package com.example;

import liquibase.GlobalConfiguration;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.LiquibaseSerializable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExampleChangeLogSerializer implements ChangeLogSerializer {

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{".my"};
    }


    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }


    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
        String outputEncoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();

        for (T child : children) {
            out.write(serialize(child, true).getBytes(outputEncoding));
        }
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        if (object instanceof ChangeSet) {
            return "my formatted changeset"; //TODO: format changeset
        } else {
            return "my formatted " + object.getClass().getName(); //TODO: format other object types
        }
    }

    @Override
    public void append(ChangeSet changeSet, File changeLogFile) throws IOException {
        String serialized = serialize(changeSet, true);

        //TODO open changelogFile as a stream and append "serialized" 
    }

}


```