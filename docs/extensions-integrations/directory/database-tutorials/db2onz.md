---
title: DB2 on z/OS
---

# Using Liquibase with DB2 on z/OS

**Note:** This database is supported **at or below the Contributed level**. Functionality may be limited. Databases at the Contributed level are not supported by the Liquibase support team. Best-effort support is provided through our community forums.  
  
For more information about the verification levels, see [Database Verification and Support](https://www.liquibase.com/supported-databases/verification-levels).  
  
If you have an update to these instructions, submit feedback so we can improve the page.

[DB2 on z/OS](https://www.ibm.com/docs/en/db2-for-zos) is a relational database management system that runs on the mainframe. For more information, see the [DB2 for z/OS documentation page](https://www.ibm.com/docs/en/db2-for-zos/13?topic=getting-started-db2-zos).

## Supported database versions

*   11.5.7+

## Prerequisites

1.  [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html) – Dive into Liquibase concepts.
2.  [Install Liquibase](https://docs.liquibase.com/start/install/home.html) – Download Liquibase on your machine.
3.  [Get Started with Liquibase](https://docs.liquibase.com/start/home.html) – Learn how to use Liquibase with an example database.
4.  [Design Your Liquibase Project](https://docs.liquibase.com/start/design-liquibase-project.html) – Create a new Liquibase project folder and organize your changelogs
5.  [How to Apply Your Liquibase Pro License Key](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html) – If you use Liquibase Pro, activate your license.

## Install drivers

### All Users
To use Liquibase and DB2 on z/OS, you need the [DB2JCC driver](https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads) ([Maven download](https://mvnrepository.com/artifact/com.ibm.db2/jcc)) and the [license JAR file](https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads), which is required when connecting to a mainframe DB2 database. You must purchase the DB2 Connect product. The license file is contained within the activation package for it. For more information regarding the license file, see [Location of the db2jcc\_license\_cisuz.jar file](https://www.ibm.com/support/pages/location-db2jcclicensecisuzjar-file). Place the license JAR file in the `liquibase/lib` directory.

The latest version of Liquibase has a [pre-installed driver](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) for this database in the `liquibase/internal/lib` directory, so you don't need to install it yourself.

1.  Ensure your DB2 on z/OS database is configured. You can check the status by running the [`DISPLAY DATABASE` command](https://www.ibm.com/docs/en/db2-for-zos/13?topic=commands-display-database-db2), which displays status information about DB2 databases.
2.  Specify the database URL in the `[liquibase.properties](https://docs.liquibase.com/concepts/connections/creating-config-properties.html)` file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format:

    ```
    url: jdbc:db2://<servername>:<port>/<dbname>
    ```

    !!! note
        The URL for DB2 on z/OS may have different formats, such as `jdbc:db2j:net:`, `jdbc:ibmdb:`, and `jdbc:ids:`, depending on your connection type. For more information, see [URL format for IBM Data Server Driver for JDBC and SQLJ type 4 connectivity](https://www.ibm.com/docs/en/db2-for-zos/13?topic=cdsudidsdjs-url-format-data-server-driver-jdbc-sqlj-type-4-connectivity).

    !!! tip
        To apply a Liquibase Pro key to your project, add the following property to the Liquibase properties file: `licenseKey: <paste code here>`

### Maven Users (additional step)

If you use Maven, you must [include the driver JAR as a dependency](https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html) in your `pom.xml` file.

```
<dependency>
    <groupId>com.ibm.db2</groupId>
    <artifactId>jcc</artifactId>
    <version>11.5.7.0</version>
</dependency>
```

## Test connection

1.  Create a text file called [changelog](https://docs.liquibase.com/concepts/changelogs/home.html) (`.xml`, `.sql`, `.json`, or `.yaml`) in your project directory and add a [changeset](https://docs.liquibase.com/concepts/changelogs/changeset.html).

    If you already created a changelog using the `[init project](https://docs.liquibase.com/commands/init/project.html)` command, you can use that instead of creating a new file. When adding onto an existing changelog, be sure to only add the changeset and to not duplicate the changelog header.

    === "XML example"

        ```
        <?xml version="1.0" encoding="UTF-8"?>
        <databaseChangeLog
            xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
            xmlns:pro="http://www.liquibase.org/xml/ns/pro"
            xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
                http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
                http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd">
    
            <changeSet id="1" author="Liquibase">
                <createTable tableName="test_table">
                    <column name="test_id" type="int">
                        <constraints primaryKey="true"/>
                    </column>
                    <column name="test_column" type="varchar"/>
                </createTable>
            </changeSet>
    
        </databaseChangeLog>
        ```

    === "SQL example"

        ```
        -- liquibase formatted sql
    
        -- changeset liquibase:1
        CREATE TABLE test_table (test_id INT, test_column VARCHAR(255), PRIMARY KEY (test_id))
        ```

        !!! tip
            Formatted SQL changelogs generated from Liquibase versions before 4.2 might cause issues because of the lack of space after a double dash ( `--` ). To fix this, add a space after the double dash. For example: `-- liquibase formatted sql` instead of `--liquibase formatted sql` and `-- changeset myname:create-table` instead of `--changeset myname:create-table`.

    === "YAML example"

        ```
        databaseChangeLog:
           - changeSet:
               id: 1
               author: Liquibase
               changes:
               - createTable:
                   tableName: test_table
                   columns:
                   - column:
                       name: test_column
                       type: INT
                       constraints:
                           primaryKey:  true
                           nullable:  false
        ```

    === "JSON example"

        ```
        {
          "databaseChangeLog": [
            {
              "changeSet": {
                "id": "1",
                "author": "Liquibase",
                "changes": [
                  {
                    "createTable": {
                      "tableName": "test_table",
                      "columns": [
                        {
                          "column": {
                            "name": "test_column",
                            "type": "INT",
                            "constraints": {
                              "primaryKey": true,
                              "nullable": false
                            }
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            }
          ]
        }
        ```

2.  Navigate to your project folder in the CLI and run the Liquibase [status](https://docs.liquibase.com/commands/change-tracking/status.html) command to see whether the connection is successful:

    ```
    liquibase status --username=test --password=test --changelog-file=<changelog.xml>
    ```

    !!! note
        You can specify arguments in the CLI or keep them in the [Liquibase properties file](https://docs.liquibase.com/concepts/connections/creating-config-properties.html).

    If your connection is successful, you'll see a message like this:

    ```
    4 changesets have not been applied to <your_jdbc_url>
    Liquibase command 'status' was executed successfully.
    ```

3.  Inspect the SQL with the [update-sql](https://docs.liquibase.com/commands/update/update-sql.html) command. Then make changes to your database with the [update](https://docs.liquibase.com/commands/update/update.html) command.

    ```
    liquibase update-sql --changelog-file=<changelog.xml>
    liquibase update --changelog-file=<changelog.xml>
    ```

    If your `update` is successful, Liquibase runs each changeset and displays a summary message ending with:

    ```
    Liquibase: Update has been successful.
    Liquibase command 'update' was executed successfully.
    ```

4.  From a database UI tool, ensure that your database contains the `test_table` you added along with the [DATABASECHANGELOG table](https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html) and [DATABASECHANGELOGLOCK table](https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html).


Now you're ready to start making deployments with Liquibase!

## Related links

*   [Deploying Changes to DB2 on z/OS using SQL Scripts](db2onzdeploy-sql.md)
*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)