# Service Discovery

## Overview

For many API interfaces, Liquibase will automatically discover and use the "best" implementation based on what is being asked for.

!!! example
    
    - When being asked to parse a changelog file ending in .xml, Liquibase will use `XmlChangeLogParser`
    - When being asked to parse a changelog file ending in .sql, Liquibase will use `SqlChangeLogParser`
        - UNLESS the file content starts with `-- liquibase formatted sql` in which case it will use `FormattedSqlChangeLogParser`


## Registration

By default, Liquibase uses the [Java ServiceLoader](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ServiceLoader.html){_target="_blank"} to discover implementations of each interface.

That means that each implementation/service that can be auto-discovered must:

1. Implement the interface
2. Have a no-argument constructor
3. Have the complete name of the class listed in a file named `META-INF/services/<interface name>`

!!! example
    
    - `liquibase.database.core.PostgresDatabase` is an implementation of the `liquibase.database.Database` interface.
    - `liquibase.database.core.PostgresDatabase` has a no-argument constructor
    - `META-INF/services/liquibase.database.Database` contains the line `liquibase.database.core.PostgresDatabase`

## Selection

For a given interface, there may be multiple different implementations that can be used.
Liquibase uses "priority" to determine the correct implementation classes to use at runtime.

While the specific methods used to return the priority can vary depending on the interface, they all follow the pattern:

**The "priority" method returns an integer, where the highest value wins**. If multiple implementations return the same highest value, any of them can be used.

Suggested priority values:

- `-1` for "does not apply"
- `1` for "use this if there is nothing better". Example: `UnsupportedDatabase` 
- `5` for "default implementation". Example: `PostgresDatabase`
- `10` for a more targeted or specific implementation than the default. Example: `RedshiftDatabase`
- Higher values for more specific implementations. 
    - Example: `15` for a custom class for how you specifically use Redshift generally
    - Example: `20` for how you use Redshift in production.

!!! tip

    Services allow the existence of other services/extensions by leaving gaps above and below their priority for other extensions to fit into. 

