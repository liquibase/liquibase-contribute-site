<h1>Using Liquibase with Hibernate</h1>
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>This database is supported <b>at or below the Contributed level</b>. Functionality may be limited. Databases at the Contributed level are not supported by the <span class="mc-variable General.CompanyName variable">Liquibase</span> support team. Best-effort support is provided through our community forums.<br /><br />For more information about the verification levels, see <a href="https://www.liquibase.com/supported-databases/verification-levels">Database Verification and Support</a>.<br /><br />If you have an update to these instructions, submit feedback so we can improve the page.</p>
<p><a href="https://hibernate.org/orm/documentation/5.5/hibernate.md">Hibernate</a> is an object-relational mapping (ORM) tool that can be used alongside Liquibase to provide a persistent framework for a relational database.</p>
<p>The purpose of this document is to guide you through the process of creating a new Liquibase project and integrating it into your Hibernate JPA setup. </p>
<h2>Reported versions</h2>
<ul>
	<li><b>6.x:</b> liquibase-hibernate6</li>
	<li><b>5.x:</b> liquibase-hibernate5</li>
</ul>
<h2>Prerequisites</h2>
<ol>
	<li value="1"><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.html" class="MCXref xref">Introduction to Liquibase</a> – Dive into Liquibase concepts.</li>
	<li value="2"><a href="https://docs.liquibase.com/start/install/home.html" class="MCXref xref">Install Liquibase</a> – Download Liquibase on your machine.</li>
	<li value="3"><a href="https://docs.liquibase.com/start/home.html" class="MCXref xref">Get Started with Liquibase</a> – Learn how to use Liquibase with an example database.</li>
	<li value="4"><a href="https://docs.liquibase.com/start/design-liquibase-project.html" class="MCXref xref">Design Your Liquibase Project</a> – Create a new <span class="mc-variable General.Liquibase variable">Liquibase</span> project folder and organize your changelogs</li>
	<li value="5"><a href="https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html" class="MCXref xref">How to Apply Your Liquibase Pro License Key</a> – If you use <span class="mc-variable General.LBPro variable">Liquibase Pro</span>, activate your license.</li>
</ol>
<ul>
	<li>Download and install <a href="https://maven.apache.org/download.cgi">Maven</a>.</li>
