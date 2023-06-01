---
title: To Liquibase 3.2
---

<h1>Liquibase 3.2 Extension Upgrade Guide</h1>
<p>For must Liquibase end users, Liquibase 3.2 is a drop-in replacement for Liquibase 3.1 version.</p>
<p>For developers of Liquibase extensions, there has been some Java API changes that may impact your code.</p>
<ul>
    <li><a href="../lb-3.0-upgrade-guide">2.x to 3.0 upgrade guide</a></li>
    <li><a href="../lb-3.1-upgrade-guide">3.0 to 3.1 upgrade guide</a>
    </li>
</ul>
<h2>Updates to UTF-8 handling</h2>
<p>Use of UTF-8 has been made more consistent. This may affect some stored checksums if using non-ASCII characters. If you run into checksum issues, you can use the <code>&lt;validCheckSum&gt;</code> tag to mark both as valid or set the md5sum column to null in your databases to have it updated.</p>
<h2>New <code>liquibase.change.Change.checkStatus(Database)</code> method</h2>
<p>There is a new <code>checkStatus(Database)</code> method added to the <code>Change</code> interface which is used to validate that the change successfully ran against the given database. The <code>AbstractChange</code> base class implements it to throw a "not implemented" exception which will be fine in most cases currently, but will be used in future releases.</p>
<h2>Major changes to ResourceAccessor</h2>
<p>The <code>liquibase.resource.ResourceAccessor</code> interface was changed to better encapsulate file access logic. See the new <code>javadoc</code> for more information</p>
<h2>Parsing and serialization logic</h2>
<p>The XML, YAML, and JSON ChangeLogParsers were changed along with many other serializable classes. A new <code>liquibase.parser.core.ParsedNode</code> class was added as an intermediary format.</p>
