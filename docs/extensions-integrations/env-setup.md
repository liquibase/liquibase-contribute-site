---
title: Dev Environment Setup 
---

# Development Environment Setup

Setting up a development environment for extension development is similar to [what you do for Liquibase development](../code/get-started/env-setup.md),
but uses an existing Liquibase installation as the runtime for your extension. 

## Clone the repository

Each extension will have its own repository. Since extensions can be hosted anywhere, the exact steps will vary.

For extensions on GitHub (including the Liquibase-owned extensions at [github.com/liquibase](https://github.com/liquibase)), the step are:

1. Browse to the repository in GitHub
2. Click on the Fork button in the top-right corner. 
3. Clone the repository to your local file system by running `git clone https://github.com/<org>/<repo>`
4. Create a development branch with `git checkout -b <new_branch>`

!!! note

    Step 2 is optional if you have write access to the repository 


## Minimal Setup

After forking and cloning the extension code, you can set up your development environment. 

### 1. Install Java

Any version or distribution of Java will work, but the current LTS version is usually best.

[Adoptium](https://adoptium.net/) is a good choice

### 2. Install Maven

[Download Maven](https://maven.apache.org/download.cgi) and unzip the archive to a directory on your system. This provides the `mvn` CLI. 

This is technically an optional step if you'll eventually use an IDE since most have Maven support built in. 
But installing it separately allows you to better duplicate the minimal build process if troubleshooting your IDE setup.   

### 3. Install Liquibase

You will need an installed version of the Liquibase CLI to run your extension in. 

The best approach is to download the [most recent release](https://github.com/liquibase/liquibase/releases){:target="_blank"} and unzip/untar/install it locally. 

!!! tip

    The rest of the guide will refer to the directoy you installed Liquibase to as "LIQUIBASE_HOME"

### 4. Check Setup
 
From your extension's source directory, run `mvn clean package` which compiles the extension, runs the tests, and creates the jar file.

You can tell the build worked successfully, if you see something like:

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  24.344 s
[INFO] Finished at: 2023-01-18T14:37:24-06:00
[INFO] ------------------------------------------------------------------------
```

at the end of the output.

## Running Builds

Running `mvn clean package` will build your local code and package it as `target/<artifact-name>-<VERSION>.jar`.

Copy the jar file from the target directory to your `LIQUIBASE_HOME/lib` directory. 

Once the jar has been copied, you can run any Liquibase operation from LIQUIBASE_HOME and your extension will be included in the run.

!!! note

    Anytime you recompile your extension with `mvn package` you need to re-copy the jar file to LIQUIBASE_HOME/lib when running Liquibase this way.

## IDE Setup

The "Minimal Setup" section builds and tests your local branch, but generally you will want to use and IDE like [VS Code](https://code.visualstudio.com/),
[IntelliJ IDEA](https://www.jetbrains.com/idea/), or your own favorite environment.

### Project Configuration

Both IDEs allow you to import the `pom.xml` file in the base of the repository as your "project". This keeps the libraries and build logic the IDE uses in sync with
what is defined in the canonical pom.xml that Maven and the CI process uses.

### Running the CLI In Your IDE

The maven-built package listed above works, but it's not the most efficient way of working. The build process takes a while, and it's difficult to enable debug support.
Instead, when using an IDE with more "native" support for running classes, you can run the CLI class more directly.

Exactly how you configure your IDE to run the CLI depend on the IDE version you are using, but the general process is to create a new "Run Configuration" with the following settings:

- **Class to run:** `liquibase.integration.commandline.LiquibaseLauncher`
- **Module Classpath:** `<YOUR EXTENSION MODULE>`
- **Environment Variable:** `LIQUIBASE_HOME=<PATH_TO_LIQUIBASE_HOME>`
- **Environment Variable:** `LIQUIBASE_LAUNCHER_PARENT_CLASSLOADER=thread`
- **Working Directory:** `<PATH_TO_LIQUIBASE_HOME>`
- **Program Arguments:** Whatever you are looking to test

When Liquibase runs, it will use the jars in `lib` and `internal/lib` so you can add any needed drivers or other extensions to those directories like you would
in a production setup.

!!! note
    The `LIQUIBASE_LAUNCHER_PARENT_CLASSLOADER=thread` environment variable tells Liquibase to use the classes and it's dependencies from your project NOT the ones in `lib` or `internal/lib`.

    That includes the version of Liquibase your extension defines as a dependency.

Your run configuration should allow you to successfully run Liquibase in either regular mode or debug mode.

## Next Steps

Now that your local environment is set up, you can start making the changes you would like. 

For more information, see:

- [Your First Extension](your-first-extension.md)
- [Extension Guides](extension-guides/index.md)