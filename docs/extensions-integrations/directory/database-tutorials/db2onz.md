---
title: DB2 z/OS
---

# Using Liquibase with DB2 for z/OS

[DB2 for z/OS](https://www.ibm.com/docs/en/db2-for-zos) is a relational database management system that runs on the mainframe. For more information, see the [DB2 for z/OS documentation page](https://www.ibm.com/docs/en/db2-for-zos/13?topic=getting-started-db2-zos).

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

The latest version of Liquibase has a [pre-installed driver](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) for this database in the `liquibase/internal/lib` directory, so you don't need to install it yourself.

1. Download the required JAR files

    * [DB2 JCC JDBC driver JAR file](https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads) ([Maven download](https://mvnrepository.com/artifact/com.ibm.db2/jcc))
    * [license JAR file](https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads), which is required when connecting to a mainframe DB2 database
    
    !!! note
        To use the DB2 JCC JDBC driver, you must purchase the DB2 Connect product. The license file is contained within the activation package for it. For more information regarding the license file, see [Location of the db2jcc\_license\_cisuz.jar file](https://www.ibm.com/support/pages/location-db2jcclicensecisuzjar-file).

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
```

## Database connection

### Configure connection

1.  Ensure your DB2 on z/OS database is configured. You can check the status by running the [`DISPLAY DATABASE` command](https://www.ibm.com/docs/en/db2-for-zos/13?topic=commands-display-database-db2), which displays status information about DB2 databases.

2.  Specify the database URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format:

    ```
    url: jdbc:db2://<servername>:<port>/<dbname>
    ```

    !!! note
        The URL for DB2 on z/OS may have different formats, such as `jdbc:db2j:net:`, `jdbc:ibmdb:`, and `jdbc:ids:`, depending on your connection type. For more information, see [URL format for IBM Data Server Driver for JDBC and SQLJ type 4 connectivity](https://www.ibm.com/docs/en/db2-for-zos/13?topic=cdsudidsdjs-url-format-data-server-driver-jdbc-sqlj-type-4-connectivity).

    !!! tip
        To apply a Liquibase Pro key to your project, add the following property to the Liquibase properties file: `licenseKey: <paste code here>`

### Test connection

--8<-- "database-tutorial-relational-test-connection-example.md"

## Related links

*   [Deploying Changes to DB2 on z/OS using SQL Scripts](db2onzdeploy-sql.md)
*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)