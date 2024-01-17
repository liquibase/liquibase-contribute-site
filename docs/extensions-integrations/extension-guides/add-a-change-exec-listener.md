---
title: Add a ChangeExecListener
---

# Implementing a Custom ChangeExecListener Class with Liquibase

[ChangeExecListener](https://javadocs.liquibase.com/liquibase-core/liquibase/changelog/visitor/ChangeExecListener.html) is a Java listener interface Liquibase calls when you use a `rollback` or `update` command to modify your database.

When Liquibase calls `ChangeExecListener` with one of these commands, it also runs several corresponding methods the class implements. For example, when you use `rollback` to revert a change in your database, Liquibase calls the `rolledBack` method.

You can use the default implementation of `ChangeExecListener` in Liquibase or write your own Java class to override the default.

## Commands

*   [rollback](https://docs.liquibase.com/commands/rollback/rollback-by-tag.html)
*   [rollback-count](https://docs.liquibase.com/commands/rollback/rollback-count.htm)
*   [rollback-count-sql](https://docs.liquibase.com/commands/rollback/rollback-count-sql.htm)
*   [rollback-sql](https://docs.liquibase.com/commands/rollback/rollback-sql.htm)
*   [rollback-to-date](https://docs.liquibase.com/commands/rollback/rollback-to-date.htm)
*   [rollback-to-date-sql](https://docs.liquibase.com/commands/rollback/rollback-to-date-sql.htm)
*   [update](https://docs.liquibase.com/commands/update/update.html)
*   [update-count](https://docs.liquibase.com/commands/update/update-count.htm)
*   [update-count-sql](https://docs.liquibase.com/commands/update/update-count-sql.html)
*   [update-sql](https://docs.liquibase.com/commands/update/update-sql.htm)
*   [update-testing-rollback](https://docs.liquibase.com/commands/update/update-testing-rollback.htm)
*   [update-to-tag](https://docs.liquibase.com/commands/update/update-to-tag.html)
*   [update-to-tag-sql](https://docs.liquibase.com/commands/update/update-to-tag-sql.html)

## Uses

You can use a custom `ChangeExecListener` class to:

*   Provide richer logging to external tools you use with Liquibase.
*   Verify what Liquibase runs or rolls back based on your custom [business logic](https://www.investopedia.com/terms/b/businesslogic.asp).
*   Improve the user interface "status reports" of custom Liquibase integrations.
*   Make audits of your database or notify you when Liquibase makes an important change.
*   Record database statistics like the duration of commands you run, which SQL statements you used, and which rows of a table your change affected.
*   Write [Preconditions](https://docs.liquibase.com/concepts/changelogs/preconditions.html) to an output file as they are executed.

## Creating a custom `ChangeExecListener` class

1.  Create a Java class that extends [AbstractChangeExecListener](https://javadocs.liquibase.com/liquibase-core/liquibase/changelog/visitor/ChangeExecListener.html). This is an [abstract class](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html) that extends `ChangeExecListener`, so they have the same methods. However, because it's abstract, you only have to override the methods you care about. By contrast, if you extend `ChangeExecListener` directly, you must write definitions for every method, even if you don't want to change their functionality.
2.  After you write your class, [compile it into a JAR file](https://docs.oracle.com/javase/tutorial/deployment/jar/build.html) in your command line:
    
    ```
    jar cf CustomChangeExecListener.jar CustomChangeExecListener.class
    ```

3.  Move your JAR file to the `liquibase/lib` directory, or add it to the classpath.
4.  Call on your `CustomChangeExecListener` class in Liquibase by using the `change-exec-listener-class` parameter.

##Setting the `--change-exec-listener-class` and `--change-exec-listener-properties-file` parameters

You can configure your ChangeExecListener by using the following command parameters of `rollback` and `update` commands:

*   `--change-exec-listener-class` is a string that determines what class Liquibase uses for `ChangeExecListener`.
*   `--change-exec-listener-properties-file` is a string that determines the path to the properties file Liquibase uses for `ChangeExecListener`.

For more information, see [ChangeExecListenerCommandStep](https://javadocs.liquibase.com/liquibase-core/liquibase/command/core/helpers/ChangeExecListenerCommandStep.html).

### Liquibase properties file

```
liquibase.command.changeExecListenerClass: YOUR_CLASS
liquibase.command.changeExecListenerPropertiesFile: YOUR_FILE
```

### CLI command parameters

```
liquibase update --change-exec-listener-class=YOUR_CLASS --change-exec-listener-properties-file=YOUR_FILE --changelog-file=dbchangelog.xml
```

### Java system property

#### Mac/Linux syntax:

```
JAVA_OPTS=-Dliquibase.command.changeExecListenerClass=YOUR_CLASS -Dliquibase.command.changeExecListenerPropertiesFile=YOUR_FILE && liquibase update --changelog-file=dbchangelog.xml
```

#### Windows syntax:

```
set JAVA_OPTS=-Dliquibase.command.changeExecListenerClass=YOUR_CLASS -Dliquibase.command>changeExecListenerPropertiesFile=YOUR_FILE && liquibase update --changelog-file=dbchangelog.xml
```

!!! note
    To use a Liquibase command alongside the [JAVA\_OPTS Environment Variable](https://docs.liquibase.com/concepts/connections/java-opts-environment-variable.htm), add `&& liquibase <command>` to the end of your input.

### Environment variable

#### Mac/Linux syntax:

```
LIQUIBASE_COMMAND_CHANGE_EXEC_LISTENER_CLASS=YOUR_CLASS
LIQUIBASE_COMMAND_CHANGE_EXEC_LISTENER_PROPERTIES_FILE=YOUR_FILE
```

#### Windows syntax:

```
set LIQUIBASE_COMMAND_CHANGE_EXEC_LISTENER_CLASS=YOUR_CLASS
set LIQUIBASE_COMMAND_CHANGE_EXEC_LISTENER_PROPERTIES_FILE=YOUR_FILE
```

!!! note
    These commands only apply to the current shell. To set permanent environment variables, see [Liquibase Environment Variables](https://docs.liquibase.com/concepts/connections/liquibase-environment-variables.htm).

##Related links

*   [Oracle: Introduction to Event Listeners](https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html)