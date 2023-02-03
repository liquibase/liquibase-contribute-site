# Create a SnapshotGenerator

## Overview

When defining the snapshot logic for a particular `DatabaseObject`, the interface you are going to implement is [liquibase.snapshot.SnapshotGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/snapshot/SnapshotGenerator.html){:target="_blank"}.

!!! tip

    There is a [liquibase.snapshot.jvm.JdbcSnapshotGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/snapshot/jvm/JdbcSnapshotGenerator.html){:target="_blank"} convenience base class that is used for all the standard `DatabaseObject` SnapshotGenerators which
    separates the "create the base object" logic from the "add to an object" logic plus has caching built into it. 

!!! tip

    The standard SnapshotGenerators tend to have overridable functions for commonly variable portions of the snapshot logic, so you generally shouldn't override
    `snapshot()` itself but more targeted methods instead.


!!! tip

    Specifying database-specific functionality is best done with `if (database instanceof ExampleDatabase)` blocks around the database-specific logic. 

    Using "instanceof" rather than "equals" allows the logic to apply to any subclasses (variants) of the given database.

## Implementing

### Empty Constructor

Like all Liquibase extensions, your SqlGenerator must have an empty constructor.

### getPriority and replaces

The `getPriority` works a bit different from other extensions in that it's not used to choose the single "best" implementation to use, but rather to choose the order to run SnapshotGenerators in.

Like other getPriority methods, if your implementation does not apply to the given type/database combination, return `liquibase.snapshot.PRIORITY_NONE`.

However, Liquibase will use ALL instances that return a priority > 0 to snapshot an object.
Therefore, if your extension is replacing a base SnapshotGenerator you override the `replaces()` function rather than returning a higher value from `getPriority()`.
The `replaces()` function lets you say "use this class in the snapshot logic instead of another one".

A good example implementation for a class that replaces the default ColumnSnapshotGenerator is

```java
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof ExampleDatabase) {
            return super.getPriority(objectType, database);
        } else {
            return PRIORITY_NONE;
        }
    }

    @Override
    public Class<? extends SnapshotGenerator>[] replaces() {
        return new Class[] {
                ColumnSnapshotGenerator.class
        };
    }
```

which relies on the superclass's "should I be part of the snapshot process for the given type" logic but only for ExampleDatabase while also taking the place of ColumnSnapshotGenerator when it's used.

### snapshot()

This is the method that is called by the snapshot system to either look up the information about the given `example` object, OR add additional information to it.
Multiple SnapshotGenerator instances will work together to build up the final snapshot of the object.

The overall flow is:

_1. The calling code defines the "example" object it wants._

!!! example

    If they want to snapshot a Table with the name "address" they construct a `liquibase.structure.core.Table` object and set the name to "address". 
    To look up the table in a different schema, they set the schema attribute on Table to the desired schema.
    The "example" object will contain the fields set that should be used to identify the actual object to snapshot. 

_2. The snapshot system will collect all the SnapshotGenerators that say they should participate in the snapshot and call the `snapshot` method in `getPriority()` order._

!!! example

    When snapshotting an example Table, the `TableSnapshotGenerator.snapshot()` call will run first which captures the table name, schema, etc.  
    Then the ColumnSnapshotGenerator.snapshot() will run which snapshots all the columns associated with the table and adds those columns to the Table object.

_3. After all the SnapshotGenerators have added their information to the Table object, it's returned to the calling code._


## Register your Class

Like all extensions, your SqlGenerator must be registered by adding your class name to `META-INF/services/liquibase.snapshot.SnapshotGenerator`

## Example Code

```java
package com.example.snapshot;

import com.example.database.ExampleDatabase;
import liquibase.database.Database;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.jvm.ColumnSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;

public class SnapshotColumnGeneratorExample extends ColumnSnapshotGenerator {

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof ExampleDatabase) {
            return super.getPriority(objectType, database);
        } else {
            return PRIORITY_NONE;
        }
    }

    @Override
    public Class<? extends SnapshotGenerator>[] replaces() {
        return new Class[] {
                ColumnSnapshotGenerator.class
        };
    }

    @Override
    protected Object readDefaultValue(CachedRow columnMetadataResultSet, Column columnInfo, Database database) {
        if (columnInfo.getType().getTypeName().equalsIgnoreCase("json")) {
            return columnMetadataResultSet.get("default_value_json");
        } else {
            return super.readDefaultValue(columnMetadataResultSet, columnInfo, database);
        }
    }
}
```