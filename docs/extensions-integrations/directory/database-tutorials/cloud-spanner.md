---
title: Cloud Spanner
---

<h1>Using Liquibase with Cloud Spanner</h1>
<p><a href="https://cloud.google.com/spanner">Cloud Spanner</a> is a fully managed relational database with unlimited scale and strong consistency. It optimizes performance by automatically sharding the data based on request load and size of the data.</p>
<p>You can use the <a href="https://github.com/cloudspannerecosystem/liquibase-spanner">Cloud Spanner Liquibase Extension</a> to manage database schema changes with Liquibase. With the Cloud Spanner Liquibase extension, you can enable Liquibase to target Cloud Spanner. All Cloud Spanner features, with the exception of some <a href="https://github.com/cloudspannerecosystem/liquibase-spanner/blob/master/limitations.md">limitations</a>, are supported.</p>
<p>Additionally, the example <a href="https://github.com/cloudspannerecosystem/liquibase-spanner/blob/master/example/changelog.yaml">changelog.yaml</a> included with the Cloud Spanner Liquibase extension demonstrates Liquibase features and how to use them with Cloud Spanner.</p>
<h2>Supported database versions</h2>
<ul>
    <li>2.7.3</li>
</ul>
<h2>Prerequisites</h2>
<ol>
    <li value="1"><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.html" class="MCXref xref">Introduction to Liquibase</a> – Dive into Liquibase concepts.</li>
    <li value="2"><a href="https://docs.liquibase.com/start/install/home.html" class="MCXref xref">Install Liquibase</a> – Download Liquibase on your machine.</li>
    <li value="3"><a href="https://docs.liquibase.com/start/home.html" class="MCXref xref">Get Started with Liquibase</a> – Learn how to use Liquibase with an example database.</li>
    <li value="4"><a href="https://docs.liquibase.com/start/design-liquibase-project.html" class="MCXref xref">Design Your Liquibase Project</a> – Create a new <span class="mc-variable General.Liquibase variable">Liquibase</span> project folder and organize your changelogs</li>
    <li value="5"><a href="https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html" class="MCXref xref">How to Apply Your Liquibase Pro License Key</a> – If you use <span class="mc-variable General.LBPro variable">Liquibase Pro</span>, activate your license.</li>
</ol>
<h2>Install drivers</h2>
<p>To use Liquibase and Cloud Spanner, you need the  <a href="https://github.com/cloudspannerecosystem/liquibase-spanner/releases">Cloud Spanner Liquibase extension file</a>, <code>liquibase-spanner-version-all.jar</code>, which includes the extension, the Cloud Spanner SDK, and the Cloud Spanner JDBC driver. You can also download the JDBC driver from the <a href="https://mvnrepository.com/artifact/com.google.cloud/google-cloud-spanner-jdbc">Central Maven Repository</a>.</p>
<p> 
<a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">Place your JAR file(s)</a> in the <code>liquibase/lib</code> directory.</p><p> If you use Maven, you must <a href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR&#160;as a dependency</a> in your <code>pom.xml</code> file.</p><pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;com.google.cloud&lt;/groupId&gt;
    &lt;artifactId&gt;google-cloud-spanner-jdbc&lt;/artifactId&gt;
    &lt;version&gt;2.7.3&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
    &lt;artifactId&gt;liquibase-cassandra&lt;/artifactId&gt;
    &lt;version&gt;4.10.0&lt;/version&gt;
&lt;/dependency&gt;</code></pre>

<h2 id="test-your-connection">Test your connection</h2>
<ol>
    <li value="1">Ensure you have created the Cloud Spanner <a href="https://cloud.google.com/spanner/docs/quickstart-console#create_an_instance">instance</a> and  <a href="https://cloud.google.com/spanner/docs/quickstart-console#create_a_database">database</a>. Then give the extension temporary use of your own Cloud Spanner user credentials for API access by running the following <code>gcloud</code> command:</li><pre><code class="language-text">gcloud auth application-default login</code></pre>
    <li value="2">Specify the database URL in the <code><a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format:</li><pre xml:space="preserve"><code class="language-text">url: jdbc:cloudspanner:/projects/&lt;project&gt;/instances/&lt;instance&gt;/databases/&lt;database&gt;</code></pre>
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
<p>You can verify the existence of these tables through the Cloud Console or <code>gcloud</code> tool. For example, running the SQL query <code>SELECT * FROM INFORMATION_SCHEMA.TABLES</code> returns a list of all tables in your database:</p><pre><code class="language-text">gcloud spanner databases execute-sql &lt;DB&gt;  --instance=&lt;INSTANCE&gt; \
--sql='SELECT * FROM INFORMATION_SCHEMA.TABLES'</code></pre>
<h2>Related links</h2>
<ul>
<li><a href="https://github.com/cloudspannerecosystem/liquibase-spanner">Cloud Spanner Liquibase Extension</a>
</li>
<li><a href="https://cloud.google.com/spanner/docs/quickstart-console">Cloud Spanner Documentation</a>
</li>
</ul>
