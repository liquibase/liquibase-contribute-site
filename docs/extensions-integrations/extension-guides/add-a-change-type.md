# Add a Change Type

--8<-- "extension-setup.md"

## Overview

When adding support for a new change type, the interface you are going to implement is `liquibase.change.Change`. 

Change implementations do not need to be thread-safe.  ChangeFactory will generate a new instance of them each time they are used.

!!! tip

    There is a [liquibase.change.AbstractChange](https://javadocs.liquibase.com/liquibase-core/liquibase/change/AbstractChange.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement. 

## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.change.Change API page](../../code/api/change-change.md).

## Example Code

```java
package com.example.liquibase.change;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.UpdateStatement;

@DatabaseChange(
        name = "setPassword",
        description = "Sets a user's password",
        priority = ChangeMetaData.PRIORITY_DEFAULT
)
public class SetPasswordChange extends AbstractChange {
    private String username;
    private String password;

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{
                new UpdateStatement(null, null, "app_users")
                        .addNewColumnValue("password", password)
                        .setWhereClause("username='" + database.escapeStringForDatabase(username) + "'")
        };
    }

    @Override
    public String getConfirmationMessage() {
        return "Password has been changed for " + username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


```