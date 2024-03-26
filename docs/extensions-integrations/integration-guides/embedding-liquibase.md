---
title: Embedding Liquibase
---

# Embed Liquibase in Your Application

The Java API allows Liquibase to be embedded directly into an application. 
This allows you to do things such as run Liquibase automatically within an application startup process.

This can be particularly helpful in environments where you have less control over the deployment process or want a simpler deployment process, such as:

- For web applications that use continuous delivery and have an automated release process from code check-in through live production which gets executed multiple times per day
- In packaged applications that are shipped to make the database management portion transparent

Depending on how your application is written, you can use existing integrations such as [Spring Boot](../directory/integration-docs/springboot/index.md)
or the [Servlet Listener](../directory/integration-docs/servlet-listener.md). However, you can also create a custom integration using the Liquibase APIs directly. 

This guide covers running an "update" operation against a changelog packaged in your application, but the same approach can be
used to perform any Liquibase logic from your custom integration including rolling back changes, generating SQL, or snapshotting the schema. 

## Get Started

### Step 1: Setup a development environment

Follow these instructions to [set up your development environment](../integrations-overview/dev-env-setup.md).

This step is not needed if you are integrating Liquibase into an existing application.

### Step 2: Create a changelog file

Create a [changelog file](https://docs.liquibase.com/concepts/changelogs/home.html) within your application's source code to be packaged with your application.

The remainder of this guide will assume you have the changelog file `com/example/changelog.xml` in your application's classpath.

The location of the changelog in your source repository file will depend on your application's build process.

### Step 3: Create a class to run Liquibase

```java
package com.example;

public class MyLiquibaseRunner {
    public void runLiquibase() throws Exception {
        System.out.println("Running Liquibase...");
        System.out.println("Running Liquibase...DONE");
    }
}
```

Where you put this class and how you run it will depend on your application's architecture.

For example, if you are using Spring, you would add the `@Service` annotation.
Or, if you are running a standalone application, you may add a call to that class from within the `main` method.

The above class doesn't actually run Liquibase yet, but if you have integrated it into your application correctly you should see the log messages during your application startup.

## Configure The ResourceAccessor

Liquibase uses the [`liquibase.resource.ResourceAccessor`](../../code/api/resource-resourceaccessor.md) interface to find and read changelog files. 
Because your changelog file is packaged with your application, you will configure Liquibase to use the `liquibase.resource.ClassLoaderResourceAccessor`.

```java
package com.example;

import liquibase.Scope;
import liquibase.resource.ClassLoaderResourceAccessor;

public class MyLiquibaseRunner {
    public void runLiquibase() throws Exception {
        System.out.println("Running Liquibase...");

        Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
            //Code here will use the new ResourceAccessor
        });
        
        System.out.println("Running Liquibase...DONE");
    }
}
```

The example code now includes a `Scope.child(....)` call, where we set the `Scope.Attr.resourceAccessor` attribute to a new `ClassLoaderResourceAccessor`.
The `Scope` object lets you define settings as part of Liquibase's [cascading configuration system](configure-configuration.md).


## Run the Update Command

With your connection and ResourceAccessor set up, you can now run the Liquibase update command.

Liquibase provides a [Command API](calling-commands.md) for running commands from Java code. Executing commands consists of 3 steps:  

1. Create a new `liquibase.command.CommandScope` object. This provides the container for configuration and running the command.
2. Set arguments on the CommandScope with `addArgumentValue()`
3. Execute the command with `execute()`

While there are constants for the command names and argument names within the Liquibase code, they are found in the various [liquibase.command.CommandStep](https://javadocs.liquibase.com/liquibase-core/liquibase/command/CommandStep.html) implementations and can be difficult to find. 
Therefore, it's generally easiest to use the command name and argument strings you find in the Liquibase CLI.  
  
```java
package com.example;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;

public class MyLiquibaseRunner {
    

    public void runLiquibase() throws Exception {
        System.out.println("Running Liquibase...");

        Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
            CommandScope update = new CommandScope("update");
            
            update.addArgumentValue("changelogFile", "com/example/changelog.xml");
            update.addArgumentValue("url", "YOUR_DB_URL");
            update.addArgumentValue("username", "YOUR_DB_USER");
            update.addArgumentValue("password", "YOUR_DB_PWD");
            
            update.execute();
        });
        
        System.out.println("Running Liquibase...DONE");
    }
}
```

!!! note
      Make sure you replace the url, username, and password arguments with your actual database connection information.

## Optional: Reuse an Existing Database Connection

The above example passes the url, username, and password that the update command which creates a new connection.
However, if you already have logic to create a database connection, you can reuse that connection with Liquibase.

The update command requires a `liquibase.database.Database` object, which wraps the base `java.sql.Connection` object with additional Liquibase-specific logic. 
The easiest way to create a `Database` object is to use 

```
liquibase.database.Database database = liquibase.database.DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
```

where `connection` is your existing `java.sql.Connection` object.

Once you have your `database` object, you can replace the url, username, and password arguments with a single`"database"` argument.

```java
package com.example;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

public class MyLiquibaseRunner {
    public void runLiquibase() throws Exception {
        System.out.println("Running Liquibase...");

        Connection connection = openConnection(); //your openConnection logic
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
            CommandScope update = new CommandScope("update");

            update.addArgumentValue("changelogFile", "com/example/changelog.xml");
            update.addArgumentValue("database", database);

            update.execute();
        });

        System.out.println("Running Liquibase...DONE");
    }
}
```

!!! note
    Depending on your database security setup, you may need or want different database credentials for the connection Liquibase uses versus what your application runs with.
    
    This is primarily because the schema manipulation permissions required by Liquibase are different from the data manipulation 
    permissions required by your application.

