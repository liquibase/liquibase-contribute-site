---
title: CockroachDB
---

<h1>Using Liquibase with CockroachDB</h1>
<p><a href="https://www.cockroachlabs.com/">CockroachDB</a> is a distributed database with standard SQL for cloud applications. You can run CockroachDB on your local machine or use a cloud cluster.</p>
<h2>Supported versions</h2>
<ul>
    <li>22.X</li>
    <li>21.X</li>
    <li>20.X</li>
</ul>
<h2>Prerequisites</h2>
<ol>
    <li value="1"><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.html" class="MCXref xref">Introduction to Liquibase</a> – Dive into Liquibase concepts.</li>
    <li value="2"><a href="https://docs.liquibase.com/start/install/home.html" class="MCXref xref">Install Liquibase</a> – Download Liquibase on your machine.</li>
    <li value="3"><a href="https://docs.liquibase.com/start/home.html" class="MCXref xref">Get Started with Liquibase</a> – Learn how to use Liquibase with an example database.</li>
    <li value="4"><a href="https://docs.liquibase.com/commands/init/project.html" class="MCXref xref">init project</a> – Create a new Liquibase project folder to store all Liquibase files.</li>
    <li value="5"><a href="https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html" class="MCXref xref">How to Apply Your Liquibase Pro License Key</a> – If you use <span class="mc-variable General.LBPro variable">Liquibase Pro</span>, activate your license.</li>
</ol>
<h2>Install drivers</h2>
<p>To use Liquibase and CockroachDB, you need the <a href="https://jdbc.postgresql.org/download/">JDBC driver JAR file</a> (<a href="https://mvnrepository.com/artifact/org.postgresql/postgresql/42.4.0">Maven download</a>).</p>
<p>
    The latest version of Liquibase has a <a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">pre-installed driver</a> for this database in the <code>liquibase/internal/lib</code> directory, so you don't need to install it yourself.</p><p>If you use Maven, you must <a href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR&#160;as a dependency</a> in your <code>pom.xml</code> file.</p>
<p><pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;org.postgresql&lt;/groupId&gt;
    &lt;artifactId&gt;postgresql&lt;/artifactId&gt;
    &lt;version&gt;42.4.0&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
<h2 id="test-your-connection">Test your connection</h2>
<ol>
    <li value="1">Ensure your CockroachDB is configured:<ol><li value="1">&#160;You can check its status depending on your cluster setup. For example, you can check basic network connectivity (ping), port connectivity (telnet), and certificate validity. See the <a href="https://www.cockroachlabs.com/docs/v20.2/cluster-setup-troubleshooting.html">Troubleshoot Cluster Setup</a> for more details.</li><li value="2">Generate or check <a href="https://www.cockroachlabs.com/docs/v20.2/cockroach-cert.html">TLS certificates</a> for the user that you created during a <a href="https://www.cockroachlabs.com/docs/v20.2/secure-a-cluster.html">secure CockroachDB cluster setup</a>. Use the <code>cockroach cert</code> command to generate the certificates:</li><pre><code class="language-text">cockroach cert create-client user --certs-dir=certs --ca-key=my-safe-directory/ca.key --also-generate-pkcs8-key</code></pre></ol></li>
    <li value="2">Specify the database URL in the <code><a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format:</li>
    <h3>CockroachDB on-premises</h3><pre><code class="language-text">url:jdbc:postgresql://localhost:26257/database?ssl=true&amp;sslmode=require&amp;sslrootcert=/full-path/certs/ca.crt&amp;sslkey=/full-ath/certs/client.user.key.pk8&amp;sslcert=/full-path/certs/client.user.crt</code></pre>
    <p>When using CockroachDB on-premises and specifying the URL, enter your IP address or host name, and then the port followed by the database name. An example of the format is: <code>jdbc:postgresql://&lt;IP OR HOSTNAME&gt;:&lt;PORT&gt;/&lt;DATABASE&gt;</code>.</p>
    <p>The SSL connection parameters to the full paths of the certificates that you generated are optional. A key in PKCS#8 format is the standard key encoding format in Java. As an alternative, you can use the URL without SSL connection parameters by specifying the username and password <span class="mc-variable General.Param/Attribute variable">attribute</span>s:</p><pre><code class="language-text">url: jdbc:postgresql://localhost:26257/dev
