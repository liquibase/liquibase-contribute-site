---
title: Amazon Keyspaces
---

# Using Liquibase with Amazon Keyspaces (for Apache Cassandra)

[Amazon Keyspaces (for Apache Cassandra)](https://aws.amazon.com/keyspaces/) is a scalable, highly available, and managed Apache Cassandra–compatible database service.

For more information, see the [Apache Cassandra Getting Started](https://aws.amazon.com/keyspaces/getting-started/) page.

## Supported database versions

The extension's JDBC wrapper uses the Java Driver for Apache Cassandra® 4.4.0 or greater which is designed for

* Amazon Keyspaces (Apache Cassandra 3.11.2)

It will throw "unsupported feature" exceptions if used against an older version of Cassandra cluster.

For more information, please check the 
[compatibility matrix](https://docs.datastax.com/en/driver-matrix/doc/driver_matrix/javaDrivers.html) and read the 
[driver documentation](https://docs.datastax.com/en/developer/java-driver/latest/).

## Prerequisites

--8<-- "database-tutorial-prerequisites.md"

### Setup Amazon Keyspaces

1. Configure the Amazon Keyspaces environment
    1. Login to AWS and navigate to **Services -> Amazon Keyspaces**.
    
    2. In the **Create Resources** section, press the **Create keyspace** button
    
    3. Enter the **Keyspace name**, select any other relevant optional arguments, and press the **Create keyspace** button
        
        The **Keyspace name** will be referenced in the connection string.
        

## Install drivers

### All users

To use Apache Cassandra with Liquibase, you need to install two additional JAR files.

1. Download the jar files
    * Download the [Cassandra JDBC wrapper](https://github.com/ing-bank/cassandra-jdbc-wrapper/releases) (`cassandra-jdbc-wrapper-<version>-bundle.jar`) from GitHub
    * Download the [Liquibase Cassandra extension](https://github.com/liquibase/liquibase-cassandra/releases) (`liquibase-cassandra-<version>.jar`) from GitHub
    
1. [Place your JAR file(s)](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `<liquibase_install_dir>/lib` directory.
    * `cassandra-jdbc-wrapper-<version>-bundle.jar`
    * `liquibase-cassandra-<version>.jar`

1. Open the Liquibase properties file and specify the driver, as follows:

    ```
    driver: com.ing.data.cassandra.jdbc.CassandraDriver
    ```

### Maven users (additional step)

If you use Maven, note that this database does not provide its driver JAR on a public Maven repository, so you must install a local copy and [add it as a dependency](https://docs.liquibase.com/tools-integrations/maven/using-liquibase-and-maven-pom-file.html) to your `pom.xml` file.

```
<dependency>
    <groupId>com.ing.data</groupId>
    <artifactId>cassandra-jdbc-wrapper</artifactId>
    <version>{{jdbc_driver_version.cassandra}}</version>
</dependency>
<dependency>
    <groupId>org.liquibase.ext</groupId>
    <artifactId>liquibase-cassandra</artifactId>
    <version>{{extension_version.cassandra}}</version>
</dependency>
```

You need to specify that the scope is `system` and provide the `systemPath` in `pom.xml`.
In the example, the `<liquibase_install_dir>/lib` is the location of the driver JAR file.

## Database connection

### Configure connection

1.  Specify the database JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL.

    ```
    url: jdbc:cassandra://<region_endpoint>:9042/<keyspace>?compliancemode=Liquibase&localdatacenter=<DC1>
    ```
    
    For more information on Amazon Keyspaces configuration, please review the following documentation
    
      * [Amazon Keyspace credentials](https://docs.aws.amazon.com/keyspaces/latest/devguide/programmatic.credentials.html) 
      * [Amazon Keyspace service region endpoints](https://docs.aws.amazon.com/keyspaces/latest/devguide/programmatic.endpoints.html)
    
    !!! note
        For more information, see the [specifying Cassandra JDBC connections strings](https://github.com/ing-bank/cassandra-jdbc-wrapper#usage) documentation.

### Test connection

--8<-- "database-tutorial-relational-test-connection-example.md"

## Related links

*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Concepts](https://docs.liquibase.com/concepts/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Workflows](https://docs.liquibase.com/workflows/home.html)