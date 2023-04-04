# Configure Configuration

## Overview

The configuration API in the `liquibase.configuration` package provides both the facade for reading configuration settings regardless of where they come from.
The API provides both a way to look-up individual configuration values and metadata about each available configuration including human-readable descriptions.

The available configuration options are a pluggable extension point, and the configuration API provides a consistent and stable way to work with the configurations regardless of what extensions are installed.

```mermaid
sequenceDiagram
    participant Integration
    participant CommandScope
    participant Liquibase Logic
    participant LiquibaseConfiguration
    participant ConfigurationValueProvider

    Integration->>ConfigurationValueProvider: Create and configure
    Integration->>LiquibaseConfiguration: getCurrentConfiguredValue
    LiquibaseConfiguration->>ConfigurationValueProvider: getProvidedValue
    ConfigurationValueProvider-->>LiquibaseConfiguration: return value
    LiquibaseConfiguration-->>Integration: return best value
    Integration->>CommandScope: execute()
    CommandScope->>Liquibase Logic: Runs Business Logic
    Liquibase Logic->>LiquibaseConfiguration: getCurrentConfiguredValue
    LiquibaseConfiguration->>ConfigurationValueProvider: getProvidedValue
    ConfigurationValueProvider-->>LiquibaseConfiguration: return value
    LiquibaseConfiguration-->>Liquibase Logic: return best value
```


## Configuration Value Providers

When code needs to access a global configuration value or a command argument value, it looks it up via the registered 
[liquibase.configuration.ConfigurationValueProvider](../../code/api/configuration-configurationvalueprovider.md) instances.

Each `ConfigurationValueProvider` has a "precedence" and the value returned from the provider with the highest precedence is the one that will be used.
This allows the user, for example, to have a `liquibase.outputFileEncoding` value defined as an environment variable and a "better" version defined as a CLI argument.

By default, Liquibase will register a set of default value providers, including:

- Java system properties
- Environment variables
- Objects on the `liquibase.Scope` object

but integrations will usually want to configure others.

!!! example

    - Most integrations create a DefaultsFileValueProvider to read a `liquibase.properties` file
    - The CLI integration registers a CommandLineArgumentValueProvider to add the CLI arguments as configuration values
    - The servlet integration registers a ServletConfigurationValueProvider to read values from the servlet context 

New value providers can be registered by constructing and configuring the ConfigurationValueProvider you need and then registering it.

!!! tip

    You can configure additional value providers based on settings read from the ones currently configured.
    
    For example, the CLI configures the CLI argument and environment variable value providers before looking for a `liquibase.defaultsFile`
    value in them to know how to configure the `DefaultValuesValueProvider`.

## Custom Value Providers

If your integration needs to define its own custom value provider, see [the ConfigurationValueProvider guide](../extension-guides/add-a-configuration-value-provider.md).  

## Reading Configuration Values

Your integration will often have settings of its own, and these should be looked up via the configuration system to take advantage of the "cascading" value providers users expect to use.

For example, you should respect the `liquibase.outputFileEncoding` global configuration, supporting it being set via environment variables, a defaultsFile, and/or other locations.

Configuration values are best read via the `getCurrentValue()` method on a [liquibase.configuration.ConfigurationDefinition](../../code/api/configuration-configurationdefinition.md) constant
to provide type safety and standardized value handling logic, such as obfuscators to use when logging values.

You will find `ConfigurationDefinition` instances defined in holder classes such as [liquibase.GlobalConfiguration](https://javadocs.liquibase.com/liquibase-core/liquibase/GlobalConfiguration.html){:target="_blank"}.
The `ConfigurationDefinition.getCurrentValue()` method provides type safety and standardized value handling logic, such as obfuscators to use when logging values.

!!! example

    ```java
    String encoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
    ```

If you do not have a ConfigurationDefinition to use, you can read arbitrary configuration values via the [liquibase.configuration.LiquibaseConfiguration](../../code/api/configuration-liquibaseconfiguration.md)
singleton directly. 

## Custom Configuration Definitions

If your integration exposes configuration settings of its own, you should define them with additional [ConfigurationDefinitions](../../code/api/configuration-configurationdefinition.md). 
This allows you to take advantage of the type safety and centralized value handling logic, plus exposes them through the configuration metadata like all other configuration settings.

## Example Code

```java
package com.example;

import liquibase.configuration.ConfigurationDefinition;

import java.io.FileWriter;

public class ExampleIntegration  {

    public static final ConfigurationDefinition<String> LOG_FILE;
    public static final ConfigurationDefinition<Boolean> SHOULD_RUN;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("example");

        LOG_FILE = builder.define("logFile", String.class).build();

        SHOULD_RUN = builder.define("shouldRun", Boolean.class)
                .setDescription("Should Liquibase commands execute")
                .setDefaultValue(true)
                .addAliasKey("should.run")
                .build();
    }

    public static void main(String[] args) throws Exception {
        boolean shouldRun = SHOULD_RUN.getCurrentValue();
        if (!shouldRun) {
            System.out.println("Not running");
            return;
        }

        FileWriter log = new FileWriter(LOG_FILE.getCurrentValue());

        // ... remaining logic
    }
}
```


## API Documentation

For a complete description of the ConfigurationValueProvider API, including what methods must be implemented, see:
- [liquibase.configuration.ConfigurationValueProvider](../../code/api/configuration-configurationvalueprovider.md).
- [liquibase.configuration.ConfigurationDefinition](../../code/api/configuration-configurationdefinition.md).
- [liquibase.configuration.ConfiguredValueModifier](../../code/api/configuration-configuredvaluemodifier.md).
- [liquibase.configuration.LiquibaseConfiguration](../../code/api/configuration-liquibaseconfiguration.md).
