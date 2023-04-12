# Add a Path Handler

## Overview

When adding support for a new path handler, the interface you are going to implement is `liquibase.resource.PathHandler`

PathHandler implementations should be thread-safe.

!!! tip

    There is a [liquibase.resource.AbstractPathHandler](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/AbstractPathHandler.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 


## Example Code

```java
--8<-- "src/main/java/com/example/resource/ExamplePathHandler.java"

```