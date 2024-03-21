---
title: With Kerberos
---

# Using Liquibase and Spring Boot with SQL Plus and Kerberos Authentication

The Liquibase Spring Boot integration supports SQL Plus using [`runWith=sqlplus`](https://docs.liquibase.com/concepts/changelogs/attributes/runwith.html) changesets for Liquibase Pro. To run the `runWith` types of changesets, Liquibase requires the authentication mechanism that allows running the native executor without specifying credentials, such as Kerberos.

For now, the `runWith=sqlplus` attribute is **not available** if you connect to your database using the following authentication mechanisms:

*   JNDI Datasource
*   External `spring.datasource.*` properties

However, the authentication mechanisms do not affect changesets which do not use the `runWith=sqlplus` attribute.

## Prerequisites

Ensure you have configured the following parts:

*   Liquibase with Oracle
*   Liquibase with Spring Boot

If you do not have the needed configuration, follow [Using Liquibase with Oracle Database](https://docs.liquibase.com/start/tutorials/oracle.html) and [Using Liquibase with Spring Boot](https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/springboot/springboot/) by replacing the values from the documentation with your values.

## Running Spring Boot and SQL Plus with Kerberos

1.  Configure Liquibase and Kerberos.
    
    !!! Tip
        See [Connecting to an Oracle Database with Liquibase via Kerberos and Active Directory](https://docs.liquibase.com/start/tutorials/oracle-connect-kerberos-active-directory.html).
    
2.  Specify how Liquibase can find SQL Plus by adding SQL Plus to your PATH. Alternatively, pass its location in the `liquibase.sqlplus.conf` file or from the command prompt using the `--sqlplus-path` parameter during runtime.

3.  Specify the `runWith` attribute to the needed changesets in the changelog file that you use. For more information, see [Use SQL Plus and runWith on Oracle Database](https://docs.liquibase.com/concepts/changelogs/attributes/use-sql-plus-integration.html).

4.  Run the `update` command:

    !!! Note
        If you have specified your changelog file as an environment variable or in the properties file, you can skip passing it at the command prompt.

    ```
    liquibase update --changelog-file=<your changelog file>
    ```