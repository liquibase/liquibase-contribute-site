---
title: Gradle
---

<h1>Getting Started with Liquibase and Gradle</h1>
<p>Using the <a href="https://github.com/liquibase/liquibase-gradle-plugin">Liquibase Gradle plugin</a> helps to manage database scripts, build and automate your software processes. When Gradle applies the plugin to the target, it creates a Gradle task for each command supported by Liquibase. To see the list of those tasks, run the <code>gradle tasks</code> command.</p>
<p>To use Liquibase and Gradle:</p>
<ol>
    <li>Create a text file called <code>build.gradle</code> in your project folder or use the existing <code>build.gradle</code> file.
    </li>
    <li>Add the following section to your <code>build.gradle</code> file to include the <code>liquibase</code> plugin into Gradle builds:
    </li><pre xml:space="preserve">
<code class="language-xml">plugins {
    id 'org.liquibase.gradle' version '2.2.0'
}</code></pre>
    <p>The following <a href="https://docs.gradle.org/current/userguide/plugins.html#sec:old_plugin_application">legacy plugin application</a> is also available to use:</p><pre xml:space="preserve">
<code class="language-text">buildscript {
    repositories {
        mavenCentral()
        }
        dependencies {
            classpath "org.liquibase:liquibase-gradle-plugin:2.2.0"
        }
}
apply plugin: 'org.liquibase.gradle'
</code></pre>
    <li>Add the <code>dependencies</code> section to include files on which Liquibase will depend to run commands. The plugin needs to find Liquibase when it runs a task, and Liquibase needs to find database drivers, changelog parsers, and other files on the classpath. When adding <code>liquibaseRuntime</code> dependencies to the <code>dependencies</code> section in the <code>build.gradle</code> file, include the Liquibase value along with your database driver:
    </li><pre xml:space="preserve">
<code class="language-text">dependencies {
    liquibaseRuntime 'org.liquibase:liquibase-core:4.24.0'
    liquibaseRuntime 'org.liquibase:liquibase-groovy-dsl:2.1.1'
    liquibaseRuntime 'info.picocli:picocli:4.7.5'
    liquibaseRuntime 'org.yaml:snakeyaml:1.33'
    liquibaseRuntime 'mysql:mysql-connector-java:5.1.34'
}
apply plugin: "org.liquibase.gradle"</code></pre>
    <p>Replace <code>org.liquibase:liquibase-core:4.24.0</code> and <code>mysql:mysql-connector-java:5.1.34</code> with your values. If you use Groovy scripts for database changes, the example code includes the <a href="https://github.com/liquibase/liquibase-groovy-dsl">Liquibase Groovy DSL</a> dependency, which parses changelogs written in a Groovy DSL. You do not need to add <code>org.liquibase:liquibase-groovy-dsl:2.1.1</code> if you do not use the Groovy changelog format. For more information, see Step 4.</p>
    <li>Create a text file in your application directory and name it <code>changelog.sql</code>. Liquibase also supports the XML, YAML, and JSON changelog formats.
Another way to use Liquibase and Gradle is with the <code>changelog.groovy</code> file.            </li>
    <li>Add changesets to your changelog file. Use the following examples depending on the format of the changelog you created:</li>
    <p>XML example:</p><pre xml:space="preserve">
<code class="language-xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;databaseChangeLog
xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
xmlns:pro="http://www.liquibase.org/xml/ns/pro"
xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd"&gt;

    &lt;changeSet id="1" author="Liquibase&gt;
        &lt;createTable tableName="test_table"&gt;
           &lt;column name="test_id" type="int"&gt;
                 &lt;constraints primaryKey="true"/&gt;
           &lt;/column&gt;
           &lt;column name="test_column" type="varchar"/&gt;
        &lt;/createTable&gt;
    &lt;/changeSet&gt;
&lt;/databaseChangeLog&gt;</code></pre>
    <p>SQL example:</p><pre xml:space="preserve"><code class="language-sql">-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table (test_id INT, test_column VARCHAR, PRIMARY KEY (test_id))</code></pre>
    </p>YAML example:</p><pre xml:space="preserve"><code class="language-yaml">databaseChangeLog:
- changeSet:
    id: 1
    author: Liquibase
    changes:
      - createTable:
          columns:
          - column:
              name: test_column
              type: INT
              constraints:  
                  primaryKey:  true  
                  nullable:  false  
          tableName: test_table</code></pre>

