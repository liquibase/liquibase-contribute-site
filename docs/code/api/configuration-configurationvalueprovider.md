---
title: configuration.ConfigurationValueProvider
---

# configuration.ConfigurationValueProvider Interface

## Overview

`liquibase.configuration.ConfigurationValueProvider` implementations define where and how to look up configuration settings exposed through the `liquibase.configuration.LiquibaseConfiguration` facade.

The configuration settings looked up through these providers are used by both global and command-specific settings. 

## ConfigurationValueProvider Precedence

When a specific configuration key is looked for, Liquibase will check for the value in all registered ConfigurationValueProviders.

Each ConfigurationValueProvider has a `getPrecedence()` method which controls which value "wins" if it is configured in multiple places.

The standard provider precedence is:

- 400 `liquibase.configuration.core.ScopeValueProvider`
- 250 Integration-specific providers such as "CLI Arguments"
- 200 `liquibase.configuration.core.SystemPropertyValueProvider`
- 150 `liquibase.configuration.core.EnvironmentValueProvider`
- 50 `liquibase.configuration.core.DefaultsFileValueProvider`

## API Highlights

### Empty Constructor

If the value provider [will be auto-enabled](#register-your-class), it must have an empty constructor.
If it will be created by an [integration](../integrations/index.md), it can accept required fields in the constructor.

### getPrecedence()

Returns where your value provider falls in the hierarchy of locations to check for values, as described in [the ConfigurationValueProvider overview](add-a-configuration-value-provider/index.md#configurationvalueprovider-precedence).

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

## Registration

`ConfigurationValueProviders` are different from many extension points because they often depend on the [integrations](../integrations/index.md).

Any value providers that **_should_** be automatically configured regardless of the integration used and regardless of configuration settings can be registered by adding it to `META-INF/services/liquibase.configuration.ConfigurationValueProvider`.

Any value providers that must be manually registered by the integration should **_not_** be added to that file. 

## API Details

The complete javadocs for `liquibase.configuration.ConfigurationValueProvider` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/ConfigurationValueProvider.html){:target="_blank"}
