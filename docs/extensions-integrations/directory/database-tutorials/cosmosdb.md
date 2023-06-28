---
title: CosmosDB
---
<h1>Using Liquibase with Cosmos DB</h1>
<p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>This database is supported <b>at or below the Contributed level</b>. Functionality may be limited. Databases at the Contributed level are not supported by the <span class="mc-variable General.CompanyName variable">Liquibase</span> support team. Best-effort support is provided through our community forums.<br /><br />For more information about the verification levels, see <a href="https://www.liquibase.com/supported-databases/verification-levels">Database Verification and Support</a>.<br /><br />If you have an update to these instructions, submit feedback so we can improve the page.</p>
<p><a href="https://azure.microsoft.com/en-us/services/cosmos-db/">Azure Cosmos DB</a> is a multi-model NoSQL&#160;database developed by Microsoft. For more information, see <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/">Azure Cosmos DB Documentation</a> and <a href="https://docs.microsoft.com/en-us/rest/api/cosmos-db/">Azure Cosmos DB: REST API Reference</a>.</p>
<h2>Reported versions</h2>
<ul>
    <li>4.35.1</li>
</ul>
<h2>Prerequisites</h2>
<ol>
    <li value="1"><a href="https://docs.liquibase.com/concepts/introduction-to-liquibase.html" class="MCXref xref">Introduction to Liquibase</a> – Dive into Liquibase concepts.</li>
    <li value="2"><a href="https://docs.liquibase.com/start/install/home.html" class="MCXref xref">Install Liquibase</a> – Download Liquibase on your machine.</li>
    <li value="3"><a href="https://docs.liquibase.com/start/home.html" class="MCXref xref">Get Started with Liquibase</a> – Learn how to use Liquibase with an example database.</li>
    <li value="4"><a href="https://docs.liquibase.com/commands/init/project.html" class="MCXref xref">init project</a> – Create a new Liquibase project folder to store all Liquibase files.</li>
    <li value="5"><a href="https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html" class="MCXref xref">How to Apply Your Liquibase Pro License Key</a> – If you use <span class="mc-variable General.LBPro variable">Liquibase Pro</span>, activate your license.</li>
</ol>
<p>To access Cosmos DB, do one of the following:</p>
<ul>
    <li>Install the <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator">Azure Cosmos DB&#160;Emulator</a> locally, and <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator-export-ssl-certificates">export the Azure Cosmos DB Emulator TLS/SSL certificate</a> for use with Liquibase</li>
    <li>Create an <a href="https://azure.microsoft.com/en-us/free/">Azure account with a subscription</a></li>
    <li>Use&#160;<a href="https://docs.microsoft.com/en-us/azure/cosmos-db/sql/create-sql-api-java?tabs=sync">Cosmos DB without an Azure subscription</a></li>
