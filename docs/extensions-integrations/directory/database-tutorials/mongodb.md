---
title: MongoDB
---

# Use Liquibase with MongoDB
<p><a href="https://www.mongodb.com">MongoDB</a>&#160;is a document-oriented NoSQL&#160;database. For more information, see <a href="https://www.mongodb.com/docs/">MongoDB&#160;Documentation</a>.</p>
## Supported versions
<ul>
    <li>6.X</li>
    <li>5.X</li>
    <li><a href="https://docs.liquibase.com/start/tutorials/mongo-db-atlas-config.html" class="MCXref xref">MongoDB Atlas</a>
    </li>
</ul>
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>Liquibase does not support the version of MongoDB used with <a href="https://docs.aws.amazon.com/documentdb/latest/developerguide/what-is.html">AWS DocumentDB</a> or <a href="https://learn.microsoft.com/en-us/azure/cosmos-db/mongodb/mongodb-introduction">Azure Cosmos DB</a> .</p>
## Prerequisites
<ol>
    <li value="1"><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.html" class="MCXref xref">Introduction to Liquibase</a> – Dive into Liquibase concepts.</li>
    <li value="2"><a href="https://docs.liquibase.com/start/install/home.html" class="MCXref xref">Install Liquibase</a> – Download Liquibase on your machine.</li>
</ol>

## Install drivers
<p>To use Liquibase and MongoDB, you need four JAR files:</p>
<ul>
    <li><a href="https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-core">MongoDB Java Driver Core</a> (<code>mongodb-driver-core-&lt;version&gt;.jar</code>)</li>
    <li><a href="https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-sync">MongoDB Synchronous Driver</a> (<code>mongodb-driver-sync-&lt;version&gt;.jar</code>)</li>
    <li><a href="https://mvnrepository.com/artifact/org.mongodb/bson">MongoDB&#160;BSON</a> (<code>bson-&lt;version&gt;.jar</code>)</li>
    <li><a href="https://github.com/liquibase/liquibase-mongodb/releases/">Liquibase MongoDB extension</a> (<code>liquibase-mongodb-&lt;version&gt;.jar</code>)</li>
</ul>
<p> <a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">Place your JAR file(s)</a> in the <code>liquibase/lib</code> directory.</p><p>If you use Maven, you must <a href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR&#160;as a dependency</a> in your <code>pom.xml</code> file.</p>
<p>To include the driver JAR in the pom.xml file, use the following code:</p><pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;org.mongodb&lt;/groupId&gt;
    &lt;artifactId&gt;mongodb-driver-core&lt;/artifactId&gt;
    &lt;version&gt;4.9.0&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.mongodb&lt;/groupId&gt;
    &lt;artifactId&gt;mongodb-driver-sync&lt;/artifactId&gt;
    &lt;version&gt;4.9.0&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.mongodb&lt;/groupId&gt;
    &lt;artifactId&gt;bson&lt;/artifactId&gt;
    &lt;version&gt;4.9.0&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
    &lt;artifactId&gt;liquibase-mongodb&lt;/artifactId&gt;
    &lt;version&gt;<span class="mc-variable General.CurrentLiquibaseVersion variable">4.20.0</span>&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
## Test your connection
<ol>
<li value="1">Ensure your MongoDB database is configured. See <a href="https://www.mongodb.com/docs/manual/installation/">Install MongoDB</a> for more information.</li>
<li value="2">Specify the database URL in the <code><a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format:</li><pre xml:space="preserve">
<code class="language-html">url: mongodb://hostname:27017/myDatabase</code>
</pre>
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>If you are unsure about how to configure the <code>url</code> property, refer to <a href="https://docs.mongodb.com/manual/reference/connection-string/">Connection String URI Format</a>.</p>
<p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>To apply a <span class="mc-variable General.LBPro variable">Liquibase Pro</span> key to your project, add the following property to the Liquibase properties file: <code>licenseKey: &lt;paste code here&gt;</code></p>
<li value="3">Create a text file called <a href="https://docs.liquibase.com/concepts/changelogs/home.html">changelog</a> (<code>.xml</code>) in your project directory and add a <a href="https://docs.liquibase.com/concepts/changelogs/changeset.html">changeset</a>.<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>The use of JSON&#160;and YAML <span class="mc-variable General.changelog variable">changelog</span>s is available in  Liquibase version 4.20</p></li>

<h3>XML example</h3>

```bash
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
  xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:mongodb="http://www.liquibase.org/xml/ns/mongodb"
  xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
         http://www.liquibase.org/xml/ns/mongodb
         http://www.liquibase.org/xml/ns/mongodb/liquibase-mongodb-latest.xsd">

  <changeSet id="1" author="your_name" labels="createCollectionLabel" context="createCollectionContext">
    <comment>create_collection_comment</comment>
    <mongodb:createCollection collectionName="towns_xml"/>;
  </changeSet>
</databaseChangeLog>
```

