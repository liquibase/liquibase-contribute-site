---
title: config.LiquibaseConfiguration
---

# liquibase.configuration.LiquibaseConfiguration

## Overview

`liquibase.configuration.LiquibaseConfiguration` provides a unified management of configuration properties within Liquibase core and in extensions.

This singleton is a lower level facade which provides only raw/untyped access to what is configured.
For most use cases, values should be read through [liquibase.configuration.ConfigurationDefinition](configuration-configurationdefinition.md) instances instead.

## API Highlights

### Getting the Singleton

The singleton is found by `Scope.getCurrentScope.getSingleton(LiquibaseConfiguration.class)`

### registerProvider()

Adds a custom-created [ConfigurationValueProvider](configuration-configurationvalueprovider.md) to the list of available providers.

### registerDefinition()

Adds a custom-created [ConfigurationDefinition](configuration-configurationdefinition.md) to the list of definitions.
This is not required for using the definition instance to read values from, but is required for the definition to show in "get available definitions" calls.

### getCurrentConfiguredValue()

Searches for the given keys in the current providers and applies any applicable modifiers.
There are several versions of the method depending on your needs, but they all take a simple string key and convert the value to the type defined in the method.

## API Details

The complete javadocs for `liquibase.configuration.LiquibaseConfiguration` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/LiquibaseConfiguration.html){:target="_blank"}