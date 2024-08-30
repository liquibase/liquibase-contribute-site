---
title: serializer.ChangeLogSerializer
---

# liquibase.serializer.ChangeLogSerializer

## Overview

While the [Changelog Parser](parser-changelogparser.md) is used when parsing a changelog, commands like `generateChangelog` and `diffChangelog` need to **_create_** a changelog file.
Support for writing a changelog uses the liquibase.serializer.ChangeLogSerializer` interface.

## Parser Selection

Each `ChangeLogSerializer` has a `supports()` method which determines if it can serialize to the given file.

To determine which supported `ChangeLogSerializer` to use, Liquibase will find all the implementations that use the given name and choose the one with the highest [priority](../architecture/service-discovery.md).
This allows extensions to either define a new format OR override an existing serializer.

## API Highlights

### Auto-Registration

Changes are [dynamically discovered](../architecture/service-discovery.md), so must have a no-arg constructor and be registered in the `META-INF/services/liquibase.serializer.ChangeLogSerializer` file.

### getValidFileExtensions()

The method returns the various file extensions that correspond to this changelog format. Liquibase will choose the serializer to use based on the filename of the changelog the user is writing to.

### getPriority()

Used in [selecting the instance to use](#parser-selection)

### write()

This method is used to generate a new changelog file. It's passed a list of objects like ChangeSets, Preconditions, etc. and this method converts them to the changelog format and writes them to the output stream.

This is generally implemented by relying on the [serialize()](#serialize--) method to format object types.

## serialize()

Converts a particular object to the changelog's format. This should be used by the [write()](#write--) function, but it can be called by other parts of Liquibase.

The types of objects passed can include ChangeSets, Preconditions, Changes, and more.

## append()

Called during diffChangeLog to add a changeset to an existing file. 

It is up to this method to open and manage the file stream, and should **_not_** impact the rest of the existing file. 
For example, the XML parser does not read the existing changelog into a DOM and append the new changeset into the DOM before
re-serializing the entire DOM because that will lose any existing formatting the user wants such as spacing between particular elements, groupings of attributes, etc.

## Precondition syntax

In Liquibase 4.29.2 and later, if you're calling on the Liquibase API programmatically using the [YamlChangeLogSerilizer](https://javadocs.liquibase.com/liquibase-core/liquibase/serializer/core/yaml/YamlChangeLogSerializer.html) or [JsonChangeLogSerializer](https://javadocs.liquibase.com/liquibase-core/liquibase/serializer/core/json/JsonChangeLogSerializer.html) classes, YAML and JSON preconditions have slightly different syntax than they do in changelogs:

===+ "YAML/JSON API syntax"

     ```
     preconditions:
           preConditions:
             nestedPreconditions:
             - or:
                 nestedPreconditions:
                 - sqlCheck:
                     expectedResult: '0'
                     sql: select count(*) from team
                 - not:
                     nestedPreconditions:
                     - sqlCheck:
                         expectedResult: '0'
                         sql: select count(*) from referenced_table
     ```

---

=== "YAML/JSON changelog syntax"

     ```
     preConditions:
         - or:
           - sqlCheck:
               expectedResult: 0
               sql: select count(*) from team
           - not:
             - sqlCheck:
                 expectedResult: 0
                 sql: select count(*) from referenced_table
     ```

However, SQL and XML preconditions have the same structure in the API as they do in changelogs.

## API Details

The complete javadocs for `liquibase.serializer.ChangeLogSerializer` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/serializer/ChangeLogSerializer.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Changelog Format](../../extensions-integrations/extension-guides/add-a-changelog-format.md)
