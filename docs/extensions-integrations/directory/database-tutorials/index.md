---
title: Database Tutorials
---
<h1>Liquibase Database Tutorials: Community-Maintained</h1>
<p>Learn more about using Liquibase with your favorite databases with our in-depth tutorials. These tutorials explain how to install and configure your database with Liquibase Community and Liquibase Pro.</p>
<h2>Prerequisites</h2>
<p>Before you start setting up your database, make sure you're familiar with how Liquibase works using these pages:</p>
<ol>
        <li><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.htm">Introduction to Liquibase</a> – Dive into Liquibase concepts.</li>
        <li><a href="https://docs.liquibase.com/start/install/home.htm">Install Liquibase</a> – Download Liquibase on your machine.</li>
        <li><a href="https://docs.liquibase.com/start/home.htm">Get Started with Liquibase</a> – Learn how to use Liquibase with an example database.</li>
        <li><a href="https://docs.liquibase.com/start/design-liquibase-project.htm">Design Your Liquibase Project</a> – Create a new Liquibase project folder and organize your changelogs</li>
        <li><a href="https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.htm">How to Apply Your Liquibase Pro License Key</a> – If you use Liquibase Pro, activate your license.</li>
    </ol>
<h2>Verification levels</h2>
<p>Liquibase works with 50+ databases including relational, NoSQL, and graph databases. A database's verification level refers to the levels of automated and real-world professional testing and certification it has passed. The levels are Advanced (highest verification), Foundational, Contributed, and Research (lowest verification). For more information, see <a href="https://www.liquibase.com/supported-databases/verification-levels">Database Verification Levels</a>.</p>
<h2>Community-maintained databases</h2>
<p>Liquibase supports English language databases only. Other languages with special characters may cause issues.</p>
<p>The following table shows databases whose compatibility with Liquibase is maintained by the community. For documentation on Liquibase-maintained databases, see the <a href="https://docs.liquibase.com/start/tutorials/home.html">Liquibase Documentation site</a>. For a full list of supported databases, see <a href="https://www.liquibase.com/supported-databases">Supported Databases</a>.</p>
<table>
    <thead>
        <tr>
            <th>Database</th>
            <th>Verification</th>
            <th>Driver</th>
            <th>Notes</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><a href="https://github.com/liquibase/liquibase-amazon-keyspaces">Amazon Keyspaces</a></td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> </br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-amazon-keyspaces">GitHub: Liquibase Amazon Keyspaces Extension</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/apache-derby/">Apache Derby</a></td>
            <td>
                <p><b>Database version(s):</b> 10.14</p>
                <p><b>Verification level:</b> Advanced</p>
            </td>
            <td><b>File:</b> <code>derbytools.jar</code><br /><b>Class:</b> <code>org.apache.derby</code><br /><b>DBMS/shortname:</b> <code>derby</code><br /><b><a href="apache-derby#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://db.apache.org/derby/derby_downloads.html">Apache Derby: Downloads</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/cassandra/">Apache Cassandra</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>CassandraJDBC&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.simba.cassandra.jdbc42.Driver</code><br /><b>DBMS/shortname:</b> <code>cassandra</code><br /><b><a href="cassandra#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://downloads.datastax.com/#odbc-jdbc-drivers">Datastax: ODBC/JDBC Drivers</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/cassandra-astra/">Apache Cassandra on DataStax Astra</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>CassandraJDBC&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.simba.cassandra.jdbc42.Driver</code><br /><b>DBMS/shortname:</b> <code>cassandra</code><br /><b><a href="cassandra-astra#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://downloads.datastax.com/#odbc-jdbc-drivers">Datastax: ODBC/JDBC Drivers</a></td>
            <td></td>
        </tr>
        <tr>
            <td>Aerospike</td>
            <td></td>
            <td><b>File:</b> <code>uber-aerospike-jdbc-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.aerospike.jdbc.AerospikeDriver</code><br /><b>DBMS/shortname:</b> <code>aerospike</code><br /><b><a href="https://github.com/aerospike/aerospike-jdbc">JDBC URL</a></b><br /><b>Download:</b> <a href="https://github.com/aerospike/aerospike-jdbc"></a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-aerospike">GitHub: Liquibase Aerospike Extension</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/cosmosdb/">Azure Cosmos DB</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>azure-cosmos-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.azure.cosmos.Driver</code><br /><b>DBMS/shortname:</b> <code>cosmosdb</code><br /><b><a href="cosmosdb#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://www.cdata.com/drivers/cosmosdb/download/jdbc/">CData: JDBC Driver Download</a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-cosmosdb/releases">GitHub: liquibase/liquibase-cosmosdb</a></td>
            <td></td>
        </tr>
        <tr>
            <td>ClickHouse</td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href=""></a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/cloud-spanner/">Cloud Spanner</a></td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code>cloudspanner</code><br /><b><a href="cloud-spanner#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://github.com/liquibase/liquibase-spanner">GitHub: Liquibase Spanner Extension</a><br /><b>Extension:</b> <code>liquibase-spanner-version-all.jar</code></td>
            <td>Includes extension and driver.</td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/cockroachdb/">CockroachDB (on-premises)</a></td>
            <td>
                <p><b>Database version(s):</b> 20, 21, 22</p>
                <p><b>Verification level:</b> Advanced</p>
            </td>
            <td><b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>org.postgresql.Driver</code><br /><b>DBMS/shortname:</b> <code>cockroachdb</code><br /><b><a href="cockroachdb#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a></td>
            <td>TLS certificate workflow is preferred. The following option is insecure: <code class="language-text">jdbc:postgresql://localhost:26257/dev</code></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/cockroachdb/">CockroachDB (cloud)</a></td>
            <td>
                <p><b>Database version(s):</b> 20, 21, 22</p>
                <p><b>Verification level:</b> Advanced</p>
            </td>
            <td><b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>org.postgresql.Driver</code><br /><b>DBMS/shortname:</b> <code>cockroachdb</code><br /><b><a href="cockroachdb#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a></td>
            <td>Requires TLS certificate workflow.</td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/firebird/">Firebird RDBMS</a></td>
            <td>
                <p><b>Database version(s):</b> 3, 4</p>
                <p><b>Verification level:</b> Advanced</p>
            </td>
            <td><b>File:</b> <code>jaybird-full-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>org.firebirdsql.jdbc.FBDriver</code><br /><b>DBMS/shortname:</b> <code>firebird</code><br /><b><a href="firebird#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://github.com/FirebirdSQL/jaybird/releases">GitHub: jaybird: releases</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/bigquery/">Google BigQuery</a></td>
            <td>
                <p><b>Database version(s):</b> Cloud</p>
                <p><b>Verification level:</b> Foundational</p>
            </td>
            <td><b>File:</b> <code>GoogleBigQueryJDBC&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.google.cloud</code><br /><b>DBMS/shortname:</b> <code>bigquery</code><br /><b><a href="bigquery#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://cloud.google.com/bigquery/">Google:&#160;BigQuery</a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-bigquery/releases">GitHub: liquibase/liquibase-bigquery</a></td>
            <td></td>
        </tr>
        <tr>
            <td>Greenplum Database</td>
            <td>
                <p><b>Database version(s):</b>6</p>
                <p><b>Verification level:</b> Foundational</p>
            </td>
            <td><b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>org.postgresql.Driver</code><br /><b>DBMS/shortname:</b> <code></code><br /><b><a href="https://docs.liquibase.com/start/tutorials/postgresql.html#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/h2/">H2 Database Engine</a></td>
            <td>
                <p><b>Database version(s):</b> 1.4, 2.1.21</p>
                <p><b>Verification level:</b> Advanced</p>
            </d>
            <td><b>File:</b> <code>h2-&lt;version&gt;.jar</code><br /><b>Class:</b> NA<br /><b>DBMS/shortname:</b> <code>h2</code><br /><b><a href="h2#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> Included with Liquibase in <code>&lt;install-directory/internal/lib&gt;</code></td>
            <td>Included with Liquibase.</td>
        </tr>
        <tr>
            <td>HarperDB</td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-harperdb">GitHub: Liquibase HarperDB Extension</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/hibernate/">Hibernate</a></td>
            <td></td>
            <td><b>File:</b> <code>h2-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>liquibase.ext.hibernate.database.connection.HibernateDriver</code><br /><b>DBMS/shortname:</b> <code></code><br /><b><a href="hibernate#create">JDBC URL</a></b><br /><b>Download:</b> Included with Liquibase in <code>&lt;install-directory/internal/lib&gt;</code><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-hibernate/releases">GitHub: liquibase/liquibase-hibernate</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/hsqldb/">HyperSQL</a></td>
            <td>
                <p><b>Database version(s) verified:</b> 2.3, 2.4, 2.5, 2.6, 2.7</p>
                <p><b>Verification level:</b> Advanced</p>
            </td>
            <td><b>File:</b> <code>hsqldb.jar</code><br /><b>Class:</b> <code>org.hsqldb.jdbcDriver</code><br /><b>DBMS/shortname:</b> <code>hsqldb</code><br /><b><a href="hsqldb#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> Included with <a href="https://sourceforge.net/projects/hsqldb/files/">HyperSQL download from SourceForge</a></td>
            <td></td>
        </tr>
        <tr>
            <td>IBM DB2 for i</td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>db2jcc&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.ibm.as400.access.AS400JDBCDriver</code><br /><b>DBMS/shortname:</b> <code>db2</code><br /><b>JDBC URL:</b> jdbc:as400://&lt;host&gt;;libraries=&lt;database&gt;;<br /><b>Download:</b> <a href="https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads">IBM: DB2 JDBC Driver Versions and Downloads</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/db2onz/">IBM Db2 for Z</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>db2jcc&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.ibm.db2.jcc.DB2Driver</code><br /><b>DBMS/shortname:</b> <code>db2</code><br /><b><a href="db2onz#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads">IBM: DB2 JDBC Driver Versions and Downloads</a></td>
            <td></td>
        </tr>
        <tr>
            <td>Impala/Hive</td>
            <td></td>
            <td><b>File:</b> <code>ImpalaJDBC&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.cloudera.impala.jdbc41.Driver</code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL:</b> jdbc:impala://localhost:21050/impala_test<br /><b>Download:</b><a href="https://www.cloudera.com/downloads/connectors/impala/jdbc/2-6-27.html"> https://www.cloudera.com/downloads/connectors/impala/jdbc/2-6-27.html</a><br /><b>Extension:</b> <a href="https://github.com/eselyavka/liquibase-impala">GitHub: Liquibase Impala/Hive Extension</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/informix/">Informix</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>jdbc-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.informix.jdbc.IfxDriver</code><br /><b>DBMS/shortname:</b> <code>informix</code><br /><b><a href="informix#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://mvnrepository.com/artifact/com.ibm.informix/jdbc">Maven: IBM Informix JDBC Driver</a></td>
            <td>Requires Informix database in ANSI mode to enable implicit transactions with statements like the following JDBC URL: <code class="language-text">CREATE DATABASE [ansiDatabase] WITH LOG MODE ANSI;</code></td>
        </tr>
        <tr>
            <td>Interbase</td>
            <td></td>
            <td><b>File:</b> <code>interclient.jar</code><br /><b>Class:</b> <code>interbase.interclient.Driver</code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL:</b> jdbc:interbase://localhost:3050/interbase/testdb.ib<br /><b>Download:</b> Downloaded with Interbase from <a href="https://www.embarcadero.com/products/interbase/downloads">https://www.embarcadero.com/products/interbase/downloads</a><br /><b>Extension:</b> <a href="https://github.com/Gtunali/Liqubase-interbase">GitHub: Liquibase Interbase Extension</a></td>
            <td></td>
        </tr>
        <tr>
            <td>InterSystems Caché</td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-cache">InterSystems Caché</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/mongodb/">MongoDB Community Extension</a></td>
            <td>
                <p><b>Database version(s):</b> 5, 6</p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>mongo-java-driver-&lt;version&gt;.jar</code><br /><b>Class:</b> NA<br /><b>DBMS/shortname:</b> <code>mongodb</code><br /><b><a href="mongodb#test-your-connection">JDBC URL</a></b><br /><b>Reference:</b> <a href="https://docs.mongodb.com/manual/reference/connection-string/">MongoDB: Connection String URI Format</a><br /><b>Download:</b> <a href="https://repo1.maven.org/maven2/org/mongodb/mongo-java-driver">Maven: org/mongodb/mongo-java-driver</a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-mongodb/releases/">GitHub: liquibase/liquibase-mongodb</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integations/directory/tutorials/neo4j/">Neo4j Graph Database</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Foundational</p>
            </td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code>neo4j</code><br /><b><a href="neo4j#test-your-connection">JDBC URL</a></b><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-neo4j/">GitHub: liquibase/liquibase-neo4j</a></td>
            <td></td>
        </tr>
        <tr>
            <td>Oracle Timesten</td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href=""></a></td>
            <td></td>
        </tr>
        <tr>
            <td>Percona Distribution for MySQL</td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Foundational</p>
            </td>
            <td><b>File:</b><code>mysql-connector-java-&lt;version&gt;.jar</code><br /><b>Class:</b> NA<br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL:</b> jdbc:mysql://&lt;servername&gt;:&lt;port&gt;/&lt;dbname&gt;<br /><b>Download:</b> <a href="https://dev.mysql.com/downloads/connector/j/">MySQL: MySQL Community Downloads: Connector/J</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/percona-xtradb-cluster/">Percona XtraDB Cluster</a></td>
            <td>
                <p><b>Database version(s):</b> 5.7, 8.0</p>
                <p><b>Verification level:</b> Advanced</p>
            </td>
            <td><b>File:</b><code>mysql-connector-java-&lt;version&gt;.jar</code><br /><b>Class:</b> NA<br /><b>DBMS/shortname:</b> <code></code><br /><b><a href="percona-xtradb-cluster#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://dev.mysql.com/downloads/connector/j/">MySQL: MySQL Community Downloads: Connector/J</a></td>
            <td></td>
        </tr>
        <tr>
            <td>Phoenix/HBase</td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href="https://github.com/manirajv06/liquibase-hbase">GitHub: Liquibase Phoenix/HBase Extension</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/sap-hana/">SAP HANA</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>ngdbc-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.sap.db.jdbc.Driver</code><br /><b>DBMS/shortname:</b> <code>hana</code><br /><b><a href="sap-hana#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://mvnrepository.com/artifact/com.sap.cloud.db.jdbc/ngdbc">Maven: SAP HANA JDBC Driver</a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-hanadb/releases">GitHub: liquibase/liquibase-hanadb</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/maxdb/">SAP MaxDB</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>sapdbc.jar</code><br /><b>Class:</b> <code>com.sap.dbtech.jdbc.DriverSapDB</code><br /><b>DBMS/shortname:</b> <code>maxdb</code><br /><b><a href="maxdb#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://wiki.scn.sap.com/wiki/display/MaxDB/SAP+MaxDB+Product+Availability">SAP: SAP MaxDB Product Availability</a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-maxdb/releases">GitHub: liquibase/liquibase-maxdb</a></td>
            <td>To view installed database software packages, including JDBC drivers, run the following command: <code class="language-text">sdbregview -l</code></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/sap-sql-anywhere/">SAP SQL Anywhere</a></td>
            <td>
                <p><b>Database version(s):</b> 17</p>
                <p><b>Verification level:</b> Foundational</p>
            </td>
            <td><b>File:</b> <code>sajdbc4.jar</code><br /><b>Class:</b> <code>sybase.jdbc.sqlanywhere.IDriver</code><br /><b>DBMS/shortname:</b> <code>asany</code><br /><b><a href="sap-sql-anywhere#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> Included in database client</td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/sap-adaptive-server-enterprise-sap-ase/">Sybase (SAP ASE)</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>jconn4.jar</code><br /><b>Class:</b> <code>com.sybase.jdbc4.jdbc.SybDriver</code><br /><b>DBMS/shortname:</b> <code>sybase</code><br /><b><a href="sap-adaptive-server-enterprise-sap-ase#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> Included in database client</td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/sqlite/">SQLite</a></td>
            <td>
                <p><b>Database version(s):</b> 3.34</p>
                <p><b>Verification level:</b> Advanced</p>
            </td>
            <td><b>File:</b> <code>sqlite-jdbc-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>org.sqlite.JDBC</code><br /><b>DBMS/shortname:</b> <code>sqlite</code><br /><b><a href="sqlite#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://github.com/xerial/sqlite-jdbc">GitHub: xerial/sqlite-jdbc</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/teradata/">Teradata Database</a></td>
            <td>
                <p><b>Database version(s):</b> 17.2</p>
                <p><b>Verification level:</b> Foundational</p>
            </td>
            <td><b>File:</b> <code>terajdbc&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.teradata.jdbc.TeraDriver</code><br /><b>DBMS/shortname:</b> <code>teradata</code><br /><b><a href="teradata#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://downloads.teradata.com/download/connectivity/jdbc-driver">Teradata: Teradata JDBC Driver</a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-teradata/releases">GitHub: liquibase/liquibase-teradata</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/vertica/">Vertica</a></td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code>vertica-jdbc-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>com.vertica.jdbc.Driver</code><br /><b>DBMS/shortname:</b> <code>vertica</code><br /><b><a href="vertica#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://www.vertica.com/download/vertica/client-drivers/">Vertica: Vertica Downloads: Client Drivers</a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-vertica/releases">GitHub: liquibase/liquibase-vertica</a></td>
            <td></td>
        </tr>
        <tr>
            <td>VMware vFabric SQLFire</td>
            <td>
                <p><b>Database version(s):</b></p>
                <p><b>Verification level:</b> Contributed</p>
            </td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href="https://github.com/liquibase/liquibase-sqlfire">GitHub: Liquibase SQLFire Extension</a></td>
            <td></td>
        </tr>
        <tr>
            <td>VoltDB</td>
            <td></td>
            <td><b>File:</b> <code></code><br /><b>Class:</b> <code></code><br /><b>DBMS/shortname:</b> <code></code><br /><b>JDBC URL</b><br /><b>Download:</b> <a href=""></a><br /><b>Extension:</b> <a href="https://github.com/diorman/liquibase-voltdb">GitHub: Liquibase VoltDB</a></td>
            <td></td>
        </tr>
        <tr>
            <td><a href="https://contribute.liquibase.com/extensions-integrations/directory/tutorials/yugabytedb/">YugabyteDB</a></td>
            <td>
                <p><b>Database version(s):</b> 2.6, 2.8, 2.12, 2.14</p>
                <p><b>Verification level:</b> Foundational</p>
            </td>
            <td><b>File:</b> <code>postgresql-&lt;version&gt;.jar</code><br /><b>Class:</b> <code>org.postgresql.Driver</code><br /><b>DBMS/shortname:</b> <code>yugabytedb</code><br /><b><a href="yugabytedb#test-your-connection">JDBC URL</a></b><br /><b>Download:</b> <a href="https://jdbc.postgresql.org/download/">PostgreSQL: Download</a></td>
            <td></td>
        </tr>
    </tbody>
</table>