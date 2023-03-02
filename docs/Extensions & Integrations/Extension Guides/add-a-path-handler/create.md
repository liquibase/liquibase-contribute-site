# Create a PathHandler

## Overview

When adding support for a new path handler, the interface you are going to implement is [liquibase.resource.PathHandler](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/PathHandler.html){:target="_blank"}.

PathHandler implementations should be thread-safe.

!!! tip

    There is a [liquibase.resource.AbstractPathHandler](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/AbstractPathHandler.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 

## Implementing

### Empty Constructor

Like most Liquibase extensions, PathHandler must have an empty constructor.

### getPriority()

Returns where your path handler falls in the hierarchy, as described in [the PathHandler overview](index.md#pathhandler-selection).

### getResource()

Looks up the given resource and returns it. This method should return a resource even if it does not exist, with callers using the `Resource.exists()` method as needed. 

### createResource()

Creates a new resource at the given path and returns a stream for writing to it.

If a file already exists at that location, a `java.nio.file.FileAlreadyExistsException` exception should be thrown.

### getResourceAccessor()

Constructs a [ResourceAccessor](../add-a-resource-accessor/index.md) for the given path. 

Because ResourceAccessors are used to look up files, the passed path should be a directory or some other "multiple-file" type location such as a zip file.    

## Register your Class

Like all extensions, your executor must be registered by adding your class name to `META-INF/services/liquibase.resource.PathHandler`

## Example Code

```java
package com.example;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class ExamplePathHandler extends AbstractPathHandler {

    @Override
    public int getPriority(String root) {
        if (root.startsWith("example:")) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ResourceAccessor getResourceAccessor(String root) throws IOException, FileNotFoundException {
        return new ExampleResourceAccessor(root);
    }

    @Override
    public Resource getResource(String path) throws IOException {
        return new ExampleResource(path);
    }

    @Override
    public OutputStream createResource(String path) throws IOException {
        return new ByteArrayOutputStream() {
            @Override
            public void close() throws IOException {
                System.out.println("Wrote to ExamplePathHandler: \n" + this);
                super.close();
            }
        };
    }
}

```