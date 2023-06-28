---
title: DB2 on z/OS
---
<h1 id="db2-on-zos-tutorial">Using Liquibase with DB2 on z/OS</h1>
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>This database is supported <b>at or below the Contributed level</b>. Functionality may be limited. Databases at the Contributed level are not supported by the <span class="mc-variable General.CompanyName variable">Liquibase</span> support team. Best-effort support is provided through our community forums.<br /><br />For more information about the verification levels, see <a href="https://www.liquibase.com/supported-databases/verification-levels">Database Verification and Support</a>.<br /><br />If you have an update to these instructions, submit feedback so we can improve the page.</p>
<p><a href="https://www.ibm.com/docs/en/db2-for-zos">DB2 on z/OS</a> is a relational database management system that runs on the mainframe. For more information, see the <a href="https://www.ibm.com/docs/en/db2-for-zos/13?topic=getting-started-db2-zos">DB2 for z/OS documentation page</a>.</p>
<h2>Reported versions</h2>
<ul>
    <li>11.5.7+</li>
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
<p>To use Liquibase and DB2 on z/OS, you need the <a href="https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads">DB2JCC driver</a> (<a href="https://mvnrepository.com/artifact/com.ibm.db2/jcc">Maven download</a>) and the <a href="https://www.ibm.com/support/pages/db2-jdbc-driver-versions-and-downloads">license JAR file</a>, which is required when connecting to a mainframe DB2 database. You must purchase the DB2 Connect product. The license file is contained within the activation package for it. For more information regarding the license file, see <a href="https://www.ibm.com/support/pages/location-db2jcclicensecisuzjar-file">Location of the db2jcc_license_cisuz.jar file</a>. Place the license JAR file in the <code>liquibase/lib</code> directory.</p>
<p>
    The latest version of Liquibase has a <a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">pre-installed driver</a> for this database in the <code>liquibase/internal/lib</code> directory, so you don't need to install it yourself.
</p>
<p>If you use Maven, you must <a href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR&#160;as a dependency</a> in your <code>pom.xml</code> file.</p>

<pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;com.ibm.db2&lt;/groupId&gt;
    &lt;artifactId&gt;jcc&lt;/artifactId&gt;
    &lt;version&gt;11.5.7.0&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
    &lt;artifactId&gt;liquibase-db2i&lt;/artifactId&gt;
    &lt;version&gt;<span class="mc-variable General.CurrentLiquibaseVersion variable">4.20.0</span>&lt;/version&gt;
&lt;/dependency&gt;</code></pre>

<h2 id="test-your-connection">Test your connection</h2>
<ol>
    <li value="1">Ensure your DB2 on z/OS database is configured. You can check the status by running the <a href="https://www.ibm.com/docs/en/db2-for-zos/13?topic=commands-display-database-db2"><code>DISPLAY DATABASE</code> command</a>, which displays status information about DB2 databases.</li>
    <li value="2">Specify the database URL in the <code><a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format:</li><pre xml:space="preserve"><code class="language-text" data-lang="text">url: jdbc:db2://&lt;servername&gt;:&lt;port&gt;/&lt;dbname&gt;</code></pre>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>The URL for DB2 on z/OS may have different formats, such as <code>jdbc:db2j:net:</code>, <code>jdbc:ibmdb:</code>, and <code>jdbc:ids:</code>, depending on your connection type. For more information, see <a href="https://www.ibm.com/docs/en/db2-for-zos/13?topic=cdsudidsdjs-url-format-data-server-driver-jdbc-sqlj-type-4-connectivity">URL format for IBM Data Server Driver for JDBC and SQLJ type 4 connectivity</a>.</p>
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
<h2>Related links</h2>
<ul>
    <li><a href="db2onzdeploy-sql.html" class="MCXref xref">Deploying Changes to DB2 on z/OS using SQL Scripts</a>
    </li>
    <li><a href="https://docs.liquibase.com/change-types/home.html" class="MCXref xref">Change Types</a>
    </li>
    <li><a href="https://docs.liquibase.com/commands/home.html" class="MCXref xref">Liquibase Commands</a>
    </li>
</ul>
