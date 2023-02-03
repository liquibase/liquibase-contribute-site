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

| Guide                                                        | Used Extension Points                       |
|--------------------------------------------------------------|---------------------------------------------|
| [Additional Database Support](additional-databases/index.md) | liquibase.database, liquibase.snapshot      |
| [New Change Types](change-types/index.md)                    | liquibase.change                            |
| [Changelog Formats](changelog-formats/index.md)              | liquibase.parser, liquibase.serializesr     |
| [New Preconditions](preconditions/index.md)                  | liquibase.precondition                      |
| [RunWith Executors](runwith-executors/index.md)              | liquibase.executor                          |
| [Snapshot Generators](snapshot-generators/index.md)          | liquibase.snapshot                          |
| [SQL Generators](sql-generators/index.md)                    | liquibase.sqlgenerator, liquibase.statement |
