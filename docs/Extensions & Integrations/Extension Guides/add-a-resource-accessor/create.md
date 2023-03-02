---
title: Create a ResourceAccessor
---

# Create a ResourceAccessor

## Overview

When adding support for a new resource accessor, the interface you are going to implement is [liquibase.resource.ResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ResourceAccessor.html){:target="_blank"}.

ResourceAccessors contain and hide the implementation details of how the file references in changelog files are actually found and read. 
They convert the path names into [liquibase.resource.Resource](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/Resource.html){:target="_blank"}s which can then be used like files normally are.   

For example, the path `my/file.sql` can be read from a directory, from a zip file, or from the network depending on the configured ResourceAccessors.  

ResourceAccessor should be thread-safe.

!!! tip

    There is a [liquibase.resource.AbstractResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/AbstractResourceAccessor.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 

    You can also extend existing classes like [liquibase.resource.DirectoryResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/DirectoryResourceAccessor.html){:target="_blank"}
    or [liquibase.resource.ClassLoaderResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ClassLoaderResourceAccessor.html){:target="_blank"} or
    [liquibase.resource.ZipResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ZipResourceAccessor.html){:target="_blank"}
    as needed.


## Implementing

### Constructor

Unlike most extension points, ResourceAccessors are not auto-created and therefore don't require an empty constructor. 

Instead, create constructor(s) as needed taking required and/or common settings.

### search()

The search method is used by `includeAll` and other code which is looking to find files in a directory. 
You must respect the SearchOptions passed, and should return the resources in an order that is expected by the user. Often times this is alphabetical order.  

### describeLocations()

Returns a description of the places this ResourceAccessor will look for paths. Used in error messages and other troubleshooting cases.

### close()

Close any references managed by this ResourceAccessor. This can be a no-op if there is nothing kept open.

## Implementing Resource

### Overview

Liquibase ships with a variety of [liquibase.resource.Resource](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/Resource.html){:target="_blank"}
implementations that your ResourceAccessor can use. The job of the Resources are to wrap the implementation-specific read logic. 
If none meet your needs, you can create your own.   

!!! tip

    There is a [liquibase.resource.AbstractResource](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/AbstractResource.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 


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

!!! tip

    `AbstractResource` defines `resolvePath()` and `resolveSiblingPath()` methods that compute the other paths based purely on the path strings.

    If your underlying system has a better way to resolve files, those should be used. But those methods exist as a fallback when needed.


## Example Code

```java
package com.example;

import liquibase.exception.UnexpectedLiquibaseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExampleResourceAccessor extends AbstractResourceAccessor {

    @Override
    public List<Resource> search(String path, boolean recursive) throws IOException {
        List<Resource> returnList = new ArrayList<>();
        returnList.add(get(path + "/1.sql"));
        returnList.add(get(path + "/2.sql"));
        if (recursive) {
            returnList.add(get(path + "/a/3.sql"));
            returnList.add(get(path + "/b/4.sql"));
        }

        return returnList;
    }

    @Override
    public List<Resource> getAll(String path) throws IOException {
        return Collections.singletonList(new ExampleResource(path, this));
    }

    @Override
    public List<String> describeLocations() {
        return Collections.singletonList("Random Resource Value");
    }

    @Override
    public void close() throws Exception {

    }

    private static class ExampleResource extends AbstractResource {

        private final ExampleResourceAccessor resourceAccessor;

        public ExampleResource(String path, ExampleResourceAccessor resourceAccessor) {
            super(path, URI.create("example:" + path));
            this.resourceAccessor = resourceAccessor;
        }

        @Override
        public InputStream openInputStream() throws IOException {
            return new ByteArrayInputStream(("Example content from "+getPath()).getBytes());
        }

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        public Resource resolve(String other) {
            try {
                return resourceAccessor.get(resolvePath(other));
            } catch (IOException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }

        @Override
        public Resource resolveSibling(String other) {
            try {
                return resourceAccessor.get(resolvePath(other));
            } catch (IOException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
    }
}


```