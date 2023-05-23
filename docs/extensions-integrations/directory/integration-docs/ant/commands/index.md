--- 
title: Overview
---

<h1>Ant Tasks</h1>
<h2 id="tasks">Tasks</h2>

<p>The following tasks are available in Ant:</p>
<ul>
    <li>
        <a href="changelogsync">Ant changeLogSync</a>
    </li>
    <li>
        <a href="changelogsynctotag">Ant changeLogSyncToTag</a>
    </li>
    <li>
        <a href="changelogsynctotagsql">Ant changeLogSyncToTagSql</a>
    </li>
    <li>
        <a href="dbdoc">Ant dbDoc</a>
    </li>
    <li>
        <a href="diffdatabase">Ant diffDatabase</a>
    </li>
    <li>
        <a href="diffdatabasetochangelog">Ant diffDatabaseToChangeLog</a>
    </li>
    <li>
        <a href="dropalldatabaseobjects">Ant dropAllDatabaseObjects</a>
    </li>
    <li>
        <a href="generatechangelog">Ant generateChangeLog</a>
    </li>
    <li>
        <a href="marknextchangesetran">Ant markNextChangesetRan</a>
    </li>
    <li>
        <a href="rollbackdatabase">Ant rollbackDatabase</a>
    </li>
    <li>
        <a href="rollbackfuturedatabase">Ant rollbackFutureDatabase</a>
    </li>
    <li>
        <a href="tagdatabase">Ant tagDatabase</a>
    </li>
    <li>
        <a href="updatedatabase">Ant updateDatabase</a>
    </li>
</ul>

<p>Additional Liquibase commands are supported by the command line that are not supported by the Ant tasks.</p>

<h2 id="migrating-from-legacy-tasks">Migrating From Legacy Tasks</h2>
<p>Starting with Liquibase 3.3, the Ant tasks were changed, moving all of the database attributes out of the task and into its own type. The deprecated attributes will now log a warning message instructing callers of the deprecation. While backward compatibility exists, it is advised that users migrate their Ant builds to use the new tasks.</p>
<p>To use the new Liquibase tasks, redefine  the <code class="highlighter-rouge">&lt;taskdef&gt;</code> to use the <code class="highlighter-rouge">antlib.xml</code> file:</p><pre xml:space="preserve">

<code class="language-xml" data-lang="xml">&lt;project  name="Example"  xmlns:liquibase="antlib:liquibase.integration.ant"&gt;  
    &lt;taskdef  resource="liquibase/integration/ant/antlib.xml"  uri="antlib:liquibase.integration.ant"&gt;  
        &lt;classpath  path="path/to/liquibase/libs"/&gt;  
    &lt;/taskdef&gt;  
&lt;/project&gt;</code></pre>

<p>Here is an example of a legacy update task:</p><pre>
<code class="language-xml">&lt;updateDatabase 
    changeLogFile="${db.changelog.file}"
    driver="${db.driver}"
    url="${db.url}" 
    username="${db.username}"  
    password="${db.password}" 
    promptOnNonLocalDatabase="${prompt.user.if.not.local.database}"  
    dropFirst="false"  
    classpathref="classpath"/&gt;</code></pre>

<p>Here is what it looks like migrated to the new task structure:</p><pre>
<code class="language-xml">&lt;liquibase:updateDatabase 
    changeLogFile="/path/to/changelog.xml"
    dropFirst="false"
    classpathref="classpath"
    promptOnNonLocalDatabase="${prompt.user.if.not.local.database}"&gt;
    &lt;liquibase:database driver="${db.driver}"
        url="${db.url}"
        user="${db.user}"
        password="${db.password}"/&gt;
&lt;/liquibase:updateDatabase&gt;</code></pre>
