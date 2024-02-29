---
title: Apache Derby
---

# Using Liquibase with Apache Derby

[Apache Derby](https://db.apache.org/derby/) is an open-source relational database implemented entirely in Java and available under the Apache License, Version 2.0.

## Supported database versions

*   10.16.X
*   10.15.X
*   10.14.X

## Prerequisites

1. [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html) – Dive into Liquibase concepts.
1. [Install Liquibase](https://docs.liquibase.com/start/install/home.html) – Download Liquibase on your machine.
1. [How to Apply Your Liquibase Pro License Key](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html) – If you use Liquibase Pro, activate your license.

## Install drivers

To use Liquibase and Apache Derby, you need the [JDBC driver JAR file](https://db.apache.org/derby/derby_downloads.html) ([Maven link](https://mvnrepository.com/artifact/org.apache.derby/derbytools)).

[Place your JAR file(s)](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `liquibase/lib` directory.

If you use Maven, you must [include the driver JAR as a dependency](https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html) in your `pom.xml` file.

```
<dependency>
    <groupId>org.apache.derby</groupId>
    <artifactId>derbytools</artifactId>
    <version>10.15.2.0</version>
</dependency>
```

## Database connection

### Configure connection

1.  Ensure your Apache Derby database is configured. As an option, you can run the `sysinfo` command to check the output of Derby system information. For more details, see the [Install Software](https://db.apache.org/derby/papers/DerbyTut/install_software.html) documentation.

1.  Specify the database URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format:

    ```
    url: jdbc:derby://localhost:1527/MYDATABASE;create=true
    ```

    !!! note
        If you created `MYDATABASE`, use `create=false` or remove `create=true` from URL.

1. (optional) Enable Liquibase Pro capabilities

    To apply a [Liquibase Pro key](https://www.liquibase.com/trial) to your project, add the following property to the Liquibase properties file:
    
    ```
    liquibase.licenseKey: <paste key here>
    ```

### Test connection

--8<-- "database-tutorial-relational-test-connection-example.md"

## Troubleshooting issues on macOS

If your Derby Server is not running or you are not using the embedded driver, use the following commands on the Mac to start the Derby Server:

1. Set the DERBY_HOME environment variable

    ```
    export DERBY_HOME=<location_of the unzipped directory_for_derby>
    ```

    Example

    ```
    export DERBY_HOME=/Users/myname/Downloads/db-derby-10.15.2.0-bin
    ```

2. Set the JAVA_HOME environment variable

    ```
    export JAVA_HOME=<path_to_your_JRE>
    ```

    !!! note
        Use the actual installed location of the JRE in place of `<path_to_your_JRE>` since Apache Derby will expect a bin directory as a subfolder.
    
    Example

    ```
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-14.jdk/Contents/Home
    ```

3. Run the Derby Server

    ```
    java -jar $DERBY_HOME/lib/derbynet.jar start -h 0.0.0.0
    ```

## Related links


*   [Apache Derby Wiki](https://cwiki.apache.org/confluence/display/DERBY/FrontPage)
*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Concepts](https://docs.liquibase.com/concepts/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Workflows](https://docs.liquibase.com/workflows/home.html)