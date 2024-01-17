---
title: API Usage
---

# Using the Liquibase Java API

Liquibase has an option to be set and run automatically on startup:

*   For web applications that use continuous delivery and have an automated release process from code check-in through live production which gets executed multiple times per day
*   In packaged applications that are shipped to make the database management portion transparent

Once Liquibase is set to run automatically on startup, your database state always matches what your code expects, and you have no manual steps to forget. This method works best in environments where you have less control over the deployment process or want a simpler deployment process.

Liquibase uses a DATABASECHANGELOGLOCK table to ensure that only one instance of Liquibase runs at a time in case you have multiple servers pointing to the same database. Even if you have a cluster of servers all coming online at the same time and all automatically running Liquibase, the DATABASECHANGELOGLOCK table will ensure that they will not update the database concurrently and cause problems.

Liquibase ships with two hooks to automatically update your database on startup: Servlet Listener and Spring Bean. However, you can also call the Liquibase Java API directly.

The most straightforward way of directly running Liquibase looks as follows:

1.  Import the Liquibase API.

    The Liquibase API is packaged in the `liquibase-core.jar` file that you need to include in your classpath. Download the `liquibase-core.jar` file at the [Maven Repository for Liquibase](https://mvnrepository.com/artifact/org.liquibase/liquibase-core) page and add it to your project. The Maven page presents different versions with the examples for various build systems.

2.  Create a text file in your application directory and name it `changelog.sql`. Liquibase also supports the `.xml`, `.yaml`, or `.json` changelog formats.

    Liquibase uses a changelog file to sequentially list all changes made to your database. It looks for changelog files in your application's classpath. Thus, put your changelog file in a location that will be compiled into your application. For example, if you use a standard Maven project, create a file in `src/main/resources`.

3.  Add changesets to your changelog file. Use the following examples depending on the format of the changelog you created:

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
    
    === "SQL example"

        ```
        -- liquibase formatted sql
    
        -- changeset liquibase:1
        CREATE TABLE test_table (test_id INT, test_column VARCHAR, PRIMARY KEY (test_id))
        ```

    === "YAML example"

        ```
        databaseChangeLog:
           - changeSet:
               id: 1
               author: Liquibase
               changes:
               - createTable:
                   columns:
                   - column:
                       name: test_column
                       type: INT
                       constraints:  
                           primaryKey:  true  
                           nullable:  false  
                           tableName: test_table
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
        		  "createTable": {
        		    "columns": [
        			{
        			  "column": 
        		      {
        				"name": "test_column",
        				"type": "INT",
        				"constraints": 
        			  {
        				"primaryKey": true,
        				"nullable": false
        				}
        				}
        			  }]
        			,
        			"tableName": "test_table"
        		  }
        		}]
        	  }
        	}]
        }
        ```

4.  Use the Liquibase API.

    * Configure and manage global configuration settings.

        The Liquibase API includes a `liquibase.Scope` object, which allows you to control the scope for all settings. The `Scope.child()` function takes the settings that apply within the given function, and when that function exits, the configured settings are dropped out of scope.

        ```
        Map<String, Object> config = new HashMap<>();
        //add values to the config
        Scope.child(config, () -> {
            //Liquibase calls will go here
        });
        ```

        For example, you can configure the [Liquibase Pro key](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html) as follows:

        ```
        Map<String, Object> config = new HashMap<>();
        config.put("liquibase.licenseKey", "YOUR_PRO_KEY");

        Scope.child(config, () -> {
        //Liquibase calls will go here
        });
        ```

        Everything running within the `Scope.child(config, () -> { //Liquibase calls will go here });` [Lambda function](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) will use the configured Liquibase Pro key.

    * Configure the `liquibase.Liquibase` [façade](https://www.baeldung.com/java-facade-pattern) using the Lambda function and creating the `liquibase` object that takes the reference to your changelog file, a [liquibase.resource.ResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ResourceAccessor.html) implementation, and a `liquibase.database.Datababase` object:

        ```
        Map<String, Object> config = new HashMap<>();
        config.put("liquibase.licenseKey", "YOUR_PRO_KEY");
    
        Scope.child(config, () -> {
          Connection connection = openConnection(); //your openConnection logic 
      
          Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
      
          Liquibase liquibase = new liquibase.Liquibase("path/to/changelog.sql", new ClassLoaderResourceAccessor(), database);
    
          //Liquibase calls will go here
        });
        ```

        !!! note
            Replace the `openConnection()` method with the method your application takes to get a connection to your database. The information can often be pulled from a connection pool.

    * Once you have your `liquibase` object created, call the [business logic](https://www.investopedia.com/terms/b/businesslogic.asp) methods on it. For example:

        ```
        Map<String, Object> config = new HashMap<>();
        config.put("liquibase.licenseKey", "YOUR_PRO_KEY");
    
        Scope.child(config, () -> {
     
          Connection connection = openConnection(); //your openConnection logic 
    
          Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
    
          Liquibase liquibase = new liquibase.Liquibase("path/to/changelog.sql", new ClassLoaderResourceAccessor(), database);
    
          CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
          updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, getDatabase());
          updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changeLogFile);
          updateCommand.execute()
        });
        ```


The command uses the [CommandScope](https://javadocs.liquibase.com/liquibase-core/liquibase/command/CommandScope.html), [UpdateCommandStep](https://javadocs.liquibase.com/liquibase-core/liquibase/command/core/UpdateCommandStep.html), and [DbUrlConnectionCommandStep](https://javadocs.liquibase.com/liquibase-core/liquibase/command/core/helpers/DbUrlConnectionCommandStep.html) classes to perform an `update` operation with the changelog and database connection passed to the Liquibase constructor. Instead of the `update` operation, you can call any other functions on the Liquibase object, such as `rollback`, `validate`, and others.

!!! tip
    The example above uses the Liquibase [CommandScope](https://javadocs.liquibase.com/liquibase-core/liquibase/command/CommandScope.html) framework. The `liquibase.update()` method is deprecated.

For more detailed information, check the [Liquibase API](https://javadocs.liquibase.com/liquibase-core/liquibase/Liquibase.html) documentation.

Related links
-------------

*   [Working with Command Parameters](https://docs.liquibase.com/parameters/working-with-command-parameters.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Installing Liquibase](https://docs.liquibase.com/start/install/home.html)