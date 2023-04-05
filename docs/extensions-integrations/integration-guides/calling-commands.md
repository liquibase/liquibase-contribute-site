# Calling Commands

## Overview

The [liquibase.command.CommandScope](../../code/api/command-commandscope.md) class is the primary high-level facade for running Liquibase operations. 
The API provides both a way to call individual commands and metadata about each command including human-readable descriptions and argument information.

The operations available through the command API are end-user facing, complete calls such as "update", "rollback", or "generateChangelog".

## API Documentation

For a complete description of the CommandScope API, including methods to call, see [liquibase.command.CommandScope](../../code/api/command-commandscope.md).

## Example Code

```java
package com.example;

import liquibase.command.CommandScope;
import liquibase.exception.CommandExecutionException;

public class ExampleIntegration  {

    public static void main(String[] args) throws Exception {
        // ... setup configuration system and file access

        try {
            new CommandScope("update")
                    .addArgumentValue("changeLogFile", "my/changelog.xml")
                    .execute();
        } catch (CommandExecutionException e) {
            System.out.println("Error running update: "+e.getMessage());
        }

    }
}
```
