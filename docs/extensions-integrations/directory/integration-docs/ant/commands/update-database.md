---
title: updateDatabase
---

<h1>Ant <code>updateDatabase</code></h1>
<p>Roll the database forward applying all changesets that have not been run.</p>
<h2 id="syntax">Syntax</h2><pre xml:space="preserve"><code class="language-xml" data-lang="xml">&lt;liquibase:updateDatabase
    changeLogFile="/path/to/changeLog.xml"&gt;
    &lt;liquibase:database
        driver="${db.driver}"
        url="${db.url}"
        user="${db.user}"
        password="${db.pasword}"/&gt;
&lt;/liquibase:updateDatabase&gt;</code></pre>
<p>A basic implementation of the update database task. Applies updates in the changelog file to the database.</p><pre xml:space="preserve"><code class="language-xml" data-lang="xml">&lt;database
    id="my-database"
    driver="${driver.classname}"
    url="${jdbc.url}"
    user="${username}"
    password="${password}"/&gt;
&lt;liquibase:updateDatabase
    databaseRef="my-database"
    changeLogFile="/path/to/changeLog.xml"/&gt;</code></pre>
<p>Updates the referenced database with the changeset found in the given changelog file.</p><pre xml:space="preserve"><code class="language-xml" data-lang="xml">&lt;liquibase:updateDatabase
    changeLogFile="/path/to/changeLog.xml"&gt;
    &lt;liquibase:database
        driver="${db.driver}"
        url="${db.url}"
        user="${db.user}"
        password="${db.pasword}"/&gt;
    &lt;liquibase:changeLogParameters&gt;
        &lt;liquibase:changeLogParameter name="property1" value="value1"/&gt;
        &lt;liquibase:changeLogParameter name="property2" value="value2"/&gt;
        &lt;propertyset&gt;
            &lt;propertyref prefix="mydb"/&gt;
        &lt;/propertyset&gt;
    &lt;/liquibase:changeLogParameters&gt;
&lt;/liquibase:updateDatabase&gt;</code></pre>
<p>Updates the database with the changelog file, substituting any changelog parameters present with values defined in the nested elements.</p>
<h2>Parameters</h2>
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
            <td><code>changeLogFile</code>
            </td>
            <td>
                The changelog file for Liquibase to use.
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
            <td>No; defaults to system encoding.</td>
        </tr>
        <tr>
            <td><code>promptOnNonLocalDatabase</code>
            </td>
            <td>
                If set to true, a dialog box with warn you if you attempt to run the Liquibase against a database that is not on localhost.
            </td>
            <td>No; default is false.</td>
        </tr>
        <tr>
            <td><code>dropFirst</code>
            </td>
            <td>If set to true, Liquibase will first drop all database objects owned by the connected user.</td>
            <td>No; default is false</td>
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
<h2 id="parameters-specified-as-nested-elements">Nested attributes</h2>
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
            <td>Required unless a <code>databaseref</code>attribute is given.</td>
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
