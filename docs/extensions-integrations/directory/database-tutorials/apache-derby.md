---
title: Apache Derby
---

# Using Liquibase with Apache Derby

[Apache Derby](https://db.apache.org/derby/) is an open-source relational database implemented entirely in Java and available under the Apache License, Version 2.0.

## Supported versions

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
          ---

    === "SQL example"
          ``` sql
          -- liquibase formatted sql

          -- changeset liquibase:1
          CREATE TABLE test_table
          (
            test_id INT, 
            test_column VARCHAR(255), 
            PRIMARY KEY (test_id)
          )
          ```
          ---
      
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
          ---

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
          ---

1.  Navigate to your project folder in the CLI and run the Liquibase [status](https://docs.liquibase.com/commands/change-tracking/status.html) command to see whether the connection is successful:

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

1.  Inspect the SQL with the [update-sql](https://docs.liquibase.com/commands/update/update-sql.html) command. 

    ```
    liquibase update-sql --changelog-file=<changelog.xml>
    ```

    Then make changes to your database with the [update](https://docs.liquibase.com/commands/update/update.html) command.
    
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