username: root
password: password</code></pre>
    <h3>CockroachDB cloud</h3><pre><code class="language-text">url: jdbc:postgresql://liquibase-3r8.aws-us-east-2.cockroachlabs.cloud:26257/defaultdb?sslmode=verify-full&amp;sslrootcert=liquibase-ca.crt</code></pre>
    <p>When using a CockroachCloud instance and specifying URL, enter a global host name and the port <code>26257</code> by referring to the <a href="https://www.cockroachlabs.com/docs/cockroachcloud/quickstart">CockroachCloud</a> website. Also, add the database name with the SSL mode and the path to the CA certificate to your URL.</p>
    <p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>To apply a <span class="mc-variable General.LBPro variable">Liquibase Pro</span> key to your project, add the following property to the Liquibase properties file: <code>licenseKey: &lt;paste code here&gt;</code></p>
</ol>
<ol start="3">
    <li value="3">Create a text file called <a href="https://docs.liquibase.com/concepts/changelogs/home.html">changelog</a> (<code>.xml</code>, <code>.sql</code>, <code>.json</code>, or <code>.yaml</code>) in your project directory and add a <a href="https://docs.liquibase.com/concepts/changelogs/changeset.html">changeset</a>.</li>
    <p>If you already created a <span class="mc-variable General.changelog variable">changelog</span> using the <code><a href="https://docs.liquibase.com/commands/init/project.html" class="MCXref xref">init project</a></code> command, you can use that instead of creating a new file. When adding onto an existing <span class="mc-variable General.changelog variable">changelog</span>, be sure to only add the <span class="mc-variable General.changeset variable">changeset</span> and to not duplicate the <span class="mc-variable General.changelog variable">changelog</span> header.</p>
    <a style="font-size: 18pt;">XML example</a>
        <pre xml:space="preserve"><code class="language-xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
<code>&lt;databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:pro="http://www.liquibase.org/xml/ns/pro"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
        http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd"&gt;</code>

    &lt;changeSet id="1" author="Liquibase"&gt;
        &lt;createTable tableName="test_table"&gt;
            &lt;column name="test_id" type="int"&gt;
                &lt;constraints primaryKey="true"/&gt;
            &lt;/column&gt;
            &lt;column name="test_column" type="varchar"/&gt;
        &lt;/createTable&gt;
    &lt;/changeSet&gt;

&lt;/databaseChangeLog&gt;</code></pre>

<a style="font-size: 18pt;">SQL example</a>
<pre xml:space="preserve"><code class="language-sql">-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table (test_id INT, test_column VARCHAR(255), PRIMARY KEY (test_id))</code></pre>
<p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>Formatted SQL <span class="mc-variable General.changelog variable">changelog</span>s generated from Liquibase versions before 4.2 might cause issues because of the lack of space after a double dash ( <code>--</code> ). To fix this, add a space after the double dash. For example: <code>--&#160;liquibase formatted sql</code> instead of <code>--liquibase formatted sql</code> and <code>--&#160;changeset myname:create-table</code> instead of <code>--changeset myname:create-table</code>.</p>

<a style="font-size: 18pt;">YAML example</a>
<pre xml:space="preserve"><code class="language-yaml">databaseChangeLog:
   - changeSet:
       id: 1
       author: Liquibase
       changes:
       - createTable:
           tableName: test_table
           columns:
           - column:
               name: test_column
               type: INT
               constraints:
                   primaryKey:  true
                   nullable:  false</code></pre>

