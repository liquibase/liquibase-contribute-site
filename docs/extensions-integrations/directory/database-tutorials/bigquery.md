---
title: BigQuery
---

# Using Liquibase with BigQuery
The Liquibase BigQuery extension enables efficient version control and database change management for BigQuery schema and application data. This extension gives Google BigQuery users a smooth, streamlined approach to database change management and deployment, fitting effortlessly into Agile development and CI/CD automation practices. 

[Google BigQuery](https://cloud.google.com/bigquery/) is a fully managed analytics data warehouse. For more information, see [BigQuery Documentation](https://cloud.google.com/bigquery/docs).

Read more about [Database DevOps with Liquibase and BigQuery](https://www.liquibase.com/blog/bigquery-schema-management-version-control-database-devops).

## Supported database versions
* 2.13.6+

!!! Warning
    Liquibase versions 4.24.0, 4.25.0, and 4.25.1 transformed table names to lowercase which caused Liquibase to not be able to read its own DATABASECHANGELOG table so it would create a new one and redeploy all previously deployed changesets.
    
    This issue is fixed in Liquibase 4.26.0 and later releases.

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

1. (optional) Enable Liquibase Pro capabilities

    To apply a [Liquibase Pro key](https://www.liquibase.com/trial) to your project, add the following property to the Liquibase properties file:
    
    ```
    liquibase.licenseKey: <paste key here>
    ```

### Maven users (additional step)
If you use Maven, you must [include the driver JAR as a dependency](https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html) in your `pom.xml` file.

```
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

## Database connection

### Configure connection
1. Ensure your BigQuery database is configured. See [BigQuery Quickstarts](https://cloud.google.com/bigquery/docs/quickstarts) for more information. For example, you can run a query of a sample table in BigQuery using the [`bq` command-line tool](https://cloud.google.com/bigquery/docs/quickstarts/load-data-bq).
    ```
    bq show bigquery-public-data:samples.shakespeare
    ```

1. Specify the JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other Liquibase property default values. Liquibase does not parse the JDBC URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format.

    ```
    url: jdbc:bigquery://<Host>:<Port>;ProjectId=<Project>;OAuthType=<AuthValue>;<Property1>=<Value1>;<Property2>=<Value2>;...
    ```
    
    * Specify the ID of your BigQuery project as the value of `ProjectId`.
    * Specify your BigQuery authentication method as the value of `OAuthType`.

    !!! note
         Detailed information on JDBC Connections for Big Query can be found here: [Simba Google BigQuery JDBC Connector Install and Configuration Guide_1.5.0.1001.pdf](https://storage.googleapis.com/simba-bq-release/jdbc/Simba%20Google%20BigQuery%20JDBC%20Connector%20Install%20and%20Configuration%20Guide_1.5.0.1001.pdf)

    Click on the following tabs to see example JDBC URLs for each authentication type.
    
    ===+ "OAuthType=0"

        **Google Services**

        Requires the options `OAuthServiceAcctEmail` and `OAuthPvtKeyPath` in your Liquibase `url` property.

        For example:
        ```
        jdbc:bigquery://https://googleapis.com/bigquery/v2:443;
        ProjectId=myProject;
        OAuthType=0;
        OAuthServiceAcctEmail=lbtest@bq123.iam.gserviceaccount.com;
        OAuthPvtKeyPath=C:\path\serviceKey.p12;
        ```
        ---

    === "OAuthType=1"

        **Google User Account authentication**

        Requires your user account credentials.

        For example:
        ```
        jdbc:bigquery://https://googleapis.com/bigquery/v2:443;
        ProjectId=myProject;
        OAuthType=1;
        ```
        ---

    === "OAuthType=2"

        **Google Authorization Server Access Token**

        Requires the options `OAuthAccessToken`, `OAuthRefreshToken`, `OAuthClientId`, and `OAuthClientSecret` in your Liquibase `url` property.

        For example:
        ```
        jdbc:bigquery://https://googleapis.com/bigquery/v2:443;
        ProjectId=myProject;
        OAuthType=2;
        OAuthAccessToken=a25c7cfd36214f94a79d;
        OAuthRefreshToken=2kl0Qvuw9qt4abia54qga5t97;
        OAuthClientId=22n6627g243322f7;
        OAuthClientSecret=cDE+F2g3Hcjk4K5lazM;
        ```
        ---

    === "OAuthType=3"

        **Application Default Credentials**

        For example:
        ```
        jdbc:bigquery://https://googleapis.com/bigquery/v2:443;
        ProjectId=myProject;
        OAuthType=3;
        ```
        ---

    === "OAuthType=4"

        **Workload or Workforce identity federation**

        For example:
        ```
        jdbc:bigquery://https://googleapis.com/bigquery/v2:443;
        ProjectId=myProject;
        OAuthType=4;
        OAuthPvtKeyPath=/__w/liquibase-repo/gha-creds-80d532a88cc2f6d6.json;
        ```
        ---

### Test connection

--8<-- "database-tutorial-relational-test-connection-example.md"

## Troubleshooting
In version 4.27.0.1 and earlier of the Liquibase BigQuery extension, Liquibase automatically makes a JDBC connection to the US region if you don't specify another location in the JDBC URL. This behavior may be unexpected.

For example, if you have a multi-region dataset in the EU and a primary replica in the US, you may expect Liquibase to use EU as your region. However, if you don't specify that in the Liquibase `url` parameter, you may receive this error message:

`The dataset replica of the cross region dataset '<dataset_id>' in region 'US' is read-only because it's not the primary replica.`

To specify your intended region, add `;Location=<value>` to your URL:

`url: jdbc:bigquery://...;OAuthType=0;Timeout=10000;Location=<value>`

In version 4.27.0.2 and later of the Liquibase BigQuery extension, Liquibase does not use the US region as a default. When you have multiple datasets in different regions, the BigQuery JDBC driver automatically chooses the correct region based on your datasets. Liquibase uses the region the driver auto-routes to. Optionally, you can still manually specify the region in the Liquibase `url` parameter.

## Related links
* [Change Types](https://docs.liquibase.com/change-types/home.html)
* [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
