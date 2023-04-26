---
title: DB2 on z/OS SQL Scripts
---

<h1>Deploying Changes to DB2 on z/OS using SQL Scripts</h1>
<h2>Step 1: Create a SQL folder</h2>
<p>In the Liquibase project folder that you created, create a folder named <code>sql_files</code>  to store SQL scripts that Liquibase will track, version, and deploy.</p>
<p>The directory structure should look like this:</p><pre><code class="language-text">$LB_HOME/db2_zos/sql_files</code></pre>
<h2>Step 2: Set up the <span class="mc-variable General.changelog variable">changelog</span></h2>
<p>This is a one-time step to configure a change log to point to the SQL folder that will contain SQL scripts. Create and save a file called <code>changelog.xml</code> in the Liquibase project directory you created (<code>$LB_HOME/db2_zos</code>).</p>
<p>The contents of <code>changelog.xml</code> should be as follows:</p><pre><code class="language-xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
<code>&lt;databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:pro="http://www.liquibase.org/xml/ns/pro"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
        http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd"&gt;</code>	
&lt;includeAll path="sql_files"/&gt;
&lt;/databaseChangeLog&gt;</code></pre>
<h2>Step 3: Add an SQL Script to the SQL Folder</h2>
<p>With a Liquibase properties file, like <span class="mc-variable General.liquiPropFile variable">liquibase.properties</span> from the tutorial setup and the newly created <code>changelog.xml</code>, we are now ready to start adding SQL scripts to the <code>sql_files</code> folder. Liquibase orders the scripts in the folder alphanumerically.</p>
<p>Create a file named <code>001_create_person_table.sql</code> with the following and save it in the <code>sql_files</code> folder:</p><pre><code class="language-sql">create table PERSON (
ID int not null,
FNAME varchar(100) not null
);</code></pre>
<h2>Step 4: Check your database</h2>
<p>Navigate to your project folder in the CLI and run the Liquibase&#160;<a href="https://docs.liquibase.com/commands/change-tracking/status.html" class="MCXref xref">status</a> command to see whether the connection is successful:</p><pre xml:space="preserve"><code class="language-text">liquibase status --username=test --password=test --changelog-file=&lt;changelog.xml&gt;</code></pre>
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>You can pass arguments in the CLI or keep them in the Liquibase properties file.</p>
<p>Inspect the SQL with the <a href="https://docs.liquibase.com/commands/update/update-sql.html" class="MCXref xref">update-sql</a> command. Then make changes to your database with the <a href="https://docs.liquibase.com/commands/update/update.html" class="MCXref xref">update</a> command:</p><pre xml:space="preserve"><code class="language-text">liquibase update-sql --changelog-file=&lt;changelog.xml&gt;
liquibase update --changelog-file=&lt;changelog.xml&gt;</code></pre>
<p>From a database UI tool, ensure that your database contains the table you added along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html" class="MCXref xref">DATABASECHANGELOG table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html" class="MCXref xref">DATABASECHANGELOGLOCK table</a>.</p>
<h2>Common command line arguments</h2>
<p>Use can use command line arguments to override the default options at runtime. The following are common command line arguments:</p>
<table>
    <thead>
        <tr>
            <th>Command line argument</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><code>--changelog-file=&lt;path and filename&gt;</code>
            </td>
            <td>Specify the XML <span class="mc-variable General.changelog variable">changelog</span></td>
        </tr>
        <tr>
            <td><code>--url=&lt;value&gt;</code>
            </td>
            <td>Specify a database URL</td>
        </tr>
        <tr>
            <td><code>--defaultsFile=&lt;path to file.properties&gt;</code>
            </td>
            <td>Specify the properties file. Default is <code>./liquibase.properties</code>).</td>
        </tr>
    </tbody>
</table>
</div>
