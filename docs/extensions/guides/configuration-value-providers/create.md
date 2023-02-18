---
title: Create a ValueProvider
---

# Create a ConfigurationValueProvider

## Overview

When adding support for a new precondition, the interface you are going to implement is [liquibase.configuration.ConfigurationValueProvider](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/ConfigurationValueProvider.html){:target="_blank"}.

ConfigurationValueProvider should be thread-safe.

!!! tip

    There is a [liquibase.configuration.AbstractMapConfigurationValueProvider](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/AbstractMapConfigurationValueProvider.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 

    If your data source cannot be loaded into a `java.util.Map` as `AbstractMapConfigurationValueProvider` requires, 
    [liquibase.configuration.AbstractConfigurationValueProvider](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/AbstractConfigurationValueProvider.html){:target="_blank"} 
    does not require that implementation while still being easier than implementing `ConfigurationValueProvider` directly.    you must implement.


## Implementing

### Empty Constructor

If your value provider [will be auto-enabled](#register-your-class), it must have an empty constructor. 
If it will be created by an [integration](../../integrations/index.md), it can accept required fields in the constructor.  

### getPrecedence()

Returns where your value provider falls in the hierarchy of locations to check for values, as described in [the ConfigurationValueProvider overview](index.md#configurationvalueprovider-precedence).

### getProvidedValue()

Looks up the given configuration key in your data. 

The key(s) passed will be the canonical/standard key for the property (such as `liquibase.shouldRun`) plus any aliases that may apply. 
It is up to your implementation to provide any "smoothing" to handles differences in case, word separators, etc. that may be expected from how your data is stored
and to check for all passed keys. If multiple passed keys match, return the value from the first key in the array.

If using `AbstractMapConfigurationValueProvider`, override `getMap()` and `keyMatches()` instead of this method.

### getMap() / keyMatches()

If using `AbstractMapConfigurationValueProvider`, `getMap()` and `keyMatches()` replace needing to implement `getProvidedValue()`.

The `getMap()` method is called _when a value is first requested from the instance._ 

The `keyMatches()` method is for defining the "smoothing" logic that handle differences in case, word separators, etc. that may be expected from how your data is stored.
For example, EnvironmentVariableValueProvider checks case-insensitively and replaces `.`s with `_`s.

If you are not extending `AbstractMapConfigurationValueProvider` these methods will not exist, so you will have to implement `getProvidedValue()` instead.

## Register your Class

`ConfigurationValueProviders` are different from many extension points because they often depend on the [integrations](../../integrations/index.md).

Any value providers that **_should_** be automatically configured regardless of the integration used and regardless of configuration settings can be registered by adding your class name to `META-INF/services/liquibase.configuration.ConfigurationValueProvider`.

Any value providers that must be manually registered by the integration should **_not_** be added to that file. 

## Example Code

```java
package com.example;

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

```