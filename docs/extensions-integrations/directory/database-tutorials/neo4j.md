<h1>Using Liquibase with Neo4j</h1>
<p><a href="https://neo4j.com/neo4j.md">Neo4j</a> is a property graph database management system with native graph storage and processing. It uses a graph query language called <a href="https://neo4j.com/docs/getting-started/current/cypher-intro/neo4j.md">Cypher</a>. For more information, see <a href="https://neo4j.com/docs/neo4j.md">Neo4j Documentation</a>.</p>
<h2>Supported versions</h2>
<ul>
    <li>3.5+</li>
</ul>
<h2>Prerequisites</h2>
<ol>
    <li value="1"><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.html" class="MCXref xref">Introduction to Liquibase</a> – Dive into Liquibase concepts.</li>
    <li value="2"><a href="https://docs.liquibase.com/start/install/home.html" class="MCXref xref">Install Liquibase</a> – Download Liquibase on your machine.</li>
    <li value="3"><a href="https://docs.liquibase.com/start/home.html" class="MCXref xref">Get Started with Liquibase</a> – Learn how to use Liquibase with an example database.</li>
    <li value="4"><a href="https://docs.liquibase.com/commands/init/project.html" class="MCXref xref">init project</a> – Create a new Liquibase project folder to store all Liquibase files.</li>
    <li value="5"><a href="https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html" class="MCXref xref">How to Apply Your Liquibase Pro License Key</a> – If you use <span class="mc-variable General.LBPro variable">Liquibase Pro</span>, activate your license.</li>
</ol>
<p>To access Neo4j, do one of the following:</p>
<ul>
    <li><a href="https://neo4j.com/download/neo4j.md">Download Neo4j Desktop locally</a>
    </li>
    <li><a href="https://hub.docker.com/_/neo4j">Configure Neo4j locally with Docker</a>
    </li>
    <li><a href="https://neo4j.com/cloud/platform/aura-graph-database/neo4j.md">Create a Neo4j AuraDB&#160;cloud instance</a>
    </li>
    <li><a href="https://neo4j.com/sandbox/neo4j.md">Launch a Neo4j Aura&#160;sandbox instance</a>
    </li>
</ul>
<h2>Install drivers</h2>
<p>To use Liquibase and Neo4j, you need the latest JAR from the <a href="https://mvnrepository.com/artifact/org.liquibase.ext/liquibase-neo4j">Liquibase extension for Neo4j</a> (<a href="https://github.com/liquibase/liquibase-neo4j/releases">GitHub link</a>).</p>
<p> <a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">Place your JAR file(s)</a> in the <code>liquibase/lib</code> directory.</p><p>If you use Maven, you must <a href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR&#160;as a dependency</a> in your <code>pom.xml</code> file.</p><pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
    &lt;artifactId&gt;liquibase-neo4j&lt;/artifactId&gt;
    &lt;version&gt;<span class="mc-variable General.CurrentLiquibaseVersion variable">4.20.0</span>&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
<p>The Neo4j extension has native JDBC connectivity support in version 4.19.0+. If you're using an earlier version, you must also install a third-party <a href="https://github.com/neo4j-contrib/neo4j-jdbc">JDBC driver</a> to connect to Liquibase. For driver configuration information, see <a href="https://github.com/liquibase/liquibase-neo4j/blob/main/docs/reference-configuration.md">Neo4j Configuration</a>. For additional JARs to integrate Neo4j with your preferred programming language, see <a href="https://neo4j.com/docs/getting-started/current/languages-guides/neo4j.md">Connecting to Neo4j</a>.</p>
<h2>Test your connection</h2>
<ol>
    <li value="1">Ensure your Neo4j database is configured. See <a href="https://neo4j.com/docs/operations-manual/current/neo4j.md">Neo4j Operations Manual</a> and <a href="https://neo4j.com/docs/aura/auradb/getting-started/create-database/neo4j.md">Neo4j AuraDB: Creating an instance</a> for more information.</li>
    <li value="2">Specify the database URL in the <code><a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format: </li><pre xml:space="preserve"><code class="language-text">url: jdbc:neo4j:bolt://&lt;host&gt;:&lt;port&gt;/?username=foo,password=bar</code></pre>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span> The Liquibase extension for Neo4j only supports connections through the Bolt protocol, not HTTP.</p>
    <p>For more information about the JDBC connection, see <a href="http://neo4j-contrib.github.io/neo4j-jdbc/neo4j.md">Neo4j JDBC Driver Documentation § Technical Reference</a>.</p>
    <p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>To apply a <span class="mc-variable General.LBPro variable">Liquibase Pro</span> key to your project, add the following property to the Liquibase properties file: <code>licenseKey: &lt;paste code here&gt;</code></p>