</ul>
<h2>Install drivers</h2>
<p>To use Liquibase and Cosmos DB, you need several JAR files:</p>
<a style="font-size: 18px;">List of JARs</a>
<ul>
    <li><a href="https://github.com/liquibase/liquibase-cosmosdb/releases">Liquibase extension for Cosmos DB</a>: <code>liquibase-cosmosdb-<span class="mc-variable General.CurrentLiquibaseVersion variable">4.20.0</span>.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/com.azure/azure-core">Azure Core</a>: <code>azure-core-1.37.0.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/com.azure/azure-cosmos">Azure Cosmos</a>: <code>azure-cosmos-4.41.0.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.dropwizard.metrics/metrics-core">Dropwizard Metrics</a>: <code>metrics-core-4.2.17.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations">Jackson Annotations</a>: <code>jackson-annotations-2.14.2.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core">Jackson Core</a>: <code>jackson-core-2.14.2.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind">Jackson Databind</a>: <code>jackson-databind-2.14.2.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310">Jackson Datatype</a>: <code>jackson-datatype-jsr310-2.14.2.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-afterburner">Jackson Afterburner</a>: <code>jackson-module-afterburner-2.14.2.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.micrometer/micrometer-core">Micrometer Core</a>: <code>micrometer-core-1.10.5.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.micrometer/micrometer-observation">Micrometer Observation</a>: <code>micrometer-observation-1.10.5.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-buffer">Netty Buffer</a>: <code>netty-buffer-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-codec">Netty Codec</a>: <code>netty-codec-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-codec-dns">Netty Codec DNS</a>: <code>netty-codec-dns-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-codec-http">Netty Codec HTTP</a>: <code>netty-codec-http-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-codec-http2">Netty Codec HTTP2</a>: <code>netty-codec-http2-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-codec-socks">Netty Codec Socks</a>: <code>netty-codec-socks-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-common">Netty Common</a>: <code>netty-common-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-handler">Netty Handler</a>: <code>netty-handler-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-handler-proxy">Netty Handler Proxy</a>: <code>netty-handler-proxy-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-resolver">Netty Resolver</a>: <code>netty-resolver-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-resolver-dns">Netty Resolver DNS</a>: <code>netty-resolver-dns-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-tcnative">Netty TC Native</a>: <code>netty-tcnative-2.0.59.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-tcnative-boringssl-static">Netty TC Native SSL</a>: <code>netty-tcnative-boringssl-static-2.0.59.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-tcnative-classes">Netty TC&#160;Classes</a>: <code>netty-tcnative-classes-2.0.59.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-transport">Netty Transport</a>: <code>netty-transport-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-transport-classes-epoll">Netty Transport Classes Epoll</a>: <code>netty-transport-classes-epoll-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-transport-classes-kqueue">Netty Transport Classes Kqueue</a>: <code>netty-transport-classes-kqueue-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-transport-native-epoll">Netty Transport Native Epoll:</a> <code>netty-transport-native-epoll-4.1.90.Final-linux-x86_64.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-transport-native-kqueue">Netty Transport Native Kqueue</a>: <code>netty-transport-native-kqueue-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.netty/netty-transport-native-unix-common">Netty Transport UNIX</a>: <code>netty-transport-native-unix-common-4.1.90.Final.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/org.reactivestreams/reactive-streams">Reactive Streams</a>: <code>reactive-streams-1.0.4.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.projectreactor/reactor-core">Reactor Core</a>: <code>reactor-core-3.5.4.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.projectreactor.netty/reactor-netty">Reactor Netty</a>: <code>reactor-netty-1.1.5.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.projectreactor.netty/reactor-netty-core">Reactor Netty Core</a>: <code>reactor-netty-core-1.1.5.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/io.projectreactor.netty/reactor-netty-http">Reactor Netty HTTP</a>: <code>reactor-netty-http-1.1.5.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/org.slf4j/slf4j-api">SLF4J</a>: <code>slf4j-api-2.0.6.jar</code></li>
    <li><a href="https://mvnrepository.com/artifact/org.slf4j/slf4j-simple">SLF4J Simple</a>: <code>slf4j-simple-2.0.6.jar</code></li>
</ul>

<p>You can use this BAT script to download the files quickly:</p>

