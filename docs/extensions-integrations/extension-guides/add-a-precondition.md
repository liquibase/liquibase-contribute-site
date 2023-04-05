# Add a Precondition

--8<-- "extension-setup.md"

## Overview

Liquibase ships with a large number of standard preconditions such as:

- tableExists
- columnExists
- sqlCheck
- etc.

but extensions can provide **_any_** functionality desired.

When adding support for a new precondition, the interface you are going to implement is [liquibase.precondition.Precondition](../../code/api/precondition-precondition.md).

Precondition implementations do not need to be thread-safe. Liquibase will generate a new instance of them each time they are used.

!!! tip

    There is a [liquibase.precondition.AbstractPrecondition](https://javadocs.liquibase.com/liquibase-core/liquibase/precondition/AbstractPrecondition.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement. 

## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.precondition.Precondition API page](../../code/api/precondition-precondition.md).

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