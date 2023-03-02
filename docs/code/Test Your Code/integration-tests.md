---
title: Integration Tests
---

# How to Write Integration Tests for Liquibase

Liquibase code contains both [unit tests](unit-tests.md) and integration tests. Here are the basics of creating integration tests used by the
automated build process to validate Liquibase and reduce regressions.

## What are integration tests?

Integration tests validate the interaction between the Liquibase code and external systems, such as databases.

Liquibase integration tests are written using both standard JUnit and the [Spock testing framework.](https://spockframework.org/){:target="_blank"} depending on the age of the tests.

There are two main styles/patterns of integration tests currently in the Liquibase codebase: 

- ["Classic" Integration tests](#classic-integration-tests)
- [Test-Harness tests](#test-harness-tests)

## When to Write Integration Tests

Integration tests should be written whenever there is new database-interacting behavior being created or fixed.

Because Liquibase is designed to be cross-database, most of the code is not database dependent and therefore should be tested with [unit tests](unit-tests.md).
In general, integration tests are needed if and only if you are creating or changing:

- [SqlGenerators](../Extensions & Integrations/Extension Guides/sql-generators)
- [SnapshotGenerators](../Extensions & Integrations/Extension Guides/snapshot-generators)
- [Preconditions](../Extensions & Integrations/Extension Guides/preconditions)

Write Test Harness integration tests when:

- You are working on a Liquibase extension
- You are adding a new change type or snapshot object
- You are adding substantial new functionality to a change type

Write classic integration tests when:

- You have not added a Test Harness test
- You are fixing bugs or handling attribute permutations in existing change/snapshot/precondition logic 
- You are changing database interaction code beyond the change, snapshot, and data tests which Test Harness can test

## Classic Integration Tests

The [liquibase-integration-tests](https://github.com/liquibase/liquibase/tree/master/liquibase-integration-tests){:target="_blank"} submodule in the Liquibase repository contains
the original and still often-used set of "classic" integration tests.

The goal of these integration tests is to ensure common functionality works across all database types that ship with Liquibase Core as well as test database-specific functionality.
They support these goals by having a `liquibase.dbtest.AbstractIntegrationTest` class which defines tests for standard behaviors, and then having database-specific subclasses such as `liquibase.dbtest.mysql.MySQLIntegrationTest` which adds and/or overrides tests as needed.

### Test Structure

These integration tests tend to test more complete workflows and test multiple things rather than try to isolate any particular part of the code for specific tests. 

For example, the `testUpdateTwice()` test runs an "update" operation using a complete changelog and then runs "update" again. This allows us to test:

- The complete changelog parse -> change sql generation -> execution -> history service flow
- Specific cases in that flow, such as include/includeAll handling and different changelog formats
- Precondition/context/label handling
- Permutations of different change configurations
- Already-ran changesets are not re-ran

Within the changelogs, we can use preconditions to check whether specific changes executed successfully but usually rely on the fact that invalid SQL will throw a parse exception from the database.

Another example is `testRollbackableChangeLog()` which performs an "update" followed by a "rollback" and then repeats that process to ensure nothing is incorrectly left over after the rollback.

### Changelog Files

There is a `src/test/resources/changelogs` directory that contains all the changelogs used by the integration tests. 

For each database, there is a "complete" and a "rollback" changelog. The "complete" changelog is an exhaustive set of change definitions which are used by most of the integration tests. 
For tests that require the ability to rollback an update, the "rollback" changelog is available which has a smaller set of changes but ensures all have rollback logic defined.

Each database defines the root changelogs as `changelogs/DB_TYPE/complete/root.changelog.xml` and `changelogs/DB_TYPE/rollback/rollbackable.changelog.xml`, but 
each database-specific changelog includes the changelog files in `changelogs/common` starting with `changelogs/common/common.tests.changelog.xml`. 
That allows anything added to the "common" changelogs to be run against all database types.   

### Running Tests

The classic integration tests use the [Liquibase SDK Test Environments](test-environments.md) to know what databases to test against and how to connect. 
When any of the `*IntegrationTest` classes like `MysqlIngegrationTest` is run, Liquibase will either skip or connect to the database under test based on the SDK Test Environments settings.

#### Running via IDE

The integration tests are JUnit classes. You will generally be running a specific database's tests, like `MysqlIntegrationTests` and your IDE will let you choose which test class(es) and methods you want to run.

#### Running via Maven

You are also able to run all tests via maven.

From the root of the liquibase repository, run `mvn test` which will build the project and run all the tests, including the classic integration tests.

The output will show the tests executing like this:

```
[INFO] ------------------------------------------------------------------------ 
[INFO] Reactor Build Order: 
[INFO] 
[INFO] liquibase-core [jar] 
[INFO] liquibase-maven-plugin [maven-plugin] 
[INFO] liquibase-cdi [jar]
[INFO] liquibase-integration-tests [jar]
[INFO] liquibase-root [pom]
[INFO] liquibase-dist [jar]
[INFO]
 ..........[ skipped output ].....................
[INFO] --------------------< org.liquibase:liquibase-integration-tests >--------------------
[INFO] Building liquibase-integration-tests 0-SNAPSHOT [1/11]
 ..........[ skipped output ].....................
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running liquibase.command.core.GenerateChangeLogDb2CommandTest
[WARNING] Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.195 s - in liquibase.command.core.GenerateChangeLogDb2CommandTest
[INFO] Running liquibase.command.core.GenerateChangeLogMSSQLCommandTest
[WARNING] Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.216 s - in liquibase.command.core.GenerateChangeLogMSSQLCommandTest
[INFO] Running liquibase.command.core.GenerateChangeLogMySQLCommandTest
[WARNING] Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.016 s - in liquibase.command.core.GenerateChangeLogMySQLCommandTest
[INFO] Running liquibase.command.core.ListLocksTest
[WARNING] Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.001 s - in liquibase.command.core.ListLocksTest
[INFO] Running liquibase.dbtest.asany.SybaseASAIntegrationTest
[WARNING] Tests run: 31, Failures: 0, Errors: 0, Skipped: 31, Time elapsed: 0.236 s - in liquibase.dbtest.asany.SybaseASAIntegrationTest
[INFO] Running liquibase.dbtest.cockroachdb.CockroachDBIntegrationTest
[WARNING] Tests run: 32, Failures: 0, Errors: 0, Skipped: 32, Time elapsed: 0.004 s - in liquibase.dbtest.cockroachdb.CockroachDBIntegrationTest
[INFO] Running liquibase.dbtest.db2.DB2IntegrationTest
[WARNING] Tests run: 31, Failures: 0, Errors: 0, Skipped: 31, Time elapsed: 0.002 s - in liquibase.dbtest.db2.DB2IntegrationTest
[INFO] Running liquibase.dbtest.db2z.DB2zIntegrationTest
[WARNING] Tests run: 31, Failures: 0, Errors: 0, Skipped: 31, Time elapsed: 0.003 s - in liquibase.dbtest.db2z.DB2zIntegrationTest
[INFO] Running liquibase.dbtest.derby.DerbyIntegrationTest
[WARNING] Tests run: 31, Failures: 0, Errors: 0, Skipped: 31, Time elapsed: 0.001 s - in liquibase.dbtest.derby.DerbyIntegrationTest
[INFO] Running liquibase.dbtest.firebird.FirebirdIntegrationTest
[WARNING] Tests run: 31, Failures: 0, Errors: 0, Skipped: 31, Time elapsed: 0.001 s - in liquibase.dbtest.firebird.FirebirdIntegrationTest
[INFO] Running liquibase.dbtest.h2.H2IntegrationTest
[WARNING] Tests run: 39, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 12.63 s - in liquibase.dbtest.h2.H2IntegrationTest
[INFO] Running liquibase.dbtest.hsqldb.HsqlIntegrationTest
[WARNING] Tests run: 31, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 9.326 s - in liquibase.dbtest.hsqldb.HsqlIntegrationTest
[INFO] Running liquibase.dbtest.informix.InformixIntegrationTest
[WARNING] Tests run: 31, Failures: 0, Errors: 0, Skipped: 31, Time elapsed: 0.001 s - in liquibase.dbtest.informix.InformixIntegrationTest
[INFO] Running liquibase.dbtest.IntXMLChangeLogSAXParserTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.714 s - in liquibase.dbtest.IntXMLChangeLogSAXParserTest
[INFO] Running liquibase.dbtest.mariadb.MariaDBIntegrationTest
[WARNING] Tests run: 32, Failures: 0, Errors: 0, Skipped: 32, Time elapsed: 0.001 s - in liquibase.dbtest.mariadb.MariaDBIntegrationTest
[INFO] Running liquibase.dbtest.mssql.MssqlCaseSensitiveIntegrationTest
[WARNING] Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0 s - in liquibase.dbtest.mssql.MssqlCaseSensitiveIntegrationTest
[INFO] Running liquibase.dbtest.mssql.MssqlIntegrationTest
[WARNING] Tests run: 36, Failures: 0, Errors: 0, Skipped: 36, Time elapsed: 0.001 s - in liquibase.dbtest.mssql.MssqlIntegrationTest
[INFO] Running liquibase.dbtest.mssql.MssqlJtdsIntegrationTest
[WARNING] Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0 s - in liquibase.dbtest.mssql.MssqlJtdsIntegrationTest
[INFO] Running liquibase.dbtest.mysql.MySQLIntegrationTest
[WARNING] Tests run: 32, Failures: 0, Errors: 0, Skipped: 32, Time elapsed: 0 s - in liquibase.dbtest.mysql.MySQLIntegrationTest
[INFO] Running liquibase.dbtest.oracle.OracleIntegrationTest
[WARNING] Tests run: 36, Failures: 0, Errors: 0, Skipped: 36, Time elapsed: 0.001 s - in liquibase.dbtest.oracle.OracleIntegrationTest
[INFO] Running liquibase.dbtest.pgsql.PostgreSQLIntegrationTest
[WARNING] Tests run: 36, Failures: 0, Errors: 0, Skipped: 36, Time elapsed: 0 s - in liquibase.dbtest.pgsql.PostgreSQLIntegrationTest
[INFO] Running liquibase.dbtest.sqlite.SQLiteIntegrationTest
[WARNING] Tests run: 32, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 3.961 s - in liquibase.dbtest.sqlite.SQLiteIntegrationTest
[INFO] Running liquibase.diffchangelog.DiffChangelogTest
[WARNING] Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.001 s - in liquibase.diffchangelog.DiffChangelogTest
 ..........[ skipped output ].....................
[INFO] Reactor Summary for liquibase-root:
[INFO]
[INFO] liquibase-core ..................................... SUCCESS [01:09 min]
[INFO] liquibase-maven-plugin ............................. SUCCESS [ 9.587 s]
[INFO] liquibase-cdi ...................................... SUCCESS [ 9.642 s]
[INFO] liquibase-integration-tests ........................ SUCCESS [ 56.768 s]
[INFO] liquibase-root ..................................... SUCCESS [ 0.020 s]
[INFO] liquibase-dist ..................................... SUCCESS [ 7.143 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 02:48 min
[INFO] Finished at: 2020-04-14T16:24:28-05:00
[INFO] ------------------------------------------------------------------------
```

You can see which databases were tested and which were skipped from the above output:

- `[WARNING] Tests run: 36, Failures: 0, Errors: 0, Skipped: 36, Time elapsed: 0.001 s - in liquibase.dbtest.oracle.OracleIntegrationTest` shows that the Oracle tests were skipped
- `[WARNING] Tests run: 39, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 12.63 s - in liquibase.dbtest.h2.H2IntegrationTest` shows that the H2 tests were not skipped.

!!! tip

    If any tests failed, you’ll see it say `BUILD FAILED` at the end and the `TESTS` section will list the failing tests.

### Adding New Tests

Since most changes should work on all databases, the most common process is:

1. Add new changeSets to `liquibase-integration-tests/src/test/resources/changelogs/common/common.tests.changelog.xml` that exercise your code. Add comments and "assertion" preconditions as needed to explain and check what is being tested.
2. Run the `testUpdateTwice()` test on the `AbstractIntegrationTest` subclass(es) you are looking to check
3. Ensure no exceptions are thrown and the test still passes

An example "test" is simply a new changeset like this in `common.tests.changelog.xml`:

```xml
<changeSet id="loadData supports gzip'ed data" author="test">
    <loadData tableName="csvdata" file="changelogs/sample.data1.csv.gz"/>
</changeSet>
```

If your change setup is not supported on all databases, exclude databases with the `dbms` attribute.

For more complex tests, you can add new test methods to either `AbstractIntegrationTest` or the specific subclasses to test what is needed.  

### Limitations

The end-to-end nature of the classic integration tests allows us to have a single test that checks that multiple components are correctly working together, 
but if tests fail it can be more difficult to determine where the problem is. For example, is an attribute not being picked up by the XML parser? Or is it not being used correctly in the SQL generation?
If there is a good way to test your code in a more isolated way, that is generally best.

The classic integration tests are skipped for databases that are disabled in the [Liquibase SDK Test Environments](test-environments.md), which can lead to some failures not being seen until they are run on the build system.

The classic integration test framework is not available to extensions, only to code directly in the main Liquibase repository. If you are writing an extension, the [Test Harness](#test-harness-tests) is your only option.  
 

## Test Harness Tests

To address the limitations of the classic integration tests, we have created the [Liquibase Test Harness](https://github.com/liquibase/liquibase-test-harness){:target="_blank"}.

The Test Harness is a separate repository that is used to verify database support both for databases managed within Liquibase Core and also from extensions.

For more information on using the Test Harness, see the README files within the `liquibase-test-harness` repository plus our [learn.liquibase.com](https://learn.liquibase.com){:target="_blank"} course

- [README.md](https://github.com/liquibase/liquibase-test-harness/blob/main/README.md){:target="_blank"}
- [README.extensions.md](https://github.com/liquibase/liquibase-test-harness/blob/main/README.extensions.md){:target="_blank"}
- ["Introduction to Liquibase Test Harness" on learn.liquibase.com](https://learn.liquibase.com/catalog/info/id:130){:target="_blank"}

### Submitting Test Harness Tests with your Liquibase Pull Request

If your PR includes new tests and/or changes to tests within the Test Harness, create a separate PR to the [liquibase-test-harness](https://github.com/liquibase/liquibase-test-harness){:target="_blank"} repo using the same branch name as you used for your PR to `liquibase`.
The build system will automatically detect the matching branch names and build the correct Test Harness and Liquibase branches together.

!!! note

    For your liquibase-test-harness branch, you must branch off `develop` and **_not_** `main`. 

    The `main` branch is for tests on the current Liquibase release, while `develop` is for tests on Liquibase snapshot builds.  

!!! note

    Please add a comment to one of the PRs referencing the other, so they are easy to find during review and merge time. 

