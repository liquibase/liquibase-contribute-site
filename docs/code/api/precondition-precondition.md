---
title: precondition.Precondition
---

# liquibase.precondition.Precondition

## Overview

`liquibase.precondition.Precondition` implementations allow validation and checks to be run against the environment before running a changelog as a whole, or before running individual changesets.  

## Precondition Selection

Each `Precondition` has a "name", and the [ChangeLogParser](parser-changelogparser.md) selects the correct implementation by matching the name in the changelog file with the names defined by Precondition implementations.

`Preconditions` do yet support a [priority](../architecture/service-discovery.md) mechanism, so each precondition must have a unique name.

## API Highlights

### Auto-Registration

Preconditions are [dynamically discovered](../architecture/service-discovery.md), so must have a no-arg constructor and be registered in the `META-INF/services/liquibase.precondition.Precondition` file.

### check()

This function contains the logic to determine if the precondition passes or fails.

If the precondition fails, throw a `PreconditionFailedException` describing the problem.

If it cannot be determined whether the precondition passed or failed, throw a `PreconditionErrorException` describing the problem.

### validate() and warn()

These methods to check whether the precondition can be run or not given its configuration and environment.

### Define Configuration Attributes

If the precondition requires custom attributes to be set (tableName, etc.), create get/set methods for them.

Any public get/set pairs will be exposed to the end user as attributes on the precondition.

## API Details

The complete javadocs for `liquibase.precondition.Precondition` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/precondition/Precondition.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Precondition](../../extensions-integrations/extension-guides/add-a-precondition.md)