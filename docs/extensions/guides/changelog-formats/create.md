# Create a Changelog Parser

## Overview

When adding support for a new changelog parser, the interface you are going to implement is [liquibase.parser.ChangeLogParser](https://javadocs.liquibase.com/liquibase-core/liquibase/parser/ChangeLogParser.html){:target="_blank"}.

## Implementing

### Empty Constructor

Like all Liquibase extensions, your parser must have an empty constructor.

### supports()

The supports method is passed the name of the file and the `ResourceAccessor` to look the file up in.  

Often the file extension is enough to know whether your parser will support the file or not, so there is no need to read the contents from the resource accessor.
However, the resource accessor is there if the actual contents of the file is needed to determine whether your parser is correct or not.

### getPriority()

Returns the priority of the parser, as described in [the overview](index.md) under "Parser Selection". 
Only parsers that return `true` from the `supports` function will have their priority compared.

### parse()

This is the method that is called to convert the given path to a parsed [liquibase.changelog.DatabaseChangeLog](https://javadocs.liquibase.com/liquibase-core/liquibase/changelog/DatabaseChangeLog.html).

There are two ways to populate the DatabaseChangeLog object once it is created: using ParsedNodes or manually configuring it.

#### Parsed Nodes

To simplify parser implementations that are providing a different way of structuring a standard changelog file, you can use a [liquibase.parser.core.ParsedNode](https://javadocs.liquibase.com/liquibase-core/liquibase/parser/core/ParsedNode.html){:target="_blank"}.
The ParsedNode structure is a format-neutral way to describe nested nodes of a changelog file, where each node can have attributes and/or values.

For example, the XML, YAML, and JSON parser read their respective formats and create a ParsedNode structure out of them, then pass that to the `DatabaseChangeLog.load()` function.
The load() function has all the logic for building ChangeSets, Changes, Preconditions, including files, etc. based on the ParsedNodes. This keeps the functionality and format consistent between all these formats.

#### Manual Configuration

For formats that are not mirrors of the XML/YAML/JSON structure, the `DatabaseChangeLog` object has methods such as

- addChangeSet()
- include()
- includeAll()

which can be called as needed based on what is in your file.

## Register your Class

Like all extensions, your executor must be registered by adding your class name to `META-INF/services/liquibase.parser.ChangeLogParser`

## Next Steps

If you would like to support generating changelogs in your custom format with `diffChangelog` or `generateChangelog` operations, you need to implement a [Changelog Serializer](serializer.md).


## Example Code

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