</ul>
<h2 id="create">Create a new Liquibase project with Hibernate</h2>
<p>We will be creating a Maven project for this tutorial. To configure a Liquibase project for Hibernate, perform the following steps:</p>
<ol>
	<li style="font-style: normal;" value="1">Specify the database URL in the <code><a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format:</li><pre><code class="language-text">url=hibernate:ejb3:com.liquibase.hibernate.tutorial.jpa</code></pre>
	<p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>To apply a <span class="mc-variable General.LBPro variable">Liquibase Pro</span> key to your project, add the following property to the Liquibase properties file: <code>licenseKey: &lt;paste code here&gt;</code></p>
	<li value="2">Create a <code>pom.xml</code> file in your project directory and add the following content to the file:</li>
	<a href="#"><code>pom.xml</code></a>
		<pre><code class="language-xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"&gt;
	&lt;modelVersion&gt;4.0.0&lt;/modelVersion&gt;

	&lt;groupId&gt;com.liquibase&lt;/groupId&gt;
	&lt;artifactId&gt;hibernate-liquibase&lt;/artifactId&gt;
	&lt;version&gt;0.0.1-SNAPSHOT&lt;/version&gt;
	&lt;packaging&gt;jar&lt;/packaging&gt;

	&lt;name&gt;Liquibase Hibernate Example&lt;/name&gt;
	&lt;description&gt;Demo project for liquibase and hibernate&lt;/description&gt;
					
	&lt;parent&gt;
        &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
        &lt;artifactId&gt;spring-boot-starter-parent&lt;/artifactId&gt;
        &lt;version&gt;3.0.0&lt;/version&gt;
        &lt;relativePath/&gt; &lt;!-- lookup parent from repository --&gt;
    &lt;/parent&gt;

    &lt;properties&gt;
        &lt;liquibase.version&gt;4.18.0&lt;/liquibase.version&gt;
        &lt;liquibase-hibernate6.version&gt;4.18.0&lt;/liquibase-hibernate6.version&gt;
        &lt;hibernate6.version&gt;6.1.5.Final&lt;/hibernate6.version&gt;
        &lt;javassist.version&gt;3.24.0-GA&lt;/javassist.version&gt;
        &lt;validation-api.version&gt;3.0.2&lt;/validation-api.version&gt;
        &lt;maven.compiler.source&gt;11&lt;/maven.compiler.source&gt;
        &lt;maven.compiler.target&gt;11&lt;/maven.compiler.target&gt;
        &lt;maven.build.timestamp.format&gt;yyyyMMddHHmmss&lt;/maven.build.timestamp.format&gt;
        &lt;project.build.sourceEncoding&gt;UTF-8&lt;/project.build.sourceEncoding&gt;
    &lt;/properties&gt;

    &lt;dependencies&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;org.hibernate.orm&lt;/groupId&gt;
            &lt;artifactId&gt;hibernate-core&lt;/artifactId&gt;
            &lt;version&gt;${hibernate6.version}&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;com.h2database&lt;/groupId&gt;
            &lt;artifactId&gt;h2&lt;/artifactId&gt;
        &lt;/dependency&gt;
    &lt;/dependencies&gt;

    &lt;build&gt;
        &lt;plugins&gt;
            &lt;plugin&gt;
                &lt;groupId&gt;org.codehaus.mojo&lt;/groupId&gt;
                &lt;artifactId&gt;exec-maven-plugin&lt;/artifactId&gt;
                &lt;version&gt;3.0.0&lt;/version&gt;
                &lt;configuration&gt;
                    &lt;executable&gt;java&lt;/executable&gt;
                    &lt;arguments&gt;
                        &lt;argument&gt;-classpath&lt;/argument&gt;
                        &lt;classpath/&gt;
                        &lt;argument&gt;com.liquibase.Application&lt;/argument&gt;
                    &lt;/arguments&gt;
                &lt;/configuration&gt;
            &lt;/plugin&gt;
            &lt;plugin&gt;
                &lt;groupId&gt;org.liquibase&lt;/groupId&gt;
                &lt;artifactId&gt;liquibase-maven-plugin&lt;/artifactId&gt;
                &lt;version&gt;${liquibase.version}&lt;/version&gt;
                &lt;configuration&gt;
                    &lt;changeLogFile&gt;master.xml&lt;/changeLogFile&gt;
                    &lt;outputChangeLogFile&gt;dbchangelog.xml&lt;/outputChangeLogFile&gt;
                    &lt;diffChangeLogFile&gt;
                        ${project.basedir}/src/main/resources/db/migrations/${maven.build.timestamp}_changelog.xml
                    &lt;/diffChangeLogFile&gt;
                    &lt;propertyFile&gt;src/main/resources/spring.properties&lt;/propertyFile&gt;
                    &lt;logging&gt;debug&lt;/logging&gt;
                &lt;/configuration&gt;
                &lt;dependencies&gt;
                    &lt;dependency&gt;
                        &lt;groupId&gt;org.liquibase&lt;/groupId&gt;
                        &lt;artifactId&gt;liquibase-core&lt;/artifactId&gt;
                        &lt;version&gt;${liquibase.version}&lt;/version&gt;
                    &lt;/dependency&gt;
                    &lt;dependency&gt;
                        &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
                        &lt;artifactId&gt;liquibase-hibernate6&lt;/artifactId&gt;
                        &lt;version&gt;${liquibase-hibernate6.version}&lt;/version&gt;
                    &lt;/dependency&gt;
                    &lt;dependency&gt;
                        &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
                        &lt;artifactId&gt;spring-boot-starter-data-jpa&lt;/artifactId&gt;
                        &lt;version&gt;3.0.0&lt;/version&gt;
                    &lt;/dependency&gt;
                    &lt;dependency&gt;
                        &lt;groupId&gt;jakarta.validation&lt;/groupId&gt;
                        &lt;artifactId&gt;jakarta.validation-api&lt;/artifactId&gt;
                        &lt;version&gt;${validation-api.version}&lt;/version&gt;
                    &lt;/dependency&gt;
                    &lt;dependency&gt;
                        &lt;groupId&gt;org.javassist&lt;/groupId&gt;
                        &lt;artifactId&gt;javassist&lt;/artifactId&gt;
                        &lt;version&gt;${javassist.version}&lt;/version&gt;
                    &lt;/dependency&gt;
                &lt;/dependencies&gt;
            &lt;/plugin&gt;
        &lt;/plugins&gt;
    &lt;/build&gt;
&lt;/project&gt;</code></pre>

<li value="3">Create a JPA configuration file at <code>META-INF/persistence.xml</code>. The <code>persistence.xml</code> file should contain the following content:</li>
<a href="#"><code>persistence.xml</code></a>
	<pre><code class="language-xml">&lt;persistence xmlns="http://java.sun.com/xml/ns/persistence"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd" version="2.0"&gt;
	&lt;persistence-unit name="com.liquibase.hibernate.tutorial.jpa" transaction-type="RESOURCE_LOCAL"&gt;
		&lt;properties&gt;
			&lt;property name="jakarta.persistence.schema-generation.database.action" value="none" /&gt;
			&lt;property name="jakarta.persistence.provider" value="org.hibernate.jpa.HibernatePersistenceProvider" /&gt;
			&lt;property name="jakarta.persistence.jdbc.driver" value="org.h2.Driver" /&gt;
			&lt;property name="jakarta.persistence.jdbc.url" value="jdbc:h2:file:~/test" /&gt;
			&lt;property name="jakarta.persistence.jdbc.user" value="dbuser" /&gt;
			&lt;property name="jakarta.persistence.jdbc.password" value="letmein" /&gt;
			&lt;property name="hibernate.connection.handling_mode" value="delayed_acquisition_and_release_after_transaction" /&gt;
		&lt;/properties&gt;
	&lt;/persistence-unit&gt;
