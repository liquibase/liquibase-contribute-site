---
title: Percona Toolkit
---

# Using the Liquibase Percona Toolkit Extension

[Percona Toolkit](https://www.percona.com/doc/percona-toolkit/LATEST/index.html) is a collection of command-line tools, which help to perform MySQL, MongoDB, and system tasks that are too difficult or complex to perform manually.

There is a Liquibase extension that supports the Percona Toolkit [pt-online-schema-change](https://www.percona.com/doc/percona-toolkit/LATEST/pt-online-schema-change.html). This extension replaces some default changes to use `pt-online-schema-change` instead of SQL. The `pt-online-schema-change` tool will allow you to perform a database upgrade without locking tables.

Currently, MySQL is the only supported database. The [liquibase-percona](https://github.com/liquibase/liquibase-percona) extension checks whether you run it against a MySQL database, and if your database is not MySQL, the extension falls back to the default changes provided by Liquibase.

## Installing the Liquibase Percona Toolkit extension

To use the Liquibase Percona Toolkit extension, follow these steps:

### All Users

1.  Ensure that you have installed the latest version of [Liquibase](https://www.liquibase.org/download). If you use earlier versions of Liquibase, you need to have a [compatible version](https://github.com/liquibase/liquibase-percona/blob/master/README.md#changelog) of the extension.

    !!! note
        For more information about installation or upgrade, see [Installing Liquibase](https://docs.liquibase.com/start/install/liquibase-windows.htm).

2.  Download the [liquibase-percona-<version>.jar](https://github.com/liquibase/liquibase-percona/releases) file from the liquibase-percona repository.

3. [Place the JAR file](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) in the `liquibase/lib` directory.


### Maven Users (additional step)
If you use Maven, you must also [include the driver JAR as a dependency](https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html) in your `pom.xml` file using the following code.

```
<dependency>
  <groupId>org.liquibase.ext</groupId>
  <artifactId>liquibase-percona</artifactId>
  <version>4.3.0</version>
</dependency>
```

After adding the dependency, you can start using the Liquibase Percona Toolkit extension.

## Configuring the Liquibase Percona Toolkit extension

You can use flags and system properties to control the use of Percona Toolkit when performing updates for your database.

### Flags

To use Percona Toolkit flags, add the needed flag to your changelog:

#### usePercona

!!! note
    Available as of liquibase-percona 1.3.0.

Each change allows you to enable or disable the use of Percona Toolkit by adding the `usePercona` flag. For more information, see the `liquibase.percona.defaultOn` system property.

=== "YAML example"

    ```
    - changeSet:
        id: 2
        author: Alice
        changes:
          - addColumn:
              tableName: person
              usePercona: false
              columns:
                - column:
                    name: address
                    type: varchar(255)
    ```

=== "XML example"

    The `usePercona` has been supported in XML changelogs since Liquibase 3.6.0.

    ```
    <addColumn tableName="person"
    xmlns:liquibasePercona="http://www.liquibase.org/xml/ns/dbchangelog-ext/liquibase-percona"
        liquibasePercona:usePercona="false">
      <column name="address" type="varchar(255)"/>
    </addColumn>
    ```

#### perconaOptions

!!! note
     Available as of liquibase-percona 2.0.0.

Each change allows you to specify options that are used when executing `pt-online-schema-change`. If you specify the `perconaOptions` flag, it will override the [liquibase.percona.options](#System) system property. If you don’t specify the `perconaOptions` flag, then the system property will be used.

=== "YAML example"

    ```
    - changeSet:
         id: 2
         author: Alice
         changes:
           - addColumn:
               tableName: person
               perconaOptions: "--alter-foreign-keys-method=auto"
               columns:
                 - column:
                     name: address
                     type: varchar(255)
    ```

=== "XML example"

    ```
    <addColumn tableName="person"
    xmlns:liquibasePercona="http://www.liquibase.org/xml/ns/dbchangelog-ext/liquibase-percona"
       liquibasePercona:perconaOptions="--alter-foreign-keys-method=auto">
      <column name="address" type="varchar(255)"/>
    </addColumn>
    ```

### System properties

You can set system properties by using the standard `java -D` option:

```
java -Dliquibase.percona.skipChanges=createIndex,dropColumn -jar liquibase.jar ...
```

Also, to set the system property, you need to specify a Liquibase `.jar` file and ensure that the `liquibase-percona.jar` file is on the classpath (the `--classpath` option).

When executing Liquibase through Maven, you can use the [Properties Maven Plugin](https://www.mojohaus.org/properties-maven-plugin/usage.html#set-system-properties) to set the system property.

 

| <div style="width:250px">Property</div> | <div style="width:100px">Values</div> | Description |
| -------------- | ----------- |----------- |
| `liquibase.percona.failIfNoPT` | `true/false` | Allows you to enforce the use of Percona Toolkit. If the property is set to true and the command `pt-online-schema-change` is not found, the database update will fail.<br/><br/>The default value: false. |
| `liquibase.percona.noAlterSqlDryMode` | `true/false` | Allows you to generate SQL statements and puts them into the migration file. When running `update-sql` or `rollback-sql` to generate a migration SQL file, the command line that is executed will be added as a comment. Additionally, the SQL statements will be generated and put into the migration file. This allows executing the generated migration SQL to perform an update; however, Percona Toolkit will not be used. If the `liquibase.percona.noAlterSqlDryMode` property is set to true, then no SQL statements will be put into the migration file.<br/><br/>The default value: false. |
| `liquibase.percona.skipChanges` | `<comma-separated list of changeTypes>` | Allows you to disable one or more changes. If a change is disabled when using Percona Toolkit, then this change will be executed by the default Liquibase implementation, and Percona Toolkit will not be used.<br/><br/>By default, the `liquibase.percona.skipChanges` property is empty so that all supported changes are executed using Percona Toolkit.<br/><br/>For example, if you don’t want to use Persona Toolkit for adding or dropping a column, set the property to `addColumn, dropColumn`. |
| `liquibase.percona.options` | `string of options` | Allows you to pass additional command line options to `pt-online-schema-change`. For example, you can use this property in a complicated replication setup to change the way subordinate instances (also known as slaves) are detected and how their state is used. You can also specify a Percona configuration file using `--config file.conf` (See [Configuration Files](https://www.percona.com/doc/percona-toolkit/2.2/configuration_files.html)). Multiple options are separated by space. If an argument contains a space, use double quotation marks: `--config "filename with spaces.conf"`.<br/><br/>Since liquibase-percona 1.2.1, the default value is `--alter-foreign-keys-method=auto --nocheck-unique-key-change`. The default value is changed starting with liquibase-percona 1.6.0. |
| `liquibase.percona.defaultOn` | `true/false` | Allows you to change the default behavior for the [usePercona](#usePerco) flag. By default, all changes that are not specified with the `usePercona` flag use the value from this system property. Setting the `liquibase.percona.defaultOn` property to false allows controlling for each change whether to use Percona Toolkit.<br/><br/>The default value: true. Since liquibase-percona 1.3.0. |
| `liquibase.password` | `<database_password>` | Allows you to shortcut the automatic detection of the password from the underlying `java.sql.Connection` (if it fails) or from the default Liquibase properties file. If the `liquibase.password` property is set, then it is used for the password when executing `pt-online-schema-change`.<br/><br/>The default value: `<empty>`. Since liquibase-percona 1.4.0. |
| `liquibase.percona.path` | `<path/pt-online-schema-change.jar>` | Allows you to select a specific Percona Toolkit installation. If this property is not set, then Percona Toolkit will be searched on the PATH. You need to specify the `bin` subfolder of the Percona Toolkit distribution.<br/><br/>The default value: `<empty>`. Since liquibase-percona 1.4.1. |
| `liquibase.percona.ptdebug` | `true/false` | Allows you to enable the debug output of `pt-online-schema-change` by setting the environment variable `PTDEBUG` before starting `pt-online-schema-change`.<br/><br/>The default value: false. Since liquibase-percona 1.5.0. |

For more information about Liquibase and Percona Toolkit changes for each version, see the [Changelog](https://github.com/liquibase/liquibase-percona#changelog) section.

## Running Liquibase Percona Toolkit Changes

The non-locking update is achieved using triggers:

1.  A new temporary table is created, including the added or dropped columns. The data is copied in chunks.
2.  While the copy is in progress, any newly created, deleted, or updated rows are copied. This is done by adding triggers to the original table.
3.  After the copy is finished, the original table is dropped, and the temporary table is renamed.

This means that `pt-online-schema-change` cannot be used if the table already uses triggers. The command `pt-online-schema-change` is searched only on the PATH. Depending on the property [liquibase.percona.failIfNoPT](https://docs.liquibase.com/workflows/liquibase-community/using-the-liquibase-percona-toolkit-extension.html), the update will fail or will run without using `pt-online-schema-change` and lock the table for the duration of the update.

To add the update to your database, add the needed change to the changelog and run the corresponding command based on the following list of supported changes:

### addColumn

!!! note
    The change is supported since liquibase-percona 1.0.0. Automatic rollback is supported.

```
<changeSet id="2" author="Alice">
    <addColumn tableName="person">
    <column name="address" type="varchar(255)"/>
  </addColumn>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="ADD COLUMN address VARCHAR(255)" ...
```

### addForeignKeyConstraint

!!! note
    The change is supported since liquibase-percona 1.3.0. Automatic rollback is supported.

```
<changeSet id="3" author="Alice">
     <addForeignKeyConstraint constraintName="fk_person_address"
         referencedTableName="person" referencedColumnNames="id"
         baseTableName="address" baseColumnNames="person_id"/>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="ADD CONSTRAINT fk_person_address FOREIGN KEY (person_id) REFERENCES person (id)" ...
```

### addPrimaryKey

!!! note
    The change is supported since liquibase-percona 1.7.0. Automatic rollback is not supported. Automatic rollback is not supported by this Percona change (as opposed to the Liquibase `addPrimaryKey` Change Type). `pt-online-schema-change` usually needs a primary key or a unique key to operate properly.

```
<changeSet id="2" author="Alice">
     <addPrimaryKey tableName="person" columnNames="id, name"/>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="DROP PRIMARY KEY, ADD PRIMARY KEY (id, name)" ...
```

!!! note
    If the table already includes a primary key, add the `DROP PRIMARY KEY` statement to the `alter` command first. By default, the `pt-online-schema-change` tool will not execute this change, you must set the additional option `--no-check-alter` (See [check-alter](https://www.percona.com/doc/percona-toolkit/LATEST/pt-online-schema-change.html#id1)). To find out whether a primary key already exists and whether you need the `DROP PRIMARY KEY` statement, a database connection is required.

### addUniqueConstraint

!!! note
    The change is supported since liquibase-percona 1.3.0. Automatic rollback is supported.

```
<changeSet id="2" author="Alice">
     <addUniqueConstraint columnNames="id, name" tableName="person" constraintName="uq_id_name"/>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="ADD CONSTRAINT uq_id_name UNIQUE (id, name)" ...
```

!!! note
    `pt-online-schema-change` is executed with the option [\--nocheck-unique-key-change](https://www.percona.com/doc/percona-toolkit/LATEST/pt-online-schema-change.html#id8). `--nocheck-unique-key-change` enables adding a unique index; however, it can cause data loss since duplicated rows are ignored. For more information, see the [\-- \[no\]check-unique-key-change](https://www.percona.com/doc/percona-toolkit/LATEST/pt-online-schema-change.html#id8) option.

### createIndex

!!! note
    The change is supported since liquibase-percona 1.2.0. Automatic rollback is supported.

```
<changeSet id="2" author="Alice">
     <createIndex indexName="emailIdx" tableName="person" unique="true">
         <column name="email"/>
        </createIndex>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="ADD UNIQUE INDEX emailIdx (email)" ...
```

!!! note
    `pt-online-schema-change` is executed with the option [\--nocheck-unique-key-change](https://www.percona.com/doc/percona-toolkit/LATEST/pt-online-schema-change.html#id8). `--nocheck-unique-key-change` enables adding a unique index; however, it can cause data loss since duplicated rows are ignored. For more information, see the [\-- \[no\]check-unique-key-change](https://www.percona.com/doc/percona-toolkit/LATEST/pt-online-schema-change.html#id8) option.

### dropColumn

!!! note
    The change is supported since liquibase-percona 1.0.0. Automatic rollback is not supported.

```
<changeSet id="2" author="Alice">
     <dropColumn tableName="person" columnName="age"/>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="DROP COLUMN age" ...
```

### dropForeignKeyConstraint

!!! note
    The change is supported since liquibase-percona 1.3.0. Automatic rollback is not supported.

```
<changeSet id="4" author="Alice">
     <dropForeignKeyConstraint baseTableName="address" constraintName="fk_person_address" />
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="DROP FOREIGN KEY _fk_person_address" ...
```

### dropUniqueConstraint

!!! note
    The change is supported since liquibase-percona 1.3.0. Automatic rollback is not supported.

```
<changeSet id="3" author="Alice">
     <dropUniqueConstraint tableName="person" constraintName="uq_id_name"/>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="DROP KEY uq_id_name" ...
```

### dropIndex

!!! note
    The change is supported since liquibase-percona 1.2.0. Automatic rollback is not supported.

```
<changeSet id="3" author="Alice">
     <dropIndex indexName="emailIdx" tableName="person"/>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="DROP INDEX emailIdx" ...
```

### modifyDataType

!!! note
    The change is supported since liquibase-percona 1.2.0. Automatic rollback is not supported.

```
<changeSet id="2" author="Alice">
     <modifyDataType tableName="person" columnName="email" newDataType="VARCHAR(400)"/>
</changeSet>
```

Corresponding command:

```
pt-online-schema-change --alter="MODIFY email VARCHAR(400)" ...
```

## Troubleshooting issues

### NoSuchMethodError: PerconaDropColumnChange.getColumns()Ljava/util/List

If you receive the following error message:

```
Unexpected error running Liquibase:
liquibase.exception.UnexpectedLiquibaseException: java.lang.NoSuchMethodError:liquibase.ext.percona.PerconaDropColumnChange.getColumn()Ljava/util/List”`
```

Check to see if you are using liquibase-percona 1.1.1 with Liquibase 3.2.x. This is an unsupported combination. For Liquibase 3.2.x, you need to use liquibase-percona 1.0.0.

## Related links

*   [Percona](https://www.percona.com/)
*   [Percona Toolkit Latest Documentation](https://www.percona.com/doc/percona-toolkit/LATEST/index.html)
*   [Percona Toolkit on GitHub](https://github.com/percona/percona-toolkit)
*   [Liquibase Percona Extension](https://github.com/liquibase/liquibase-percona/blob/master/README.md)
*   [Building the Liquibase Percona Extension](https://github.com/liquibase/liquibase-percona/blob/master/README.md)
*   [DZone: Avoiding MySQL ALTER Table Downtime](https://dzone.com/articles/avoiding-mysql-alter-table)