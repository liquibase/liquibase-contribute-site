# Extension Guides

Once you have learned the basics of Liquibase Extensions in the [HelloWorldChange](../your-first-extension.md)
sample,
it's time to build some real-world extensions.

In each guide or sample, you can expect to find:

- Thoroughly commented source code
- An example showing the usage of the sample extension
- Listing of APIs being used
- Explanation of API concepts

## Guides & Samples

Here are the available guides, including the APIs they use

| Guide                                                               | Extension Points                            |
|---------------------------------------------------------------------|---------------------------------------------|
| [Add New Database Support](add-a-database/index.md)                 | liquibase.database, liquibase.snapshot      |
| [New Change Types](add-a-change-type/index.md)                      | liquibase.change                            |
| [Changelog Formats](add-a-changelog-format/index.md)                | liquibase.parser, liquibase.serializer      |
| [Configuration Values](add-a-configuration-value-provider/index.md) | liquibase.configuration                     |
| [Path Handlers](add-a-path-handler/index.md)                        | liquibase.resource                          |
| [New Preconditions](add-a-precondition/index.md)                    | liquibase.precondition                      |
| [Resource Accessors](add-a-resource-accessor/index.md)              | liquibase.resource                          |
| [RunWith Executors](add-a-native-database-executor/index.md)        | liquibase.executor                          |
| [Snapshot Generators](add-a-snapshot-generator/index.md)            | liquibase.snapshot                          |
| [SQL Generators](add-a-sql-generator/index.md)                      | liquibase.sqlgenerator, liquibase.statement |
