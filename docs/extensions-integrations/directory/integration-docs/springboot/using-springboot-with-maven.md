---
title: With Maven
---

# Application startup deployments with Liquibase, Spring Boot, and Maven

Use [Spring Boot](https://spring.io/projects/spring-boot#overview) with Liquibase to create and configure standalone Spring applications and automate your database updates. Spring Boot with Maven allows you to create Java applications started by running `java -jar` or `war` deployments.

The Liquibase Spring Boot integration ensures the application's database is updated along with the application code using Spring Boot auto-configuration.

Using the Liquibase Maven Plugin with Spring Boot has two main features:

*   It collects all the `.jar` files in the classpath and builds a single `uber-jar`, which helps to execute your service in more convenient way.
*   It searches for the `public static void main()` method to flag any classes with that method signature as a runnable class.

## Install Maven

1.  [Install Maven](https://maven.apache.org/download.cgi) and [add it to your path](https://maven.apache.org/install.html).
2.  Ensure you have [Java Development Kit](https://www.oracle.com/java/technologies/java-se-glance.html) (JDK 8, 11, or 16).

## Create a Spring Boot project

1.  Create a project by using the Spring Boot application:

    *   If you have an existing Spring Boot project, add the `liquibase-core` dependency to your project `pom.xml`.
    *   To manually create a new Spring Boot project, follow the [Spring Boot Getting Started](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/getting-started.html#getting-started.installing) documentation.
    *   To create a basic Spring Boot project, use a web-based service called [Spring Initializr](https://start.spring.io/).

1. Enter the following information in Spring Initializr:

    *   Project: Maven
    *   Language: Java
    *   Spring Boot: the version you need
    *   Project Metadata:
        *   Group: com.example.liquibase
        *   Artifact: springbootProject
        *   Name: springbootProject
        *   Description: Liquibase Project for Spring Boot
        *   Package name: com.example.liquibase.springbootProject
        *   Packaging: Jar
        *   Java: 8, 11, or 16
    *   Dependencies: Spring Data JPA and Liquibase Migration. The service lets you add your database driver dependency and any developer tool.

1.  Select **GENERATE** to download your project template as a `.zip` file.
1.  Extract the files and open `pom.xml` in your IDE or text editor. By default, the Liquibase dependency will find the latest `liquibase-core` version. You can edit the Liquibase dependency to include the exact version of Liquibase you want to use.
1.  Follow the instructions depending on your project.

## Configure the database properties

1.  Open the existing Spring Boot `application.properties` file. To find the file, navigate to `src/main/resources/application.properties`.
    
2.  Add the following properties to run Liquibase migrations. Update the values depending on your database requirements:
    
    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
    spring.datasource.username=example
    spring.datasource.password=example
    ```

    !!! Note
        To find the URL format for your database, see [Liquibase Database Tutorials](https://docs.liquibase.com/start/tutorials/home.html).

3.  Create a new text file called `pom.xml` or use the `pom.xml` file created for your project by the Spring Initializr.
4.  Specify attributes in your `pom.xml` based on the example:

    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
    https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-parent</artifactId>
             <version>2.5.3</version>
             <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example.liquibase</groupId>
    <artifactId>springbootProject</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springbootProject</name>
    <description>Liquibase Project for Spring Boot</description>
    <properties>
            <java.version>1.8</java.version>
    </properties>
    <dependencies>
           <dependency>
                     <groupId>org.springframework.boot</groupId>
                     <artifactId>spring-boot-starter-data-jpa</artifactId>
           </dependency>
           <dependency>
                     <groupId>org.liquibase</groupId> 
                     <artifactId>liquibase-core</artifactId>
           </dependency>
           <dependency>
                     <groupId>org.springframework.boot</groupId>
                     <artifactId>spring-boot-starter-test</artifactId>
                     <scope>test</scope>
           </dependency>
    
           <!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
           <dependency>
                     <groupId>org.postgresql</groupId>
                     <artifactId>postgresql</artifactId>
                     <version>42.2.5</version>
           </dependency>
    </dependencies>
     
    <build>
         <plugins> 
                 <plugin>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-maven-plugin</artifactId>
                 </plugin>
         </plugins>
    </build>
    
    </project>
    ```

## Configure the changelog location

1.  Create a text file called `changelog.sql` or use the changelog file from the `examples` directory. Liquibase also supports the `.xml`, `.yaml`, or `.json` changelog formats.

1.  Specify the location of the changelog file

    === "application.properties file"
    
        ```
        spring.liquibase.change-log=classpath:db/changelog/changelog.sql
        ```

    === "pom.xml file"

        ```
        pom.xml: <changelog-file>your/path/to/changelog.sql</changelog-file>
        ```

    === "liquibase.properties file"
    
        **Windows example**
        ```
        changelog-file: ..\path\to\changelog.sql
        ```
        
        **Linux example**
        ```
        changelog-file: ../path/to/changelog.sql
        ```

## Add changes to the changelog
7.  Add changesets to your changelog file. Use the following examples depending on the format of the changelog you created:

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
                   tableName: test_table
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
                "tableName": "test_table",
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

## Deploy the changes

1.  Run your first migration with the following command:

    ```
    mvn compile package
    ```

    The command creates the Spring Boot application JAR file in the target directory. If the metadata specified in Spring Initializr was used in the Spring Boot project setup, you can directly execute the `jar` file by running:

    ```
    java -jar springbootProject-0.0.1-SNAPSHOT.jar
    ```

    To roll back any changes, create the `liquibase.rollback-file` file in Spring Boot and generate a rollback script for changes associated with changesets:

    ```
    -- rollback drop table test_table;
    ```

    !!! Note
        You can also use [tags](https://docs.liquibase.com/change-types/tag-database.html) for the rollback.

## Related links

*   [Maven documentation](https://docs.liquibase.com/tools-integrations/maven/home.html)
*   [Spring Boot documentation](https://spring.io/projects/spring-boot#learn)
