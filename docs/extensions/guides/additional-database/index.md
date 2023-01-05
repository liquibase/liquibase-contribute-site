---
title: Overview
---

# Additional Support for Additional Databases

Liquibase is designed to be cross-database and therefore separates the database-specific logic from the overall database-agnostic code.
Therefore, the overall process of adding support for a new database is finding and fixing the specific places where the default database interaction logic
doesn't work with your database.

This work is broken up into two milestones:

1. [Compatible/Foundational Support](#compatible) which configures Liquibase to understand your database and get basic update/rollback logic working
2. [Advanced Support](#advanced) which ensures change and snapshot based functionality works correctly

In each milestone, you have an end-goal of working functionality and are working with a specific subset of interfaces to implement. 

## Prerequisites

Implementing support for additional databases requires an understanding of Java. 
You will be creating classes, overridding methods, and working with inheritance hierarchies.

You will also need to understand how to work with the new database.
As you hit places where Liquibase incorrectly assumes particular SQL will work against your database, you will need to know what the correct SQL is.

Finally, this section assumes you are adding support for a SQL-based database. 
NoSQL and non-relational databases use this exact same process, but there will be more changes to make and you will need to re-implement more complex interfaces.
Therefore, to keep this information more stream-lined it is only focusing on what needs to be done for relational database. 

## Milestone 0: Project Setup

If you have not already created a repository to hold your code, see [Your First Extension](../get-started/your-first-extension.html) in the Getting Started guide. 

## <a name="compatible"></a>Milestone 1: Compatible Support

The end-goal of this milestone is to have Liquibase understand how your database works and be able
to run any functionality that depends on generated SQL from change tags or snapshot support.
Basically "you can run update and rollback using [Formatted SQL](https://docs.liquibase.com/concepts/changelogs/sql-format.html) or 
the [sql change type](https://docs.liquibase.com/change-types/sql.html) in xml/yaml/json.

The overall steps in this milestone are:

1. Create a new `liquibase.database.Database` implementation
2. Test that Liquibase works with sql-based changelog files
3. (If needed) Iteratively failures from step 2 until there are no more

### Step 1

The first step in adding support for your database is [defining a new liquibase.database.Database implementation](database.html).
Visit that topic for detailed information on creating that class.

The Database implementation acts as a dialect definition and facade to your database. 
The standard [liquibase.changelog.ChangeLogHistoryService](https://javadocs.liquibase.com/liquibase-core/liquibase/changelog/ChangeLogHistoryService.html), 
[liquibase.lockservice.LockService](https://javadocs.liquibase.com/liquibase-core/liquibase/lockservice/LockService.html), and [liquibase.executor.Executor](https://javadocs.liquibase.com/liquibase-core/liquibase/executor/Executor.html)
implementations rely on SQL and JDBC standard statements plus dialect info from your `Database` class, so will generally not need any updates.

### Step 2

With your Database class defined, Liquibase should be able to run user-defined SQL statements against the database. Give it a try!

Create a changelog file like:

```sql
-- liquibase formatted sql

--changeset example:1
create table person (id int not null primary key, name varchar(255))
--rollback drop table person

--changeset example:2
create table company (id int not null primary key, name varchar(255))
--rollback drop table company
```

and run `liquibase update` against your database using that changelog file. 
If there are any failures with creating the databasechangelog table, managing the lock, marking the change sets ran, etc. GOTO step 3.

Once update works, any Liquibase functionality that doesn't rely on modeled changes or snapshot should work. For example, all of this should work:

- `liquibase rollback-count 2`
- `liquibase history`
- `liquibase status`
- `liquibase tag`
- Using xml/yaml/json changelog files with only `<sql>` and `<sqlFile>` tags

### Step 3

For this milestone, database interactions primarily consists of:

- Creates and populates `databasechangeloglock` table using very basic/standard SQL
- Creates and populates `databasechangelog` table using very basic/standard SQL
- Executes user-specified SQL

If you run into any problems with those, the most likely fix is overriding additional `liquibase.database.Database` methods to more correctly
describe how your database works. 

For example, if the `databasechangeloglock` table isn't being created correctly because your database quotes object names in a special way, override the `escapeObjectName` function.
Exactly what you need to override will depend on the actual problems you hit.

In rare cases, you may need to [override one or more](sql-generator.html) of the following SQLGenerators:
- `liquibase.sqlgenerator.core.ClearDatabaseChangeLogTableGenerator`
- `liquibase.sqlgenerator.core.CreateDatabaseChangeLogLockTableGenerator`
- `liquibase.sqlgenerator.core.CreateDatabaseChangeLogTableGenerator`
- `liquibase.sqlgenerator.core.InitializeDatabaseChangeLogLockTableGenerator`
- `liquibase.sqlgenerator.core.MarkChangeSetRanGenerator`

## Milestone 2: Foundational Support

If you'd like your database support to included in the main Liquibase documentation and a more official part of Liquibase, contact the Liquibase team and we can work
with you to bring it more officially into the fold.

## <a name="advanced"></a>Milestone 3: Advanced Support

At the end of "milestone 1", Liquibase works against your database in a large variety of use cases, but it does not supporting everything Liquibase can do. 

By default, the change and snapshot logic uses standard SQL and JDBC calls so **_many_** will work out of the box, but not enough to advertise support for them until you have ensured they work.

The overall steps in this phase are:
1. Test that all the change types work correctly and all object types can be snapshotted
2. (If needed) Iteratively failures from step 1 until there are no more

### Step 1

Ensuring snapshot and changes work requires going through each specific change type and through each specific object type, then ensuring they execute correctly for all the different argument permutations that are valid for your database.

To automate this process, Liquibase provides a [Test Harness](https://github.com/liquibase/liquibase-test-harness/blob/main/README.extensions.html) to find everywhere the default logic isn't compatible with your database.
Configure your project using the steps [in the Test Harness repository](https://github.com/liquibase/liquibase-test-harness/blob/main/README.extensions.html) and run the tests.

### Step 2

Fixing the failing tests will entail creating custom [liquibase.sqlgenerator.SqlGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/sqlgenerator/SqlGenerator.html)
and [liquibase.snapshot.SnapshotGenerator](https://javadocs.liquibase.com/liquibase-core/liquibase/snapshot/SnapshotGenerator.html) extension classes. 

The general pattern for both is to create a new class which returns a higher priority **_if and only if_** Liquibase is using the `Database` class you defined in milestone 1.

You will likely have a number of failures across the two types of functionality:

- Failing Change calls, which require you to [define new SqlGenerator implementations](sql-generator.html) 
- Failing Snapshot calls, which require you to [define a new SnapshotGenerator implementations](snapshot-generator.html)

Once all the Test Harness tests are passing, you will be able to use change types in XML/YAML/JSON changelogs as well as any snapshot based functionality.
For example, all of this should now work:

- `liquibase update` with `<createTable...` in the changelog
- `liquibase update` with `<tableExists...` preconditions in the changelog
- `liquibase snapshot`
- `liquibase diff`
- `liquibase diff-changelog`
- `liquibase generate-changelog`

## Next Steps

Congratulations! You can certainly stop here and use all the standard Liquibase functionality against your database. 

However, if there is new and unique functionality your database supports, you can always expand beyond Liquibase's standard logic to 
work with it.

Perhaps your database supports different object types? Add new `DatabaseObject` classes to your extension and snapshot in order to see them in diff operations.

Does your database has statements or operations which you would like to wrap in custom Change functions? Add add new `Change` classes to your extension and the `SqlGenerator` for it.

Any other new functionality can be bundled into your extension, but also think about the scope of the functionality you are adding. 
If it is truly unique to this database you can certainly add it to this extension, but if it is something that can be used beyond your database it is likely best packaged as an independent exception.  

