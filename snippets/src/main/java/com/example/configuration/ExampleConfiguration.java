package com.example.configuration;

import liquibase.configuration.AutoloadedConfigurations;
import liquibase.configuration.ConfigurationDefinition;

public class ExampleConfiguration implements AutoloadedConfigurations {

    public static final ConfigurationDefinition<String> MY_SETTING;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("liquibase");

        MY_SETTING = builder.define("mySetting", String.class)
                .setDescription("A test setting that takes a string")
                .build();
    }
}
