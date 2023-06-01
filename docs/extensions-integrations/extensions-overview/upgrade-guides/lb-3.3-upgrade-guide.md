---
title: To Liquibase 3.3
---
<h1>Liquibase 3.3 Extension Upgrade Guide</h1>
<p>For most Liquibase end users, Liquibase 3.3 is a drop-in replacement for Liquibase 3.2 version. <a href="../lb-3.2-upgrade-guide">3.1 to 3.2 extension upgrade guide.</a></p>
<h2>Checksum issues</h2>
<p>A bugfix in how float values are handled can cause some checksums to return a different value, even though the changeset did not change. Add the <code>validCheckSum</code> tag to any changesets that are throwing unexpected validation errors.</p>
