---
title: Apache Cassandra
---

# Using Liquibase with Cassandra

<small>Verified on: December 7, 2023</small>

[Apache Cassandra](https://cassandra.apache.org/doc/latest/architecture/overview.html) is an open source, distributed, NoSQL database. It presents a partitioned wide column storage model with consistent semantics.

For more information, see the [Apache Cassandra](https://cassandra.apache.org) page.

## Supported database versions

The extension's JDBC wrapper uses the Java Driver for Apache Cassandra® which is designed for:

* Apache Cassandra® 2.1+
* DataStax Enterprise (5.0+)

It will throw "unsupported feature" exceptions if used against an older version of Cassandra cluster.

For more information, please check the 
[compatibility matrix](https://docs.datastax.com/en/driver-matrix/docs/java-drivers.html) and read the 
[driver documentation](https://docs.datastax.com/en/developer/java-driver/latest/).

--8<-- "database-tutorial-prerequisites.md"

1. Configure the Apache Cassandra environment

    1. Ensure your Cassandra database is installed and configured.
    
        For more information, see the [Installing Cassandra](https://cassandra.apache.org/doc/latest/tools/index.html) documentation.

    2. If you have Cassandra tools installed locally, check the status of Cassandra
    
        ```
        $ bin/nodetool status
        ```
    
         The status column in the output should report UN, which stands for "Up/Normal":

         ```
         # nodetool status
         
         Datacenter: datacenter1
         =======================
         Status=Up/Down
         | / State=Normal/Leaving/Joining/Moving
         -- Address    Load        Tokens  Owns (effective)  Host ID                               Rack
         UN 172.18.0.6 198.61 KiB  276     100.0%            5rtc74d1-237f-87c0-88eb-72643bd0a8t7  rack1
         ```

    3. Create your Keyspace
    
        The Keyspace will be referenced later in the Liquibase changelog as the schema when managing objects in the datastore.


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

## Database connection

### Configure connection

1.  Specify the database JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL.

    ```
    url: jdbc:cassandra://<host1>[:<port1>][...--<hostN>[:<portN>]]/<keyspace>?compliancemode=Liquibase[&localdatacenter=<datacenter_name>]
    ```
 
    !!! note
        Be careful to always specify the `compliancemode` parameter with the value `Liquibase` to avoid any unexpected behaviour when running the changelog.
        
    !!! tip
        For more information, see the [specifying Cassandra JDBC connection strings](https://github.com/ing-bank/cassandra-jdbc-wrapper/wiki/JDBC-driver-and-connection-string) documentation.

--8<-- "database-tutorial-relational-test-connection-example.md"


## Related links

*   [Get Up and Running with Liquibase and Apache Cassandra](https://www.liquibase.com/blog/running-liquibase-apache-cassandra)
*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Concepts](https://docs.liquibase.com/concepts/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Workflows](https://docs.liquibase.com/workflows/home.html)