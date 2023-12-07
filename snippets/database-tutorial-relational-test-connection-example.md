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
    liquibase status --username=test --password=test --changelog-file=<changelog.xml>
    ```

    !!! note
        You can specify arguments in the CLI or keep them in the <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html">Liquibase properties file.
  
    If your connection is successful, you'll see a message like this:

    ```
    1 changeset has not been applied to <your_jdbc_url>
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
