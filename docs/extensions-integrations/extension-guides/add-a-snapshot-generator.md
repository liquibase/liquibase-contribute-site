# Add a SnapshotGenerator

--8<-- "extension-setup.md"

## Overview

When defining the snapshot logic for a particular `DatabaseObject`, the interface you are going to implement is [liquibase.snapshot.SnapshotGenerator](../../code/api/snapshot-snapshotgenerator.md).

## Best Practices

### Base Class

There is a [liquibase.snapshot.jvm.JdbcSnapshotGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/snapshot/jvm/JdbcSnapshotGenerator.html){:target="_blank"} convenience base class that is used for all the standard `DatabaseObject` SnapshotGenerators which
separates the "create the base object" logic from the "add to an object" logic plus has caching built into it. 

### Custom Methods

The standard SnapshotGenerators tend to have overridable functions for commonly variable portions of the snapshot logic, so you generally shouldn't override
`snapshot()` itself but more targeted methods instead.

### Instanceof

Specifying database-specific functionality is best done with `if (database instanceof ExampleDatabase)` blocks around the database-specific logic. 

Using "instanceof" rather than "equals" allows the logic to apply to any subclasses (variants) of the given database.


## Example Code

```java
--8<-- "src/main/java/com/example/snapshot/ColumnSnapshotGeneratorExample.java"
```