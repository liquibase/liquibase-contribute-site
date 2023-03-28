# Add a SnapshotGenerator

--8<-- "extension-setup.md"

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