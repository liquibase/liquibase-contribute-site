---
title: Amazon Keyspaces
---

# Using Liquibase with Amazon Keyspaces (for Apache Cassandra)

[Amazon Keyspaces](https://aws.amazon.com/keyspaces) is a DBaaS compatible with Apache Cassandra. 
For more information, see [Amazon Keyspaces Documentation](https://aws.amazon.com/keyspaces/resources).

## Prerequisites

--8<-- "database-tutorial-prerequisites.md"

### Setup Amazon Keyspaces

Configure your Amazon Keyspaces instance following the [official documentation](https://docs.aws.amazon.com/keyspaces/latest/devguide/accessing.html).

## Install drivers

### All users

To use Liquibase and Cassandra on Amazon Keyspaces, you need two JAR files: a JDBC driver and the Liquibase Cassandra extension:

!!! note
    These instructions assume Liquibase Cassandra extension v4.29.0 or newer. Prior versions are not compatible with Amazon Keyspaces.


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

## Database connection

### Configure connection

1.  Specify the database JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL.

    Please specify the URL using this JDBC format:

    ```
    url: jdbc:cassandra://cassandra.<aws-region>.amazonaws.com:9142/<keyspace>?compliancemode=Liquibase&user=<username>&password=<password>
    ```
    
    Replace `<keyspace>` with your own keyspace name and `<aws-region>` by the AWS region where your Keyspaces instance is deployed (for example `us-east-1`, `eu-west-1`, ...).

    !!! note
        Be careful to always specify the `compliancemode` parameter with the value `Liquibase` to avoid any unexpected behaviour when running the changelog.

2.  Add the following property in the `liquibase.properties` file to activate the compatibility mode with Amazon Keyspaces:

    ```
    liquibase.cassandra.awsKeyspacesCompatibilityModeEnabled: true
    ```

    !!! tip
        For more information about the available options regarding the JDBC connection string for Amazon Keyspaces, please check [the driver documentation](https://github.com/ing-bank/cassandra-jdbc-wrapper/wiki/JDBC-driver-and-connection-string).

### Test connection

--8<-- "database-tutorial-relational-test-connection-example.md"

Related links
-------------

*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Concepts](https://docs.liquibase.com/concepts/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Workflows](https://docs.liquibase.com/workflows/home.html)