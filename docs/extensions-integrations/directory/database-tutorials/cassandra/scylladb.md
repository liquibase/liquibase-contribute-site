---
title: ScyllaDB
---

# Using Liquibase with ScyllaDB

[ScyllaDB](https://www.scylladb.com/) is a high-performance NoSQL database that offers **drop-in compatibility** with Apache Cassandra. ScyllaDB implements the Cassandra Query Language (CQL) and uses the same wire protocol, making it fully compatible with the Liquibase Cassandra extension without any code modifications.

For more information about ScyllaDB, see the [ScyllaDB Documentation](https://docs.scylladb.com/).

## Compatibility

ScyllaDB works seamlessly with the Liquibase Cassandra extension as a drop-in replacement for Apache Cassandra. All core Liquibase operations are supported, including:

- Schema migrations (DDL operations)
- Data manipulation (DML operations)
- Changelog tracking
- Distributed locking
- Rollback functionality

For detailed compatibility information and test results, see the [ScyllaDB Compatibility Report](https://github.com/liquibase/liquibase-cassandra/blob/main/SCYLLADB_COMPATIBILITY.md) in the extension repository.

## Prerequisites

--8<-- "database-tutorial-prerequisites.md"

### Setup ScyllaDB

1. **Using Docker (recommended for local development)**

    ScyllaDB can be easily run using Docker:

    **Standard setup**:
    ```bash
    docker run --name scylladb -p 9042:9042 -d scylladb/scylla --smp 1
    ```

    **macOS setup** (requires additional flag):
    ```bash
    docker run --name scylladb -p 9042:9042 -d scylladb/scylla --reactor-backend=epoll --smp 1
    ```

    !!! note
        macOS users must include the `--reactor-backend=epoll` flag due to reactor backend requirements.

    Wait 15-20 seconds for ScyllaDB to fully initialize before connecting.

2. **Production deployments**

    For production environments, follow the official [ScyllaDB Installation Guide](https://docs.scylladb.com/stable/getting-started/install-scylla/).

3. **Create your Keyspace**

    Connect to ScyllaDB using `cqlsh` and create a keyspace:

    ```bash
    docker exec -it scylladb cqlsh
    ```

    ```sql
    CREATE KEYSPACE my_keyspace
      WITH REPLICATION = {
       'class' : 'SimpleStrategy',
       'replication_factor' : 1
      };
    ```

    The keyspace will be referenced in your Liquibase configuration as the schema.

## Install drivers

ScyllaDB uses **the same drivers and extension** as Apache Cassandra. No ScyllaDB-specific drivers are needed.

Follow the installation instructions in the [Apache Cassandra tutorial](apache-cassandra.md#install-drivers), which covers:

- Downloading the Cassandra JDBC wrapper
- Downloading the Liquibase Cassandra extension
- Placing JAR files in the correct directory
- Configuring the JDBC driver
- Maven configuration (if applicable)

## Database connection

### Configure connection

ScyllaDB uses the **same JDBC connection format** as Apache Cassandra.

1. Specify the database JDBC URL in the [`liquibase.properties`](https://docs.liquibase.com/concepts/connections/creating-config-properties.html) file:

    ```
    url: jdbc:cassandra://<host>[:<port>]/<keyspace>?compliancemode=Liquibase&localdatacenter=<datacenter_name>
    ```

    **Example for local Docker setup**:
    ```
    url: jdbc:cassandra://localhost:9042/my_keyspace?compliancemode=Liquibase&localdatacenter=datacenter1
    ```

2. Configure credentials:

    ```
    username: cassandra
    password: cassandra
    ```

    !!! note
        ScyllaDB uses `cassandra`/`cassandra` as default credentials. For production, configure appropriate authentication following the [ScyllaDB Security Guide](https://docs.scylladb.com/stable/operating-scylla/security/).

    !!! note
        Always specify the `compliancemode=Liquibase` parameter to ensure correct behavior.

    !!! tip
        For complete JDBC connection string options, see the [Cassandra JDBC connection strings documentation](https://github.com/ing-bank/cassandra-jdbc-wrapper/wiki/JDBC-driver-and-connection-string).

### Test connection

--8<-- "database-tutorial-relational-test-connection-example.md"

## Additional resources

- [ScyllaDB Documentation](https://docs.scylladb.com/)
- [ScyllaDB Compatibility Report](https://github.com/liquibase/liquibase-cassandra/blob/main/SCYLLADB_COMPATIBILITY.md) - Detailed test results and technical information
- [Apache Cassandra tutorial](apache-cassandra.md) - Complete reference for driver installation and configuration
- [Liquibase Cassandra Extension Repository](https://github.com/liquibase/liquibase-cassandra)

## Related links

*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Concepts](https://docs.liquibase.com/concepts/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Workflows](https://docs.liquibase.com/workflows/home.html)