<h3>YAML example</h3>

```bash
databaseChangeLog:
   - changeSet:
        id: 2
        author: your_name
        labels: createCollectionLabel
        context: createCollectionContext
        comment: create_collection_comment
        changes:
           - createCollection:
                collectionName: towns_yaml
```

<h3>JSON example</h3>

```bash
{
  "databaseChangeLog": [
    {
      "changeSet": {
        "id": "3",
        "author": "your_name",
        "labels": "test_label",
        "context": "test_context",
        "comment": "test_comment",
        "changes": [
          {
            "createCollection": {
              "collectionName": "countries_json"
            }
          }
        ]
      }
    }
  ]
}
```

<li value="4">Navigate to your project folder in the CLI and run the Liquibase&#160;<a href="https://docs.liquibase.com/commands/change-tracking/status.html" class="MCXref xref">status</a> command to see whether the connection is successful:</li>
```bash
liquibase status --username=test --password=test --changelog-file=<changelog.xml>
```
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>You can pass arguments in the CLI or keep them in the Liquibase properties file.</p>
<li value="5">Make changes to your database with the <a href="https://docs.liquibase.com/commands/update/update.html" class="MCXref xref">update</a> command.</li>
```bash
liquibase update --changelog-file=<changelog.xml>
```
<li value="6">From a database UI tool, ensure that your database contains <code>myCollection</code> along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html" class="MCXref xref">DATABASECHANGELOG table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html" class="MCXref xref">DATABASECHANGELOGLOCK table</a>.</li>
<p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>You can use <a href="https://www.mongodb.com/products/compass">MongoDB Compass</a> to easily view collections in your database. For example, run the commands <code>use myDatabase</code> and <code>db.myCollection.find()</code>.</p>
</ol>
<h3>MongoDB command examples</h3>
<ul>
    <li><a href="https://docs.mongodb.com/manual/reference/method/db.createCollection#db.createCollection">createCollection</a> creates a collection with the validator.</li>
</ul><pre xml:space="preserve"><code class="language-json">
&lt;changeSet id="1" author="liquibase"&gt;
    &lt;ext:createCollection collectionName="myCollection"&gt;
        &lt;ext:options&gt;
        {
          validator: {
            $jsonSchema: {
                bsonType: "object",
                required: ["name", "address"],
                properties: {
	                name: {
		    	          bsonType: "string",
		    	          description: "The Name"
                  },
                  address: {
			        bsonType: "string",
			        description: "The Address"
                  }
		            }
		          }
		        },
		      validationAction: "warn",
		      validationLevel: "strict"
		    }
        &lt;/ext:options&gt;
    &lt;/ext:createCollection&gt;
&lt;/changeSet&gt;
</code></pre>
<ul>
    <li><a href="https://docs.mongodb.com/manual/reference/method/db.collection.drop">dropCollection</a> removes a collection or view from the database.</li>
</ul><pre xml:space="preserve"><code class="language-json">
&lt;changeSet id="1" author="liquibase"&gt;
  &lt;ext:dropCollection collectionName="myCollection"/&gt;
&lt;/changeSet&gt;
</code></pre>
<ul>
    <li><a href="https://docs.mongodb.com/manual/reference/method/db.collection.createIndex#db.collection.createIndex">createIndex</a> creates an index for a collection.</li>
</ul><pre xml:space="preserve"><code class="language-json">
&lt;changeSet id="1" author="liquibase"&gt;
     &lt;ext:createIndex collectionName="createIndexTest"&gt;
          &lt;ext:keys&gt;
               { clientId: 1, type: 1}
	      &lt;/ext:keys&gt;
	      &lt;ext:options&gt;
	           {unique: true, name: "ui_tppClientId"}
          &lt;/ext:options&gt;
     &lt;/ext:createIndex&gt;
&lt;/changeSet&gt;
</code></pre>
<ul>
    <li><a href="https://docs.mongodb.com/manual/reference/method/db.collection.dropIndex#db.collection.dropIndex">dropIndex</a> drops an index for a collection by keys.</li>
</ul><pre xml:space="preserve"><code class="language-json">&lt;changeSet id="1" author="liquibase"&gt;
     &lt;ext:dropIndex collectionName="createIndexTest"&gt;
         &lt;ext:keys&gt;
             { clientId: 1, type: 1}
	     &lt;/ext:keys&gt;
	     &lt;ext:options&gt;
	         {unique: true, name: "ui_tppClientId"}
	     &lt;/ext:options&gt;
     &lt;/ext:dropIndex&gt;
&lt;/changeSet&gt;</code></pre>
<ul>
    <li><a href="https://docs.mongodb.com/manual/reference/method/db.collection.insertMany#db.collection.insertMany">insertMany</a> inserts multiple documents into a collection.</li>
