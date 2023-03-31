---
title: resource.Resource
---

# liquibase.resource.Resource

## Overview

Instances of the [liquibase.resource.Resource](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/Resource.html){:target="_blank"} interface
wrap implementation-specific read logic. It provides a standard interface for code to use regardless of whether the underlying file is local, remote, or anywhere else.  

Often times, [PathHandler](resource-pathhandler.md) and/or [ResourceAccessor](resource-resourceaccessor.md) implementations will create custom Resource implementations.


## API Highlights

### Constructor

The base AbstractResource constructor takes a URI along with the path. Because the same path can match multiple Resources within a ResourceAccessor, the URI is the unique descriptor of this particular Resource.

!!! tip

    The `resolve` methods will need to look up other Resources, so passing the ResourceAccessor accessor into the constructor is often needed.   

### openInputStream

Returns the contents of the file as a stream.

### exists

Returns whether the resource exists or not. The resolve methods return relative Resources, without worrying about whether they are existing files or not.
This method is used to check the existence of the resources.

### resolve / resolveSibling

These methods return objects corresponding to other resources located relative to this resource.

`Resolve` is for finding a path **_within_** this resource's location (like a file in a directory),
whereas `resolveSibling` is for finding a path **_next to_** this resource's location (like a file in the same directory).

## API Details

The complete javadocs for `liquibase.resource.Resource` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/Resource.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Path Handler](../../extensions-integrations/extension-guides/add-a-path-handler.md)
- [Add a Resource Accessor](../../extensions-integrations/extension-guides/add-a-resource-accessor.md)