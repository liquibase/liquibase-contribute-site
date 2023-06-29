---
title: SingleStoreDB
---

# Using Liquibase with SingleStoreDB
[SingleStoreDB](https://www.singlestore.com/) unifies transactions and analytics in a single engine to drive low-latency access to large datasets, simplifying the development of fast, modern enterprise applications.

## Supported Versions
* SingleStoreDB v8.1+

## Prerequisites
1. [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html) – Dive into Liquibase concepts.
2. [Install Liquibase](https://docs.liquibase.com/start/install/home.html) – Download Liquibase on your machine.
3. [Get Started with Liquibase](https://docs.liquibase.com/start/home.html) – Learn how to use Liquibase with an example database.
4. [Design Your Liquibase Project](https://docs.liquibase.com/start/design-liquibase-project.html) – Create a new Liquibase project folder and organize your changelogs
5. [How to Apply Your Liquibase Pro License Key](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html) – If you use Liquibase Pro, activate your license.

## Install Drivers
To use Liquibase and SingleStoreDB you need two JAR files:

* The [SingleStoreDB JDBC driver](https://docs.singlestore.com/managed-service/en/developer-resources/connect-with-application-development-tools/connect-with-java-jdbc/the-singlestore-jdbc-driver.html)
* The Liquibase [extension for SingleStoreDB](https://github.com/liquibase/liquibase-singlestore/releases)

[Place your JAR files](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `liquibase/lib` directory.

If you use Maven, you must include the driver JARs as dependencies in your `pom.xml` file.
    
    <dependency>
        <groupId>com.singlestore</groupId>
        <artifactId>singlestore-jdbc-client</artifactId>
        <version>1.1.5</version>
    </dependency>
    <dependency>
        <groupId>org.liquibase.ext</groupId>
        <artifactId>liquibase-singlestore</artifactId>
        <version>1.0.0</version>
    </dependency>

## Test Your Connection
Step 1. Ensure your SingleStoreDB database is configured. See the [SingleStoreDB Getting Started documentation](https://docs.singlestore.com) for more information.

Step 2. Specify the database URL in the [liquibase.properties](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format: `jdbc:singlestore://localhost:3306/dev`

Step 3. Create a text file called [changelog](https://docs.liquibase.com/concepts/changelogs/home.html) (.xml, .sql, .json, or .yaml) in your project directory and add a [changeset](https://docs.liquibase.com/concepts/changelogs/changeset.html).

If you already created a changelog using the [init project](https://docs.liquibase.com/commands/init/project.html) command, you can use that instead of creating a new file. When adding onto an existing changelog, be sure to only add the changeset and to not duplicate the changelog header.

### XML Example
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

### SQL Example
    -- liquibase formatted sql
    
    -- changeset liquibase:1
    CREATE TABLE test_table (test_id INT, test_column VARCHAR(255), PRIMARY KEY (test_id))

**Tip**: Formatted SQL changelogs generated from Liquibase versions before 4.2 might cause issues because of the lack of space after a double dash ( `--` ). To fix this, add a space after the double dash. For example: `-- liquibase formatted sql` instead of `--liquibase formatted sql` and `-- changeset myname:create-table` instead of `--changeset myname:create-table`.

### JSON Example
    {
       "databaseChangeLog":[
          {
             "changeSet":{
                "id":"1",
                "author":"Liquibase",
                "changes":[
                   {
                      "createTable":{
                         "tableName":"test_table",
                         "columns":[
                            {
                               "column":{
                                  "name":"test_column",
                                  "type":"INT",
                                  "constraints":{
                                     "primaryKey":true,
                                     "nullable":false
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

### YAML Example
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

Step 4. Navigate to your project folder in the CLI and run the Liquibase [status](https://docs.liquibase.com/commands/change-tracking/status.html) command to see whether the connection is successful:

    liquibase status --username=test --password=test --changelog-file=<changelog.xml>

**Note**: You can specify arguments in the CLI or keep them in the [Liquibase properties file](https://docs.liquibase.com/concepts/connections/creating-config-properties.html).

If your connection is successful, you'll see a message like this:

    4 changesets have not been applied to <your_jdbc_url>
    Liquibase command 'status' was executed successfully.

Step 5. Inspect the SQL with the [update-sql](https://docs.liquibase.com/commands/update/update-sql.html) command. Then make changes to your database with the [update](https://docs.liquibase.com/commands/update/update.html) command.

    liquibase update-sql --changelog-file=<changelog.xml>
    liquibase update --changelog-file=<changelog.xml>

If your update is successful, Liquibase runs each changeset and displays a summary message ending with:

    Liquibase: Update has been successful.
    Liquibase command 'update' was executed successfully.

Step 6. From a database UI tool, ensure that your database contains the `test_table` you added along with the [DATABASECHANGELOG table](https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html) and [DATABASECHANGELOGLOCK table](https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html).

Now you're ready to start making deployments with Liquibase!

## Related Links
* [Change Types](https://docs.liquibase.com/change-types/home.html)
* [Liquibase Commands](https://docs.liquibase.com/commands/home.html)



