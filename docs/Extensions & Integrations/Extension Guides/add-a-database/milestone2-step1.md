---
title: "Milestone 2: Run Tests"
---

# Configure and Run Test Harness

## Overview

Ensuring snapshot and change types work requires going through each specific change type and through each specific object type, then ensuring they execute correctly for all the different argument permutations that are valid for your database.

To automate this process, Liquibase provides a [Test Harness](https://github.com/liquibase/liquibase-test-harness){:target="_blank"} to help you find everywhere the default logic isn't compatible with your database.

## Test Harness Configuration

Configure your project using the steps [in the Test Harness 'extension' setup docs](https://github.com/liquibase/liquibase-test-harness/blob/main/README.extensions.md){:target="_blank"}. 

When completed, you should have a `harness-config.yml` file that specifies how to connect to the database and a new `ExtensionHarnessTest` class which will run all the tests.

## Running the Tests

The [Test Harness 'Framework' documentation](https://github.com/liquibase/liquibase-test-harness#framework){:target="_blank"} describes how the tests work and how to run them.

At the most basic level, you can run the tests using `mvn test` using the configuration options defined in the test-harness framework documentation such as `-DchangeObjects`. 
If you are using an IDE, you can run your `ExtensionHarnessTest` class directly and pass along the same settings as system properties.

## Next Step

The first time you run the tests, you will get failure from missing files. See [Milestone 2: Fix & Restest](milestone2-step2.md) to continue.