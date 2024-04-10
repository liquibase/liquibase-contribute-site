---
title: "Milestone 2: Advanced Tests"
---

# Configure and Run Test Harness

## Overview

As you move beyond [foundational support](milestone1-step1.md), the testing requirements become more complex.

Ensuring the `snapshot` command and change types work requires testing each change type and object type to verify that they execute correctly for the different argument permutations that are valid for your database.

To automate this process, Liquibase provides a [Test Harness](https://github.com/liquibase/liquibase-test-harness){:target="_blank"} to help you find situations where the default logic isn't compatible with your database.

## Test Harness Configuration

Configure your project using the steps [in the Test Harness 'extension' setup docs](https://github.com/liquibase/liquibase-test-harness/blob/main/README.extensions.md){:target="_blank"}. 

When completed, you should have a `harness-config.yml` file that specifies how to connect to the database and a new `ExtensionHarnessTest` class that will run all the tests.

## Running the Tests

The [Test Harness 'Framework' documentation](https://github.com/liquibase/liquibase-test-harness#framework){:target="_blank"} describes how the tests work and how to run them.

At the most basic level, you can run the tests using `mvn test` with the configuration options defined in the test-harness framework documentation such as `-DchangeObjects`. 
If you are using an IDE, you can run your `ExtensionHarnessTest` class directly and pass along the same settings as system properties.

## Next Step

The first time you run the tests, you will get failures from missing files. See [Milestone 2: Fix & Restest](milestone2-step2.md) to continue.