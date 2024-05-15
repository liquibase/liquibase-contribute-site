---
title: Configuration
---

# Configuring Liquibase with Spring Boot

## Application Properties

Spring Boot includes
a [powerful configuration system](https://docs.spring.io/spring-boot/reference/features/external-config.html){:target="_
blank"}
that allows you to configure your application using a variety of methods.

When just starting out, however, you begin with a `application.properties` or `application.yml` file in
the `src/main/resources` directory.

!!! note

    None of the below configuration settings are required. 

    Spring Boot will automatically configure Liquibase with sensible defaults for most applications.

## Commonly Used Properties

| <div style="width:330px">Property</div>    | Description                                                                                                                                                                                              |
|--------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `spring.liquibase.change-log`              | changelog configuration path. Default: `db/changelog/db.changelog-master.yaml`                                                                                                                           |
| `spring.liquibase.label-filter`            | Comma-separated list of runtime labels to use                                                                                                                                                            |
| `spring.liquibase.contexts`                | Comma-separated list of runtime contexts to use                                                                                                                                                          |
| `spring.liquibase.enabled`                 | Whether to enable Liquibase support. Default: true                                                                                                                                                       |
| `spring.liquibase.default-schema`          | Default database schema                                                                                                                                                                                  |
| `spring.liquibase.parameters.*`            | changelog parameters                                                                                                                                                                                     |
| `spring.liquibase.tag`                     | Tag name to use when applying database changes. Can also be used with `rollback-file` to generate a rollback script for all existing changes associated with that tag                                    |
| `spring.liquibase.rollback-file`           | File to append rollback SQL for any update operations performed. If you ever need to roll back your database, the contents of the file will contain the SQL to do it.                                    |
| `spring.liquibase.test-rollback-on-update` | If set to true, Liquibase will run an update followed by a rollback followed by an update again. This is useful during initial development to ensure your rollback logic works correctly. Default: false |
| `spring.liquibase.show-summary`            | Whether to print a summary of the update operation. Options: off, summary, verbose                                                                                                                       |

## Uncommonly Used Properties

| <div style="width:330px">Property</div>           | Description                                                                                                                |
|---------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| `spring.liquibase.database-change-log-lock-table` | Name of table to use for tracking concurrent Liquibase usage                                                               |
| `spring.liquibase.database-change-log-table`      | Name of table to use for tracking change history                                                                           |
| `spring.liquibase.liquibase-schema`               | Schema to use for Liquibase objects. Default: default-schema                                                               |
| `spring.liquibase.liquibase-tablespace`           | Tablespace to use for Liquibase objects                                                                                    |
| `spring.liquibase.url`                            | JDBC URL of the database to migrate. If not set, the primary configured data source is used                                |
| `spring.liquibase.user`                           | Login user of the database to migrate. If not set, the primary configured data source user is used                         |
| `spring.liquibase.password`                       | Login password of the database to migrate                                                                                  |
| `spring.liquibase.clear-checksums`                | Whether to clear all checksums in the current changelog, so they will be recalculated upon the next update. Default: false |
| `spring.liquibase.drop-first`                     | Whether to first drop the database schema. Default: false                                                                  |
| `spring.liquibase.driver-class-name`              | Fully qualified name of the JDBC driver. Default: auto-detected based on the database URL.                                 |
| `spring.liquibase.show-summary-output`            | Where to print a summary of the update operation. Options: all, console, log                                               |
| `spring.liquibase.ui-service`                     | Which UIService to use. Options: console, log                                                                              |
