---
title: Overview
---

# Adding Support for Additional Databases

Liquibase is designed to be cross-database and separates the database-specific logic from the overall database-agnostic code.

In general, Liquibase works against standard SQL and JDBC calls so most functionality works out of the box.
Therefore, the overall process of adding support for a new database is finding and fixing the specific places where the default logic
doesn't work with your database.

This work is broken up into two milestones:

1. [Foundational Support](#foundational) which enables Liquibase to understand your database and get basic update/rollback logic working
2. [Advanced Support](#advanced) which enables "change type" and snapshot-based functionality

In each milestone, there is an end goal of working functionality and have a specific subset of interfaces to implement. 

!!! note

    This guide assumes you are adding support for a SQL-based relational database. 
    
    NoSQL and non-relational databases use the same process, but there there is less default behavior to rely on 
    and so there will be more changes to make.

    If you are interested in adding support for a NoSQL database, contact [the Liquibase team](mailto:kevin@liquibase.com) for more information.

## Prerequisites

Implementing support for additional databases requires an understanding of Java. 
You will be creating classes, overriding methods, and working with inheritance hierarchies.

You will also need to understand how to work with the new database.
As you hit places where Liquibase incorrectly assumes particular SQL will work against your database, you will need to know what the correct SQL is.

## Project Setup

If you have not already created a repository to hold your code, see [Your First Extension](../../extensions-overview/your-first-extension.md) in the Getting Started guide. 

## <a name="foundational"></a>Foundational Support

The first milestone is to have Liquibase understand how your database works and be able to run any functionality that depends on user-specified SQL.

Basically "you can run update and rollback using [Formatted SQL](https://docs.liquibase.com/concepts/changelogs/sql-format.html){:target="_blank"} or 
the [sql change type](https://docs.liquibase.com/change-types/sql.html){:target="_blank"} in xml/yaml/json. 
You will not be able to use more complex change types like `createTable` or use `snapshot` or `generateChangelog` functionality until you have finished [milestone 2](#advanced)

There are three steps in this milestone:

1. [Create a new `liquibase.database.Database` implementation](milestone1-step1.md)
2. [Test that Liquibase works with sql-based changelog files](milestone1-step2.md)
3. [(If step 2 fails) Test and fix failures until there are no more](milestone1-step3.md)

## <a name="advanced"></a>Advanced Support

At the end of this second milestone, Liquibase works against your database in a large variety of use cases, but it does not support everything Liquibase can do. 

By default, the change and snapshot logic uses standard SQL and/or JDBC calls so **_many_** will work out of the box, but not enough to advertise support for them until you have ensured they work.

The steps in this phase are:

1. [Configure test harness to determine what needs to be fixed](milestone2-step1.md)
2. [Iteratively failures from step 1 until there are no more](milestone2-step2.md)

## Next Steps

The first step in foundational support is to [create your new `liquibase.database.Database` implementation](milestone1-step1.md).
