---
title: DB2 on z/OS SQL Scripts
---

# Deploying Changes to DB2 on z/OS using SQL Scripts

## Step 1: Create a SQL folder

In the Liquibase project folder that you created, create a folder named `sql_files` to store SQL scripts that Liquibase will track, version, and deploy.

The directory structure should look like this:

```
$LB_HOME/db2_zos/sql_files
```

## Step 2: Set up the changelog

This is a one-time step to configure a change log to point to the SQL folder that will contain SQL scripts. Create and save a file called `changelog.xml` in the Liquibase project directory you created (`$LB_HOME/db2_zos`).

The contents of `changelog.xml` should be as follows:

```
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:pro="http://www.liquibase.org/xml/ns/pro"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
        http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd">	
<includeAll path="sql_files"/>
</databaseChangeLog>
```

## Step 3: Add SQL Script

With a Liquibase properties file, like liquibase.properties from the tutorial setup and the newly created `changelog.xml`, we are now ready to start adding SQL scripts to the `sql_files` folder. Liquibase orders the scripts in the folder alphanumerically.

Create a file named `001_create_person_table.sql` with the following and save it in the `sql_files` folder:

```
create table PERSON (
ID int not null,
FNAME varchar(100) not null
);
```

## Step 4: Check your database

Navigate to your project folder in the CLI and run the Liquibase [status](https://docs.liquibase.com/commands/change-tracking/status.html) command to see whether the connection is successful:

```
liquibase status --username=test --password=test --changelog-file=<changelog.xml>
```

!!! note
    You can pass arguments in the CLI or keep them in the Liquibase properties file.

Inspect the SQL with the [update-sql](https://docs.liquibase.com/commands/update/update-sql.html) command. Then make changes to your database with the [update](https://docs.liquibase.com/commands/update/update.html) command:

```
liquibase update-sql --changelog-file=<changelog.xml>
liquibase update --changelog-file=<changelog.xml>
```

From a database UI tool, ensure that your database contains the table you added along with the [DATABASECHANGELOG table](https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html) and [DATABASECHANGELOGLOCK table](https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html).

## Common command line arguments
You can use command line arguments to override the default options at runtime. The following are common command line arguments:

| Command line argument | Action |
| --------------------- | ------ |
| `--changelog-file=<path and filename>` | Specify the XML changelog |
| `--url=<value>` | Specify a database URL |
| `--defaultsFile=<path to file.properties>` | Specify the properties file. Default is `./liquibase.properties`). |
