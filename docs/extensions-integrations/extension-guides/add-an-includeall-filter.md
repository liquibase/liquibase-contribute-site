# Add an IncludeAll Filter

--8<-- "extension-setup.md"

## Overview

By default, Liquibase's [includeAll] tag will include all valid changelog files in the referenced path which are valid changelog files.
If you would like to limit the files, you can specify the `filter` attribute pointing to a class that implements the [liquibase.changelog.IncludeAllFilter](https://javadocs.liquibase.com/liquibase-core/liquibase/changelog/IncludeAllFilter.html){target="_blank"} interface.

!!! note

    If you would like to control the **_ordering_** of files included, see [Add an IncludeAll Comparator](add-an-includeall-comparator.md) 

## Example

Suppose you have a changelog directory containing many files and a process where you will sometimes want to archive a changelog file by changing the extension to `.bak.xml`.
When you archive those files, you no longer want them executed by Liquibase.

If you create a class like this:

```java
--8<-- "src/main/java/com/example/includeall/NoBakFilesFilter.java"
```

and add it to your Liquibase classpath, you can reference it in the `filter` attribute on your includeAll like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">
    <includeAll path="com/example/changelogs/" filter="com.example.includeall.NoBakFilesFilter"/>
</databaseChangeLog>
```

!!! tip

    Unlike other extensions, there is no need to register your class in the `META-INF/services` directory. 