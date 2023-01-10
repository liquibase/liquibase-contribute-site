---
title: "Milestone 1: Fix & Retest"
---

# Fix and Retest

## Overview

The functionality you [tested for milestone 1](milestone1-step2.md) relies on:

- Create and populate `databasechangeloglock` table
- Create and populate `databasechangelog` table
- Execute user-specified SQL

## Improving Your Database Class

The standard [liquibase.changelog.ChangeLogHistoryService](https://javadocs.liquibase.com/liquibase-core/liquibase/changelog/ChangeLogHistoryService.html){:target="_blank"},
[liquibase.lockservice.LockService](https://javadocs.liquibase.com/liquibase-core/liquibase/lockservice/LockService.html){:target="_blank"}, and [liquibase.executor.Executor](https://javadocs.liquibase.com/liquibase-core/liquibase/executor/Executor.html){:target="_blank"}
implementations which drive that rely on SQL and/or JDBC standards guided by dialect info from your `Database` class.

If you run into any problems with those standard services, the most likely fix is overriding additional `liquibase.database.Database` methods to more correctly
describe how your database works.  

For example, if the `databasechangeloglock` table isn't being created correctly because your database quotes object names in a special way, override the `escapeObjectName` function.

!!! tip

    Exactly what you need to override will depend on the actual problems you hit. If you have questions on what it takes to fix your problem, ask [on the forum](https://forum.liquibase.org){:target="_blank"}.

## Advanced Fixes

If your database deviates significantly from what the standard services expect, your changes may not be isolated to your new `Database` class.

Instead, you may need to [override one or more](sql-generator.md) of the following SQLGenerators:

- `liquibase.sqlgenerator.core.ClearDatabaseChangeLogTableGenerator`
- `liquibase.sqlgenerator.core.CreateDatabaseChangeLogLockTableGenerator`
- `liquibase.sqlgenerator.core.CreateDatabaseChangeLogTableGenerator`
- `liquibase.sqlgenerator.core.InitializeDatabaseChangeLogLockTableGenerator`
- `liquibase.sqlgenerator.core.MarkChangeSetRanGenerator`

## Iterate

After you have made a potential fix to the problem you found, re-run your test and see if Liquibase is working better.

- Sometimes your fix didn't work as well as you hoped. Try a new one
- Sometimes your fix resolved one issue, but now you have hit another. Fix that up
- Eventually you will be done. Congratulations!! :material-party-popper: 

## Next Steps

With Liquibase working against your database, now is a great time to release the first version of your extension and get feedback.

When you are ready to build advanced support, you can move on to [milestone 2](milestone2-step2.md)