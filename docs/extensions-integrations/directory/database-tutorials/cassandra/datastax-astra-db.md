---
title: DataStax Astra DB 
---

# Using Liquibase with DataStax Astra DB (powered by Apache Cassandra)

[DataStax Astra DB](https://www.datastax.com/products/datastax-astra) is a multi-cloud DBaaS built on Apache Cassandra. Astra DB simplifies cloud-native Cassandra application development and reduces deployment time from weeks to minutes. For more information, see [DataStax Astra DB Documentation](https://docs.datastax.com/en/astra/docs/index.html).

--8<-- "database-tutorial-prerequisites.md"

1.  Configure the DataStax Astra DB database environment:
    1.  Log into your [DataStax Astra DB account](https://astra.datastax.com/).
          1. From **Dashboard**, select the needed database
          2. Go to the **Connect** tab. 
          3. Under **Connect using an API**, select **Java**
          4. Download the **Connect Bundle** by following the link in step 1 under **Prerequisites**.
    2.  Once the `secure-connect-<dbname>.zip` file is fully downloaded, place it in a secure place in your file system.

## Install drivers

### All users

To use Liquibase and Cassandra on DataStax Astra DB, you need two JAR files: a JDBC driver and the Liquibase Cassandra extension:

!!! note
    These instructions assume Liquibase Cassandra extension v4.25.0.1 or newer. This extension was updated to replace the previous Simba JDBC driver with the new Cassandra JDBC wrapper.


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

    To configure the JDBC URL, you will need the following information:

    - `secureconnectbundle`: the fully qualified path of the cloud secure connect bundle file
    - `keyspace`: the keyspace to connect to
    - `user`: the username
    - `password`: the password (if you are using a token, you can specify it here and set user value to token)

    Please specify the URL using this JDBC format:

    ```
    url: jdbc:cassandra:dbaas:///<keyspace>?compliancemode=Liquibase&secureconnectbundle=<bundle_name>&user=token&password=<token>
    ```
    
    Replace `<keyspace>` with your own keyspace name, `<bundle_name>` by the real location of your secure connect bundle, and the password `<token>` value with your Astra DB token.
    
    !!! note
        Be careful to always specify the `compliancemode` parameter with the value `Liquibase` to avoid any unexpected behaviour when running the changelog.
        
    !!! tip
        For more information about the available options regarding the JDBC connection string for Astra DB, please check [the driver documentation](https://github.com/ing-bank/cassandra-jdbc-wrapper/wiki/JDBC-driver-and-connection-string#connection-to-cloud-databases-dbaas).


--8<-- "database-tutorial-relational-test-connection-example.md"

Related links
-------------

*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Concepts](https://docs.liquibase.com/concepts/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Workflows](https://docs.liquibase.com/workflows/home.html)