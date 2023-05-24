---
title: changeLogSyncToTagSql
---
<h1>Ant <code>changeLogSyncToTagSql</code></h1>
<p><code>changeLogSyncToTagSql</code> is a helper task that produces the raw SQL that Liquibase would run when using the <code>changeLogSyncToTag</code> task to mark all undeployed changesets associated with the specified tag as executed in your database.</p>
<h2>Uses</h2>
<p>The <code>changeLogSyncToTagSql</code> task is typically used when you want to inspect the SQL Liquibase will use to baseline a new database environment.</p>
<p>It is best practice to inspect any SQL that Liquibase would run when using the <code>cchangeLogSyncToTag</code> task so you can review any changes the task would make to your database before running it. The <code>changeLogSyncToTag</code> task marks all changesets starting with the first changeset at the top of the changelog file and moving down to the changeset up to and including the tag.</p>
<p>Note: 	If you don't have any tag specified in your changelog file, add it by using the <a href="https://docs.liquibase.com/change-types/tag-database.html">tagDatabase</a> Change Types as follows:</p><pre>
<code class="language-text">&lt;changeSet id="13.1" author="liquibase"&gt;
    &lt;tagDatabase tag="version_2.0"/&gt;
&lt;/changeSet&gt;</code></pre>
<h2>Syntax</h2>
<p>To execute the <code>changeLogSyncToTagSql</code> task, include the following values in your Ant default file, which can be <code>build.xml</code>:</p><pre xml:space="preserve">
<code class="language-text">&lt;project  name="Example"  xmlns:liquibase="antlib:liquibase.integration.ant"&gt;
    &lt;taskdef  resource="liquibase/integration/ant/antlib.xml"  uri="antlib:liquibase.integration.ant"&gt;
        &lt;classpath  path="path/to/liquibase/libs"/&gt;
    &lt;/taskdef&gt;
    &lt;property name="db.changelog.file" value="changelog.xml" /&gt;

    &lt;target name="changeLogSyncToTagSql" depends="prepare"&gt;
        &lt;liquibase:database 
            id="my-database" 
            driver="${driver.classname}"  
            url="${jdbc.url}" 
            user="${username}" 
            password="${password}"/&gt;
        &lt;liquibase:changeLogSyncToTagSql 
            databaseref="my-database" 
            changeLogFile="com/example/changelog.xml"
            labelFilter="mylabels"
            toTag="version_1.1"
            classpathref="classpath"/&gt;
    &lt;/target&gt;
&lt;/project&gt;
</code></pre>
<p>This example will sync the referenced database with the changesets found in the changelog file up to and including the tag changeset.</p>
<p>Next, run the following in the CLI to implement the task and update your database:</p><pre xml:space="preserve"><code class="language-text">ant -f build.xml changeLogSyncToTagSql</code></pre>
<p>Note: 	It is important to type the task name in the command prompt exactly like you specify it in the target name of your <code>build.xml</code> file. Ant uses the task name to determine which Liquibase command it will run.</p>
<h2>Attributes</h2>
<table>
    <thead>
        <tr>
            <th style="width: 40%">Attribute</th>
            <th>Description</th>
            <th>Required</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><code>changelogFile</code>
            </td>
            <td>
                The changelog file for Liquibase to use.
            </td>
            <td>Yes</td>
        </tr>
        <tr>
            <td><code>toTag</code>
            </td>
            <td>
                Syncs the changesets up to and including the tag.
            </td>
            <td>Yes</td>
        </tr>
        <tr>
            <td><code>contexts</code>
            </td>
            <td>
                A comma-separated list of <a href="https://docs.liquibase.com/concepts/changelogs/attributes/contexts.html">Contexts</a> to execute. If not specified, all contexts are run.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>outputFile</code>
            </td>
            <td>
                If specified, Liquibase will save the update SQL statements to the specified file rather than executing them in the database.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>outputEncoding</code>
            </td>
            <td>
                The character encoding to use when writing to output file.
            </td>
            <td>No. Defaults to system encoding.</td>
        </tr>
        <tr>
            <td><code>promptOnNonLocalDatabase</code>
            </td>
            <td>
                If set to true, a dialog box with warn you if you attempt to run the Liquibase against a database that is not on localhost.
            </td>
            <td>No. The default value is set to false.</td>
        </tr>
        <tr>
            <td><code>classpathref</code>
            </td>
            <td>
                A reference to the classpath used to run the task with.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>databaseref</code>
            </td>
            <td>
                A reference to the database that Liquibase will connect to.
            </td>
            <td>Yes, unless a nested <code>&lt;database&gt;</code> element is present.</td>
        </tr>
        <tr>
            <td><code>driver</code>
            </td>
            <td>
                <b>Deprecated:</b> Name of the database driver to connect with.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>url</code>
            </td>
            <td>
                <b>Deprecated:</b> Use <code>&lt;database&gt;</code>'s url attribute instead. The database URL.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>username</code>
            </td>
            <td>
                <b>Deprecated:</b> The database username to connect with.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>password</code>
            </td>
            <td>
                <b>Deprecated:</b> The password to use when connecting to the database.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>defaultSchemaName</code>
            </td>
            <td>
                <b>Deprecated:</b> Schema to use by default for managed database objects and Liquibase control tables.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>currentDateTimeFunction</code>
            </td>
            <td>
                <b>Deprecated:</b> Overrides current date time function used in SQL. Useful for unsupported databases.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>databaseChangeLogTableName</code>
            </td>
            <td>
                <b>Deprecated:</b> Overrides the name of the DATABASECHANGELOG table to use.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>databaseChangeLogLockTableName</code>
            </td>
            <td>
                <b>Deprecated:</b> Overrides the name of the DATABASECHANGELOGLOCK table to use.
            </td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>logLevel</code>
            </td>
            <td>
                <b>Deprecated:</b> Specifies one of the following logging levels: debug, info, warning, severe, off. The default level is <b>info</b>.
            </td>
            <td>No</td>
        </tr>
    </tbody>
</table>
<h3>Nested attributes</h3>
<table>
    <thead>
        <tr>
            <th style="width: 30%">Attribute
            </th>
            <th>Description</th>
            <th>Required</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><code>classpath</code>
            </td>
            <td>
                The classpath used to run the task.
            </td>
            <td>Optional</td>
        </tr>
        <tr>
            <td><code>database</code>
            </td>
            <td>
                See <a href="/extensions-integrations/directory/integration-docs/ant/">database data type</a> for more information.
            </td>
            <td>Required unless a <code>databaseref</code> attribute is given.</td>
        </tr>
        <tr>
            <td><code>changelogparameters</code>
            </td>
            <td>
                See <a href="https://docs.liquibase.com/concepts/changelogs/property-substitution.html">Substituting Properties in Changelogs</a> for more information.
            </td>
            <td>Optional</td>
        </tr>
    </tbody>
</table>
