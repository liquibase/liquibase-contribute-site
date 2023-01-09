---
title: "Milestone 1: Test"
---

# Testing Your New Database

## Overview

With your [new Database class defined](../milestone1-step1), Liquibase should be able to run user-defined SQL statements against the database. Give it a try!

## Testing

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

If there are any failures with creating the databasechangelog table, managing the lock, marking the change sets ran, or anything else. [GOTO step 3](../milestone1-step3).

Once `update` works, any Liquibase functionality that doesn't rely on modeled changes or snapshot should work. For example, all of this should work:

- `liquibase rollback-count 2`
- `liquibase history`
- `liquibase status`
- `liquibase tag`
- Any xml/yaml/json changelog files with only `<sql>` and `<sqlFile>` change tags

## Next Steps

If you ran into problems when testing, [see step 3](../milestone1-step3).

If everything seems to work, Congratulations!! :material-party-popper: Now is a great time to release the first version of your extension

!!! tip

    If you'd like your database extension to included in the main Liquibase documentation and be a more official part of Liquibase, contact the Liquibase team and we can work
    with you to bring it more officially into the fold.