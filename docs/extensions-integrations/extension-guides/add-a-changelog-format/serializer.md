# Create a Changelog Serializer

## Overview

While the [Changelog Parser](create.md) is used when parsing a changelog, commands like `generateChangelog` and `diffChangelog` need to **_create_** a changelog file.
Support for writing a changelog uses the [liquibase.serializer.ChangeLogSerializer](https://javadocs.liquibase.com/liquibase-core/liquibase/serializer/ChangeLogSerializer.html){:target="_blank"} interface.

## Implementing

### Empty Constructor

Like most Liquibase extensions, yourserializer must have an empty constructor.

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

## Register your Class

Like all extensions, your executor must be registered by adding your class name to `META-INF/services/liquibase.serializer.ChangeLogSerializer`

## Example Code

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