</ol>
<ol start="3">
    <li value="3">Create a text file called <a href="https://docs.liquibase.com/concepts/changelogs/home.html">changelog</a> (<code>.xml</code>, <code>.cypher</code>, <code>.json</code>, or <code>.yaml</code>) in your project directory and add a <a href="https://docs.liquibase.com/concepts/changelogs/changeset.html">changeset</a>. The <code>&lt;neo4j:cypher&gt;</code>&#160;<span class="mc-variable General.changetypes variable">Change Type</span> has the same behavior as the <code>&lt;<a href="https://docs.liquibase.com/change-types/sql.html" class="MCXref xref">sql</a>&gt;</code>&#160;<span class="mc-variable General.changetypes variable">Change Type</span>. For more information about Cypher syntax, see the <a href="https://neo4j.com/docs/cypher-manual/current/introduction/neo4j.md">Neo4j Cypher Manual</a> (general syntax) the <a href="https://github.com/liquibase/liquibase-neo4j/blob/main/docs/reference-features.md">Neo4j Extension Cypher Manual</a> (Liquibase syntax).</li>
    <a style="font-size: 18pt;"> XML example</a>
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
        &lt;neo4j:cypher&gt;CREATE (:TestNode {test_property: "Value"});&lt;/neo4j:cypher&gt;
    &lt;/changeSet&gt;
&lt;/databaseChangeLog&gt;</code></pre>
<a style="font-size: 18pt;"> Cypher (SQL) example</a>
    <pre xml:space="preserve"><code class="language-sql">-- liquibase formatted cypher

-- changeset liquibase:1
CREATE (:TestNode {test_property: "Value"});</code></pre>
<p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>Formatted SQL/Cypher <span class="mc-variable General.changelog variable">changelog</span>s generated from Liquibase versions before 4.2 might cause issues because of the lack of space after a double dash ( <code>--</code> ). To fix this, add a space after the double dash. For example: <code>--&#160;liquibase formatted cypher</code> instead of <code>--liquibase formatted cypher</code> and <code>--&#160;changeset myname:create</code> instead of <code>--changeset myname:create</code>.</p>
<a style="font-size: 18pt;"> YAML example</a>
<pre xml:space="preserve"><code class="language-yaml">databaseChangeLog:
   - changeSet:
       id: 1
       author: Liquibase
       changes:
       - neo4j:cypher:
           sql: CREATE (:TestNode {test_property: "Value"});</code></pre>
                                            <a style="font-size: 18pt;"> JSON example</a>
                                                <pre><code class="language-json">{
  "databaseChangeLog": [
    {
      "changeSet": {
        "id": "1",
        "author": "Liquibase",
        "changes": [
          {
            "neo4j:cypher": {
              "sql": CREATE (:TestNode {test_property: "Value"});
            }
          }
        ]
      }
    }
  ]
}</code></pre>
    <li value="4">Navigate to your project folder in the CLI and run the Liquibase&#160;<a href="https://docs.liquibase.com/commands/change-tracking/status.html" class="MCXref xref">status</a> command to see whether the connection is successful:</li><pre xml:space="preserve"><code class="language-text">liquibase status --username=test --password=test --changelog-file=&lt;changelog.xml&gt;</code></pre>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>You can pass arguments in the CLI or keep them in the Liquibase properties file.</p>
    <li value="5">Inspect the SQL with  the <a href="https://docs.liquibase.com/commands/update/update-sql.html" class="MCXref xref">update-sql</a> command. Then make changes to your database with the <a href="https://docs.liquibase.com/commands/update/update.html" class="MCXref xref">update</a> command.</li><pre xml:space="preserve"><code class="language-text">liquibase update-sql --changelog-file=&lt;changelog.xml&gt;
liquibase update --changelog-file=&lt;changelog.xml&gt;</code></pre>
    <li value="6">
        <p>From the Neo4j browser, ensure that your database contains the <span class="mc-variable General.changelog variable">changelog</span> node by running <code>MATCH (c:__LiquibaseChangeLog) RETURN c</code>.</p>
        <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>The <code>__LiquibaseChangeLogLock</code> node is only present during an active Liquibase execution, not after, so it isn't normally visible.</p>
    </li>
</ol>
<h2>Related links</h2>
<ul>
    <li><a href="https://neo4j.com/labs/liquibase/docs/neo4j.md">Neo4j documentation: Neo4j Plugin for Liquibase</a>
    </li>
    <li><a href="https://neo4j.com/docs/getting-started/current/neo4j.md">Neo4j documentation: Welcome to Neo4j</a>
    </li>
    <li><a href="https://neo4j.com/docs/getting-started/current/appendix/graphdb-concepts/neo4j.md">Neo4j documentation: Graph database concepts</a>
    </li>
</ul>
