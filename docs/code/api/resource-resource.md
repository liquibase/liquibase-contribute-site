---
title: resource.Resource
---

# liquibase.resource.Resource Interface

## Overview

Liquibase ships with a variety of [liquibase.resource.Resource](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/Resource.html){:target="_blank"}
implementations that your ResourceAccessor can use. The job of the Resources are to wrap the implementation-specific read logic.
If none meet your needs, you can create your own.

!!! tip

    There is a [liquibase.resource.AbstractResource](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/AbstractResource.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 


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

The complete javadocs for `liquibase.resource.Change` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/Resource.html){:target="_blank"}