</ul><pre xml:space="preserve"><code class="language-json">&lt;changeSet id="1" author="liquibase"&gt;
     &lt;ext:insertMany collectionName="insertManyTest1"&gt;
         &lt;ext:documents&gt;
	  	[
	  		{ id: 2 },
	  		{ id: 3,
	    	  	  address: { nr: 1, ap: 5}
	  		}
	  	]
         &lt;/ext:documents&gt;
  	 &lt;/ext:insertMany&gt;
&lt;/changeSet&gt;</code></pre>
<ul>
    <li><a href="https://docs.mongodb.com/manual/tutorial/insert-documents">insertOne</a> inserts a single document into a collection.</li>
</ul><pre xml:space="preserve"><code class="language-json">&lt;changeSet id="1" author="liquibase"&gt;
    &nbsp;&nbsp;&lt;ext:insertOne collectionName="insertOneTest1"&gt;
         &nbsp;&nbsp;&nbsp;&nbsp;&lt;ext:document&gt;
	  		&nbsp;&nbsp;&nbsp;&nbsp;{
	  		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;id: 111
	  		&nbsp;&nbsp;&nbsp;&nbsp;}
	  	&nbsp;&nbsp;&nbsp;&nbsp;&lt;/ext:document&gt;
    &nbsp;&nbsp;&lt;/ext:insertOne&gt;
&lt;/changeSet&gt;

&lt;changeSet id="2" author="liquibase"&gt;
    	&nbsp;&nbsp;&lt;ext:insertOne collectionName="insertOneTest2"&gt;
	        &nbsp;&nbsp;&nbsp;&nbsp;&lt;ext:document&gt;
        	&nbsp;&nbsp;&nbsp;&nbsp;{
        	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;id: 2
            	&nbsp;&nbsp;&nbsp;&nbsp;}
	     	&nbsp;&nbsp;&nbsp;&nbsp;&lt;/ext:document&gt;
    &nbsp;&nbsp;&lt;/ext:insertOne&gt;
    &lt;/changeSet&gt;
    </code></pre>
<ul>
    <li><a href="https://docs.mongodb.com/manual/reference/method/db.runCommand">runCommand</a> provides a helper to run specified database commands. This is the preferred method to issue database commands as it provides a consistent interface between the shell and drivers.</li>
</ul><pre xml:space="preserve"><code class="language-json">&lt;changeSet id="1" author="liquibase"&gt;
    &nbsp;&nbsp;&lt;ext:runCommand&gt;
        &nbsp;&nbsp;&nbsp;&nbsp;&lt;ext:command&gt;
	           &nbsp;&nbsp;&nbsp;&nbsp;{ buildInfo: 1 }
        &nbsp;&nbsp;&nbsp;&nbsp;&lt;/ext:command&gt;
    &nbsp;&nbsp;&lt;/ext:runCommand&gt;
&lt;/changeSet&gt;

&lt;changeSet id="2" author="liquibase"&gt;
    &nbsp;&nbsp;&lt;ext:adminCommand&gt;
        &nbsp;&nbsp;&nbsp;&nbsp;&lt;ext:command&gt;
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{ buildInfo: 1 }
        &nbsp;&nbsp;&nbsp;&nbsp;&lt;/ext:command&gt;
   &nbsp;&nbsp;&lt;/ext:adminCommand&gt;
&lt;/changeSet&gt;</code></pre>
<ul>
    <li><a href="https://docs.mongodb.com/manual/reference/method/db.adminCommand#db.adminCommand">adminCommand</a> provides a helper to run specified database commands against the admin database.</li>
</ul><pre xml:space="preserve"><code class="language-json">&lt;changeSet id="2" author="liquibase"&gt;
     &nbsp;&nbsp;&lt;ext:adminCommand&gt;
         &nbsp;&nbsp;&nbsp;&nbsp;&lt;ext:command&gt;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{ buildInfo: 1 }
         &nbsp;&nbsp;&nbsp;&nbsp;&lt;/ext:command&gt;
    &nbsp;&nbsp;&lt;/ext:adminCommand&gt;
&lt;/changeSet&gt;</code></pre>

## Related links
<ul>
    <li><a href="https://github.com/liquibase/liquibase-mongodb">Liquibase MongoDB Extension</a></li>
    <li><a href="https://github.com/liquibase/liquibase-mongodb/tree/main/src/test/resources/liquibase/ext">Liquibase MongoDB Extension Examples</a></li>
    <li><a href="https://docs.liquibase.com/commands/home.html" class="MCXref xref">Liquibase Commands</a></li>
    <li><a href="https://docs.liquibase.com/change-types/home.html" class="MCXref xref">Change Types</a></li>
</ul>
