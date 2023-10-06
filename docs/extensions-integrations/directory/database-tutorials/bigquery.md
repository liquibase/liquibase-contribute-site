---
title: BigQuery
---

# Using Liquibase with BigQuery
The Liquibase BigQuery extension enables efficient version control and database change management for BigQuery schema and application data. This extension gives Google BigQuery users a smooth, streamlined approach to database change management and deployment, fitting effortlessly into Agile development and CI/CD automation practices. 

[Google BigQuery](https://cloud.google.com/bigquery/) is a fully managed analytics data warehouse. For more information, see [BigQuery Documentation](https://cloud.google.com/bigquery/docs).

Read more about [Database DevOps with Liquibase and BigQuery](https://www.liquibase.com/blog/bigquery-schema-management-version-control-database-devops).

## Supported database versions
* 2.13.6+

## Prerequisites
1. [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html) – Dive into Liquibase concepts.
1. [Install Liquibase](https://docs.liquibase.com/start/install/home.html) – Download Liquibase on your machine.
1. [How to Apply Your Liquibase Pro License Key](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html) – If you use Liquibase Pro, activate your license.

## Install drivers
To use Liquibase and BigQuery, you need several JAR files.

### All users

1. Download the required JAR files

    * All of the JAR files in the [BigQuery JDBC ZIP file](https://cloud.google.com/bigquery/docs/reference/odbc-jdbc-drivers#current_jdbc_driver) (under "Current JDBC driver")
    * The [Liquibase extension for Google BigQuery](https://github.com/liquibase/liquibase-bigquery/releases) (`liquibase-bigquery-{version}.jar` in the Assets section)

1. Copy your JAR files into your Liquibase installation

    [Place your JAR file(s)](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `liquibase/lib` directory.

### Maven users (additional step)
1. If you use Maven, you must [include the driver JAR as a dependency](https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html) in your `pom.xml` file.

    ``` xml
        <dependency>
          <groupId>com.google.cloud</groupId>
          <artifactId>google-cloud-bigquery</artifactId>
          <version>2.24.4</version>
        </dependency>
        <dependency>
          <groupId>org.liquibase.ext</groupId>
          <artifactId>liquibase-bigquery</artifactId>
          <version>{{liquibase.current_version}}</version>
        </dependency>
    ```

## Configure database connection
1. Ensure your BigQuery database is configured. See [BigQuery Quickstarts](https://cloud.google.com/bigquery/docs/quickstarts) for more information. For example, you can run a query of a sample table in BigQuery using the [`bq` command-line tool](https://cloud.google.com/bigquery/docs/quickstarts/load-data-bq).
    ```
    bq show bigquery-public-data:samples.shakespeare
    ```

1. Specify the JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other Liquibase property default values. Liquibase does not parse the JDBC URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format.
    ```
    url: jdbc:bigquery://https://googleapis.com/bigquery/v2:443/<dbname>;ProjectId=<project id string>;OAuthType=<oauth type number>;
    ```
    * Specify the name of your database in place of `dbname`.
    * Specify the ID of your BigQuery project as the value of `ProjectId`.
    * Specify your BigQuery authentication method as the value of `OAuthType`. Click on the following tabs to see example JDBC URLs for each authentication type.

        ===+ "OAuthType=0"

            **Google Services**
    
            Requires the options `OAuthServiceAcctEmail` and `OAuthPvtKeyPath` in your Liquibase `url` property.

            For example:
    
            ```
            jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
            ProjectId=myProject;
            OAuthType=0;
            OAuthServiceAcctEmail=lbtest@bq123.iam.gserviceaccount.com;
            OAuthPvtKeyPath=C:\path\serviceKey.p12;
            ```
  
            For more information, see:
  
            * <a href="https://cloud.google.com/bigquery/docs/authentication/service-account-file">BigQuery: Authenticating with a service account key file</a>
            * <a href="https://developers.google.com/identity/protocols/oauth2/service-account">Google: Using OAuth 2.0 for Server to Server Applications</a>

        === "OAuthType=1"

            
            Requires your user account credentials.
    
            For example:
    
            ```
            jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
            ProjectId=myProject;
            OAuthType=1;
            ```
    
            For more information, see [Google: Authenticate installed apps with user accounts](https://cloud.google.com/docs/authentication/end-user)

        === "OAuthType=2"

            **Google Authorization Server Access Token**
    
            Requires the options `OAuthAccessToken`, `OAuthRefreshToken`, `OAuthClientId`, and `OAuthClientSecret` in your Liquibase `url` property.
                
            For example:
    
            ```
            jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
            ProjectId=myProject;
            OAuthType=2;
            OAuthAccessToken=a25c7cfd36214f94a79d;
            OAuthRefreshToken=2kl0Qvuw9qt4abia54qga5t97;
            OAuthClientId=22n6627g243322f7;
            OAuthClientSecret=cDE+F2g3Hcjk4K5lazM;
            ```
    
            For more information, see:
    
            * [BigQuery: Authorizing API requests](https://cloud.google.com/bigquery/docs/authorization)
            * [Google: Using OAuth 2.0 to Access Google APIs](https://developers.google.com/identity/protocols/oauth2/)

        === "OAuthType=3"

            **Application Default Credentials**
  
            For example:
    
            ```
            jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
            ProjectId=myProject;
            OAuthType=3;
            ```
    
            For more information, see [Google: Authenticate installed apps with user accounts](https://cloud.google.com/docs/authentication#service-accounts)

    !!! tip
        To apply a Liquibase Pro key to your project, add the following property to the Liquibase properties file:
        ```
        liquibase.licenseKey: <paste key here>
        ```

## Test database connection
1. Create a text file called [changelog](https://docs.liquibase.com/concepts/changelogs/home.html) (`.xml`, `.sql`, `.json`, or `.yaml`) in your project directory and add a [changeset](https://docs.liquibase.com/concepts/changelogs/changeset.htmlhttps://docs.liquibase.com/concepts/changelogs/changeset.html).

    If you already created a changelog using the [`init project`](https://docs.liquibase.com/commands/init/project.html) command, you can use that instead of creating a new file. When adding onto an existing changelog, be sure to only add the changeset and to not duplicate the changelog header.
    
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
        
        ``` sql
        -- liquibase formatted sql

        -- changeset liquibase:1
        CREATE TABLE test_table (test_id INT, test_column VARCHAR(255), PRIMARY KEY (test_id))
        ```
    
        !!! tip
            Formatted SQL changelogs generated from Liquibase versions before 4.2.0 might cause issues because of the lack of space after a double dash ( `--` ). To fix this, add a space after the double dash. For example: ``-- liquibase formatted sql` instead of `--liquibase formatted sql` and `-- changeset myname:create-table` instead of `--changeset myname:create-table`
    
    === "YAML example"
        
        ``` yaml
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
        ``` json
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

1. Navigate to your project folder in the CLI and run the Liquibase [`status`](https://docs.liquibase.com/commands/change-tracking/status.html) command to see whether the connection is successful:

    ```
    liquibase status --username=test --password=test --changelog-file=<changelog.xml>
    ```

    !!! note
        You can specify arguments in the CLI or keep them in the <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html">Liquibase properties file.
  
    If your connection is successful, you'll see a message like this:

    ```
    4 changesets have not been applied to <your_jdbc_url>
    Liquibase command 'status' was executed successfully.
    ```

1. Inspect the SQL with the [`update-sql`](https://docs.liquibase.com/commands/update/update-sql.html) command. Then make changes to your database with the [`update`](https://docs.liquibase.com/commands/update/update.html) command.

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
* [Change Types](https://docs.liquibase.com/change-types/home.html)
* [Liquibase Commands](https://docs.liquibase.com/commands/home.html)

