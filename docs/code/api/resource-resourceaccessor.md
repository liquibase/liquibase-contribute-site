---
title: resource.ResourceAccessor
---

# liquibase.resource.ResourceAccessor

## Overview

`liquibase.resource.ResourceAccessor` implementations define where and how to look up files referenced in changelog files.

Changelog files need to be runnable in a wide variety of environments, and therefore exactly where a file lives cannot be written into the changelog.
To support this, Liquibase defines a ["Search Path"](#searchpathresourceaccessor) which is a set of ResourceAccessors which can each try to find the given file path.
Liquibase ships with ResourceAccessors that know how to find files in directories and zip files, but ResourceAccessors can be created to read files from anywhere.

## SearchPathResourceAccessor

For integrations that would like to allow users to specify a "search path", create an instance of
[SearchPathResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/SearchPathResourceAccessor.html){:target="_blank"} 
passing along their specified search path and any other ResourceAccessors that should be searched as well. 

## CompositeResourceAccessor

ResourceAccessor instances should generally handle a single location, such as a directory or zip file. 

To search multiple locations, combine the instances together with [CompositeResourceAccessor](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/CompositeResourceAccessor.html){:target="_blank"}  

## API Highlights

### Constructor

There is no [priority](../architecture/service-discovery.md) for ResourceAccessors. They are either created explicitly by integrations or
implicitly by a [PathHandler](resource-pathhandler.md).

Therefore, create constructor(s) as needed taking required and/or common settings.

### search()

The search method is used by `includeAll` and other code which is looking to find files in a directory.
The logic must respect the SearchOptions passed, and should return the resources in an order that is expected by the user. Often times this is alphabetical order.

### describeLocations()

Returns a description of the places this ResourceAccessor will look for paths. Used in error messages and other troubleshooting cases.

### close()

Close any references managed by this ResourceAccessor. This can be a no-op if there is nothing kept open.

## API Details

The complete javadocs for `liquibase.resource.ResourceAccessor` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ResourceAccessor.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Resource Accessor](../../extensions-integrations/extension-guides/add-a-resource-accessor.md)