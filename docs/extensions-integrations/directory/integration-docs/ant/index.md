---
title: Overview
---

<h1>Ant Overview</h1>
<p>Liquibase has a set of Ant tasks that it provides.</p>
<h2 id="installation">Concepts and types</h2>
<h3 id="database">Database</h3>
<p>All of the Liquibase Ant tasks are designed around the <code class="highlighter-rouge">&lt;database&gt;</code> type. This element configures the database connection and corresponding settings that Liquibase will use when accessing and updating the database. It is a required in all Liquibase Ant tasks.</p>
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
            <td><code>id</code>
            </td>
            <td>The unique identifier for the instance that can be used to reference tasks.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>driver</code>
            </td>
            <td>The fully qualified class name of the JDBC driver.</td>
            <td>Yes</td>
        </tr>
        <tr>
            <td><code>url</code>
            </td>
            <td>The JDBC connection URL.</td>
            <td>Yes</td>
        </tr>
        <tr>
            <td><code>user</code>
            </td>
            <td>The username to the JDBC connection.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>password</code>
            </td>
            <td>The password to the JDBC connection.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>defaultSchemaName</code>
            </td>
            <td>The schema to use by default for managed database objects and Liquibase control tables.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>defaultCatalogName</code>
            </td>
            <td>The catalog to use by default for managed database objects and Liquibase control tables.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>outputDefaultSchema</code>
            </td>
            <td>The attribute to send output with the default schema name.</td>
            <td>No; Default is true</td>
        </tr>
        <tr>
            <td><code>outputDefaultCatalog</code>
            </td>
            <td>The attribute to send output  with the default catalog name.</td>
            <td>No; Default is true</td>
        </tr>
        <tr>
            <td><code>liquibaseSchemaName</code>
            </td>
            <td>The schema name where the Liquibase tables will be located.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>liquibaseCatalogName</code>
            </td>
            <td>The catalog name where the Liquibase tables will be located.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>databaseClass</code>
            </td>
            <td>The fully qualified class name of a class that implements the <code>Database</code> interface. Used to add database types that are not officially supported by Liquibase.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>databaseChangeLogTableName</code>
            </td>
            <td>The attribute that overrides the name of the DATABASECHANGELOG table.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>databaseChangeLogLockTableName</code>
            </td>
            <td>The attribute that overrides the name of the DATABASECHANGELOGLOCK table.</td>
            <td>No</td>
        </tr>
        <tr>
            <td><code>liquibaseTablespaceName</code>
            </td>
            <td>The name of the tablespace where Liquibase tables are located.</td>
            <td>No</td>
        </tr>
    </tbody>
</table>

<pre xml:space="preserve">
<code class="language-xml" data-lang="xml">&lt;liquibase:database  
    id="my-database"  
    driver="${db.driver}"  
    url="${db.url}"  
    user="${db.user}"  
    password="${db.password}"/&gt;</code>
</pre>
<p>If you use more than one Liquibase task in your Ant build,  create the <code class="highlighter-rouge">&lt;database&gt;</code> anywhere in your build, give it an <code>id</code>, and reference it using the <code class="highlighter-rouge">databaseref</code> attribute:</p><pre xml:space="preserve">
<code class="language-xml" data-lang="xml">&lt;liquibase:database  
    id="my-database"  
    driver="${db.driver}"  
    url="${db.url}"  
    user="${db.user}"  
    password="${db.pass}"/&gt;

&lt;liquibase:update  
    databaseref="my-database"  
    changeLogFile="path/to/changelog.xml"/&gt;  

&lt;liquibase:tag  
    databaseref="my-database"  
    tag="new-tag"/&gt;</code></pre>

<p>The <code class="highlighter-rouge">&lt;database&gt;</code> type also supports a nested element <code class="highlighter-rouge">&lt;connectionProperties&gt;</code> which allows users to specify custom JDBC connection properties to Liquibase:</p>

<pre xml:space="preserve"><code class="language-xml" data-lang="xml">&lt;liquibase:database  
    id="my-database"  
    driver="${db.driver}"  
    url="${db.url}"  
    user="${db.user}"  
    password="${db.pass}"&gt;  
&lt;liquibase:connectionproperties&gt;  
    &lt;liquibase:connectionproperty  name="prop1"  value="value1"/&gt;  
    &lt;liquibase:connectionproperty  name="prop2"  value="value2"/&gt;  
    &lt;propertyset&gt;  
        &lt;propertyref  prefix="liquibase"/&gt;  
    &lt;/propertyset&gt;  
&lt;/liquibase:connectionproperties&gt;  
&lt;/liquibase:database&gt;</code></pre>

<h3 id="changelog-parameters">changelog parameters</h3>
<p>Liquibase changelog files can have parameters that are dynamically substituted at runtime. All Liquibase Ant tasks support these parameters by way of the <code class="highlighter-rouge">&lt;changeLogParameters&gt;</code> element.</p><pre xml:space="preserve"><code class="language-xml" data-lang="xml">&lt;liquibase:updateDatabase  databaseref="my-database"  changeLogFile="/path/to/changelog.xml"&gt;  
	&lt;liquibase:changeLogParameters&gt;  
		&lt;liquibase:changeLogParameter  name="name1"  value="value1"/&gt;  
		&lt;liquibase:changeLogParameter  name="name2"  value="value2"/&gt;  
		&lt;propertyset&gt;  
			&lt;propertyref  prefix="params"/&gt;  
		&lt;/propertyset&gt;  
	&lt;/liquibase:changeLogParameters&gt;  
&lt;/liquibase:updateDatabase&gt;</code></pre>

<p>To start using Liquibase and Ant, follow <a href="getting-started-liquibase-ant">Getting Started with Liquibase and Ant</a>.</p>
