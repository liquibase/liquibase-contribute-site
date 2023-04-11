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
--8<-- "src/main/java/com/example/change/SetPasswordChange.java"
```