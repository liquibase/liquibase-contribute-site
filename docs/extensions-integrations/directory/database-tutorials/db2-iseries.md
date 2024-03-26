---
title: DB2 iSeries
---
# Using Liquibase with IBM DB2 for iSeries

This is a Liquibase DB2 extension for iSeries support. [DB2 for iSeries](https://www.ibm.com/products/db2-database) enables Liquibase to connect to DB2 for iSeries data sources.

!!! note
    This database is supported **at or below the Contributed level**. Functionality may be limited. Databases at the Contributed level are not supported by the Liquibase support team. Best-effort support is provided through our community forums.  
  
    For more information about the verification levels, see [Database Verification and Support](https://www.liquibase.com/supported-databases/verification-levels).  
  
If you have an update to these instructions, submit feedback so we can improve the page.

## Supported database versions

*   11.5.7+

## Prerequisites

1. [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html) – Dive into Liquibase concepts.
1. [Install Liquibase](https://docs.liquibase.com/start/install/home.html) – Download Liquibase on your machine.
1. [How to Apply Your Liquibase Pro License Key](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html) – If you use Liquibase Pro, activate your license.

## Install drivers

### All Users

1. Download the required JAR files

    *   [JDBC driver JAR file](https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads) ([Maven download](https://mvnrepository.com/artifact/com.ibm.db2/jcc))
    *   [Liquibase DB2 extension for iSeries support](https://github.com/liquibase/liquibase-db2i/releases)

1. Copy your JAR files into your Liquibase installation

    [Place your JAR file(s)](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `liquibase/lib` directory.

1. (optional) Enable Liquibase Pro capabilities

    To apply a [Liquibase Pro key](https://www.liquibase.com/trial) to your project, add the following property to the Liquibase properties file:
    
    ```
    liquibase.licenseKey: <paste key here>
    ```

### Maven Users (additional step)

If you use Maven, you must [include the driver JAR as a dependency](https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html) in your `pom.xml` file.

```
<dependency>
    <groupId>com.ibm.db2</groupId>
    <artifactId>jcc</artifactId>
    <version>11.5.7.0</version>
</dependency>
<dependency>
    <groupId>org.liquibase.ext</groupId>
    <artifactId>liquibase-db2i</artifactId>
    <version>{{liquibase.current_version}}</version>
</dependency>
```

## Database connection

### Configure connection

1. Ensure your IBM DB2 iSeries database is configured. See [Verifying the Installation](https://www.ibm.com/docs/en/db2/11.5?topic=servers-verifying-installation) for more information.

1. Specify the JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other Liquibase property default values. Liquibase does not parse the JDBC URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format.

    ```
    url: jdbc:db2://<server_name>:<port>/<db_name>
    ```

    !!! tip
        To apply a Liquibase Pro key to your project, add the following property to the Liquibase properties file: `licenseKey: <paste code here>`

### Test connection

--8<-- "database-tutorial-relational-test-connection-example.md"

## Related links

*   [DB2 documentation](https://www.ibm.com/support/knowledgecenter/SSEPGG_11.1.0/com.ibm.db2.luw.licensing.doc/doc/c0059812.html)
