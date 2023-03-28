# Add a Path Handler

## Overview

When adding support for a new path handler, the interface you are going to implement is `liquibase.resource.PathHandler`

PathHandler implementations should be thread-safe.

!!! tip

    There is a [liquibase.resource.AbstractPathHandler](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/AbstractPathHandler.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 


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