<a style="font-size: 18px;">get_dependencies.bat</a>
<pre xml:space="preserve"><code class="language-shell">setlocal
SET LIQUIBASE_COSMOSDB="<span class="mc-variable General.CurrentLiquibaseVersion variable">4.20.0</span>"
SET AZURE_COSMOS_VERSION="4.41.0"
SET AZURE_CORE_VERSION="1.37.0"
SET JACKSON_CORE="2.14.2"
SET SLF4J_API="2.0.6"
SET NETTY_BUFFER="4.1.90"
SET REACTOR_CORE="3.5.3"
SET REACTIVE_STREAMS="1.0.4"
SET JACKSON_DATABIND="2.14.2"
SET JACKSON_ANNOTATIONS="2.14.2"
SET JACKSON_MODULE_AFTERBURNER="2.14.2"
SET REACTOR_NETTY="1.1.4"
SET REACTOR_NETTY_CORE="1.1.4"
SET NETTY_RESOLVER="4.1.90"
SET REACTOR_NETTY_HTTP="1.1.4"
SET NETTY_TRANSPORT="4.1.90"
SET NETTY_TCNATIVE_BORINGSSL_STATIC="2.0.59"
SET NETTY_RESOLVER_DNS="4.1.90"
SET NETTY_HANDLER_PROXY="4.1.90"
SET NETTY_HANDLER="4.1.90"
SET NETTY_COMMON="4.1.90"
SET NETTY_CODEC_SOCKS="4.1.90"
SET NETTY_CODEC_HTTP="4.1.90"
SET NETTY_CODEC_HTTP2="4.1.90"
SET NETTY_CODEC_DNS="4.1.90"
SET NETTY_CODEC="4.1.90"
SET MICROMETER_CORE="1.10.4"
SET METRICS_CORE="4.2.17"
SET LATENCYUTILS="2.0.3"
SET JACKSON_DATATYPE_JSR310="2.14.2"
SET NETTY_TCNATIVE_CLASSES="2.0.59"
SET NETTY_TRANSPORT_CLASSES_KQUEUE="4.1.90"
SET SLF4J_SIMPLE="2.0.6"
SET MICROMETER_OBSERVATION="1.10.5"
SET MICROMETER_COMMONS="1.10.5"
curl -L  https://repo1.maven.org/maven2/com/azure/azure-cosmos/%AZURE_COSMOS_VERSION%/azure-cosmos-%AZURE_COSMOS_VERSION%.jar --output azure-cosmos-%AZURE_COSMOS_VERSION%.jar
curl -L  https://repo1.maven.org/maven2/com/azure/azure-core/%AZURE_CORE_VERSION%/azure-core-%AZURE_CORE_VERSION%.jar --output  azure-core-%AZURE_CORE_VERSION%.jar
curl -L  https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/%JACKSON_CORE%/jackson-core-%JACKSON_CORE%.jar --output jackson-core-%JACKSON_CORE%.jar 
curl -L  https://repo1.maven.org/maven2/org/slf4j/slf4j-api/%SLF4J_API%/slf4j-api-%SLF4J_API%.jar --output slf4j-api-%SLF4J_API%.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-buffer/%NETTY_BUFFER%.Final/netty-buffer-%NETTY_BUFFER%.Final.jar --output netty-buffer-%NETTY_BUFFER%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/projectreactor/reactor-core/%REACTOR_CORE%/reactor-core-%REACTOR_CORE%.jar --output reactor-core-%REACTOR_CORE%.jar
curl -L  https://repo1.maven.org/maven2/io/projectreactor/netty/reactor-netty-core/%REACTOR_NETTY_CORE%/reactor-netty-core-%REACTOR_NETTY_CORE%.jar --output reactor-netty-core-%REACTOR_NETTY_CORE%.jar
curl -L  https://repo1.maven.org/maven2/io/projectreactor/netty/reactor-netty-http/%REACTOR_NETTY_HTTP%/reactor-netty-http-%REACTOR_NETTY_HTTP%.jar --output reactor-netty-http-%REACTOR_NETTY_HTTP%.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-resolver/%NETTY_RESOLVER%.Final/netty-resolver-%NETTY_RESOLVER%.Final.jar --output netty-resolver-%NETTY_RESOLVER%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-transport/%NETTY_TRANSPORT%.Final/netty-transport-%NETTY_TRANSPORT%.Final.jar --output netty-transport-%NETTY_TRANSPORT%.Final.jar
curl -L  https://repo1.maven.org/maven2/org/reactivestreams/reactive-streams/%REACTIVE_STREAMS%/reactive-streams-%REACTIVE_STREAMS%.jar --output reactive-streams-%REACTIVE_STREAMS%.jar
curl -L  https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/%JACKSON_DATABIND%/jackson-databind-%JACKSON_DATABIND%.jar --output jackson-databind-%JACKSON_DATABIND%.jar
curl -L  https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/%JACKSON_ANNOTATIONS%/jackson-annotations-%JACKSON_ANNOTATIONS%.jar --output jackson-annotations-%JACKSON_ANNOTATIONS%.jar
curl -L  https://repo1.maven.org/maven2/com/fasterxml/jackson/module/jackson-module-afterburner/%JACKSON_MODULE_AFTERBURNER%/jackson-module-afterburner-%JACKSON_MODULE_AFTERBURNER%.jar --output jackson-module-afterburner-%JACKSON_MODULE_AFTERBURNER%.jar
curl -L  https://repo1.maven.org/maven2/io/projectreactor/netty/reactor-netty/%REACTOR_NETTY%/reactor-netty-%REACTOR_NETTY%.jar --output reactor-netty-%REACTOR_NETTY%.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-resolver/%NETTY_RESOLVER%.Final/netty-resolver-%NETTY_RESOLVER%.Final.jar --output netty-resolver-%NETTY_RESOLVER%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-transport-native-unix-common/%NETTY_TRANSPORT%.Final/netty-transport-native-unix-common-%NETTY_TRANSPORT%.Final.jar --output netty-transport-native-unix-common-%NETTY_TRANSPORT%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-transport-native-epoll/%NETTY_TRANSPORT%.Final/netty-transport-native-epoll-%NETTY_TRANSPORT%.Final-linux-x86_64.jar --output netty-transport-native-epoll-%NETTY_TRANSPORT%.Final-linux-x86_64.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-tcnative-boringssl-static/%NETTY_TCNATIVE_BORINGSSL_STATIC%.Final/netty-tcnative-boringssl-static-%NETTY_TCNATIVE_BORINGSSL_STATIC%.Final.jar --output netty-tcnative-boringssl-static-%NETTY_TCNATIVE_BORINGSSL_STATIC%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-resolver-dns/%NETTY_RESOLVER_DNS%.Final/netty-resolver-dns-%NETTY_RESOLVER_DNS%.Final.jar --output netty-resolver-dns-%NETTY_RESOLVER_DNS%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-handler-proxy/%NETTY_HANDLER_PROXY%.Final/netty-handler-proxy-%NETTY_HANDLER_PROXY%.Final.jar --output netty-handler-proxy-%NETTY_HANDLER_PROXY%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-handler/%NETTY_HANDLER%.Final/netty-handler-%NETTY_HANDLER%.Final.jar --output netty-handler-%NETTY_HANDLER%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-common/%NETTY_COMMON%.Final/netty-common-%NETTY_COMMON%.Final.jar --output netty-common-%NETTY_COMMON%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-codec-socks/%NETTY_CODEC_SOCKS%.Final/netty-codec-socks-%NETTY_CODEC_SOCKS%.Final.jar --output netty-codec-socks-%NETTY_CODEC_SOCKS%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-codec-http/%NETTY_CODEC_HTTP%.Final/netty-codec-http-%NETTY_CODEC_HTTP%.Final.jar --output netty-codec-http-%NETTY_CODEC_HTTP%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-codec-http2/%NETTY_CODEC_HTTP2%.Final/netty-codec-http2-%NETTY_CODEC_HTTP2%.Final.jar --output netty-codec-http2-%NETTY_CODEC_HTTP2%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-codec-dns/%NETTY_CODEC_DNS%.Final/netty-codec-dns-%NETTY_CODEC_DNS%.Final.jar --output netty-codec-dns-%NETTY_CODEC_DNS%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-codec/%NETTY_CODEC%.Final/netty-codec-%NETTY_CODEC%.Final.jar --output netty-codec-%NETTY_CODEC%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/micrometer/micrometer-core/%MICROMETER_CORE%/micrometer-core-%MICROMETER_CORE%.jar --output micrometer-core-%MICROMETER_CORE%.jar
curl -L  https://repo1.maven.org/maven2/io/dropwizard/metrics/metrics-core/%METRICS_CORE%/metrics-core-%METRICS_CORE%.jar --output metrics-core-%METRICS_CORE%.jar
curl -L  https://repo1.maven.org/maven2/org/latencyutils/LatencyUtils/%LATENCYUTILS%/LatencyUtils-%LATENCYUTILS%.jar --output LatencyUtils-%LATENCYUTILS%.jar
curl -L  https://repo1.maven.org/maven2/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/%JACKSON_DATATYPE_JSR310%/jackson-datatype-jsr310-%JACKSON_DATATYPE_JSR310%.jar --output jackson-datatype-jsr310-%JACKSON_DATATYPE_JSR310%.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-tcnative-classes/%NETTY_TCNATIVE_CLASSES%.Final/netty-tcnative-classes-%NETTY_TCNATIVE_CLASSES%.Final.jar --output netty-tcnative-classes-%NETTY_TCNATIVE_CLASSES%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-transport-classes-kqueue/%NETTY_TRANSPORT_CLASSES_KQUEUE%.Final/netty-transport-classes-kqueue-%NETTY_TRANSPORT_CLASSES_KQUEUE%.Final.jar --output netty-transport-classes-kqueue-%NETTY_TRANSPORT_CLASSES_KQUEUE%.Final.jar
curl -L  https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/%SLF4J_SIMPLE%/slf4j-simple-%SLF4J_SIMPLE%.jar --output slf4j-simple-%SLF4J_SIMPLE%.jar
curl -L  https://github.com/liquibase/liquibase-cosmosdb/releases/download/liquibase-cosmosdb-%LIQUIBASE_COSMOSDB%/liquibase-cosmosdb-%LIQUIBASE_COSMOSDB%.jar --output liquibase-cosmosdb-%LIQUIBASE_COSMOSDB%.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-transport-classes-epoll/%NETTY_TRANSPORT%.Final/netty-transport-classes-epoll-%NETTY_TRANSPORT%.Final.jar --output netty-transport-classes-epoll-%NETTY_TRANSPORT%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/netty/netty-transport-native-kqueue/%NETTY_TRANSPORT%.Final/netty-transport-native-kqueue-%NETTY_TRANSPORT%.Final.jar --output netty-transport-native-kqueue-%NETTY_TRANSPORT%.Final.jar
curl -L  https://repo1.maven.org/maven2/io/micrometer/micrometer-observation/%MICROMETER_OBSERVATION%/micrometer-observation-%MICROMETER_OBSERVATION%.jar --output micrometer-observation-%MICROMETER_OBSERVATION%.jar
curl -L  https://repo1.maven.org/maven2/io/micrometer/micrometer-commons/%MICROMETER_COMMONS%/micrometer-commons-%MICROMETER_COMMONS%.jar --output micrometer-commons-%MICROMETER_COMMONS%.jar
endlocal</code></pre>

