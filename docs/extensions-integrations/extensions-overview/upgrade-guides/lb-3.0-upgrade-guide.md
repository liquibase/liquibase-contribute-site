---
title: To Liquibase 3.0
---

<h1>Liquibase 3.0 Extension Upgrade Guide</h1>
<p>For the normal Liquibase end user, Liquibase 3.0.0 is a drop-in replacement for any Liquibase 2.0.x version.</p>
<p>For developers of Liquibase extensions, there has been some Java API changes that may impact your code. This page continue to be updated with those changes.</p>
<p><a href="../lb-2.0-upgrade-guide">1.x to 2.x upgrade guide</a>
</p>
<h2><code>runOnChange</code> and Other Checksum Related Functionality</h2>
<p>There was a change in how checksums are computed between 2.x and 3.x. We try to keep these changes to a minimum, but unfortunately they do happen, especially when there are extensive code changes like there was in 3.0.0. Liquibase will detect that there has been a checksum version change and automatically update the checksums accordingly when you first run 3.0, but that also means Liquibase cannot detect differences between a changeset between a 2.x and a 3.x run.</p>
<p>When Liquibase cannot determine if a changeset has changed or not it assumes it has not.</p>
<p>Therefore, if you use runOnChange functionality that you think may be triggered, you will want to run Liquibase 3.0 against your target database with a known unchanged changelog file before running a new changelog file.</p>
<h2>API changes</h2>
<h3>Database.escapeDatabaseObject</h3><pre><code class="language-text">Database.escapeDatabaseObject -&gt; Database.escapeObjectName(String objectName, Class&lt;? extends DatabaseObject&gt; objectType)</code></pre>
<p>To better support escaping objects, we removed the generic <code>escapeDatabaseObject</code> method in favor of a new one that better describes what you are doing (escaping the name, not escaping the object) and specifies the type of object that is being escaped.</p>
<h3>Database.escape * Name(schema, tableName)</h3>
<p>In 3.0, we now support catalogs in addition to schemas and so all the <code>escapeTYPEName</code> methods such as <code>escapeTableName</code>, <code>escapeColumnName</code> etc. take an additional catalog parameter now.</p>
<h3>AbstractChange constructor</h3>
<p>In 2.x, change metadata such as priority, name, and description was passed in the constructor. To better support subclassing default Changes, the metadata was moved to a new <code>@DatabaseChange</code> class level annotation.</p>
<p>For example, in 2.x you would create a class as:</p><pre><code class="language-text">public AddCheckChange() {
super("addCheck", "Add Check", ChangeMetaData.PRIORITY_DEFAULT);
}</code></pre>
<p>But in 3.x you would have a no-arg constructor and instead add the following to the class definition:</p><pre><code class="language-text">@DatabaseChange(
name="addCheck",
description = "Add Check",
priority = ChangeMetaData.PRIORITY_DEFAULT)</code></pre>
