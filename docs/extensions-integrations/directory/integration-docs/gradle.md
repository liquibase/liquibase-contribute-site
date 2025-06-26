---
title: Gradle
---

# Getting Started with Liquibase and Gradle

Using the [Liquibase Gradle plugin](https://github.com/liquibase/liquibase-gradle-plugin) helps to manage database scripts, build and automate your software processes. When Gradle applies the plugin to the target, it creates a Gradle task for each command supported by Liquibase. To see the list of those tasks, run the `gradle tasks` command.

To use Liquibase and Gradle:

1.  Create a text file called `build.gradle` in your project folder or use the existing `build.gradle` file.
2.  Add the following section to your `build.gradle` file to include the `liquibase` plugin into Gradle builds:
    ```
    plugins {
        id 'org.liquibase.gradle' version '2.2.0'
    }
    ```

    The following [legacy plugin application](https://docs.gradle.org/current/userguide/plugins.html#sec:old_plugin_application) is also available to use:

    ```
    buildscript {
        repositories {
            mavenCentral()
            }
            dependencies {
                classpath "org.liquibase:liquibase-gradle-plugin:2.2.0"
            }
    }
    apply plugin: 'org.liquibase.gradle'
    ```

3.  Add the `dependencies` section to include files on which Liquibase will depend to run commands. The plugin needs to find Liquibase when it runs a task, and Liquibase needs to find database drivers, changelog parsers, and other files on the classpath. When adding `liquibaseRuntime` dependencies to the `dependencies` section in the `build.gradle` file, include the Liquibase value along with your database driver:

    ```
    dependencies {
        liquibaseRuntime 'org.liquibase:liquibase-core:4.24.0'
        liquibaseRuntime 'org.liquibase:liquibase-groovy-dsl:2.1.1'
        liquibaseRuntime 'info.picocli:picocli:4.7.5'
        liquibaseRuntime 'org.yaml:snakeyaml:1.33'
        liquibaseRuntime 'mysql:mysql-connector-java:5.1.34'
    }
    apply plugin: "org.liquibase.gradle"
    ```

    Replace `org.liquibase:liquibase-core:4.24.0` and `mysql:mysql-connector-java:5.1.34` with your values.
    
    If you use Groovy scripts for database changes, the example code includes the [Liquibase Groovy DSL](https://github.com/liquibase/liquibase-groovy-dsl) dependency, which parses changelogs written in a Groovy DSL. You do not need to add `org.liquibase:liquibase-groovy-dsl:2.1.1` if you do not use the Groovy changelog format. For more information, see Step 4.

4.  Create a text file in your application directory and name it `changelog.sql`. Liquibase also supports the XML, YAML, and JSON changelog formats. Another way to use Liquibase and Gradle is with the `changelog.groovy` file.

5.  Add changesets to your changelog file. Use the following examples depending on the format of the changelog you created:

    === "Groovy example"
         ``` groovy
         databaseChangeLog = {
            changeSet(id: '1', author: 'liquibase') {
                createTable(tableName: 'test_table') {
                    column(name:'test_id', type="int") {
                        constraints(primaryKey: true)
                    }
                    column(name:'test_column', type="int")
                }
            }
         }
         ```
         ---

    === "SQL example"
          ``` sql
          -- liquibase formatted sql

          -- changeset my_name:1
          CREATE TABLE test_table 
          (
            test_id INT, 
            test_column INT, 
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
                <column name="test_column" type="INT"/>
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
                  name: test_id
                    type: INT
                    constraints:
                      primaryKey:  true
                      nullable:  false
                - column:
                  name: test_column
                    type: INT
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
                              "name": "test_id",
                              "type": "INT",
                              "constraints": {
                                "primaryKey": true,
                                "nullable": false
                              }
                            }
                          },
                          {
                            "column": {
                              "name": "test_column",
                              "type": "INT"
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

6.  Set the following Liquibase properties in your `build.gradle` file, and do your first update by adding the task section.

    !!! Note
        Replace the values from the example with your values.

    !!! Tip
        The following error can often be fixed by changing the Java version or deleting IntelliJ caches. ([source article](https://stackoverflow.com/questions/32905270/intellij-idea-and-gradle-cannot-be-applied-to-groovy-lang-closure))

        ```
        main in build cannot be applied to (groovy.lang.Closure)
        ```

    !!! Tip
        To store other Liquibase parameters in a file instead of passing them at runtime, you can either specify the properties in the `build.gradle` file or create a new text file called `liquibase.properties` and set them there.
    
        If you create a Liquibase properties file, specify `propsFile "../<liquibase.properties>"` in the main section of the `build.gradle` file, where `<liquibase.properties>` represents the name of the Liquibase properties file.
    
        For more information, see [Create and Configure a liquibase.properties File](https://docs.liquibase.com/concepts/connections/creating-config-properties.html).


    ```
    task('deploy changeLog') {
        doFirst() {
            liquibase {
                activities {
                    main {
                        changeLogFile System.properties.liquibaseChangeLogFile
                        url "mysql://localhost:3306/testdatabase"
                        username "username"
                        password "password"
                        contexts System.properties.liquibaseContexts
                    }
                }
            }
        }
    }
    update.dependsOn('deploy changeLog')
    ```

7.  Run the `gradle build` command, and then run the following:

    ```
    gradle update
    ```

    After your first update, you will see a new table along with the [DATABASECHANGELOG table](https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html) and [DATABASECHANGELOGLOCK table](https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html) added to the database.
    
    !!! Note
        Don't worry if you see "UPDATE SUMMARY" listed twice. The update is occuring once, but the output is getting displayed by both Liquibase and the Gradle extension.
        ```
        [2024-01-26 14:37:14] INFO [liquibase.ui] Database is up to date, no changesets to execute
        [2024-01-26 14:37:14] INFO [liquibase.changelog] Reading from public.databasechangelog
        [2024-01-26 14:37:14] INFO [liquibase.util] UPDATE SUMMARY
        [2024-01-26 14:37:14] INFO [liquibase.util] Run:                          0
        [2024-01-26 14:37:14] INFO [liquibase.util] Previously run:              27
        [2024-01-26 14:37:14] INFO [liquibase.util] Filtered out:                 0
        [2024-01-26 14:37:14] INFO [liquibase.util] -------------------------------
        [2024-01-26 14:37:14] INFO [liquibase.util] Total change sets:           27
        UPDATE SUMMARY
        Run:                          0
        Previously run:              27
        Filtered out:                 0
        -------------------------------
        Total change sets:           27
        ```

8.  \[Optional\] Do your first rollback by using the `rollback-count` command:

    ```
    gradle build
    gradle rollbackCount -PliquibaseCommandValue=1
    ```

    !!! Note
        You can also specify the command value in the `build.gradle` file or use other `rollback` commands: [rollback](https://docs.liquibase.com/commands/rollback/rollback-by-tag.htm), [rollback-to-date](https://docs.liquibase.com/commands/rollback/rollback-to-date.htm), [rollback-one-changeset](https://docs.liquibase.com/commands/rollback/rollback-one-changeset.html), [rollback-one-update](https://docs.liquibase.com/commands/rollback/rollback-one-update.html).

    !!! Tip
        Automatic rollback is not supported for formatted SQL changesets. You need to add custom rollback statements to formatted SQL changesets if you want to use rollback commands:

    ```
    -- changeset liquibase:1
    create table test_table ( id int primary key, name varchar(255) );
    -- rollback drop table test_table;
    ```

9.  Check the changes by inspecting your database or running the [status](https://docs.liquibase.com/commands/change-tracking/status.htm) command.

# Related links

*   [Gradle](https://gradle.org/)
*   [Liquibase Gradle repository](https://github.com/liquibase/liquibase-gradle-plugin)
*   [Liquibase Groovy DSL](https://github.com/liquibase/liquibase-groovy-dsl)
*   [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html)

