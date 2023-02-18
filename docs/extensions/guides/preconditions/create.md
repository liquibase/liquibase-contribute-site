# Create a Precondition

## Overview

When adding support for a new precondition, the interface you are going to implement is [liquibase.precondition.Precondition](https://javadocs.liquibase.com/liquibase-core/liquibase/precondition/Precondition.html){:target="_blank"}.

Precondition implementations do not need to be thread safe. Liquibase will generate a new instance of them each time they are used.

!!! tip

    There is a [liquibase.precondition.AbstractPrecondition](https://javadocs.liquibase.com/liquibase-core/liquibase/precondition/AbstractPrecondition.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement. 

## Implementing

### Empty Constructor

Like most Liquibase extensions, yourprecondition must have an empty constructor.

### check()

This function contains your logic to determine if the precondition passes or fails. 

If the precondition fails, throw a `PreconditionFailedException` describing the problem.

If you cannot determine whether the precondition passed or failed, throw a `PreconditionErrorException` describing the problem.

### validate() and warn()

Override these methods to check whether your precondition can be run or not given its configuration and environment.  

### Define Configuration Attributes

If your precondition requires custom attributes to be set (tableName, etc.), create get/set methods for them. 

Any public get/set pairs will be exposed to the end user as attributes on the precondition.

## Register your Class

Like all extensions, your executor must be registered by adding your class name to `META-INF/services/liquibase.precondition.Precondition`

## Example Code

```java
package com.example;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.*;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.precondition.AbstractPrecondition;
import liquibase.precondition.ErrorPrecondition;
import liquibase.statement.core.RawSqlStatement;

import java.util.Arrays;

public class PasswordIsSetPrecondition extends AbstractPrecondition {

    private String username;
    @Override
    public String getName() {
        return "passwordIsSet";
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors()
                .checkRequiredField("username", getUsername());
    }

    @Override
    public String getSerializedObjectNamespace() {
        return GENERIC_CHANGELOG_EXTENSION_NAMESPACE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            Executor executor = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
            String password = executor.queryForObject(new RawSqlStatement("select password from app_users where username='"+database.escapeStringForDatabase(username)+"'"), String.class);

            if (password == null) {
                throw new PreconditionFailedException("Password not set for "+username, changeLog, this);
            }
        } catch (DatabaseException e) {
            throw new PreconditionErrorException(e.getMessage(), Arrays.asList(new ErrorPrecondition(e, changeLog, this)));
        }
    }


}
```