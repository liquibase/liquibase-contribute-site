---
title: Neo4j
---

# Using Liquibase with Neo4j

[Neo4j](https://neo4j.com/) is a property graph database management system with native graph storage and processing. It uses a graph query language called [Cypher](https://neo4j.com/docs/getting-started/cypher-intro/). For more information, see [Neo4j Documentation](https://neo4j.com/docs/).

## Supported database versions

*   3.5+

## Prerequisites

* [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html) – Dive into Liquibase concepts.
* [Install Liquibase](https://docs.liquibase.com/start/install/home.html) – Download Liquibase on your machine.

To access Neo4j, do one of the following:

*   [Download Neo4j Desktop locally](https://neo4j.com/download/)
*   [Configure Neo4j locally with Docker](https://hub.docker.com/_/neo4j)
*   [Create a Neo4j AuraDB cloud instance](https://neo4j.com/cloud/platform/aura-graph-database/)
*   [Launch a Neo4j Aura sandbox instance](https://neo4j.com/sandbox/)

## Install drivers

### All Users

1. Download the following JAR files:
    * [Liquibase extension for Neo4j](https://github.com/liquibase/liquibase-neo4j/releases) ([Maven Link](https://mvnrepository.com/artifact/org.liquibase.ext/liquibase-neo4j))
    
    !!! Warning
        **Liquibase 4.23.0 is not compatible with the Neo4j Extension**
        
        Upgrade both core and the extension to 4.23.1 (or later).
        
        Or, use Liquibase core 4.21.1 and the Neo4j extension at version 4.21.1.2.

1. [Place the JAR file(s)](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `liquibase/lib` directory.

!!! Note
    The Neo4j extension has native JDBC connectivity support in version 4.19.0+.
    
    If you're using an earlier version, you must also install a third-party [JDBC driver](https://github.com/neo4j-contrib/neo4j-jdbc) 
    to connect to Liquibase. 
    
    For driver configuration information, see [Neo4j Configuration](https://github.com/liquibase/liquibase-neo4j/blob/main/docs/reference-configuration.md). 
    For additional JARs to integrate Neo4j with your preferred programming language, see [Connecting to Neo4j](https://neo4j.com/docs/getting-started/current/languages-guides/).
    
### Maven Users (additional step)

If you use Maven, you must [include the driver JAR as a dependency](https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html) in your `pom.xml` file.

```
<dependency>
    <groupId>org.liquibase.ext</groupId>
    <artifactId>liquibase-neo4j</artifactId>
    <version>[4.26.0.1,)</version>
</dependency>
```

## Test your connection

1.  Ensure your Neo4j database is configured. See [Neo4j Operations Manual](https://neo4j.com/docs/operations-manual/current/) and [Neo4j AuraDB: Creating an instance](https://neo4j.com/docs/aura/auradb/getting-started/create-database/) for more information.
2.  Specify the database URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can either specify the full database connection string or specify the URL using your database's standard JDBC format:

    ```
    url: jdbc:neo4j:bolt://<host>:<port>
    ```
      
    !!! Note
        The Liquibase extension for Neo4j only supports connections through the Bolt protocol, not HTTP.

        For more information about the JDBC connection, see [Neo4j JDBC Driver Documentation § Technical Reference](http://neo4j-contrib.github.io/neo4j-jdbc/).

    !!! Tip
         To apply a Liquibase Pro key to your project, add the following property to the Liquibase properties file: `licenseKey: <paste code here>`

3.  Create a text file called [changelog](https://docs.liquibase.com/concepts/changelogs/home.html) (`.xml`, `.cypher`, `.json`, or `.yaml`) in your project directory and add a [changeset](https://docs.liquibase.com/concepts/changelogs/changeset.html). The `<neo4j:cypher>` change type has the same behavior as the <[`sql`](https://docs.liquibase.com/change-types/sql.html)> change type. For more information about Cypher syntax, see the [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/introduction/) (general syntax) the [Neo4j Extension Cypher Manual](https://github.com/liquibase/liquibase-neo4j/blob/main/docs/reference-features.md) (Liquibase syntax).

    === "Cypher (SQL) example"

        ```
        -- liquibase formatted cypher
    
        -- changeset liquibase:1
        CREATE (:TestNode {test_property: "Value"});
        ```

        !!! Tip
            Formatted SQL/Cypher changelogs generated from Liquibase versions before 4.2 might cause issues because of the lack of 
            space after a double dash ( `--` ). To fix this, add a space after the double dash. 
            
            For example: 
            
            `-- liquibase formatted cypher` instead of `--liquibase formatted cypher` and 
           
            `-- changeset myname:create` instead of `--changeset myname:create`.

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
                <neo4j:cypher>CREATE (:TestNode {test_property: "Value"});</neo4j:cypher>
            </changeSet>
        </databaseChangeLog>
        ```

    === "YAML example"

        ```
        databaseChangeLog:
           - changeSet:
               id: 1
               author: Liquibase
               changes:
               - neo4j:cypher:
                   sql: CREATE (:TestNode {test_property: "Value"});
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
                    "neo4j:cypher": {
                      "sql": CREATE (:TestNode {test_property: "Value"});
                    }
                  }
                ]
              }
            }
          ]
        }
        ```

4.  Navigate to your project folder in the CLI and run the Liquibase [status](https://docs.liquibase.com/commands/change-tracking/status.html) command to see whether the connection is successful:

    ```
    liquibase status --username=test --password=test --changelog-file=<changelog.xml>
    ```

    !!! Note
        You can pass arguments in the CLI or keep them in the Liquibase properties file.

5.  Inspect the SQL with the [update-sql](https://docs.liquibase.com/commands/update/update-sql.html) command. Then make changes to your database with the [update](https://docs.liquibase.com/commands/update/update.html) command.

    ```
    liquibase update-sql --changelog-file=<changelog.xml>
    liquibase update --changelog-file=<changelog.xml>
    ```

6.  From the Neo4j browser, ensure that your database contains the changelog node by running `MATCH (c:__LiquibaseChangeLog) RETURN c`.
    
    !!! Note
        The `__LiquibaseChangeLogLock` node is only present during an active Liquibase execution, not after, so it isn't normally visible.
    

## Related links

*   [Neo4j documentation: Neo4j Plugin for Liquibase](https://neo4j.com/labs/liquibase/docs/)
*   [Neo4j documentation: Welcome to Neo4j](https://neo4j.com/docs/getting-started/current/)
*   [Neo4j documentation: Graph database concepts](https://neo4j.com/docs/getting-started/current/appendix/graphdb-concepts/)