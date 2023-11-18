<h1>Using Liquibase with Cassandra</h1>
<p><a href="https://cassandra.apache.org/doc/latest/architecture/overview.html">Apache Cassandra</a> is an open source,
    distributed, NoSQL database. It presents a partitioned wide column storage model with consistent semantics. For more
    information, see the <a href="https://cassandra.apache.org/cassandra.md">Apache Cassandra</a> page.</p>
<h2>Supported versions</h2>
<ul>
    <li>4.X</li>
    <li>3.11.X</li>
</ul>
<h2>Prerequisites</h2>
<ol>
    <li value="1"><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.html" class="MCXref xref">Introduction to
        Liquibase</a> – Dive into Liquibase concepts.
    </li>
    <li value="2"><a href="https://docs.liquibase.com/start/install/home.html" class="MCXref xref">Install Liquibase</a> – Download <span
            class="mc-variable General.Liquibase variable">Liquibase</span> on your machine.
    </li>
    <li value="3"><a href="https://docs.liquibase.com/start/home.html" class="MCXref xref">Get Started with Liquibase</a> – Learn how to use <span
            class="mc-variable General.Liquibase variable">Liquibase</span> with an example database.
    </li>
    <li value="4"><a href="https://docs.liquibase.com/start/design-liquibase-project.html" class="MCXref xref">Design Your Liquibase Project</a> – Create a new <span class="mc-variable General.Liquibase variable">Liquibase</span> project folder and organize your changelogs</li>
    <li value="5"><a href="https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html"
                     class="MCXref xref">How to Apply Your Liquibase Pro License Key</a> – If you use <span
            class="mc-variable General.LBPro variable">Liquibase Pro</span>, activate your license.
    </li>
</ol>
<h2>Install drivers</h2>
<p>To use Liquibase and Apache Cassandra, you need two JAR
    files: a JDBC driver and the Liquibase Cassandra
    extension:</p>
<ol>
    <li value="1">Download the latest version of <a href="https://github.com/ing-bank/cassandra-jdbc-wrapper/releases/latest">JDBC wrapper of the Java Driver for Apache Cassandra JAR file</a>.
    </li>
    <li value="2">Move the downloaded <code>cassandra-jdbc-wrapper-x.x.x-bundle.jar</code> file to the <code>liquibase/lib</code> directory.
    </li>
    <li value="3">Open the Liquibase properties file and specify the driver, as follows:
    </li>
    <pre><code class="language-text">driver: com.ing.data.cassandra.jdbc.CassandraDriver</code></pre>
    <li value="4">Go to the <a href="https://github.com/liquibase/liquibase-cassandra/releases/cassandra.md">liquibase-cassandra</a> repository and download the latest released 
    <span class="mc-variable General.Liquibase variable">Liquibase</span> extension JAR file: <code>liquibase-cassandra-version.jar</code>.
    </li>
</ol>
<p>
    <a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">Place your JAR file(s)</a> in the <code>liquibase/lib</code> directory.
    </p><p>
    If you use Maven,
     <a href="https://docs.liquibase.com/tools-integrations/maven/using-liquibase-and-maven-pom-file.html">add the driver JAR as a dependency</a> to your <code>pom.xml</code> file.
</p>
<pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;com.ing.data&lt;/groupId&gt;
    &lt;artifactId&gt;cassandra-jdbc-wrapper&lt;/artifactId&gt;
    &lt;version&gt;4.10.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
    &lt;artifactId&gt;liquibase-cassandra&lt;/artifactId&gt;
    &lt;version&gt;<span class="mc-variable General.CurrentLiquibaseVersion variable">4.25.0</span>&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
<h2 id="test-your-connection">Test your connection</h2>
<ol>
    <li value="1">Ensure your Cassandra database is configured. If you have Cassandra tools locally and want to check
        the status of Cassandra, run <code>$ bin/nodetool status</code>. The status column in the output should report
        UN, which stands for "Up/Normal":
    </li>
    <pre><code class="language-text"># nodetool status
Datacenter: datacenter1
=======================
Status=Up/Down
|/ State=Normal/Leaving/Joining/Moving</code></pre>
    <table>
        <thead>
        <tr>
            <th>-- Address Load</th>
            <th>Tokens</th>
            <th>Owns (effective)</th>
            <th>Host ID</th>
            <th>Rack</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>UN 172.18.0.6 198.61 KiB</td>
            <td>276</td>
            <td>100.0%</td>
            <td>5rtc74d1-237f-87c0-88eb-72643bd0a8t7</td>
            <td>rack1</td>
        </tr>
        </tbody>
    </table>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span
            class="autonumber"><span><b>Note: </b></span></span>For more information, see the <a
            href="https://cassandra.apache.org/doc/latest/tools/index.html">Installing Cassandra</a> documentation.</p>
