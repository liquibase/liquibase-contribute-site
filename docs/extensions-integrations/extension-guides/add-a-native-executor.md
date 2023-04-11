# Add a Native Executor

--8<-- "extension-setup.md"

## Overview

When adding support for a new `runWith` option or replacing the default, the interface you are going to implement is [liquibase.executor.Executor](../../code/api/executor-executor.md).

!!! tip

    There is a [liquibase.executor.AbstractExecutor](https://javadocs.liquibase.com/liquibase-core/liquibase/executor/AbstractExecutor.html){:target="_blank"} base class you can use which limits the number of methods
    you must implement. 

    If you are wrapping custom logic around a JDBC connection, you can subclass `liquibase.executor.jvm.JdbcExecutor` to further limit what you need to implement.


## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.executor.Executor API page](../../code/api/executor-executor.md).


## Example Code

```java
--8<-- "src/main/java/com/example/executor/ExampleExecutor.java"
```