<p> <a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">Place your JAR file(s)</a> in the <code>liquibase/lib</code> directory.</p>
<p>If you use Maven, you must <a href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR&#160;as a dependency</a> in your <code>pom.xml</code> file.</p>
<a style="font-size: 18px;"><code>pom.xml</code> file</a>
<pre xml:space="preserve"><code class="language-text">&lt;dependency&gt;
    &lt;groupId&gt;org.liquibase.ext&lt;/groupId&gt;
    &lt;artifactId&gt;liquibase-cosmosdb&lt;/artifactId&gt;
    &lt;version&gt;<span class="mc-variable General.CurrentLiquibaseVersion variable">4.20.0</span>&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;com.azure&lt;/groupId&gt;
    &lt;artifactId&gt;azure-core&lt;/artifactId&gt;
    &lt;version&gt;1.37.0&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;com.azure&lt;/groupId&gt;
    &lt;artifactId&gt;azure-cosmos&lt;/artifactId&gt;
    &lt;version&gt;4.41.0&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.dropwizard.metrics&lt;/groupId&gt;
    &lt;artifactId&gt;metrics-core&lt;/artifactId&gt;
    &lt;version&gt;4.2.17&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;com.fasterxml.jackson.core&lt;/groupId&gt;
    &lt;artifactId&gt;jackson-annotations&lt;/artifactId&gt;
    &lt;version&gt;2.14.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;com.fasterxml.jackson.core&lt;/groupId&gt;
    &lt;artifactId&gt;jackson-core&lt;/artifactId&gt;
    &lt;version&gt;2.14.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;com.fasterxml.jackson.core&lt;/groupId&gt;
    &lt;artifactId&gt;jackson-databind&lt;/artifactId&gt;
    &lt;version&gt;2.14.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;com.fasterxml.jackson.datatype&lt;/groupId&gt;
    &lt;artifactId&gt;jackson-datatype-jsr310&lt;/artifactId&gt;
    &lt;version&gt;2.14.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;com.fasterxml.jackson.module&lt;/groupId&gt;
    &lt;artifactId&gt;jackson-module-afterburner&lt;/artifactId&gt;
    &lt;version&gt;2.14.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.micrometer&lt;/groupId&gt;
    &lt;artifactId&gt;micrometer-core&lt;/artifactId&gt;
    &lt;version&gt;1.10.5&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.micrometer&lt;/groupId&gt;
    &lt;artifactId&gt;micrometer-observation&lt;/artifactId&gt;
    &lt;version&gt;1.10.5&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-buffer&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-codec&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-codec-dns&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-codec-http&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-codec-http2&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-codec-socks&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-common&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-handler&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-handler-proxy&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-resolver&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-resolver-dns&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-tcnative&lt;/artifactId&gt;
    &lt;version&gt;2.0.59.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-tcnative-boringssl-static&lt;/artifactId&gt;
    &lt;version&gt;2.0.59.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-transport&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-transport-classes-epoll&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-transport-classes-kqueue&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-transport-native-epoll&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-transport-native-kqueue&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.netty&lt;/groupId&gt;
    &lt;artifactId&gt;netty-transport-native-unix-common&lt;/artifactId&gt;
    &lt;version&gt;4.1.90.Final&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.reactivestreams&lt;/groupId&gt;
    &lt;artifactId&gt;reactive-streams&lt;/artifactId&gt;
    &lt;version&gt;1.0.4&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.projectreactor&lt;/groupId&gt;
    &lt;artifactId&gt;reactor-core&lt;/artifactId&gt;
    &lt;version&gt;3.5.4&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.projectreactor.netty&lt;/groupId&gt;
    &lt;artifactId&gt;reactor-netty&lt;/artifactId&gt;
    &lt;version&gt;1.1.5&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.projectreactor.netty&lt;/groupId&gt;
    &lt;artifactId&gt;reactor-netty-core&lt;/artifactId&gt;
    &lt;version&gt;1.1.5&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;io.projectreactor.netty&lt;/groupId&gt;
    &lt;artifactId&gt;reactor-netty-http&lt;/artifactId&gt;
    &lt;version&gt;1.1.5&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.slf4j&lt;/groupId&gt;
    &lt;artifactId&gt;slf4j-api&lt;/artifactId&gt;
    &lt;version&gt;2.0.6&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.slf4j&lt;/groupId&gt;
    &lt;artifactId&gt;slf4j-simple&lt;/artifactId&gt;
    &lt;version&gt;2.0.6&lt;/version&gt;
