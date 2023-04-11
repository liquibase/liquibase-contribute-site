package com.example.resource;


import liquibase.resource.AbstractResourceAccessor;
import liquibase.resource.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExampleResourceAccessor extends AbstractResourceAccessor {

    private final String root;

    public ExampleResourceAccessor() {
        root = null;
    }

    public ExampleResourceAccessor(String root) {
        this.root = root;
    }

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

}