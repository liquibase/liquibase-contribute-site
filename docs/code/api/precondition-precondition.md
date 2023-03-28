---
title: precondition.Precondition
---

# liquibase.precondition.Precondition Interface

## Overview

`liquibase.precondition.Precondition` implementations allow validation and checks to be run against the environment before running a changelog as a whole, or before running individual changesets.  

Liquibase ships with a large number of standard preconditions such as:

- tableExists
- columnExists
- sqlCheck
- etc.

but extensions can provide **_any_** functionality desired.  

## Precondition Selection

Each `Precondition` has a "name", and the ChangeLogParser selects the correct implementation by matching the name in the changelog file with the names defined by Precondition implementations.

`Preconditions` do yet support a [priority](../../extension-references/priority.md) mechanism, so each precondition must have a unique name.

## API Highlights

### Empty Constructor

Liquibase requires implementations to have an empty constructor.

### check()

This function contains the logic to determine if the precondition passes or fails.

If the precondition fails, throw a `PreconditionFailedException` describing the problem.

If it cannot be determined whether the precondition passed or failed, throw a `PreconditionErrorException` describing the problem.

### validate() and warn()

Override these methods to check whether the precondition can be run or not given its configuration and environment.

### Define Configuration Attributes

If the precondition requires custom attributes to be set (tableName, etc.), create get/set methods for them.

Any public get/set pairs will be exposed to the end user as attributes on the precondition.

### Registration

Implementation classes are registered by adding it to `META-INF/services/liquibase.precondition.Precondition`

## API Details

The complete javadocs for `liquibase.precondition.Precondition` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/precondition/Precondition.html){:target="_blank"}