&lt;/dependency&gt;</code></pre>

<h2 id="test-your-connection">Test your connection</h2>
<ol>
    <li value="1">Ensure your Cosmos&#160;DB&#160;database is configured. See <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/sql/create-sql-api-java">Quickstart: Build a Java app to manage Azure Cosmos DB SQL API data</a> for more information.</li>
    <li value="2">
        <p>Specify the database URL in the <code><a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html"><span class="mc-variable General.liquiPropFile variable">liquibase.properties</span></a></code> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format:</p>
    </li><pre xml:space="preserve"><code class="language-text">url: jdbc:cosmosdb://&lt;host&gt;:&lt;accountKey&gt;@&lt;host&gt;:&lt;port&gt;/&lt;databaseName&gt;?&lt;Query Parameters&gt;</code></pre>
    <table>
        <thead>
            <tr>
                <th>Type</th>
                <th>Example</th>
                <th>Notes</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Azure Cosmos DB Emulator</td>
                <td><pre xml:space="preserve"><code class="language-text">jdbc:cosmosdb://
localhost:C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU
5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==
@localhost:8080/testdb1</code></pre>
                </td>
                <td>This example shows the default account key, formatted for readability. For more information, see <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator">Install and use the Azure Cosmos DB Emulator for local development and testing</a>.</td>
            </tr>
            <tr>
                <td>Azure Cosmos DB</td>
                <td><pre xml:space="preserve"><code class="language-text">jdbc:cosmosdb://
