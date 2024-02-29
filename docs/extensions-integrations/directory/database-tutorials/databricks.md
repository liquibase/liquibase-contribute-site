---
title: Databricks
description: Learn how to use Liquibase with Databricks SQL Warehouses to automate database schema migration.
---

# Using Liquibase with Databricks Data Lakehouses

<!-- <small>Verified on: February 28, 2024</small> -->

A data lakehouse is a new, open data management architecture that combines the flexibility, cost-efficiency, and scale of data lakes with the data management and ACID transactions of data warehouses, enabling business intelligence (BI) and machine learning (ML) on all data.

The lakehouse architecture and [Databricks SQL](https://docs.databricks.com/en/sql/index.html) bring cloud data warehousing capabilities to your data lakes. Using familiar data structures, relations, and management tools, you can model a highly-performant, cost-effective data warehouse that runs directly on your data lake.

For more information on Databricks, see the [Databricks](https://www.databricks.com) website.

## Prerequisites

--8<-- "database-tutorial-prerequisites.md"

### Setup Databricks

1. Create a Databricks account and workspace

    If you don't already have a Databricks account and workspace, follow the [Databricks Getting Started](https://docs.databricks.com/en/getting-started/index.html) instructions.

1. Navigate to your Workspaces tab and click the Open Workspace button in the upper right of the page.

    ![Databricks Open Workspace](../../../images/extensions-integrations/databricks-open-workspace.jpg)

1. Create a SQL Warehouse

    If you don't have a SQL Warehouse set up, follow the Databricks instructions on [Creating a SQL Warehouse](https://docs.databricks.com/en/compute/sql-warehouse/create-sql-warehouse.html)

1. Create a catalog

    If you don't already have a catalog setup, follow the Databricks instructions on [Create and Manage Catalogs](https://docs.databricks.com/en/data-governance/unity-catalog/create-catalogs.html)

1. Click the SQL Editor option in the left navigation, enter your SQL to create your database (also called a schema), and click the Run button

    `CREATE DATABASE IF NOT EXISTS <catalog_name>.<database_name>;`

    ![Databricks Create Database](../../../images/extensions-integrations/databricks-create-database.jpg)

1. Your database is configured and ready to use.

## Install drivers

### All users

To use Databricks with Liquibase, you need to install two additional JAR file.

1. Download the jar files
    * Download the [Databricks JDBC driver](https://www.databricks.com/spark/jdbc-drivers-download) (`DatabricksJDBC42-<version>.zip`) from driver download site and unzip the folder to locate the `DatabricksJDBC42.jar` file.
    * Download the [Liquibase Databricks extension](https://github.com/liquibase/liquibase-databricks) (`liquibase-databricks-<version>.jar`) from the GitHub Assets listed at the end of the release.

1. [Place your JAR file(s)](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `<liquibase_install_dir>/lib` directory.
    * `DatabricksJDBC42.jar`
    * `liquibase-databricks-<version>.jar`
      
    !!! Note
        If you are running your project on MacOS or Linux, you might need to run the following command in your terminal 
        (you can add it to your bash profile as well) to allow the dependencies to work properly:
        
        `export JAVA_OPTS=--add-opens=java.base/java.nio=ALL-UNNAMED`

### Maven users (additional step)

If you use Maven, note that this database does not provide its driver JAR on a public Maven repository, so you must install a local copy and [add it as a dependency](https://docs.liquibase.com/tools-integrations/maven/using-liquibase-and-maven-pom-file.html) to your `pom.xml` file.

```
<dependency>
    <groupId>com.databricks</groupId>
    <artifactId>databricks-jdbc</artifactId>
    <version>[2.6.36,)</version>
</dependency>
<dependency>
    <groupId>org.liquibase.ext</groupId>
    <artifactId>liquibase-databricks</artifactId>
    <version>[1.1.3,)</version>
</dependency>
```

### Verify installation

Run the following command to confirm you have successfully installed everything:

```
liquibase --version
```

Review the libaries listing output for the two newly installed jar files: `DatabricksJDBC42-<version>.zip` and `liquibase-databricks-<version>.jar`.

![Databricks Install Verification](../../../images/extensions-integrations/databricks-install-verification.jpg)

## Database connection

### Configure connection

1.  Specify the database JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL.

    ```
    liquibase.command.url: jdbc:databricks://<your_workspace_host_name>:443/default;transportMode=http;ssl=1;AuthMech=3;httpPath=/sql/1.0/warehouses/<your_warehouse_id>;ConnCatalog=<your_catalog>;ConnSchema=<your_schema>;
    ```
     
    !!! Note
        Your base JDBC connection string can be found on the **SQL Warehouses -> *your_warehouse* -> Connection details** tab.

    !!! Note
        Additional information on specifying the Databricks JDBC connection can be found in the [Databricks JDBC Driver](https://docs.databricks.com/en/integrations/jdbc/index.html) documentation.

1. Specify your username and password in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file)

    1. The username, in our case is just “token” for the User or Service Principal you want to manage Liquibase.

    ```
    # Enter the username for your Target database.
    liquibase.command.username: token
    ```
    
    1. This is the token for the User or Service Principal we want to authenticate. This is usually passed in dynamically using frameworks like GitActions + Secrets.
    
    ```    
    # Enter the password for your Target database.
    liquibase.command.password: <your_token_here>
    ```

### Test connection


1. Create a text file called [changelog](https://docs.liquibase.com/concepts/changelogs/home.html) (`.xml`, `.sql`, `.json`, or `.yaml`) in your project directory and add a [changeset](https://docs.liquibase.com/concepts/changelogs/changeset.htmlhttps://docs.liquibase.com/concepts/changelogs/changeset.html).

    If you already created a changelog using the [`init project`](https://docs.liquibase.com/commands/init/project.html) command, you can use that instead of creating a new file. When adding onto an existing changelog, be sure to only add the changeset and to not duplicate the changelog header.

    === "SQL example"
          ``` sql
          -- liquibase formatted sql

          -- changeset my_name:1
          CREATE TABLE test_table 
          (
            test_id INT, 
            test_column VARCHAR(255), 
            PRIMARY KEY (test_id)
          )
          ```
          ---

    === "XML example"
          ``` xml
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

            <changeSet id="1" author="my_name">
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

    === "YAML example"
          ``` yaml
          databaseChangeLog:
            - changeSet:
              id: 1
              author: my_name
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
          ``` json
          {
            "databaseChangeLog": [
              {
                "changeSet": {
                  "id": "1",
                  "author": "my_name",
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

1. Navigate to your project folder in the CLI and run the Liquibase [`status`](https://docs.liquibase.com/commands/change-tracking/status.html) command to see whether the connection is successful:

    ```
    liquibase status --changelog-file=<changelog.xml>
    ```

    If your connection is successful, you'll see a message like this:

    ```
    1 changeset has not been applied to <your_jdbc_url>
    Liquibase command 'status' was executed successfully.
    ```

1. Inspect the SQL with the [`update-sql`](https://docs.liquibase.com/commands/update/update-sql.html) command. Then, make changes to your database with the [`update`](https://docs.liquibase.com/commands/update/update.html) command.

    ```
    liquibase update-sql --changelog-file=<changelog.xml>
    liquibase update --changelog-file=<changelog.xml>
    ```

    If your `update` is successful, Liquibase runs each changeset and displays a summary message ending with:

    ```
    Liquibase: Update has been successful.
    Liquibase command 'update' was executed successfully.
    ```

1. From a database UI tool, ensure that your database contains the `test_table` you added along with the [DATABASECHANGELOG table](https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html) and [DATABASECHANGELOGLOCK table](https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html).

Now you're ready to start making deployments with Liquibase!

## Related links

* [Database Change Management on Lakehouse with Databricks SQL and Liquibase](https://medium.com/dbsql-sme-engineering/database-change-management-on-lakehouse-with-databricks-sql-and-liquibase-c3c238781616)
* [Advanced Schema Management on Databricks with Liquibase](https://medium.com/dbsql-sme-engineering/advanced-schema-management-on-databricks-with-liquibase-1900e9f7b9c0)