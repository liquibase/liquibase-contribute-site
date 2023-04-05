---
title: config.ValueModifier
---

# liquibase.configuration.ConfiguredValueModifier

## Overview

`liquibase.configuration.ConfiguredValueModifier` implementations allow configuration values to be translated, regardless of the [ConfigurationValueProvider](configuration-configurationvalueprovider.md) which returns them.

!!! example

    A ConfiguredValueModifier can be created that converts the value `LOCAL_DB` to a localhost connection string. 

    With that value modifier in place, anywhere a user can configure their `url` (CLI arguments, environment variables, liquibase.properties file, etc.) they can use `LOCAL_DB` instead of the full connection string.  

## Execution Order

For each configuration value, Liquibase will run all ConfiguredValueModifier instances based on the value they return from `getOrder()`. 

This allows modifiers to modify the values from each other.

## API Highlights

### Auto-Registration

If the value modifier can be [dynamically discovered](../architecture/service-discovery.md), it must have a no-arg constructor and be registered in `META-INF/services/liquibase.configuration.ConfiguredValueModifier`.

If it will be created programmatically by an integration, it can accept required fields in the constructor and should **_NOT_** be registered.

### getOrder()

Returns where the modifier falls in the [execution order](#execution-order).

### override()

Replaces the configured value if necessary. Generally, implementations will look for patterns in the current value but can also know which values to modify based on key names, data types, or anything else in the passed `liquibase.configuration.ConfigurationValue`.

If the function should not modify the value, it just exits.

```java
public class MyValueModifier implements ConfiguredValueModifier<String> {
    //...
    
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
```


## API Details

The complete javadocs for `liquibase.configuration.ConfiguredValueModifier` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/ConfiguredValueModifier.html){:target="_blank"}

