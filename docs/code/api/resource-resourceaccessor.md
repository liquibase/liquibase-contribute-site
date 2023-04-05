---
title: resource.ResourceAccessor
---

# liquibase.resource.ResourceAccessor

## Overview

Changelog files need to be runnable in a wide variety of environments, and therefore exactly where a file lives cannot be written into the changelog.

To separate the "where" a file is from the "what" file a changelog wants, Liquibase provides a "Search Path".
The search path follows the same pattern as the "Classpath" or the "sys.path" in Python or the "module.paths" in NodeJS.  

Each integration defines a list of base locations where Liquibase should look for file references. Each base location in the search path is a `liquibase.resource.ResourceAccessor` implementation.
Liquibase ships with ResourceAccessors that know how to find files in directories and zip files, but ResourceAccessors can be created to read files from anywhere.

When a path like `com/example/file.sql` is referenced in a changelog, Liquibase will check each ResourceAccessor for that path to find the place(s) it can be found.

It is up to each integration to correctly set up the search path based on expected defaults and/or the user configuration.  

!!! example

    A changelog file may `include` a "com/example/child.changelog.xml" file, or have a `<sqlFile>` tag with a path of "custom_proc.sql". 
    That same changelog file reference may be run:

    - Via a CLI on Windows-based developer machines which each have their project directories stored in different locations
    - Via Maven on a Linux build server
    - Via Spring, reading the files from pre-built jar files

## Compared to PathHandler

There are two styles of file access that Liquibase supports:

- File references **_within_** changelog files which must remain **_consistent across environments_** are handled by the `ResourceAccessor` API
- File references **_outside_** changelog files that point to **_environment-specific_** paths are handled by the [liquibase.resource.PathHandler](resource-pathhandler.md) API

## Standard Implementations

Liquibase ships with the following ResourceAccessor implementations, which can be used directly or extended as needed:

- `liquibase.resource.DirectoryResourceAccessor` for files stored in a local directory
- `liquibase.resource.ZipResourceAccessor` for files stored in a zip or jar file
- `liquibase.resource.ClassLoaderResourceAccessor` for files stored in a Java classpath
- `liquibase.resource.CompositeResourceAccessor` combines multiple ResourceAccessors
- `liquibase.resource.SearchPathResourceAccessor` creates a composite ResourceAccessor using paths in the `liquibase.searchPath` setting

## API Highlights

### Constructor

There is no [priority](../architecture/service-discovery.md) for ResourceAccessors. They are either created explicitly by integrations or
implicitly by a [PathHandler](resource-pathhandler.md).

Therefore, constructor(s) take required and/or common settings.

### search()

The search method is used by `includeAll` and other code which is looking to find files in a directory.
The logic must respect the SearchOptions passed and should return the resources in an order that is expected by the user. Oftentimes this is in alphabetical order.

### describeLocations()

Returns a description of the places this ResourceAccessor will look for paths. Used in error messages and other troubleshooting cases.

### close()

Close any references managed by this ResourceAccessor. This can be a no-op if there is nothing kept open.

## API Details

The complete javadocs for `liquibase.resource.ResourceAccessor` [is available at https://javadocs.liquibase.com](https://javadocs.liquibase.com/liquibase-core/liquibase/resource/ResourceAccessor.html){:target="_blank"}

## Extension Guides

The following guides provide relevant examples:

- [Add a Resource Accessor](../../extensions-integrations/extension-guides/add-a-resource-accessor.md)
- [Configure File Access](../../extensions-integrations/integration-guides/configure-file-access.md)