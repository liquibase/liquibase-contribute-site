# Working with the Code

## High Level Module Layout

The Liquibase repository is divided into a series of submodules, including:

- `liquibase-core` which contains most of the code
- `liquibase-cli` and `liquibase-emaven-plugin` which include the CLI and Maven Plugin integrations respectively
- `liquibase-dist` which contains the final "distributions"
- `liquibase-integration-tests` which contains tests that run against databases
- And more

Within each submodule, the code is structured following the Maven standard layout, with:

- `src` containing all the source code
- `src/main` containing the "production" code
- `src/main/java` containing the "production" Java code
- `src/main/resources` containing static files for the "production" artifacts
- `src/test` containing the "test" code
- `src/test/java` and `src/test/groovy` containing the "test" java and groovy code
- `src/test/resources`containing static files for use in tests

## Testing

For more information on unit testing in Liquibase, see the [unit testing page](unit-testing.md).

## Need help?

Visit our [Developer forum](https://forum.liquibase.org/c/liquibase-development/){:target="_blank"} or reach out [on Discord](https://discord.gg/pDB5DfE){target="_blank"}.

## Next Steps

Ready to contribute your changes? [Send us a pull request!](create-pr.md)