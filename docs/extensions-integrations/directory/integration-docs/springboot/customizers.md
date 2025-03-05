# Customizing Liquibase with Spring Boot Customizers

Spring Boot's Liquibase integration provides a customization mechanism that allows you to modify or enhance Liquibase's behavior during initialization. This document explains how to create and use Liquibase customizers in your Spring Boot application.

## What are Liquibase Customizers?

Customizers let you modify Liquibase's behavior programmatically before database migrations run. They enable advanced integration scenarios such as:

- Executing additional Liquibase commands
- Performing pre/post migration tasks
- Using Liquibase Pro features
- Setting up custom logging or monitoring

## Creating a Liquibase Customizer

To create a customizer, implement the `Customizer<T extends Liquibase>` interface and override the `customize` method. Here's how to create a customizer for Liquibase Pro Flow:

```java
package org.liquibase.springboot.demo.customizer;

import com.datical.liquibase.ext.command.FlowCommandStep;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.command.CommandScope;
import liquibase.integration.spring.Customizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseFlowCustomizer<T extends Liquibase> implements Customizer<T> {

    @Value("${liquibase.flowfile}")
    private String flowFile;

    @Value("${liquibase.license_key}")
    private String licenseKey;

    private static final Logger LOG = LoggerFactory.getLogger(LiquibaseFlowCustomizer.class);

    @Override
    public void customize(T liquibase) {
        System.setProperty("liquibaseProLicenseKey", licenseKey);
        try {
            liquibase.listUnrunChangeSets(new Contexts(), new LabelExpression())
                            .forEach(changeSet -> LOG.info("Unrun changeset: {}", changeSet));

            new CommandScope(FlowCommandStep.COMMAND_NAME)
                .addArgumentValue(FlowCommandStep.FLOW_FILE, flowFile)
                .execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

## Configuration Requirements

To use the above customizer, add these properties to your `application.properties` or `application.yml` file:

```properties
# application.properties
liquibase.flowfile=path/to/your/flowfile.xml
liquibase.license_key=your-liquibase-pro-license-key
```

Or in YAML format:

```yaml
# application.yml
liquibase:
  flowfile: path/to/your/flowfile.xml
  license_key: your-liquibase-pro-license-key
```

## How It Works

1. Create a class that implements `Customizer<T extends Liquibase>` interface
2. Annotate the class with `@Configuration` to register it as a Spring bean
3. Implement the `customize(T liquibase)` method to add your custom behavior
4. Spring Boot automatically detects and applies the customizer during application startup

## Example Explained

The `LiquibaseFlowCustomizer` example above:

1. Sets the Liquibase Pro license key as a system property
2. Lists and logs all unrun changesets for monitoring purposes
3. Executes a Liquibase Flow command using the specified flow file

## Common Use Cases

- **Pre-migration validation**: Verify database state before running migrations
- **Custom logging**: Enhance logging for better visibility of database changes
- **Integration with Liquibase Pro features**: Like Flow, Quality Checks, or Reports
- **Environment-specific configurations**: Apply different behaviors based on environment

## Notes

- Multiple customizers can be defined and will be applied in order of their `@Order` annotation (if specified)
- Customizers are only applied when Liquibase is enabled (`spring.liquibase.enabled=true`)
- For Pro features, ensure you have a valid Liquibase Pro license

## Dependencies

To use the Flow feature shown in the example, include the Liquibase Pro dependency:

=== "Maven pom.xml"

    ```xml
    <dependency>
        <groupId>com.datical.liquibase</groupId>
        <artifactId>liquibase-commercial</artifactId>
        <version>4.x.x</version>
    </dependency>
    ```

=== "Gradle build.gradle"

    ```groovy
    dependencies {
        implementation 'com.datical.liquibase:liquibase-commercial:4.x.x'
    }
    ```