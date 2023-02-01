# Create a Change Type

## Overview

When adding support for a new change type, the interface you are going to implement is [liquibase.change.Change](https://javadocs.liquibase.com/liquibase-core/liquibase/change/Change.html){:target="_blank"}.

Change implementations do not need to be thread safe.  ChangeFactory will generate a new instance of them each time they are used.

!!! tip

    There is an [liquibase.change.AbstractChange](https://javadocs.liquibase.com/liquibase-core/liquibase/change/AbstractChange.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement. 

## Implementing

### Empty Constructor

Like all Liquibase extensions, your change must have an empty constructor.

### generateStatements()

Returns an array of [liquibase.statement.SqlStatement](https://javadocs.liquibase.com/liquibase-core/liquibase/statement/SqlStatement.html){:target="_blank"}
which describes the steps to perform against the database when this change is run during an `update`.

### getConformationMessage()

Returns the message to output when this change executes successfully. 

### Configure Change Metadata

The AbstractChange base class's `createChangeMetaData()` method will configure your Change's metadata based on a [liquibase.change.DatabaseChange](https://javadocs.liquibase.com/liquibase-core/liquibase/change/DatabaseChange.html) annotation
on the Change class. 

This annotation is required, and requires the following attributes to be set:

- `name` is the name used in the changelog file. Example: `createTable`
- `priority` is used as other [priority](../../references/priority.md) values to control which Change implementation for a given name should be used. If unsure, use `ChangeMetaData.PRIORITY_DEFAULT`
- `description` gives a human-readable description of what the change does

### Define Configuration Attributes

If your Change requires custom attributes to be set (tableName, etc.), create get/set methods for them.

The AbstractChange base class's `createChangeMetaData()` method will find all get/set method pairs and expose them as attributes on the change.

If you wish to control the metadata, annotate your get method with the [liquibase.change.DatabaseChangeProperty](https://javadocs.liquibase.com/liquibase-core/liquibase/change/DatabaseChangeProperty.html) annotation. 
This annotation can be used to control the name, mark a get/set method as not actually a property, and more. 


### Define Rollback Logic

If you know how to roll back your change based on the attributes set on the change, override `createInverse()` or `generateRollbackStatements()` to provide auto-rollback support for your change.

The `createInverse` method lets you specify another Change instance, while `generateRollbackStatements` returns SqlStatement objects to rollback. Only one or the other method needs to be implemented. 

If this method is not overridden, the user must specify the rollback logic themselves in their changelog file.

## Register your Class

Like all extensions, your executor must be registered by adding your class name to `META-INF/services/liquibase.change.Change`

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