# Extension Guides

Once you have learned the basics of Liquibase Extensions in the [HelloWorldChange](../extensions-overview/your-first-extension.md)
sample, it's time to build some real-world extensions.

In each guide or sample, you can expect to find:

- Thoroughly commented source code
- An example showing the usage of the sample extension
- Listing of APIs being used
- Explanation of API concepts

## Guides & Samples

Here are the available guides, including the APIs they use:

| Guide                                                                       | Extension Points                            |
|-----------------------------------------------------------------------------|---------------------------------------------|
| [Add a Database](add-a-database/index.md)                                   | liquibase.database, liquibase.snapshot      |
| [Add a Native Database Executor](add-a-native-executor.md)                  | liquibase.executor                          |
| [Add a Change Type](add-a-change-type.md)                                   | liquibase.change                            |
| [Add a Changelog Format](add-a-changelog-format.md)                         | liquibase.parser, liquibase.serializer      |
| [Add a Configuration Value Provider](add-a-configuration-value-provider.md) | liquibase.configuration                     |
| [Add a Path Handler](add-a-path-handler.md)                                 | liquibase.resource                          |
| [Add a Precondition](add-a-precondition.md)                                 | liquibase.precondition                      |
| [Add a Resource Accessor](add-a-resource-accessor.md)                       | liquibase.resource                          |
| [Add a Snapshot Generator](add-a-snapshot-generator.md)                     | liquibase.snapshot                          |
| [Add a SQL Generator](add-a-sql-generator.md)                               | liquibase.sqlgenerator, liquibase.statement |
