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
--8<-- "src/main/java/com/example/precondition/PasswordIsSetPrecondition.java"
```