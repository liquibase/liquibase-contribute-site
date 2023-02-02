---
title: Overview
---

# Creating New Preconditions

## Overview

`liquibase.precondition.Precondition` implementations allow you to run validation and checks against your database before running a changelog as a whole, or before running individual changesets.  

Liquibase ships with a large number of standard preconditions such as:

- tableExists
- columnExists
- sqlCheck
- etc.

but extensions can provide **_any_** functionality desired.  

## Precondition Selection

Each `Precondition` has a "name", and the ChangeLogParser selects the correct implementation by matching the name in the changelog file with the names defined by Precondition implementations.

`Preconditions` do yet support a [priority](../../references/priority.md) mechanism, so each precondition must have a unique name.

## Prerequisites

Implementing support for additional databases requires an understanding of Java. You will be creating classes, overridding methods, and working with inheritance hierarchies.

## Project Setup

If you have not already created a repository to hold your code, see [Your First Extension](../../your-first-extension.md) in the Getting Started guide.

## Next Steps

When you are ready to create your new `Precondition`, head to the [Create a Precondition](create.md) page.