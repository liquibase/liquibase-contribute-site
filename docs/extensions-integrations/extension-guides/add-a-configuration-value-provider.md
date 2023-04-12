# Add a ValueProvider

--8<-- "extension-setup.md"

## Overview

When adding support for a new precondition, the interface you are going to implement is `liquibase.configuration.ConfigurationValueProvider`.

ConfigurationValueProvider should be thread-safe.

!!! tip

    There is a [liquibase.configuration.AbstractMapConfigurationValueProvider](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/AbstractMapConfigurationValueProvider.html){:target="_blank"}
    base class you can use which limits the number of methods you must implement. 

    If your data source cannot be loaded into a `java.util.Map` as `AbstractMapConfigurationValueProvider` requires, 
    [liquibase.configuration.AbstractConfigurationValueProvider](https://javadocs.liquibase.com/liquibase-core/liquibase/configuration/AbstractConfigurationValueProvider.html){:target="_blank"} 
    does not require that implementation while still being easier than implementing `ConfigurationValueProvider` directly.    you must implement.


## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.configuration.ConfigurationValueProvider API page](../../code/api/configuration-configurationvalueprovider.md).

## Example Code

```java
--8<-- "src/main/java/com/example/configuration/ExampleValueProvider.java"

```