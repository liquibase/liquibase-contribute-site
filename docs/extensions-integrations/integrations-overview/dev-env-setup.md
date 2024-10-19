---
title: Dev Environment Setup 
---

# Development Environment Setup

How you set up a development environment for integration development is mainly dependent on what works best for developing the application/integration driving Liquibase.

For integrations, Liquibase is simply another library dependency you are calling out to. 

## Clone the repository

Each integration will have its own repository. Since integration can be hosted anywhere, the exact steps will vary.

For integration on GitHub (including the Liquibase-owned extensions at [github.com/liquibase](https://github.com/liquibase)), the steps are:

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

### 2. Add Liquibase as a dependency

If your application is using Maven, Gradle or some other build system that manages your dependencies, add Liquibase to that project configuration.

!!! example
    
    If using Maven, add the following to your `pom.xml` file: 

    ```xml
            <dependency>
                <groupId>org.liquibase</groupId>
                <artifactId>liquibase-core</artifactId>
                <version>${liquibase.version}</version>
            </dependency>
    ```

### 3. Check Setup
 
With the dependency added you can add a simple reference to a Liquibase class and build your project to ensure everything is configured correctly.

!!! example

    ```java
    System.out.println(LiquibaseUtil.getBuildVersionInfo());
    ```

If your application build still compiles, the setup is successful.

## Next Steps

Now that your local environment is set up, you can start making the changes you would like. 

For more information, see:

- [Integration Guides](../integration-guides/index.md)
