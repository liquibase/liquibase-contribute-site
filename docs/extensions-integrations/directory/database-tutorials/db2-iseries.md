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

1.  Create a text file called [changelog](https://docs.liquibase.com/concepts/changelogs/home.html) (`.xml`, `.sql`, `.json`, or `.yaml`) in your project directory and add a [changeset](https://docs.liquibase.com/concepts/changelogs/changeset.html).

    If you already created a changelog using the [`init project`](https://docs.liquibase.com/commands/init/project.html) command, you can use that instead of creating a new file. When adding onto an existing changelog, be sure to only add the changeset and to not duplicate the changelog header.

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

1.  Navigate to your project folder in the CLI and run the Liquibase [status](../../commands/change-tracking/status.html) command to see whether the connection is successful:

    ```
    liquibase status --username=test --password=test --changelog-file=<changelog.xml>
    ```

    !!! note
        You can specify arguments in the CLI or keep them in the [Liquibase properties file](../../concepts/connections/creating-config-properties.html).

    If your connection is successful, you'll see a message like this:

    ```
    4 changesets have not been applied to <your_jdbc_url>
    Liquibase command 'status' was executed successfully.
    ```

1.  Inspect the deployment SQL with the [update-sql](../../commands/update/update-sql.html) command:

    ```
    liquibase update-sql --changelog-file=<changelog.xml>
    ```

1.  Then make changes to your database with the [update](../../commands/update/update.html) command:

    ```
    liquibase update --changelog-file=<changelog.xml>
    ```

    If your `update` is successful, Liquibase runs each changeset and displays a summary message ending with:

    ```
    Liquibase: Update has been successful.
    Liquibase command 'update' was executed successfully.
    ```

1.  From a database UI tool, ensure that your database contains the `test_table` you added along with the [DATABASECHANGELOG table](https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html) and [DATABASECHANGELOGLOCK table](https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html).

Now you're ready to start making deployments with Liquibase!

## Related links

*   [DB2 documentation](https://www.ibm.com/support/knowledgecenter/SSEPGG_11.1.0/com.ibm.db2.luw.licensing.doc/doc/c0059812.html)
