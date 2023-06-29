---
title: BigQuery
---

<h1>Using Liquibase with BigQuery</h1>
<p><a href="https://cloud.google.com/bigquery/bigquery.md">Google BigQuery</a> is a fully managed analytics data warehouse. For
    more information, see <a href="https://cloud.google.com/bigquery/docs">BigQuery Documentation</a>.</p>
<h2>Supported versions</h2>
<ul>
    <li>2.13.6+</li>
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
<p>To use Liquibase and BigQuery, you need several JAR
    files:</p>
<ul>
    <li> All the JAR files in the <a href="https://cloud.google.com/bigquery/docs/reference/odbc-jdbc-drivers">BigQuery
        JDBC&#160;ZIP&#160;file</a> (under "Current JDBC driver")
    </li>
    <li>The <a href="https://github.com/liquibase/liquibase-bigquery"><span
            class="mc-variable General.Liquibase variable">Liquibase</span> extension for Google BigQuery</a></li>
</ul>
<p>
    <a
            href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">Place your JAR
        file(s)</a> in the <code>liquibase/lib</code> directory.
</p><p>
If you use Maven,
    you must <a
            href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR&#160;as a dependency</a> in
        your <code>pom.xml</code> file.
    
</p>
<pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;com.google.cloud&lt;/groupId&gt;
    &lt;artifactId&gt;google-cloud-bigquery&lt;/artifactId&gt;
    &lt;version&gt;2.24.4&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
    &lt;artifactId&gt;liquibase-bigquery&lt;/artifactId&gt;
    &lt;version&gt;<span class="mc-variable General.CurrentLiquibaseVersion variable">4.20.0</span>&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
<h2 id="test-your-connection">Test your connection</h2>
<ol>
    <li value="1">Ensure your BigQuery database is configured. See <a
            href="https://cloud.google.com/bigquery/docs/quickstarts">BigQuery Quickstarts</a> for more information. For
        example, you can run a query of a sample table in BigQuery using the <a
                href="https://cloud.google.com/bigquery/docs/quickstarts/load-data-bq"><code>bq</code> command-line tool</a>:
    </li>
    <pre><code class="language-text">bq show bigquery-public-data:samples.shakespeare</code></pre>
    <li value="2">
        <p>Specify the database URL in the <code><a
                href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span
                class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults
            file), along with other properties you want to set a default value for. <span
                    class="mc-variable General.Liquibase variable">Liquibase</span> does not parse the URL. You can
            either specify the full database connection string or specify the URL using your database's standard JDBC
            format:</p>
    </li>
    <pre xml:space="preserve"><code class="language-text">url: jdbc:bigquery://https://googleapis.com/bigquery/v2:443/&lt;dbname&gt;;ProjectId=&lt;STR&gt;;OAuthType=&lt;INT&gt;;</code></pre>
    <p>Specify the name of your database in place of <code>dbname</code>. Specify the ID of your BigQuery project as the
        value of <code>ProjectId</code>. Specify your BigQuery authentication method as the value of
        <code>OAuthType</code>. Click on the following tabs to see example JDBC URLs for each authentication type:</p>
    
<a style="font-size: 14pt;" href="#">
<code>OAuthType=0</code> (Google Services)</a>
   
<p>Requires the options <code>OAuthServiceAcctEmail</code> and <code>OAuthPvtKeyPath</code> in your <code>url</code>
                property. For example:</p>
            <pre xml:space="preserve"><code class="language-text">jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
ProjectId=myProject;
OAuthType=0;
OAuthServiceAcctEmail=lbtest@bq123.iam.gserviceaccount.com;
OAuthPvtKeyPath=C:\path\serviceKey.p12;</code></pre>
            <p>For more information, see:</p>
            <ul>
                <li><a href="https://cloud.google.com/bigquery/docs/authentication/service-account-file">BigQuery:
                    Authenticating with a service account key file</a>
                </li>
                <li><a href="https://developers.google.com/identity/protocols/oauth2/service-account">Google: Using
                    OAuth 2.0 for Server to Server Applications</a>
                </li>
            </ul>

<a style="font-size: 14pt;"><code>OAuthType=1</code> (Google User Account)</a>

            <p>Requires your user account credentials. For example:</p>
            <pre xml:space="preserve"><code class="language-text">jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
ProjectId=myProject;
OAuthType=1;</code></pre>
            <p>For more information, see <a href="https://cloud.google.com/docs/authentication/end-user">Google:&#160;Authenticate
                installed apps with user accounts</a>.</p>

    <a style="font-size: 14pt;">
<code>OAuthType=2</code> (Google Authorization Server Access Token)</a>

            <p>Requires the options <code>OAuthAccessToken</code>, <code>OAuthRefreshToken</code>,
                <code>OAuthClientId</code>, and <code>OAuthClientSecret</code> in your <code>url</code> property. For
                example:</p>
            <pre xml:space="preserve"><code class="language-text">jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
ProjectId=myProject;
OAuthType=2;
OAuthAccessToken=a25c7cfd36214f94a79d;
OAuthRefreshToken=2kl0Qvuw9qt4abia54qga5t97;
OAuthClientId=22n6627g243322f7;
OAuthClientSecret=cDE+F2g3Hcjk4K5lazM;</code></pre>
            <p>For more information, see:</p>
            <ul>
                <li><a href="https://cloud.google.com/bigquery/docs/authorization">BigQuery: Authorizing API
                    requests</a>
                </li>
                <li><a href="https://developers.google.com/identity/protocols/oauth2">Google: Using OAuth 2.0 to Access
                    Google APIs</a>
                </li>
            </ul>

    <a style="font-size: 14pt;"><code>OAuthType=3</code> (Application Default Credentials)</a>

            <p>For example:</p>
            <pre xml:space="preserve"><code class="language-text">jdbc:bigquery://https://googleapis.com/bigquery/v2:443/myDatabase;
ProjectId=myProject;
OAuthType=3;</code></pre>
            <p>For more information, see <a href="https://cloud.google.com/docs/authentication#service-accounts">Google:
                Authenticating as a service account</a>.</p>

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
    <li><a href="https://docs.liquibase.com/change-types/home.html" class="MCXref xref">Change Types</a>
    </li>
    <li><a href="https://docs.liquibase.com/commands/home.html" class="MCXref xref">Liquibase Commands</a>
    </li>
</ul>
