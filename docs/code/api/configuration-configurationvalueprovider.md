---
title: config.ValueProvider
---

# liquibase.configuration.ConfigurationValueProvider

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

### Auto-Registration

If the value provider can be [dynamically discovered](../architecture/service-discovery.md), it must have a no-arg constructor and be registered in `META-INF/services/liquibase.configuration.ConfigurationValueProvider`.

If it will be created programmatically by an integration, it can accept required fields in the constructor and should **_NOT_** be registered.

### getPrecedence()

Returns where the value provider falls in the [hierarchy of locations to check for values](#configurationvalueprovider-precedence).

### getProvidedValue()

Looks up the given configuration key in the source data.

The key(s) passed will be the canonical/standard key for the property (such as `liquibase.shouldRun`) plus any aliases that may apply.
It is up to implementations to provide any "smoothing" to handle differences in case, word separators, etc. that may be expected from how the data is stored
and to check for all passed keys. If multiple passed keys match, return the value from the first key in the array.

If using `AbstractMapConfigurationValueProvider`, override `getMap()` and `keyMatches()` instead of this method.

### getMap() / keyMatches()

If using `AbstractMapConfigurationValueProvider`, `getMap()` and `keyMatches()` replace needing to implement `getProvidedValue()`.

The `getMap()` method is called _when a value is first requested from the instance._

The `keyMatches()` method is for defining the "smoothing" logic that handles differences in case, word separators, etc. that may be expected from how the data is stored.
For example, EnvironmentVariableValueProvider checks case-insensitively and replaces `.`s with `_`s.

If you are not extending `AbstractMapConfigurationValueProvider` these methods will not exist, so you will have to implement `getProvidedValue()` instead.

## API Details

The complete javadocs for `liquibase.configuration.ConfigurationValueProvider` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/ConfigurationValueProvider.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Value Provider](../../extensions-integrations/extension-guides/add-a-configuration-value-provider.md)