<a style="font-size: 18pt;">JSON example</a>
<pre><code class="language-json">{
  "databaseChangeLog": [
    {
      "changeSet": {
        "id": "1",
        "author": "Liquibase",
        "changes": [
          {
            "createTable": {
              "tableName": "test_table",
              "columns": [
                {
                  "column": {
                    "name": "test_column",
                    "type": "INT",
                    "constraints": {
                      "primaryKey": true,
                      "nullable": false
                    }
                  }
                }
              ]
            }
          }
        ]
      }
    }
  ]
}</code></pre>
    <li value="4">Navigate to your project folder in the CLI and run the Liquibase&#160;<a href="https://docs.liquibase.com/commands/change-tracking/status.html" class="MCXref xref">status</a> command to see whether the connection is successful:</li><pre xml:space="preserve"><code class="language-text">liquibase status --username=test --password=test --changelog-file=&lt;changelog.xml&gt;</code></pre>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>You can specify arguments in the CLI or keep them in the <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html">Liquibase properties file</a>.</p>
    <p>If your connection is successful, you'll see a message like this:</p><pre xml:space="preserve"><code class="language-text">4 changesets have not been applied to &lt;your_jdbc_url&gt;
Liquibase command 'status' was executed successfully.</code></pre>
    <li value="5">Inspect the SQL with  the <a href="https://docs.liquibase.com/commands/update/update-sql.html" class="MCXref xref">update-sql</a> command. Then make changes to your database with the <a href="https://docs.liquibase.com/commands/update/update.html" class="MCXref xref">update</a> command.</li><pre xml:space="preserve"><code class="language-text">liquibase update-sql --changelog-file=&lt;changelog.xml&gt;
liquibase update --changelog-file=&lt;changelog.xml&gt;</code></pre>
    <p>If your <code>update</code> is successful, Liquibase runs each <span class="mc-variable General.changeset variable">changeset</span> and displays a summary message ending with:</p><pre xml:space="preserve"><code class="language-text">Liquibase: Update has been successful.
Liquibase command 'update' was executed successfully.</code></pre>
    <li value="6">From a database UI tool, ensure that your database contains the <code>test_table</code> you added along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html" class="MCXref xref">DATABASECHANGELOG table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html" class="MCXref xref">DATABASECHANGELOGLOCK table</a>.</li>
</ol>
<p>Now you're ready to start making deployments with Liquibase!</p>
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>	CockroachDB has <a href="https://www.cockroachlabs.com/docs/v20.2/online-schema-changes#limited-support-for-schema-changes-within-transactions">limited support for online schema changes in transactions</a>. To avoid issues with incomplete transactions, you can set the <a href="https://docs.liquibase.com/concepts/basic/changeset.html">runInTransaction <span class="mc-variable General.Param/Attribute variable">attribute</span></a> to <code>false</code>. However, take into account that if this <span class="mc-variable General.Param/Attribute variable">attribute</span> is set to false and an error occurs part way through running a <span class="mc-variable General.changeset variable">changeset</span> that contains multiple statements, the Liquibase <span class="mc-variable General.databasechangelog variable">DATABASECHANGELOG</span> table will be left in an invalid state.</p>
<h2>Related links</h2>
<ul>
    <li><a href="https://www.cockroachlabs.com/docs/stable/">CockroachDB Docs</a>
    </li>
    <li><a href="https://www.cockroachlabs.com/docs/stable/cockroach-cert.html">Cockroach CA</a>
    </li>
    <li><a href="https://www.cockroachlabs.com/docs/stable/cockroach-sql.html">Cockroach SQL</a>
    </li>
    <li><a href="https://www.cockroachlabs.com/docs/v20.2/liquibase.html">CockroachDB's Liquibase Tutorial</a>
    </li>
    <li><a href="https://www.liquibase.org/blog/managing-cockroach-database-schema-changes">Managing Cockroach Database Schema Changes</a>
    </li>
    <li><a href="https://docs.liquibase.com/change-types/home.html" class="MCXref xref">Change Types</a>
    </li>
    <li><a href="https://docs.liquibase.com/workflows/home.html" class="MCXref xref">Workflows</a>
    </li>
</ul>