</ol>
<ol start="2">
    <li value="2">Specify the database URL in the <code><a
            href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span
            class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults
        file), along with other properties you want to set a default value for. <span
                class="mc-variable General.Liquibase variable">Liquibase</span> does not parse the URL. Please specify the URL using your database's standard JDBC format:
    </li>
    <pre><code
            class="language-text">url: jdbc:cassandra://localhost:9042/myKeyspace?compliancemode=Liquibase</code></pre>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>Be careful to always specify the <code>compliancemode</code> parameter with the value <code>Liquibase</code> to avoid any unexpected behaviour when running the changelog.</p>
    <p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>For more information about the available options regarding the JDBC connection string, please check <a href="https://github.com/ing-bank/cassandra-jdbc-wrapper#usage">the driver documentation</a>.</p>
    <p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>To
        apply a <span class="mc-variable General.LBPro variable">Liquibase Pro</span> key to your project, add the
        following property to the Liquibase properties file:
        <code>licenseKey: &lt;paste code here&gt;</code></p>
</ol>
<ol start="3">
    <li value="3">Create a text file called <a href="https://docs.liquibase.com/concepts/changelogs/home.html">changelog</a>
        (<code>.xml</code>, <code>.sql</code>, <code>.json</code>, or <code>.yaml</code>) in your project directory and
        add a <a href="https://docs.liquibase.com/concepts/changelogs/changeset.html">changeset</a>.
    </li>
    <p>If you already created a <span class="mc-variable General.changelog variable">changelog</span> using the <code><a
            href="https://docs.liquibase.com/commands/init/project.html" class="MCXref xref">init project</a></code> command, you can use
        that instead of creating a new file. When adding onto an existing <span
                class="mc-variable General.changelog variable">changelog</span>, be sure to only add the <span
                class="mc-variable General.changeset variable">changeset</span> and to not duplicate the <span
                class="mc-variable General.changelog variable">changelog</span> header.</p>
    <a style="font-size: 18pt;" >XML example</a>
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

<p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>Formatted
SQL <span class="mc-variable General.changelog variable">changelog</span>s generated from <span
                        class="mc-variable General.Liquibase variable">Liquibase</span> versions before 4.2 might cause
issues because of the lack of space after a double dash ( <code>--</code> ). To fix this, add a space
after the double dash. For example: <code>--&#160;liquibase formatted sql</code> instead of <code>--liquibase
formatted sql</code> and <code>--&#160;changeset myname:create-table</code> instead of <code>--changeset
myname:create-table</code>.</p>

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
      primaryKey: true
      nullable: false</code></pre>

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

    <li value="4">Navigate to your project folder in the CLI and run the <span
            class="mc-variable General.Liquibase variable">Liquibase</span>&#160;<a
            href="https://docs.liquibase.com/commands/change-tracking/status.html" class="MCXref xref">status</a> command to see whether the
        connection is successful:
    </li>
    <pre xml:space="preserve"><code class="language-text">liquibase status --username=test --password=test --changelog-file=&lt;changelog.xml&gt;</code></pre>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span
            class="autonumber"><span><b>Note: </b></span></span>You can specify arguments in the CLI or keep them in the
        <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span
                class="mc-variable General.Liquibase variable">Liquibase</span> properties file</a>.</p>
    <p>If your connection is successful, you'll see a message like this:</p>
    <pre xml:space="preserve"><code class="language-text">4 changesets have not been applied to &lt;your_jdbc_url&gt;

Liquibase command 'status' was executed successfully.</code></pre>

<li value="5">Inspect the SQL with the <a href="https://docs.liquibase.com/commands/update/update-sql.html" class="MCXref xref">update-sql</a>
command. Then make changes to your database with the <a href="https://docs.liquibase.com/commands/update/update.html"
                                                                class="MCXref xref">update</a> command.
</li>
<pre xml:space="preserve"><code class="language-text">liquibase update-sql --changelog-file=&lt;changelog.xml&gt;
liquibase update --changelog-file=&lt;changelog.xml&gt;</code></pre>
<p>If your <code>update</code> is successful, Liquibase
runs each <span class="mc-variable General.changeset variable">changeset</span> and displays a summary message
ending with:</p>
<pre xml:space="preserve"><code class="language-text">Liquibase: Update has been successful.
Liquibase command 'update' was executed successfully.</code></pre>
<li value="6">From a database UI tool, ensure that your database contains the <code>test_table</code> you added
along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html" class="MCXref xref">DATABASECHANGELOG
table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html" class="MCXref xref">DATABASECHANGELOGLOCK
table</a>.
</li>

</ol>
<p>Now you're ready to start making deployments with <span
        class="mc-variable General.Liquibase variable">Liquibase</span>!</p>
<h2>Related links</h2>
<ul>
    <li><a href="https://www.liquibase.com/blog/running-liquibase-apache-cassandra">Get Up and Running with <span
            class="mc-variable General.Liquibase variable">Liquibase</span> and Apache Cassandra</a>
    </li>
    <li><a href="https://docs.liquibase.com/change-types/home.html" class="MCXref xref">Change Types</a>
    </li>
    <li><a href="https://docs.liquibase.com/concepts/home.html" class="MCXref xref">Concepts</a>
    </li>
    <li><a href="https://docs.liquibase.com/commands/home.html" class="MCXref xref">Liquibase Commands</a>
    </li>
    <li><a href="https://docs.liquibase.com/workflows/home.html" class="MCXref xref">Workflows</a>
    </li>
</ul>
