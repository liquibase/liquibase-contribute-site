package com.example.configuration;

import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.ConfiguredValueModifier;
import liquibase.configuration.ProvidedValue;

public class ExampleValueModifier implements ConfiguredValueModifier<String> {

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public final void override(ConfiguredValue<String> object) {
        String currentValue = object.getValue();
        if (currentValue == null || !currentValue.toString().equals("LOCAL_DB")) {
            return;
        }
        object.override(
                new ProvidedValue(
                        object.getProvidedValue().getRequestedKey(),
                        object.getProvidedValue().getActualKey(),
                        "jdbc:postgresql://localhost:5432/mydb",
                        "Local_DB replacement",
                        object.getProvidedValue().getProvider()
                )
        );
    }

}