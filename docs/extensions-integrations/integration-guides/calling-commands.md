# Calling Commands

## Overview

The [liquibase.command.CommandScope](../../code/api/command-commandscope.md) class is the primary high-level facade for running Liquibase operations. 
The API provides both a way to call individual commands and metadata about each command including human-readable descriptions and argument information.

The operations available through the command API are end-user facing, complete calls such as "update", "rollback", or "generateChangelog".

# How to use Command Framework

Main components from Command frameworks you need to know before using it are:

### - Command Name:

This is just a constant where we will define the known names for a given command. 

```java
public static String[] COMMAND_NAME = {"update"};
```

Similarly, for certain commands there could be a legacy command name which can be defined in the same way and be passed to the `CommandBuilder` constructor.

### - Arguments:
There are required and optional arguments used by a command that are defined in the below way. Either in the code or [documentation](https://docs.liquibase.com) you can see
what is each command for and whether it's required or not.
```java
static {

        public static final String[] LEGACY_COMMAND_NAME = {"migrate"};
        public static String[] COMMAND_NAME = {"update"};
        
        public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
        
        public static final CommandArgumentDefinition<DatabaseChangeLog> CHANGELOG_ARG;
        public static final CommandArgumentDefinition<String> LABEL_FILTER_ARG;
        public static final CommandArgumentDefinition<String> CONTEXTS_ARG;
        
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME, LEGACY_COMMAND_NAME);
        CHANGELOG_ARG = builder.argument("databaseChangelog", DatabaseChangeLog.class).hidden().build();
        CHANGELOG_FILE_ARG = builder.argument(CommonArgumentNames.CHANGELOG_FILE, String.class)
                .required().description("The root changelog").supersededBy(CHANGELOG_ARG).build();
        CHANGELOG_ARG.setSupersededBy(CHANGELOG_FILE_ARG);
        LABEL_FILTER_ARG = builder.argument("labelFilter", String.class)
                .addAlias("labels")
                .description("Changeset labels to match")
                .build();
        CONTEXTS_ARG = builder.argument("contextFilter", String.class)
                .addAlias("contexts")
                .description("Changeset contexts to match")
                .build();
    }
```

Additionally, we can also specify some other generic argument, for example a `outputStream` were command results will be written into. Below an example of it:

```java
CommandScope updateCommand = new CommandScope(UpdateSqlCommandStep.COMMAND_NAME);
        ...
updateCommand.setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
updateCommand.execute();
```

### - Dependencies:
Dependencies are not useful for calling a command, but they are useful when defining a new command. These specified dependencies are basically part of the different stages of the pipeline execution for a given command.
```java
@Override
    public List<Class<?>> requiredDependencies() {
        List<Class<?>> deps = Arrays.asList(Database.class, DatabaseChangeLog.class, 
        ChangeExecListener.class, ChangeLogParameters.class, UpdateSummaryEnum.class);
        return deps;
    }
```
What we need to understand here is the above dependencies classes will execute their own `run()` method which is needed to accomplish with the `run()` of the given command, for example update command.
### - Execution:
Each command step class will need to implement their own `run()` method. Basically, this method will have the main business logic of a given command/component.
```java
@Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        setDBLock(false);
        super.run(resultsBuilder);
    }
```
This method will be called by invoking the CommandScope `execute()` method as shown below:
```java
CommandScope updateCommand = new CommandScope("update");
        updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changeLogFile);
        ...
        updateCommand.execute();
```

# Command Execution examples

### Regular use execution example

```java
package com.example;

import liquibase.command.CommandScope;
import liquibase.exception.CommandExecutionException;

public class ExampleIntegration  {

    public static void main(String[] args) throws Exception {
        // ... setup configuration system and file access

        try {
            CommandScope updateCommand = new CommandScope("update");
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changeLogFile);
            updateCommand.addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, contexts);
            updateCommand.addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, labelFilter);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_CLASS_ARG, changeExecListenerClass);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG, changeExecListenerPropertiesFile);
            setDatabaseArgumentsToCommand(updateCommand);
            updateCommand.execute();
        } catch (CommandExecutionException e) {
            System.out.println("Error running update: "+e.getMessage());
        }
    }
}
```

### Execution in scope example
A scope refers to an environment where some given set of operations happen. For example, in the context of CLI a Scope refers to the environment where these set of operations will be executed.
Following with the example of a terminal when we open one it will create a main or parent scope, but we can also create a child scope which will execute some other operations but with the benefit that variables or objects from the parent scope can be accessible. As an example, it could happen the way of accessing files is defined in the parent scope and to make it available to other commands or instructions we can pass it to child scope as shown below:

```java
  Map<String, Object> scopedSettings = new LinkedHashMap<>();
  scopedSettings.put(Scope.Attr.resourceAccessor.name(), resourceAccessor);
  Scope.child(scopeSettings, () {
    CommandScope commandScope = new CommandScope(UpdateCommandStep.COMMAND_NAME);
    commandScope.addArgumentValue(DbUrlConnectionCommandStep.URL_ARG, db.getConnectionUrl());
    commandScope.addArgumentValue(DbUrlConnectionCommandStep.USERNAME_ARG, db.getUsername());
    commandScope.addArgumentValue(DbUrlConnectionCommandStep.PASSWORD_ARG, db.getPassword());
    commandScope.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changelogFile);
    commandScope.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, UpdateSummaryEnum.SUMMARY);
    commandScope.execute();
  }); 
```

## API Documentation

For a complete description of the CommandScope API, including methods to call, see [liquibase.command.CommandScope](../../code/api/command-commandscope.md).
