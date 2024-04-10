---
title: Overview
---

# Adding Support for Additional Databases

Liquibase is designed to be cross-database and separates the database-specific logic from the overall database-agnostic code.

Liquibase works with standard SQL and JDBC calls so most new database extension functionality works out of the box.

The overall process of adding support for a new database is focused on finding and fixing the specific places where the default logic doesn't work for your database.

This extension guide is broken up into two milestones:

1. [Foundational Support](#foundational-support) which enables Liquibase to understand your database and get basic update/rollback logic working
2. [Advanced Support](#advanced-support) which enables "change type" and snapshot-based functionality

Each milestone has a goal of working functionality using a specific subset of interfaces. 

!!! note

    This guide assumes you are adding support for a SQL-based relational database. 
    
    NoSQL and non-relational databases use the same process, but there there is less default behavior to rely on 
    and so there will be more changes to make.

    If you are interested in adding support for a NoSQL database, contact [the Liquibase team](mailto:community@liquibase.com) for more information.

## Prerequisites

Implementing support for additional databases requires an understanding of Java. 
You will be creating classes, overriding methods, and working with inheritance hierarchies.

You will also need to understand how to work with the new database. As you find situations where Liquibase incorrectly assumes particular SQL will work for your database, you will need to know what the correct SQL needs to be.

## Project Setup

If you have not already created a repository to hold your code, see [Your First Extension](../../extensions-overview/your-first-extension.md) in the Getting Started guide. 

## Foundational Support

The first milestone is to have Liquibase understand how your database works and to be able to run any functionality that depends on user-specified SQL.

You will be able to run update and rollback commands using a [Formatted SQL](https://docs.liquibase.com/concepts/changelogs/sql-format.html){:target="_blank"} changelog or you can use the [`sql`](https://docs.liquibase.com/change-types/sql.html){:target="_blank"} change type in a xml/yaml/json changelog. 

You will not be able to use more complex change types like `createTable`. You will also not be able to use `snapshot` or `generateChangelog` commands until you have finished [milestone 2](#advanced-support)

There are three steps in this milestone:

1. [Create a new `liquibase.database.Database` implementation](milestone1-step1.md)
2. [Test that Liquibase works with sql-based changelog files](milestone1-step2.md)
3. [(If step 2 fails) Test and fix failures until there are no more](milestone1-step3.md)

## Advanced Support

At the end of this second milestone, Liquibase will work with your database in a large number of use cases, but it does not support everything Liquibase can do. 

By default, the change and snapshot logic uses standard SQL and/or JDBC calls so **_many_** will work out of the box, but not enough to advertise support for them until you have validated they work.

The steps in this phase are:

1. [Configure test harness to determine what needs to be fixed](milestone2-step1.md)
2. [Iteratively failures from step 1 until there are no more](milestone2-step2.md)

## Next Steps

The first step in foundational support is to [create your new `liquibase.database.Database` implementation](milestone1-step1.md).