AccountEndpoint=https://&lt;cosmosdb-account-name&gt;.documents.azure.com:443;
AccountKey=&lt;accountKey&gt;;</code></pre>
                </td>
                <td>Replace <code>&lt;cosmosdb-account-name&gt;</code>&#160;with the name you chose for your Azure Cosmos DB. Replace <code>&lt;accountKey&gt;</code> with your private account key. For more information, see <a href="https://docs.microsoft.com/en-us/azure/private-link/tutorial-private-endpoint-cosmosdb-portal">Tutorial: Connect to an Azure Cosmos account using an Azure Private Endpoint</a>.</td>
            </tr>
        </tbody>
    </table>
    <p>For more information, see <a href="https://github.com/liquibase/liquibase-cosmosdb#adjust-connection-string">GitHub:&#160;liquibase-cosmosdb § Adjust connection string</a>.</p>
    <p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>To apply a <span class="mc-variable General.LBPro variable">Liquibase Pro</span> key to your project, add the following property to the Liquibase properties file: <code>licenseKey: &lt;paste code here&gt;</code></p>
</ol>
<ol start="3">
    <li value="3">Create a text file called <a href="https://docs.liquibase.com/concepts/changelogs/home.html">changelog</a> (<code>.xml</code>, <code>.sql</code>, <code>.json</code>, or <code>.yaml</code>) in your project directory and add a <a href="https://docs.liquibase.com/concepts/changelogs/changeset.html">changeset</a>.</li>
    <p>If you already created a <span class="mc-variable General.changelog variable">changelog</span> using the <code><a href="https://docs.liquibase.com/commands/init/project.html" class="MCXref xref">init project</a></code> command, you can use that instead of creating a new file. When adding onto an existing <span class="mc-variable General.changelog variable">changelog</span>, be sure to only add the <span class="mc-variable General.changeset variable">changeset</span> and to not duplicate the <span class="mc-variable General.changelog variable">changelog</span> header.</p>
    <a style="font-size: 18pt;">XML example</a>
        <code class="language-xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