&lt;/persistence&gt;</code></pre>

<li value="4">Create the folder <code>src/main/java/com/liquibase</code>, which will be used for entity classes. In this directory, create a file <code>House.java</code> in a text editor and add the following content:</li>
<a href="#"><code>House.java</code></a>
	<pre><code class="language-java">package com.liquibase;
	
	import java.io.Serializable;
	import jakarta.persistence.*;
	@Entity
	public class House implements Serializable {
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue
		private Integer id;
		private String owner;
		private boolean fullyPaid;
	
		public Integer getId() {
			return id;
		}
	
		public void setId(Integer id) {
			this.id = id;
		}
	
		public String getOwner() {
			return owner;
		}
	
		public void setOwner(String owner) {
			this.owner = owner;
		}
	
		public boolean isFullyPaid() {
			return fullyPaid;
		}
	
		public void setFullyPaid(boolean fullyPaid) {
			this.fullyPaid = fullyPaid;
		}
	
	}</code></pre>
<li value="5">Create a second file <code>Item.java</code> in the same directory and paste the following:</li>
<a href="#"><code>Item.java</code></a>
	<pre><code class="language-java">package com.liquibase;
    
    import java.io.Serializable;
    import jakarta.persistence.*;
    @Entity
    public class Item implements Serializable {
    	private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue
        private Integer id;
        private String name;
        @ManyToOne
        private House house;

        public Integer getId() {
        	return id;
        }

        public void setId(Integer id) {
        	this.id = id;
        }

        public String getName() {
        	return name;
        }

        public void setName(String name) {
        	this.name = name;
        }

		public House getHouse() {
			return house;
		}

		public void setHouse(House house) {
			this.house = house;
		}

	}</code></pre>
	<li value="6">Install the application using the <code>mvnw install</code> command, or <code>mvnw.cmd install</code> for Windows.</li>
	<li value="7">The generated JAR is what is referenced in the <code><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></code> file: <code class="language-text">classpath=target\\hibernate-liquibase-0.0.1-SNAPSHOT.jar</code></li>
	<li value="8">Next, generate a <code>dbchangelog.xml</code> file from Hibernate in your project folder:</li><pre xml:space="preserve"><code class="language-text">mvn liquibase:diff</code></pre>
	<li value="9">A file will be generated at <code>src/main/resources/db/migrations/20221208174852_changelog.xml</code>.
You can move and rename it to <code>src/main/resources/dbchangelog.xml</code>, then run <code>mvn install</code> again. </li>
	<li value="10">Verify the project configuration by running the Liquibase <code>status</code> command. Open a command prompt and go to the project folder. Run the following command:</li><pre xml:space="preserve"><code class="language-text">mvn liquibase:status</code></pre><pre xml:space="preserve"><code class="language-text">Example output:</code></pre><pre xml:space="preserve"><code class="language-text">5 changesets have not been applied to DBUSER@jdbc:h2:file:~/test</code></pre>
	<li value="11">Now apply the changeset using the command</li><pre xml:space="preserve"><code class="language-text">mvn liquibase:update</code></pre>
	<li value="12">From a database UI tool, ensure that your database contains the table you added along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html" class="MCXref xref">DATABASECHANGELOG table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html" class="MCXref xref">DATABASECHANGELOGLOCK table</a>.</li>
	<li value="13">You can run the application using command <code>mvn exec:exec</code> and it shall work successfully.</li>
</ol>
<h2>Finished</h2>
<p>You have successfully configured your project and can begin creating <span class="mc-variable General.changeset variable">changeset</span>s to migrate changes to your database using Hibernate.</p>
<p>Source code is available at: <a href="https://github.com/juliuskrah/hibernate-liquibase">https://github.com/juliuskrah/hibernate-liquibase</a></p>
<h2>Related links</h2>
<ul>
	<li><a href="https://hibernate.org/orm/documentation/hibernate.md">Hibernate documentation</a>
	</li>
	<li><a href="https://docwiki.embarcadero.com/InterBase/2020/en/Main_Page">InterBase documentation</a>
	</li>
	<li><a href="https://docs.liquibase.com/concepts/home.html" class="MCXref xref">Concepts</a>
	</li>
	<li><a href="https://docs.liquibase.com/commands/home.html" class="MCXref xref">Liquibase Commands</a>
	</li>
</ul>
