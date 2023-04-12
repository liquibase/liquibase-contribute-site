package com.example.resource;

import liquibase.resource.AbstractPathHandler;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;

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
        return new ExampleResource(path, new ExampleResourceAccessor());
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