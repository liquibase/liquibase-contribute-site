---
title: resource.PathHandler
---

# liquibase.resource.PathHandler

## Overview

`liquibase.resource.PathHandler` implementations allow Liquibase to read files from different physical locations. 

They differ from [ResourceAccessors](resource-resourceaccessor.md) in that `ResourceAccesors` are used for looking up file references within changelogs, 
whereas `PathHandlers` are used to look up files referenced outside changelogs. 

!!! note

    `PathHandler` implementations can construct ResourceAccessors. This allows new the file lookup logic provided by custom PathHandlers to be used
    in the `liquibase.searchPath` setting.  

Liquibase ships with [liquibase.resource.DirectoryPathHandler](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/DirectoryPathHandler.html){:target="_blank"}
which looks up paths without a protocol as a relative or absolute file paths. Other PathHandlers should generally recognize paths they handle through an expected URL-like protocol, such as `ftp://example.com/my/file.sql` 
or through specific file extensions like `*.zip`. 

```mermaid
sequenceDiagram
    Calling Code->>PathHandlerFactory: getResource()
    PathHandlerFactory->>PathHandler: getResource()
    PathHandler-->>Calling Code: Returns Resource
```

## PathHandler Selection

Each `PathHandler` defines a `getPriority(path)` method which returns the [priority](../architecture/service-discovery.md) for handling that particular path.
`PathHandlerFactory` will use the `PathHandler` that returns the highest priority value.
This allows extensions to either define a new way to look up files OR override existing logic.

## API Highlights

### Auto-Registration

If the PathHandler can be [dynamically discovered](../architecture/service-discovery.md), it must have a no-arg constructor and be registered in the `META-INF/services/liquibase.resource.PathHandler` file.

If the PathHandler must be configured by the integration code, it can have constructor(s) that take required arguments and should **_NOT_** be registered in `META-INF/services`.

### getPriority()

Used in [selecting the instance to use](#pathhandler-selection)

### getResource()

Looks up the given resource and returns it. This method should return a resource even if it does not exist, with callers using the `Resource.exists()` method as needed.

### createResource()

Creates a new resource at the given path and returns a stream for writing to it.

If a file already exists at that location, a `java.nio.file.FileAlreadyExistsException` exception should be thrown.

### getResourceAccessor()

Constructs a [ResourceAccessor](resource-resourceaccessor.md) for the given path.

Because ResourceAccessors are used to look up files, the passed path should be a directory or some other "multiple-file" type location such as a zip file.

## API Details

The complete javadocs for `liquibase.resource.PathHandler` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/PathHandler.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Path Handler](../../extensions-integrations/extension-guides/add-a-path-handler.md)