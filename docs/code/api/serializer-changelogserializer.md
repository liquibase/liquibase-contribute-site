---
title: serializer.ChangeLogSerializer
---

# serializer.ChangeLogSerializer Interface

## Overview

While the [Changelog Parser](parser-changelogparser.md) is used when parsing a changelog, commands like `generateChangelog` and `diffChangelog` need to **_create_** a changelog file.
Support for writing a changelog uses the [liquibase.serializer.ChangeLogSerializer](https://javadocs.liquibase.com/liquibase-core/liquibase/serializer/ChangeLogSerializer.html){:target="_blank"} interface.

## Parser Selection

Each `ChangeLogSerializer` has a `supports()` method which determines if it can serialize to the given file.

To determine which supported `ChangeLogSerializer` to use, Liquibase will find all the implementations that use the given name and choose the one with the highest [priority](../../extensions-integrations/extension-references/priority.md).
This allows extensions to either define a new format OR override an existing serializer.

## API Highlights

### Empty Constructor

Liquibase requires implementations to have an empty constructor.

### getValidFileExtensions()

The method returns the various file extensions that correspond to this changelog format. Liquibase will choose the serializer to use based on the filename of the changelog the user is writing to.

### getPriority()

Returns the [priority](../../extension-references/priority.md) of the serializer. Of all the serializers that support the changelog file extension, Liquibase will choose the one with the highest priority.

### write()

This is the method used to generate a new changelog file. It's passed a list of objects like ChangeSets, Preconditions, etc. and this method converts them to your changelog format and writes them to the output stream.

For each particular object, you should rely on the [serialize()](#serialize--) method to format it.

## serialize()

Converts a particular object to your changelog's format. This should be used by your [write()](#write--) function, but it can be called by other parts of Liquibase.

The types of objects passed can include ChangeSets, Preconditions, Changes, and more.

## append()

Called during diffChangeLog to add a changeset to an existing file. It is up to this method to open and manage the file stream, and you should be sure to not
impact the rest of the existing file. For example, the XML parser does not read the existing changelog into a DOM and append the new changeset into the DOM before
re-serializing the entire DOM because that will lose any existing formatting the user wants such as spacing between particular elements, groupings of attributes, etc.

## Registration

ChangeLogSerializer classes are registered by adding it to `META-INF/services/liquibase.serializer.ChangeLogSerializer`

## API Details

The complete javadocs for `liquibase.serializer.ChangeLogSerializer` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/serializesr/ChangeLogSerializer.html){:target="_blank"}
