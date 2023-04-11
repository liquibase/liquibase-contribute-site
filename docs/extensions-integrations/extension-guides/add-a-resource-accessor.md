# Add a Resource Accessor

--8<-- "extension-setup.md"

## Overview

When adding support for a new resource accessor, the interface you are going to implement is [liquibase.resource.ResourceAccessor](../../code/api/resource-resourceaccessor.md).

ResourceAccessors contain and hide the implementation details of how the file references in changelog files are actually found and read. 
They convert the path names into [liquibase.resource.Resource](../../code/api/resource-resource.md)s which can then be used like files normally are.   

For example, the path `my/file.sql` can be read from a directory, from a zip file, or from the network depending on the configured ResourceAccessors.  

ResourceAccessor should be thread-safe.

!!! tip

    There is a [liquibase.resource.AbstractResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/AbstractResourceAccessor.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 

    You can also extend existing classes like [liquibase.resource.DirectoryResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/DirectoryResourceAccessor.html){:target="_blank"}
    or [liquibase.resource.ClassLoaderResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ClassLoaderResourceAccessor.html){:target="_blank"} or
    [liquibase.resource.ZipResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ZipResourceAccessor.html){:target="_blank"}
    as needed.


## Custom Resources

Depending on the new ResourceAccessor, you may need to define a new [liquibase.resource.Resource](../../code/api/resource-resource.md) implementation. 

!!! tip

    `AbstractResource` defines `resolvePath()` and `resolveSiblingPath()` methods that compute the other paths based purely on the path strings.

    If your underlying system has a better way to resolve files, those should be used. But those methods exist as a fallback when needed.


## Example Code

```java
--8<-- "src/main/java/com/example/resource/ExampleResourceAccessor.java"
```

## ExampleResource Code

```java
--8<-- "src/main/java/com/example/resource/ExampleResource.java"
```