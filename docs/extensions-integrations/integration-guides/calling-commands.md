# Calling Commands

## Overview

The [liquibase.command.CommandScope](../../code/api/command-commandscope.md) class is the primary high-level facade for running Liquibase operations. 
The API provides both a way to call individual commands and metadata about each command including human-readable descriptions and argument information.

The operations available through the command API are end-user facing, complete calls such as "update", "rollback", or "generateChangelog".

# How to use Command Framework

Main components from Command frameworks you need to know before using it are:

### - Command Name:

This just a constant where we will define the known names for a given command. 

```java
public static String[] COMMAND_NAME = {"update"};
```

Similarly, for certain commands there could be a legacy command name which can be defined in the same way and be passed to the `CommandBuilder` constructor.

### - Arguments:
There required and optional arguments used by a command that are defined in the below way. Either in the code or [documentation](https://docs.liquibase.com) you can see
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
What we need to understand here is the above dependencies classes will execute their own `run()` method which is needed to accomplish with the `run()` of the given command, for example example update command.
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

```java
package com.example;

import liquibase.command.CommandScope;
import liquibase.exception.CommandExecutionException;

public class ExampleIntegration  {

    public static void main(String[] args) throws Exception {
        // ... setup configuration system and file access

        try {
            runInScope(() -> {
                CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
                updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, getDatabase());
                updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, databaseChangeLog);
                updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changeLogFile);
                updateCommand.addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null);
                updateCommand.addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null);
                updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, changeExecListener);
                updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, showSummaryOutput);
                updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, changeLogParameters);
                updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, showSummary);
                updateCommand.execute();
            });
        } catch (CommandExecutionException e) {
            System.out.println("Error running update: "+e.getMessage());
        }
    }
}
```
Or in cases where for example we need to want to pass define an specific how to access files we can pass a resource accessor as shown in the below example:

```groovy
 def scopeSettings = [
                (Scope.Attr.resourceAccessor.name()): resourceAccessor
        ]
        Scope.child(scopeSettings, {
            CommandScope commandScope = new CommandScope(UpdateCommandStep.COMMAND_NAME)
            commandScope.addArgumentValue(DbUrlConnectionCommandStep.URL_ARG, db.getConnectionUrl())
            commandScope.addArgumentValue(DbUrlConnectionCommandStep.USERNAME_ARG, db.getUsername())
            commandScope.addArgumentValue(DbUrlConnectionCommandStep.PASSWORD_ARG, db.getPassword())
            commandScope.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changelogFile)
            commandScope.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, UpdateSummaryEnum.SUMMARY)
            commandScope.execute()
        } as Scope.ScopedRunnerWithReturn<Void>)
```

## API Documentation

For a complete description of the CommandScope API, including methods to call, see [liquibase.command.CommandScope](../../code/api/command-commandscope.md).
