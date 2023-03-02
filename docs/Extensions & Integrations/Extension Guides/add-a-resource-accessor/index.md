---
title: Overview
---

# Creating New ResourceAccessors

## Overview

`liquibase.resource.ResourceAccessor` implementations allow you to define where and how to look up files referenced in changelog files.

Changelog files need to be runnable in a wide variety of environments, and therefore exactly where a file lives cannot be written into the changelog.
To support this, Liquibase defines a "Search Path" which is a set of ResourceAccessors which can each try to find the given file path.
Liquibase ships with ResourceAccessors that know how to find files in directories and zip files, but ResourceAccessors can be created to read files from anywhere.

## ResourceAccessor Creation

There is no [priority](../../references/priority.md) for ResourceAccessors. They are either created explicitly by the [integrations](../../integrations/index.md) or 
implicitly by a [PathHandler](../path-handlers/index.md).

## Prerequisites

Implementing support for additional databases requires an understanding of Java. You will be creating classes, overriding methods, and working with inheritance hierarchies.

## Project Setup

If you have not already created a repository to hold your code, see [Your First Extension](../../your-first-extension.md) in the Getting Started guide.

## Next Steps

When you are ready to create your new `ResourceAccessor`, head to the [Create a ResourceAccessor](create.md) page.