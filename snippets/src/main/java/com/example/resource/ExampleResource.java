package com.example.resource;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.resource.AbstractResource;
import liquibase.resource.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

class ExampleResource extends AbstractResource {

    private final ExampleResourceAccessor resourceAccessor;

    public ExampleResource(String path, ExampleResourceAccessor resourceAccessor) {
        super(path, URI.create("example:" + path));
        this.resourceAccessor = resourceAccessor;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(("Example content from " + getPath()).getBytes());
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
