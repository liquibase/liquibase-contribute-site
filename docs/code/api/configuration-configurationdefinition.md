---
title: config.ConfigurationDefinition
---

# liquibase.configuration.ConfigurationDefinition

## Overview

`liquibase.configuration.ConfigurationDefinition` instances define metadata about available configuration settings and provide type safety for code looking to read configuration values.

Each configuration setting is a dot-separated, "camelCase" key, such as `liquibase.shouldRun` or `liquibase.outputFileEncoding`. 

The beginning of the key acts as a "namespace" for the setting to avoid conflicts between similar settings. Sections within the namespace can be used to group similar settings for better end-user usability. 
Settings defined in the main liquibase code will start with `liquibase.`, but any meaningful namespace can be used.

## Building ConfigurationDefinitions

ConfigurationDefinitions are immutable and built using the [ConfigurationDefinition.Building](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/ConfigurationDefinition.Building.html){:target="_blank"} instance
from `new ConfigurationDefinition.Builder(...).define(...)`.

```java
ConfigurationDefinition<String> MY_SETTING = new ConfigurationDefinition.Builder("liquibase")
        .define("mySetting", String.class)
        .setDescription("A test setting that takes a string")
        .build();
```
The call to `build()` registers the definition with the `LiquibaseConfiguration` system.

ConfigurationDefintions can be created on demand, but generally they get bundled into a "holder" class such as `liquibase.configuration.GlobalConfiguration`.
If the holder class implements `liquibase.configuration.AutoloadedConfigurations` and is registered in `META-INF/services/liquibase.configuration.AutoloadedConfigurations`, the definitions will be autoloaded when Liquibase starts up.

The above example, refactored to be a 
```java
public class ExampleConfiguration implements AutoloadedConfigurations {

    public static final ConfigurationDefinition<String> MY_SETTING;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("liquibase");

        MY_SETTING = builder.define("mySetting", String.class)
                .setDescription("A test setting that takes a string")
                .build();
    }
}
```


## Reading Configured Values

Once the ConfigurationDefinition is created, current values can be read with the `getCurrentValue()` method.

```java
String myValue = MY_SETTING.getCurrentValue();
```

For more information, see the [Definition API Highlights](#definition-api-highlights) below.

## Builder API Highlights

### define()

Begins the definition with the given key and type. The full key for the setting is a concatenation of the "base" key defined in the `ConfigurationDefinition.Builder(...)` call plus the value from this method. 

### setDescription()

Sets the description for the setting. This is used in end-user-facing documentation for the setting.

### setDefaultValue()

Configures the default value for the setting.

### addAliasKey()

Defines an additional key that can be used to look up the setting. This is useful for backward compatibility with older keys, or for supporting different naming conventions.

### setValueHandler()

Configures custom logic for converting the "raw" value from the configuration source into the type expected by the setting. 

Liquibase will do standard conversions for most data types, like parsing strings into numbers, booleans, enums, dates, etc. But if you need non-standard values, you can define a custom handler.

!!! note

    The value handlers are purely for converting the same value between datatypes. If you are looking to modify or translate values, see [liquibase.configuration.ConfiguredValueModifer](configuration-configuredvaluemodifier.md). 

### setValueObfuscator

If the configuration value is sensitive, you can define a custom obfuscator to hide the value in logs and other output. 
Generally this can be set to `ConfigurationValueObfuscator.STANDARD` which replaces any value with `*****`. 

## ConfigurationDefinition API Highlights

### Getting the Current Value

`getCurrentValue()` and `getCurrentValueObfuscated()` return the "best" value based on all the configured [ConfigurationValueProvider](configuration-configurationvalueprovider.md) instances, or the default value if none are set.

`getCurrentValueObfuscated()` will also return the "raw" value **unless** an [obfuscator](#setvalueobfuscator) is defined, in which case the raw value will be run through the obfuscator before being returned.

Any code that will output the value to logging or the UI should normally use `getCurrentValueObfuscated()` to respect whatever obfuscation settings the creator of the definition has configured.
Code that uses the value without printing it, should use `getCurrentValue()`

This is generally the easiest way to get the current value for a setting.

### getCurrentConfiguredValue()

Similar to `getCurrentValue()`, but metadata about the source of the value as well.

## API Details

The complete javadocs for `liquibase.configuration.ConfigurationDefinition` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/ConfigurationDefinition.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Value Provider](../../extensions-integrations/extension-guides/add-a-configuration-value-provider.md)
- [Configure Configuration](../../extensions-integrations/integration-guides/configure-configuration.md)