<p>JSON example</p><pre xml:space="preserve"><code class="language-json">{ 
    "databaseChangeLog": [
    {
        "changeSet": {
        "id": "1",
        "author": "Liquibase",
        "changes": [
        {
          "createTable": {
            "columns": [
            {
              "column": 
              {
                "name": "test_column",
                "type": "INT",
                "constraints": 
              {
                "primaryKey": true,
                "nullable": false
                }
                }
              }]
            ,
            "tableName": "test_table"
          }
        }]
    }
}]
}</code></pre>
    <p>Groovy example:</p><pre xml:space="preserve"><code class="language-text">databaseChangeLog = {
    changeSet(id: '1', author: 'liquibase') {
        createTable(tableName: 'test_table') {
            column(name:'test_id', type="int") {
                constraints(primaryKey: true)
            }
            column(name:'test_column', type="varchar")
        }
    }
}</code></pre>
    <li>Set the following Liquibase properties in your <code>build.gradle</code> file:
    </li><pre xml:space="preserve"><code class="language-text">liquibase {
    activities {
        main {
          changelogFile "../changelog.sql"
          url "mysql://localhost:3306/testdatabase"
          username "username"
          password "password"
        }
    }
}</code></pre>
    <p>Note: Replace the values from the example with your values.</p>
    
    <p>Note: If you get the following error, this can often be fixed by changing the Java version or deleting IntelliJ caches. (<a href="https://stackoverflow.com/questions/32905270/intellij-idea-and-gradle-cannot-be-applied-to-groovy-lang-closure">source article</a>)
    <pre xml:space="preserve"><code class="language-text">main in build cannot be applied to (groovy.lang.Closure)</code></pre>
    
    <p>Tip: To store other Liquibase properties in a file instead of passing them at runtime, you can continue specifying the properties in the <code>build.gradle</code> file or create a new text file called <code>liquibase.properties</code> and set them there. If you create a Liquibase properties file, specify <code>propsFile "../&lt;liquibase.properties&gt;"</code> in the main section of the <code>build.gradle</code> file, where <code>&lt;liquibase.properties&gt;</code> represents the name of the Liquibase properties file. For more information, see <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html">Create and Configure a liquibase.properties File</a>.</p>
    
    <li>Do your first update by adding the task section to the <code>build.gradle</code> file:
    </li><pre xml:space="preserve"><code class="language-text">task('deploy changeLog') {
    doFirst() {
        liquibase {
            activities {
                main {
                    changeLogFile System.properties.liquibaseChangeLogFile
                    contexts System.properties.liquibaseContexts
                }
            }
        }
    }
}
update.dependsOn('deploy changeLog')</code>
</pre>
    <li>Run the <code>gradle build</code> command, and then run the following:
    </li><pre xml:space="preserve"><code class="language-text">gradle update</code></pre>
    <p>After your first update, you will see a new table along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html">DATABASECHANGELOG table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html">DATABASECHANGELOGLOCK table</a> added to the database.</p>
    <li>[Optional] Do your first rollback by using the <code>rollback-count</code> command:
    </li><pre xml:space="preserve"><code class="language-text">gradle build
gradle rollbackCount -PliquibaseCommandValue=1</code></pre>
    <p>Note: You can also specify the command value in the <code>build.gradle</code> file or use other <code>rollback</code> commands: <a href="https://docs.liquibase.com/commands/rollback/rollback-by-tag.htm">rollback</a>, <a href="https://docs.liquibase.com/commands/rollback/rollback-to-date.htm">rollback-to-date</a>, <a href="https://docs.liquibase.com/commands/rollback/rollback-one-changeset.html">rollback-one-changeset</a>, <a href="https://docs.liquibase.com/commands/rollback/rollback-one-update.html">rollback-one-update</a>.</p>
    <p>Tip: Automatic rollback is not supported for formatted SQL changesets. You need to add custom rollback statements to formatted SQL changesets if you want to use rollback commands:</p><pre xml:space="preserve"><code class="language-text">-- changeset liquibase:1
create table test_table ( id int primary key, name varchar(255) );
-- rollback drop table test_table;</code></pre>
    <li>Check the changes by inspecting your database or running the <a href="https://docs.liquibase.com/commands/change-tracking/status.htm">status</a> command.
    </li>
</ol>
<h2>Related links</h2>
<ul>
    <li><a href="https://gradle.org/">Gradle</a>
    </li>
    <li><a href="https://github.com/liquibase/liquibase-gradle-plugin">Liquibase Gradle repository</a>
    </li>
    <li><a href="https://github.com/liquibase/liquibase-groovy-dsl">Liquibase Groovy DSL</a>
    </li>
    <li>
        <a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.htm">Introduction to Liquibase</a>
    </li>
</ul>
