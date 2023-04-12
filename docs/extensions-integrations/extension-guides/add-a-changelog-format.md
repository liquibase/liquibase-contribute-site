# Add a Changelog Format

--8<-- "extension-setup.md"

## Overview

Liquibase ships with XML, YAML, JSON, and SQL parsers, but there is nothing special about those formats in the Liquibase internals.

Examples of custom parsers include:

- A parser for a custom Liquibase DSL
- A parser to read database changes stored in a 3rd party system

When adding support for a new changelog parser, the interface you are going to implement is [liquibase.parser.ChangeLogParser](../../code/api/parser-changelogparser.md).

If you would like to support generating changelogs in your custom format with `diffChangelog` or `generateChangelog` operations, you need to implement a [Changelog Serializer](../../code/api/parser-changelogparser.md).

## API Documentation

A complete description of the API, including what methods must be implemented and how is available [on the liquibase.parser.ChangeLogParser API page](../../code/api/parser-changelogparser.md).

## Example Parser Code

```java
--8<-- "src/main/java/com/example/parser/ExampleParser.java"
```
## Example Serializer Code

```java
--8<-- "src/main/java/com/example/serializer/ExampleChangeLogSerializer.java"
```