---
title: Getting Started
---

# Getting Started with Liquibase and Ant

[Apache Ant](https://ant.apache.org/) is a Java library and command-line build tool for Java applications. Liquibase has a set of Ant tasks to automate your database changes at build time.

Note: Liquibase Ant tasks are implemented on the `<database>` type. For more information about the attributes you can configure for the `<database>` type, see [Ant](../).

## Install and Configure Ant

1.  Ensure you have [installed Ant](https://ant.apache.org/manual/install.html). To verify that Ant is installed, run `ant -version` at the command prompt. You will get the output that looks like `Apache Ant(TM) version 1.10.11 compiled on July 10 2021`. Liquibase Ant tasks require Ant 1.7.1 or later versions.
2.  Create a Liquibase project directory to store all Liquibase and Ant files.
3.  Create an Ant build file called `build.xml` to specify your configuration settings with tasks, targets, and dependencies. The `build.xml` file also lets you define the needed Liquibase properties. See the following build.xml example for a basic configuration.

    **`build.xml` example**

    ```
    <project name="liquibase-test" basedir="." xmlns:liquibase="antlib:liquibase.integration.ant">
    <!-- The "prepare" target configures the classpath and properties Liquibase will use during task execution.-->
    <!-- Liquibase targets must include a "depends" attribute to ensure the prepare target executes before the Liquibase task.-->
        <target name="prepare">
    
            <taskdef resource="liquibase/integration/ant/antlib.xml" uri="antlib:liquibase.integration.ant">
                   <classpath path="lib\liquibase.jar;lib\postgresql-42.2.18.jar"/>
            </taskdef>
    
    <!-- set global properties for Liquibase Tasks -->
    <!-- Liquibase properties can be referenced using a "${}" string replacement within a liquibase.database change.-->
            <property name="db.url" value="jdbc:postgresql://localhost:8080/example"/>
            <property name="db.user" value="user"/>
            <property name="db.pass" value="password"/>
            <property name="db.driver" value="org.postgresql.Driver"/>
            <!-- Alternatively, a database instance can be created and referenced with databaseref in a Liquibase task.-->
            <liquibase:database id="my-database" url="${db.url}" user="${db.user}" password="${db.pass}" driver="org.postgresql.Driver"/>
        </target>
    
    <!-- Liquibase Tasks -->
        <!-- The updateDatabase target shows using the individual database connection properties.-->
            <target name="updateDatabase" depends="prepare">
                  <liquibase:updateDatabase  changeLogFile="com/example/changelog.sql">
                  <liquibase:database driver="${db.driver}" url="${db.url}"  user="${db.user}"  password="${db.pass}"/>
            </liquibase:updateDatabase>
        </target>
    </project>
    ```

4.  Include Liquibase in your Ant classpath and load it by adding the `<taskdef>` task in the `build.xml` file:

    ```
    <project basedir="the/runtime/location/of/Ant" name="example" xmlns:liquibase="antlib:liquibase.integration.ant">
         <taskdef resource="liquibase/integration/ant/antlib.xml" uri="antlib:liquibase.integration.ant">
         <classpath path="the/path/to/the/liquibase.jar;the/path/to/the/driver.jar" />
         </taskdef>
    </project>
    ```

    !!! Tip
        You can put the Liquibase JAR in your `ANT_HOME/lib` folder.

## Create the Liquibase Changelog
5.  Create a text file called `changelog.sql` in your Liquibase project directory. Liquibase also supports the `.xml`, `.yaml`, or `.json` changelog formats.
6.  Add changesets to your changelog file. Use the following examples depending on the format of the changelog you created:

--8<-- "database-tutorial-relational-test-connection-example.md"

## Deploy the changes to a database
7.  Execute the `updateDatabase` task by including the values in your Ant `build.xml` file:

    ```
    <target name="updateDatabase" depends="prepare">
        <liquibase:updateDatabase changeLogFile="com/example/changelog.sql">
        <liquibase:database driver="org.postgresql.Driver" 
            url="${db.url}" 
            user="${db.user}" 
            password="${db.pass}"/>
        </liquibase:updateDatabase>
    </target>
    ```

8.  Run the following in the CLI to implement the task and update your database:

    ```
    ant -f build.xml updateDatabase
    ```

After your first update, you will see a new table along with the [DATABASECHANGELOG table](https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html) and [DATABASECHANGELOGLOCK table](https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html) added to the database.


## Related links

*   [Ant](https://ant.apache.org/manual/index.html)
*   [Ant Tasks](../commands)