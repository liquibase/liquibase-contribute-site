---
title: resource.ResourceAccessor
---

# liquibase.resource.ResourceAccessor Interface

## Overview

`liquibase.resource.ResourceAccessor` implementations allow you to define where and how to look up files referenced in changelog files.

Changelog files need to be runnable in a wide variety of environments, and therefore exactly where a file lives cannot be written into the changelog.
To support this, Liquibase defines a "Search Path" which is a set of ResourceAccessors which can each try to find the given file path.
Liquibase ships with ResourceAccessors that know how to find files in directories and zip files, but ResourceAccessors can be created to read files from anywhere.

## ResourceAccessor Creation

There is no [priority](../../extension-references/priority.md) for ResourceAccessors. They are either created explicitly by the [integrations](../../integrations/index.md) or 
implicitly by a [PathHandler](../add-a-path-handler/index.md).

## API Highlights

### Constructor

Unlike most extension points, ResourceAccessors are not auto-created and therefore don't require an empty constructor.

Instead, create constructor(s) as needed taking required and/or common settings.

### search()

The search method is used by `includeAll` and other code which is looking to find files in a directory.
You must respect the SearchOptions passed, and should return the resources in an order that is expected by the user. Often times this is alphabetical order.

### describeLocations()

Returns a description of the places this ResourceAccessor will look for paths. Used in error messages and other troubleshooting cases.

### close()

Close any references managed by this ResourceAccessor. This can be a no-op if there is nothing kept open.

## API Details

The complete javadocs for `liquibase.resource.ResourceAccessor` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/Resource.html){:target="_blank"}