<code>&lt;databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:pro="http://www.liquibase.org/xml/ns/pro"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
        http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd"&gt;</code>

    &lt;changeSet id="1" author="Liquibase"&gt;
        &lt;createTable tableName="test_table"&gt;
            &lt;column name="test_id" type="int"&gt;
                &lt;constraints primaryKey="true"/&gt;
            &lt;/column&gt;
            &lt;column name="test_column" type="varchar"/&gt;
        &lt;/createTable&gt;
    &lt;/changeSet&gt;

&lt;/databaseChangeLog&gt;</code></pre>

<a style="font-size: 18pt;">SQL example</a>
<pre xml:space="preserve"><code class="language-sql">-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table (test_id INT, test_column VARCHAR(255), PRIMARY KEY (test_id))</code></pre>
                                                    <p class="tip" data-mc-autonum="&lt;b&gt;Tip: &lt;/b&gt;"><span class="autonumber"><span><b>Tip: </b></span></span>Formatted SQL <span class="mc-variable General.changelog variable">changelog</span>s generated from Liquibase versions before 4.2 might cause issues because of the lack of space after a double dash ( <code>--</code> ). To fix this, add a space after the double dash. For example: <code>--&#160;liquibase formatted sql</code> instead of <code>--liquibase formatted sql</code> and <code>--&#160;changeset myname:create-table</code> instead of <code>--changeset myname:create-table</code>.</p>

<a style="font-size: 18pt;">YAML example</a>
<pre xml:space="preserve"><code class="language-yaml">databaseChangeLog:
   - changeSet:
       id: 1
       author: Liquibase
       changes:
       - createTable:
           tableName: test_table
           columns:
           - column:
               name: test_column
               type: INT
               constraints:
                   primaryKey:  true
                   nullable:  false</code></pre>

