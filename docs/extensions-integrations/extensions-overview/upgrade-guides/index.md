---
title: Overview
---

<h1>Liquibase Extension Upgrade Guides</h1>

<p>As a platform built around extensibility, Liquibase strives to preserve API-level compatibility from release to release. However, preserving compatibility sometimes comes at a cost of not being able to use new technologies, patterns, and use cases that do not fit well into that original API structure.</p>
<p>In our quest to balance stability with innovation, Liquibase 4.0 introduces some API breaking changes. To ease the transition, we also included an update compatibility layer to keep existing extensions working, and give everyone time to make the (minor) extension changes required to stay fully Liquibase compatible</p>
<h2>Impact on end-users</h2>
<p>For most extensions, you can still run a Liquibase 3.x-designed version of the extension on Liquibase 4.0 as long as you include the <code>liquibase-compat.jar</code> to your classpath.</p>
<p>If you're using a 3.x version of an extension,  download the compatibility library from Maven Central or from Github at <a href="https://github.com/liquibase/liquibase-compat/releases">https://github.com/liquibase/liquibase-compat/releases</a>.</p>
<p>If your extension has more complexity and falls outside of the basic development patterns, it's possible that some of the functionality will be broken. The indicator of problems is the occurrence of method not found errors when performing certain operations. If this occurs, let the extension developer know.</p>
<h2>Impact on extension authors</h2>
<p>As an extension author, the <code>liquibase-compat</code> library should give you time to get your library up-to-date with Liquibase4.x so you aren't relying on the<code>liquibase-compat</code> library or the deprecated methods for very long.</p>
<p>To update your library, you will need to:</p>
<ol>
    <li>Compile your extension against the 4.0 version of Liquibase without <code>liquibase-compat.jar</code> in your classpath</li>
    <li>Fix any compilation errors</li>
    <li>Fix any deprecated warnings</li>
    <li>Re-test functionality</li>
</ol>
<p>By releasing a new version of your extension to work with the Liquibase 4.0, you keep your users from having to rely on the <code>liquibase-compat</code> library. Removing this library will reduce installation overhead and get rid of unexpected behaviors.</p>
<h2>How to run Liquibase 4.x with pre-4.x extensions</h2>
<p>We recognize the importance of backward compatibility to extension writers and extension users. To better support extension compatibility, we created a new <code>liquibase-compat</code> library for Liquibase 4.0. We intend to include new compatibility code, as changes are made to Liquibase Core, to maintain compatibility.</p>
<p>Here are the details about the three changes we made to this new <code>liquibase-compat</code> library:</p>
<ol>
    <li>Introduced <code>@Deprecated</code> versions of classes/methods that have been shifted around<ol style="list-style-type: lower-alpha;"><li>We renamed the <code>Log.debug()</code> method to <code>Log.fine()</code>, added a <code>Log.debug()</code> method marked as deprecated that calls out to the <code>new fine()</code> method.</li><li>We replaced how extension classes are found in Liquibase Core, so we added the old logic for locating both classes and files into the new <code>liquibase-compat</code> library.</li></ol></li>
    <li>Added feature/functionality flags to enable old functionality<ol style="list-style-type: lower-alpha;"><li>Since we changed how Liquibase handled the translation between database-specific types and generic types, we added a configurable flag to change back to the old behavior.</li></ol></li>
</ol>
<p>Even though we added these tools, we are not able to promise 100% compatibility with extensions built against earlier Liquibase versions. We are striving to provide as much compatibility as possible. However, to be fully compatible with new versions of Liquibase you should build your extensions without using the <code>liquibase-compat</code> library and without using deprecated methods.</p>
<h2>Available extension upgrade guides</h2>
<ul>
    <li>
        <a href="lb-4.0-upgrade-guide"> 4.0 Upgrade Guide</a>
    </li>
    <li>
        <a href="lb-3.3-upgrade-guide"> 3.3 Extension Upgrade Guide</a>
    </li>
    <li>
        <a href="lb-3.2-upgrade-guide"> 3.2 Extension Upgrade Guide</a>
    </li>
    <li>
        <a href="lb-3.1-upgrade-guide"> 3.1 Extension Upgrade Guide</a>
    </li>
    <li>
        <a href="lb-3.0-upgrade-guide"> 3.0 Extension Upgrade Guide</a>
    </li>
    <li>
        <a href="lb-2.0-upgrade-guide"> 2.0 Extension Upgrade Guide</a>
    </li>
</ul>
