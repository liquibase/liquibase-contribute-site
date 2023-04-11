# Add a SqlGenerator

--8<-- "extension-setup.md"

## Overview

When defining the execution logic for a particular `SqlGenerator`, the interface you are going to implement is [liquibase.sqlgenerator.SqlGenerator](../../code/api/sqlgenerator-sqlgenerator.md).

## Best Practices

### Base Class

There is a [liquibase.sqlgenerator.core.AbstractSqlGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/sqlgenerator/core/AbstractSqlGenerator.html){:target="_blank"} base class you can use which limits the number of methods
you must implement.

### Subclassing

When adding a SqlGenerator that provides database-specific logic, it's often best (but not required) to subclass the default SqlGenerator for that SqlStatement
and override and change only the specific parts that need to be different while preserving the rest of the logic. 

Also, remember to always check the parent class's signature for methods it defines. It may have methods like `nullComesBeforeType()` which it uses to determine
the final SQL and you simply have to override that method to get the correct SQL.

### Instanceof

Specifying database-specific functionality is best done with `if (database instanceof ExampleDatabase)` blocks around the database-specific logic. 

Using "instanceof" rather than "equals" allows the logic to apply to any subclasses (variants) of the given database.

### New Operations

If you are looking to define a new operation, create a new `SqlStatement` class and then the `SqlGenerator` for it.

### Extending SqlGenerator 

When extending an existing SqlGenerator class, you **_must_** override getPriority() so it will be picked over the base implementation you are extending. 
If you do not override this function they will both return the same priority and one will be chosen at random.

To ensure a higher value is returned, `return super.getPriority() + 5`.

## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.sqlgenerator.SqlGenerator API page](../../code/api/sqlgenerator-sqlgenerator.md).

## Example Code

```java
--8<-- "src/main/java/com/example/sqlgenerator/RenameColumnGeneratorExample.java"
```