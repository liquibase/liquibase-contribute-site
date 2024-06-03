---
title: "Milestone 1: Test"
---

# Testing Your New Database

## Overview

With your [new Database class defined](milestone1-step1.md), Liquibase should be able to run user-defined SQL statements on the database. Give it a try!

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

and run `liquibase update` on your database using that changelog file.

If there are any failures with creating the databasechangelog table, managing the lock, marking the change sets ran, or anything else. [GOTO step 3](milestone1-step3.md).

Once `update` works, any Liquibase functionality that doesn't rely on modeled changes or snapshot should work. For example, all of these should work:

- `liquibase rollback-count 2`
- `liquibase history`
- `liquibase status`
- `liquibase tag`
- Any xml/yaml/json changelog files with only `<sql>` and `<sqlFile>` change tags

## Next Steps

If/when you run into problems during testing, [go to step 3](milestone1-step3.md).

If everything seems to work, Congratulations!! :material-party-popper: Now is a great time to release the first version of your extension.
After releasing, you can proceed to [Milestone 2](milestone2-step1.md) to add advanced support.

!!! tip

    If you'd like your database extension to be included in the main Liquibase documentation and be a more official part of Liquibase, 
    [contact the Liquibase team](mailto:community@liquibase.com) and we will work with you to make it more visible in documentation 
    and in the supported databases list.