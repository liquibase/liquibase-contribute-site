---
title: Overview
---

# Liquibase Database Tutorials: Community-Maintained
Liquibase works with 60+ databases including relational, NoSQL, and graph databases.

Learn how to use Liquibase for your favorite databases with our in-depth tutorials. These tutorials explain how to install and configure your database with Liquibase.

* The full list of supported databases: [Supported Databases](https://www.liquibase.com/supported-databases){:target="_blank"}.
    * Liquibase-maintained databases: [Liquibase-maintained databases](https://docs.liquibase.com/start/tutorials/home.html){:target="_blank"}.
    * Community-maintained databases ([see below](http://localhost:8080/extensions-integrations/directory/database-tutorials/#community-maintained-database-support))

!!! Note
    Liquibase supports English language databases only. Other languages with special characters may have issues.

## Community-maintained Database Support

<table id="dbtutorials">
  <thead>
    <tr>
      <th>Database</th>
      <th>Verified Database Versions</th>
      <th>Verification Level</th>
      <th>Shortname / Driver / Notes</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <a href="https://github.com/liquibase/liquibase-amazon-keyspaces">Amazon Keyspaces</a>
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-amazon-keyspaces">GitHub: Liquibase Amazon Keyspaces Extension</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/apache-derby/">Apache Derby</a>
      </td>
      <td>
        10.14
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>derby</code><br>
        <br>
        <b>File:</b> <code>derbytools.jar</code><br>
        <b>Class:</b> <code>org.apache.derby</code><br>
        <b>JDBC URL:</b> <code>jdbc:derby://localhost:1527/MYDATABASE;create=true</code><br>
        <b>Download:</b> <a href="https://db.apache.org/derby/derby_downloads.html">Apache Derby: Downloads</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/cassandra/apache-cassandra/">Apache Cassandra</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>cassandra</code><br>
        <br>
        <b>File:</b> <code>cassandra-jdbc-wrapper-&lt;version&gt;-bundle.jar</code><br>
        <b>Class:</b> <code>com.ing.data.cassandra.jdbc.CassandraDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:cassandra://localhost:9042/myKeyspace?compliancemode=Liquibase&localdatacenter=DC1</code><br>
        <b>Download:</b> <a href="https://github.com/ing-bank/cassandra-jdbc-wrapper/releases">Cassandra JDBC wrapper</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/cassandra/datastax-astra-db/">Apache Cassandra on DataStax Astra DB</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>cassandra</code><br>
        <br>
        <b>File:</b> <code>cassandra-jdbc-wrapper-&lt;version&gt;-bundle.jar</code><br>
        <b>Class:</b> <code>com.ing.data.cassandra.jdbc.CassandraDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:cassandra:dbaas:///myKeyspace?compliancemode=Liquibase&consistency=LOCAL_QUORUM&user=myUsername&password=myPassword&secureconnectbundle=&lt;/path/to/location/secure-connect-bundle-cluster.zip&gt;</code><br>
        <b>Download:</b> <a href="https://github.com/ing-bank/cassandra-jdbc-wrapper/releases">Cassandra JDBC wrapper</a>
      </td>
    </tr>
    <tr>
      <td>
        Aerospike
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b> <code>aerospike</code><br>
        <br>
        <b>File:</b> <code>uber-aerospike-jdbc-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.aerospike.jdbc.AerospikeDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:aerospike:&lt;host&gt;:&lt;port&gt;/&lt;namespace&gt;</code><br>
        <b>Download:</b> <a href="https://github.com/aerospike/aerospike-jdbc"></a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-aerospike">GitHub: Liquibase Aerospike Extension</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/cosmosdb/">Azure Cosmos DB</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>cosmosdb</code><br>
        <br>
        <b>File:</b> <code>azure-cosmos-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.azure.cosmos.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:cosmosdb://AccountEndpoint=https://&lt;cosmosdb-account-name&gt;.documents.azure.com:443;AccountKey=&lt;accountKey&gt;;</code><br>
        <b>Download:</b> <a href="https://www.cdata.com/drivers/cosmosdb/download/jdbc/">CData: JDBC Driver Download</a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-cosmosdb/releases">GitHub: liquibase/liquibase-cosmosdb</a><br>
        <p>
<b>Notes:</b> For Azure Cosmos DB Emulator, use this JDBC URL:<code class="language-text">jdbc:cosmosdb://localhost:C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==@localhost:8080/testdb1</code></p>
      </td>
    </tr>
    <tr>
      <td>
        ClickHouse
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href=""></a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/cloud-spanner/">Cloud Spanner</a>
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b> <code>cloudspanner</code><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b> <code>jdbc:cloudspanner:/projects/&lt;project&gt;/instances/&lt;instance&gt;/databases/&lt;database&gt;</code><br>
        <b>Download:</b> <a href="https://github.com/liquibase/liquibase-spanner">GitHub: Liquibase Spanner Extension</a><br>
        <b>Extension:</b> <code>liquibase-spanner-version-all.jar</code><br>
        <p><b>Notes:</b> Includes extension and driver.</p>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/cockroachdb/">CockroachDB (on-premises)</a>
      </td>
      <td>
        20, 21, 22
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>cockroachdb</code><br>
        <br>
        <b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>org.postgresql.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:postgresql://localhost:26257/database?ssl=true&amp;sslmode=require&amp;sslrootcert=/full-path/certs/ca.crt&amp;sslkey=/full-ath/certs/client.user.key.pk8&amp;sslcert=/full-path/certs/client.user.crt</code><br>
        <b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a><br>
        <p><b>Notes:</b> TLS certificate workflow is preferred. The following option is insecure: <code class="language-text">jdbc:postgresql://localhost:26257/dev</code></p>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/cockroachdb/">CockroachDB (cloud)</a>
      </td>
      <td>
        20, 21, 22
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>cockroachdb</code><br>
        <br>
        <b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>org.postgresql.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:postgresql://liquibase-3r8.aws-us-east-2.cockroachlabs.cloud:26257/defaultdb?sslmode=verify-full&amp;sslrootcert=liquibase-ca.crt</code><br>
        <b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a><br>
        <p><b>Notes:</b> Requires TLS certificate workflow.</p>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/firebird/">Firebird RDBMS</a>
      </td>
      <td>
        3, 4
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>firebird</code><br>
        <br>
        <b>File:</b> <code>jaybird-full-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>org.firebirdsql.jdbc.FBDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:firebirdsql://&lt;IP/host&gt;:&lt;port&gt;//&lt;server/path/to/database&gt;</code><br>
        <b>Download:</b> <a href="https://github.com/FirebirdSQL/jaybird/releases">GitHub: jaybird: releases</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/bigquery/">Google BigQuery</a>
      </td>
      <td>
        Cloud
      </td>
      <td>
        Foundational
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>bigquery</code><br>
        <br>
        <b>File:</b> <code>GoogleBigQueryJDBC&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.google.cloud</code><br>
        <b>JDBC URL:</b> <code>jdbc:bigquery://https://googleapis.com/bigquery/v2:443/&lt;dbname&gt;;ProjectId=&lt;STR&gt;;OAuthType=&lt;INT&gt;;</code><br>
        <b>Download:</b> <a href="https://cloud.google.com/bigquery/">Google:&nbsp;BigQuery</a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-bigquery/releases">GitHub: liquibase/liquibase-bigquery</a>
      </td>
    </tr>
    <tr>
      <td>
        Greenplum Database
      </td>
      <td>
        6
      </td>
      <td>
        Foundational
      </td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>org.postgresql.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:postgresql://&lt;host&gt;:&lt;port&gt;/&lt;database&gt;</code><br>
        <b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/tutorials/h2/">H2 Database Engine</a>
      </td>
      <td>
        1.4, 2.1.21
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>h2</code><br>
        <br>
        <b>File:</b> <code>h2-&lt;version&gt;.jar</code><br>
        <b>Class:</b> NA<br>
        <b>JDBC URL:</b>
        <ul>
          <li>Memory: <code>jdbc:h2:tcp://localhost:9090/mem:dev</code>
          </li>
          <li>File: <code>jdbc:h2:file:C:/tools/LB_DBs/H2Example/h2tutorial</code>
          </li>
        </ul><b>Download:</b> Included with Liquibase in <code>&lt;install-directory/internal/lib&gt;</code><br>
        <p><b>Notes:</b> Included with Liquibase.</p>
      </td>
    </tr>
    <tr>
      <td>
        HarperDB
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-harperdb">GitHub: Liquibase HarperDB Extension</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/hibernate/">Hibernate</a>
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b> <code>h2-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>liquibase.ext.hibernate.database.connection.HibernateDriver</code><br>
        <b>JDBC URL:</b> <code>hibernate:ejb3:com.liquibase.hibernate.tutorial.jpa</code><br>
        <b>Download:</b> Included with Liquibase in <code>&lt;install-directory/internal/lib&gt;</code><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-hibernate/releases">GitHub: liquibase/liquibase-hibernate</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/hsqldb/">HyperSQL</a>
      </td>
      <td>
        2.3, 2.4, 2.5, 2.6, 2.7
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>hsqldb</code><br>
        <br>
        <b>File:</b> <code>hsqldb.jar</code><br>
        <b>Class:</b> <code>org.hsqldb.jdbcDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:hsqldb:hsql://192.168.1.15:9001/&lt;db-name&gt;</code><br>
        <b>Download:</b> Included with <a href="https://sourceforge.net/projects/hsqldb/files/">HyperSQL download from SourceForge</a>
      </td>
    </tr>
    <tr>
      <td>
        IBM DB2 for i
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>db2</code><br>
        <br>
        <b>File:</b> <code>db2jcc&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.ibm.as400.access.AS400JDBCDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:as400://&lt;host&gt;;libraries=&lt;database&gt;;</code><br>
        <b>Download:</b> <a href="https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads">IBM: DB2 JDBC Driver Versions and Downloads</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/db2onz/">IBM Db2 for Z</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>db2</code><br>
        <br>
        <b>File:</b> <code>db2jcc&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.ibm.db2.jcc.DB2Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:db2://&lt;host-IP-address&gt;:&lt;port&gt;/&lt;db-name&gt;:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;</code><br>
        <b>Download:</b> <a href="https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads">IBM: DB2 JDBC Driver Versions and Downloads</a>
      </td>
    </tr>
    <tr>
      <td>
        Impala/Hive
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b> <code>ImpalaJDBC&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.cloudera.impala.jdbc41.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:impala://localhost:21050/impala_test</code><br>
        <b>Download:</b> <a href="https://www.cloudera.com/downloads/connectors/impala/jdbc/2-6-27.html">https://www.cloudera.com/downloads/connectors/impala/jdbc/2-6-27.html</a><br>
        <b>Extension:</b> <a href="https://github.com/eselyavka/liquibase-impala">GitHub: Liquibase Impala/Hive Extension</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/informix/">Informix</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>informix</code><br>
        <br>
        <b>File:</b> <code>jdbc-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.informix.jdbc.IfxDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:informix-sqli://localhost:9088/dbname:INFORMIXSERVER=dbserver</code><br>
        <b>Download:</b> <a href="https://mvnrepository.com/artifact/com.ibm.informix/jdbc">Maven: IBM Informix JDBC Driver</a><br>
        <p><b>Notes:</b> Requires Informix database in ANSI mode to enable implicit transactions with statements like the following JDBC URL: <code class="language-text">CREATE DATABASE [ansiDatabase] WITH LOG MODE ANSI;</code></p>
      </td>
    </tr>
    <tr>
      <td>
        Interbase
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b> <code>interclient.jar</code><br>
        <b>Class:</b> <code>interbase.interclient.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:interbase://localhost:3050/interbase/testdb.ib</code><br>
        <b>Download:</b> Downloaded with Interbase from <a href="https://www.embarcadero.com/products/interbase/downloads">https://www.embarcadero.com/products/interbase/downloads</a><br>
        <b>Extension:</b> <a href="https://github.com/Gtunali/Liqubase-interbase">GitHub: Liquibase Interbase Extension</a>
      </td>
    </tr>
    <tr>
      <td>
        InterSystems Caché
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-cache">InterSystems Caché</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/mongodb/">MongoDB Community Extension</a>
      </td>
      <td>
        5, 6
      </td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>mongodb</code><br>
        <br>
        <b>File:</b> <code>mongo-java-driver-&lt;version&gt;.jar</code><br>
        <b>Class:</b> NA<br>
        <b>JDBC URL:</b> <code>mongodb://hostname:27017/&lt;db-name&gt;</code><br>
        <b>Reference:</b> <a href="https://docs.mongodb.com/manual/reference/connection-string/">MongoDB: Connection String URI Format</a><br>
        <b>Download:</b> <a href="https://repo1.maven.org/maven2/org/mongodb/mongo-java-driver">Maven: org/mongodb/mongo-java-driver</a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-mongodb/releases/">GitHub: liquibase/liquibase-mongodb</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="https://www.liquibase.com/databases/neo4j-graph-database">Neo4j Graph Database</a>
      </td>
      <td></td>
      <td>
        Foundational
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>neo4j</code><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b> <code>jdbc:neo4j:bolt://&lt;host&gt;:&lt;port&gt;/?username=foo,password=bar</code><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-neo4j/">GitHub: liquibase/liquibase-neo4j</a>
      </td>
    </tr>
    <tr>
      <td>
        Oracle Timesten
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href=""></a>
      </td>
    </tr>
    <tr>
      <td>
        Percona Distribution for MySQL
      </td>
      <td></td>
      <td>
        Foundational
      </td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><code>mysql-connector-java-&lt;version&gt;.jar</code><br>
        <b>Class:</b> NA<br>
        <b>JDBC URL:</b><code>jdbc:mysql://&lt;servername&gt;:&lt;port&gt;/&lt;dbname&gt;</code><br>
        <b>Download:</b> <a href="https://dev.mysql.com/downloads/connector/j/">MySQL: MySQL Community Downloads: Connector/J</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/percona/percona-xtradb-cluster/">Percona XtraDB Cluster</a>
      </td>
      <td>
        5.7, 8.0
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><code>mysql-connector-java-&lt;version&gt;.jar</code><br>
        <b>Class:</b> NA<br>
        <b>JDBC URL:</b><code>jdbc:mysql://&lt;servername&gt;:&lt;port&gt;/&lt;dbname&gt;</code><br>
        <b>Download:</b> <a href="https://dev.mysql.com/downloads/connector/j/">MySQL: MySQL Community Downloads: Connector/J</a>
      </td>
    </tr>
    <tr>
      <td>
        Phoenix/HBase
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href="https://github.com/manirajv06/liquibase-hbase">GitHub: Liquibase Phoenix/HBase Extension</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/sap-hana/">SAP HANA</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>hana</code><br>
        <br>
        <b>File:</b> <code>ngdbc-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.sap.db.jdbc.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:sap://localhost:30013/?databaseName=mydatabase&amp;encrypt=true</code><br>
        <b>Download:</b> <a href="https://mvnrepository.com/artifact/com.sap.cloud.db.jdbc/ngdbc">Maven: SAP HANA JDBC Driver</a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-hanadb/releases">GitHub: liquibase/liquibase-hanadb</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/maxdb/">SAP MaxDB</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>maxdb</code><br>
        <br>
        <b>File:</b> <code>sapdbc.jar</code><br>
        <b>Class:</b> <code>com.sap.dbtech.jdbc.DriverSapDB</code><br>
        <b>JDBC URL:</b> <code>jdbc:sapdb://localhost:7200/MaxDB1</code><br>
        <b>Download:</b> <a href="https://wiki.scn.sap.com/wiki/display/MaxDB/SAP+MaxDB+Product+Availability">SAP: SAP MaxDB Product Availability</a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-maxdb/releases">GitHub: liquibase/liquibase-maxdb</a><br>
        <p><b>Notes:</b> To view installed database software packages, including JDBC drivers, run the following command: <code class="language-text">sdbregview -l</code></p>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/sap-sql-anywhere/">SAP SQL Anywhere</a>
      </td>
      <td>
        17
      </td>
      <td>
        Foundational
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>asany</code><br>
        <br>
        <b>File:</b> <code>sajdbc4.jar</code><br>
        <b>Class:</b> <code>sybase.jdbc.sqlanywhere.IDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:sqlanywhere:UserID=DBA;Password=passwd;</code><br>
        <b>Download:</b> Included in database client
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/sap-adaptive-server-enterprise-sap-ase/">Sybase (SAP ASE)</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>sybase</code><br>
        <br>
        <b>File:</b> <code>jconn4.jar</code><br>
        <b>Class:</b> <code>com.sybase.jdbc4.jdbc.SybDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:sybase:Tds:127.0.0.1:5000/dbname</code><br>
        <b>Download:</b> Included in database client
      </td>
    </tr>
    <tr>
      <td><a href="/extensions-integrations/directory/database-tutorials/singlestore/">SingleStoreDB</a></td>
      <td>Cloud</td>
      <td>Foundational</td>
      <td>
        <b>DBMS/shortname:</b> <code></code><br>
        <br>
        <b>File:</b> <code></code><br>
        <b>Class:</b> <code></code><br>
        <b>JDBC URL:</b> <code></code><br>
        <b>Download:</b>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/sqlite/">SQLite</a>
      </td>
      <td>
        3.34
      </td>
      <td>
        Advanced
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>sqlite</code><br>
        <br>
        <b>File:</b> <code>sqlite-jdbc-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>org.sqlite.JDBC</code><br>
        <b>JDBC URL:</b> <code>jdbc:sqlite:example.db</code><br>
        <b>Download:</b> <a href="https://github.com/xerial/sqlite-jdbc">GitHub: xerial/sqlite-jdbc</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/teradata/">Teradata Database</a>
      </td>
      <td>
        17.2
      </td>
      <td>
        Foundational
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>teradata</code><br>
        <br>
        <b>File:</b> <code>terajdbc&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.teradata.jdbc.TeraDriver</code><br>
        <b>JDBC URL:</b> <code>jdbc:teradata://hostname/DATABASE=&lt;db-name&gt;</code><br>
        <b>Download:</b> <a href="https://downloads.teradata.com/download/connectivity/jdbc-driver">Teradata: Teradata JDBC Driver</a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-teradata/releases">GitHub: liquibase/liquibase-teradata</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/vertica/">Vertica</a>
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>vertica</code><br>
        <br>
        <b>File:</b> <code>vertica-jdbc-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>com.vertica.jdbc.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:vertica://localhost:5433/docker</code><br>
        <b>Download:</b> <a href="https://www.vertica.com/download/vertica/client-drivers/">Vertica: Vertica Downloads: Client Drivers</a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-vertica/releases">GitHub: liquibase/liquibase-vertica</a>
      </td>
    </tr>
    <tr>
      <td>
        VMware vFabric SQLFire
      </td>
      <td></td>
      <td>
        Contributed
      </td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href="https://github.com/liquibase/liquibase-sqlfire">GitHub: Liquibase SQLFire Extension</a>
      </td>
    </tr>
    <tr>
      <td>
        VoltDB
      </td>
      <td></td>
      <td></td>
      <td>
        <b>DBMS/shortname:</b><br>
        <br>
        <b>File:</b><br>
        <b>Class:</b><br>
        <b>JDBC URL:</b><br>
        <b>Download:</b> <a href=""></a><br>
        <b>Extension:</b> <a href="https://github.com/diorman/liquibase-voltdb">GitHub: Liquibase VoltDB</a>
      </td>
    </tr>
    <tr>
      <td>
        <a href="/extensions-integrations/directory/database-tutorials/yugabytedb/">YugabyteDB</a>
      </td>
      <td>
        2.6, 2.8, 2.12, 2.14
      </td>
      <td>
        Foundational
      </td>
      <td>
        <b>DBMS/shortname:</b> <code>yugabytedb</code><br>
        <br>
        <b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br>
        <b>Class:</b> <code>org.postgresql.Driver</code><br>
        <b>JDBC URL:</b> <code>jdbc:postgresql://&lt;host&gt;:&lt;port&gt;/&lt;db-name&gt;</code><br>
        <b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a>
      </td>
    </tr>
  </tbody>
</table>

## Verification levels
A database's verification level refers to the levels of automated and real-world professional testing and certification it has passed. The levels in order of most to least validation are: Advanced, Foundational, Contributed, and Research.