<a style="font-size: 18pt;">JSON example</a>
<pre><code class="language-json">{
  "databaseChangeLog": [
    {
      "changeSet": {
        "id": "1",
        "author": "Liquibase",
        "changes": [
          {
            "createTable": {
              "tableName": "test_table",
              "columns": [
                {
                  "column": {
                    "name": "test_column",
                    "type": "INT",
                    "constraints": {
                      "primaryKey": true,
                      "nullable": false
                    }
                  }
                }
              ]
            }
          }
        ]
      }
    }
  ]
}</code></pre>

    <li value="4">Navigate to your project folder in the CLI and run the Liquibase&#160;<a href="https://docs.liquibase.com/commands/change-tracking/status.html" class="MCXref xref">status</a> command to see whether the connection is successful:</li><pre xml:space="preserve"><code class="language-text">liquibase status --username=test --password=test --changelog-file=&lt;changelog.xml&gt;</code></pre>
    <p class="note" data-mc-autonum="&lt;b&gt;Note: &lt;/b&gt;"><span class="autonumber"><span><b>Note: </b></span></span>You can specify arguments in the CLI or keep them in the <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html">Liquibase properties file</a>.</p>
    <p>If your connection is successful, you'll see a message like this:</p><pre xml:space="preserve"><code class="language-text">4 changesets have not been applied to &lt;your_jdbc_url&gt;
Liquibase command 'status' was executed successfully.</code></pre>
    <li value="5">Inspect the SQL with  the <a href="https://docs.liquibase.com/commands/update/update-sql.html" class="MCXref xref">update-sql</a> command. Then make changes to your database with the <a href="https://docs.liquibase.com/commands/update/update.html" class="MCXref xref">update</a> command.</li><pre xml:space="preserve"><code class="language-text">liquibase update-sql --changelog-file=&lt;changelog.xml&gt;
liquibase update --changelog-file=&lt;changelog.xml&gt;</code></pre>
    <p>If your <code>update</code> is successful, Liquibase runs each <span class="mc-variable General.changeset variable">changeset</span> and displays a summary message ending with:</p><pre xml:space="preserve"><code class="language-text">Liquibase: Update has been successful.
Liquibase command 'update' was executed successfully.</code></pre>
    <li value="6">From a database UI tool, ensure that your database contains the <code>test_table</code> you added along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html" class="MCXref xref">DATABASECHANGELOG table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html" class="MCXref xref">DATABASECHANGELOGLOCK table</a>.</li>
</ol>
<p>Now you're ready to start making deployments with Liquibase!</p>
<h2>Related links</h2>
<ul>
    <li><a href="https://docs.liquibase.com/change-types/home.html" class="MCXref xref">Change Types</a>
    </li>
    <li><a href="https://docs.liquibase.com/commands/home.html" class="MCXref xref">Liquibase Commands</a>
    </li>
    <li><a href="https://docs.microsoft.com/en-us/azure/cosmos-db/">Azure Cosmos DB&#160;Documentation</a>
    </li>
    <li><a href="https://docs.microsoft.com/en-us/rest/api/cosmos-db/">Azure Cosmos DB: REST API Reference</a>
    </li>
    <li><a href="https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator">Install and use the Azure Cosmos DB Emulator for local development and testing</a>
    </li>
    <li><a href="https://docs.microsoft.com/en-us/azure/cosmos-db/sql/create-sql-api-java">Quickstart: Build a Java app to manage Azure Cosmos DB SQL API data</a>
    </li>
    <li><a href="https://docs.microsoft.com/en-us/azure/cosmos-db/sql/sql-api-sdk-java-v4">Azure Cosmos DB Java SDK v4 for Core (SQL) API: release notes and resources</a>
    </li>
    <li><a href="https://docs.microsoft.com/en-us/rest/api/cosmos-db/cosmosdb-resource-uri-syntax-for-rest">Azure Cosmos DB Resource URI Syntax for REST</a>
    </li>
    <li><a href="https://docs.microsoft.com/en-us/azure/private-link/tutorial-private-endpoint-cosmosdb-portal">Tutorial: Connect to an Azure Cosmos account using an Azure Private Endpoint</a>
    </li>
</ul>
