# Add an IncludeAll Comparator

--8<-- "extension-setup.md"

## Overview

By default, Liquibase's [includeAll] tag will sort files in alphanumeric order.
If you would like a different ordering, you can specify the `resourceComparator` attribute pointing to a class that implements the [java.util.Comparator<String>](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html){target="_blank"} interface.

Your `compare` method will be passed the path to the files to sort.

!!! note

    If you would like to control the **_filtering_** of files included, see [Add an IncludeAll Filter](add-an-includeall-filter.md) 

## Example

Suppose your changelog files are named like `1-create-table.sql`, `23-add-age.sql`, `1037-rest-age.sql` where there is always a number before a hyphen, but the number is not
zero-padded and so does not work well with the default alphanumeric sorting.

If you create a class like this:

```java
--8<-- "src/main/java/com/example/includeall/NumberComparator.java"
```

and add it to your Liquibase classpath, you can reference it in the `resourceComparator` attribute on your includeAll like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">
    <includeAll path="com/example/changelogs/" resourceComparator="com.example.includeall.NumberComparator"/>
</databaseChangeLog>
```

!!! tip

    Unlike other extensions, there is no need to register your class in the `META-INF/services` directory. 