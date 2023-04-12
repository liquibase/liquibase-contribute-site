package com.example.configuration;

import liquibase.configuration.AbstractMapConfigurationValueProvider;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class ExampleValueProvider extends AbstractMapConfigurationValueProvider {

    @Override
    public int getPrecedence() {
        return 200;
    }

    @Override
    protected String getSourceDescription() {
        return "Example value";
    }

    @Override
    protected Map<?, ?> getMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("liquibase.url", "jdbc:my://database");
        values.put("other.key.here", "my value");
        values.put("liquibase.random", new SecureRandom().nextInt());

        return values;
    }
}