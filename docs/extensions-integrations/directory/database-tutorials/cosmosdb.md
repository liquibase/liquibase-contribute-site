---
title: CosmosDB
---
# Using Liquibase with Cosmos DB

<a href="https://azure.microsoft.com/en-us/services/cosmos-db/">Azure Cosmos DB</a> is a multi-model NoSQL database developed by Microsoft. For more information, see <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/">Azure Cosmos DB Documentation</a> and <a href="https://docs.microsoft.com/en-us/rest/api/cosmos-db/">Azure Cosmos DB: REST API Reference</a>.

!!! note
    This database is supported <b>at or below the Contributed level</b>. Functionality may be limited. Databases at the Contributed level are not supported by the Liquibase support team. Best-effort support is provided through our community forums.

    For more information about the verification levels, see <a href="https://www.liquibase.com/supported-databases/verification-levels">Database Verification and Support</a>

## Reported versions
- 4.35.1

## Prerequisites
1. [Introduction to Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html) – Dive into Liquibase concepts.
1. [Install Liquibase](https://docs.liquibase.com/start/install/home.html) – Download Liquibase on your machine.
1. [How to Apply Your Liquibase Pro License Key](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html) – If you use Liquibase Pro, activate your license.

To access Cosmos DB, do one of the following:

- Install the <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator">Azure Cosmos DB&#160;Emulator</a> locally, and <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator-export-ssl-certificates">export the Azure Cosmos DB Emulator TLS/SSL certificate</a> for use with Liquibase
- Create an <a href="https://azure.microsoft.com/en-us/free/">Azure account with a subscription</a>
- Use <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/sql/create-sql-api-java?tabs=sync">Cosmos DB without an Azure subscription</a>

## Install drivers

### All Users

#### List of JARs

To use Liquibase and Cosmos DB, you need several JAR files:

* [Liquibase extension for Cosmos DB](https://github.com/liquibase/liquibase-cosmosdb/releases): `liquibase-cosmosdb-4.20.0.jar`
* [Azure Core](https://mvnrepository.com/artifact/com.azure/azure-core): `azure-core-1.37.0.jar`
* [Azure Cosmos](https://mvnrepository.com/artifact/com.azure/azure-cosmos): `azure-cosmos-4.41.0.jar`
* [Dropwizard Metrics](https://mvnrepository.com/artifact/io.dropwizard.metrics/metrics-core): `metrics-core-4.2.17.jar`
* [Jackson Annotations](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations): `jackson-annotations-2.14.2.jar`
* [Jackson Core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core): `jackson-core-2.14.2.jar`
* [Jackson Databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind): `jackson-databind-2.14.2.jar`
* [Jackson Datatype](https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310): `jackson-datatype-jsr310-2.14.2.jar`
* [Jackson Afterburner](https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-afterburner): `jackson-module-afterburner-2.14.2.jar`
* [Micrometer Core](https://mvnrepository.com/artifact/io.micrometer/micrometer-core): `micrometer-core-1.10.5.jar`
* [Micrometer Observation](https://mvnrepository.com/artifact/io.micrometer/micrometer-observation): `micrometer-observation-1.10.5.jar`
* [Netty Buffer](https://mvnrepository.com/artifact/io.netty/netty-buffer): `netty-buffer-4.1.90.Final.jar`
* [Netty Codec](https://mvnrepository.com/artifact/io.netty/netty-codec): `netty-codec-4.1.90.Final.jar`
* [Netty Codec DNS](https://mvnrepository.com/artifact/io.netty/netty-codec-dns): `netty-codec-dns-4.1.90.Final.jar`
* [Netty Codec HTTP](https://mvnrepository.com/artifact/io.netty/netty-codec-http): `netty-codec-http-4.1.90.Final.jar`
* [Netty Codec HTTP2](https://mvnrepository.com/artifact/io.netty/netty-codec-http2): `netty-codec-http2-4.1.90.Final.jar`
* [Netty Codec Socks](https://mvnrepository.com/artifact/io.netty/netty-codec-socks): `netty-codec-socks-4.1.90.Final.jar`
* [Netty Common](https://mvnrepository.com/artifact/io.netty/netty-common): `netty-common-4.1.90.Final.jar`
* [Netty Handler](https://mvnrepository.com/artifact/io.netty/netty-handler): `netty-handler-4.1.90.Final.jar`
* [Netty Handler Proxy](https://mvnrepository.com/artifact/io.netty/netty-handler-proxy): `netty-handler-proxy-4.1.90.Final.jar`
* [Netty Resolver](https://mvnrepository.com/artifact/io.netty/netty-resolver): `netty-resolver-4.1.90.Final.jar`
* [Netty Resolver DNS](https://mvnrepository.com/artifact/io.netty/netty-resolver-dns): `netty-resolver-dns-4.1.90.Final.jar`
* [Netty TC Native](https://mvnrepository.com/artifact/io.netty/netty-tcnative): `netty-tcnative-2.0.59.Final.jar`
* [Netty TC Native SSL](https://mvnrepository.com/artifact/io.netty/netty-tcnative-boringssl-static): `netty-tcnative-boringssl-static-2.0.59.Final.jar`
* [Netty TC Classes](https://mvnrepository.com/artifact/io.netty/netty-tcnative-classes): `netty-tcnative-classes-2.0.59.Final.jar`
* [Netty Transport](https://mvnrepository.com/artifact/io.netty/netty-transport): `netty-transport-4.1.90.Final.jar`
* [Netty Transport Classes Epoll](https://mvnrepository.com/artifact/io.netty/netty-transport-classes-epoll): `netty-transport-classes-epoll-4.1.90.Final.jar`
* [Netty Transport Classes Kqueue](https://mvnrepository.com/artifact/io.netty/netty-transport-classes-kqueue): `netty-transport-classes-kqueue-4.1.90.Final.jar`
* [Netty Transport Native Epoll:](https://mvnrepository.com/artifact/io.netty/netty-transport-native-epoll) `netty-transport-native-epoll-4.1.90.Final-linux-x86_64.jar`
* [Netty Transport Native Kqueue](https://mvnrepository.com/artifact/io.netty/netty-transport-native-kqueue): `netty-transport-native-kqueue-4.1.90.Final.jar`
* [Netty Transport UNIX](https://mvnrepository.com/artifact/io.netty/netty-transport-native-unix-common): `netty-transport-native-unix-common-4.1.90.Final.jar`
* [Reactive Streams](https://mvnrepository.com/artifact/org.reactivestreams/reactive-streams): `reactive-streams-1.0.4.jar`
* [Reactor Core](https://mvnrepository.com/artifact/io.projectreactor/reactor-core): `reactor-core-3.5.4.jar`
* [Reactor Netty](https://mvnrepository.com/artifact/io.projectreactor.netty/reactor-netty): `reactor-netty-1.1.5.jar`
* [Reactor Netty Core](https://mvnrepository.com/artifact/io.projectreactor.netty/reactor-netty-core): `reactor-netty-core-1.1.5.jar`
* [Reactor Netty HTTP](https://mvnrepository.com/artifact/io.projectreactor.netty/reactor-netty-http): `reactor-netty-http-1.1.5.jar`
* [SLF4J](https://mvnrepository.com/artifact/org.slf4j/slf4j-api): `slf4j-api-2.0.6.jar`
* [SLF4J Simple](https://mvnrepository.com/artifact/org.slf4j/slf4j-simple): `slf4j-simple-2.0.6.jar`

#### get_dependencies.bat

You can use this BAT script to download the files quickly:

``` shell
setlocal
SET LIQUIBASE_COSMOSDB="{{liquibase.current_version}}"
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
endlocal
```

<a href="https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html">Place your JAR file(s)</a> in the `liquibase/lib` directory.

### Maven Users (additional step)

If you use Maven, you must <a href="https://docs.liquibase.com/tools-integrations/maven/maven-pom-file.html">include the driver JAR as a dependency</a> in your `pom.xml` file.

#### Maven pom.xml file
```
<dependency>
	<groupId>org.liquibase.ext</groupId>
	<artifactId>liquibase-cosmosdb</artifactId>
	<version>{{liquibase.current_version}}</version>
</dependency>
<dependency>
	<groupId>com.azure</groupId>
	<artifactId>azure-core</artifactId>
	<version>1.37.0</version>
</dependency>
<dependency>
	<groupId>com.azure</groupId>
	<artifactId>azure-cosmos</artifactId>
	<version>4.41.0</version>
</dependency>
<dependency>
	<groupId>io.dropwizard.metrics</groupId>
	<artifactId>metrics-core</artifactId>
	<version>4.2.17</version>
</dependency>
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-annotations</artifactId>
	<version>2.14.2</version>
</dependency>
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-core</artifactId>
	<version>2.14.2</version>
</dependency>
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
	<version>2.14.2</version>
</dependency>
<dependency>
	<groupId>com.fasterxml.jackson.datatype</groupId>
	<artifactId>jackson-datatype-jsr310</artifactId>
	<version>2.14.2</version>
</dependency>
<dependency>
	<groupId>com.fasterxml.jackson.module</groupId>
	<artifactId>jackson-module-afterburner</artifactId>
	<version>2.14.2</version>
</dependency>
<dependency>
	<groupId>io.micrometer</groupId>
	<artifactId>micrometer-core</artifactId>
	<version>1.10.5</version>
</dependency>
<dependency>
	<groupId>io.micrometer</groupId>
	<artifactId>micrometer-observation</artifactId>
	<version>1.10.5</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-buffer</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-codec</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-codec-dns</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-codec-http</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-codec-http2</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-codec-socks</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-common</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-handler</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-handler-proxy</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-resolver</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-resolver-dns</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-tcnative</artifactId>
	<version>2.0.59.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-tcnative-boringssl-static</artifactId>
	<version>2.0.59.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-transport</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-transport-classes-epoll</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-transport-classes-kqueue</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-transport-native-epoll</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-transport-native-kqueue</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-transport-native-unix-common</artifactId>
	<version>4.1.90.Final</version>
</dependency>
<dependency>
	<groupId>org.reactivestreams</groupId>
	<artifactId>reactive-streams</artifactId>
	<version>1.0.4</version>
</dependency>
<dependency>
	<groupId>io.projectreactor</groupId>
	<artifactId>reactor-core</artifactId>
	<version>3.5.4</version>
</dependency>
<dependency>
	<groupId>io.projectreactor.netty</groupId>
	<artifactId>reactor-netty</artifactId>
	<version>1.1.5</version>
</dependency>
<dependency>
	<groupId>io.projectreactor.netty</groupId>
	<artifactId>reactor-netty-core</artifactId>
	<version>1.1.5</version>
</dependency>
<dependency>
	<groupId>io.projectreactor.netty</groupId>
	<artifactId>reactor-netty-http</artifactId>
	<version>1.1.5</version>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>2.0.6</version>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-simple</artifactId>
	<version>2.0.6</version>
</dependency>
```

## Database connection

### Configure connection

1. Ensure your Cosmos DB database is configured. See <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/sql/create-sql-api-java">Quickstart: Build a Java app to manage Azure Cosmos DB SQL API data</a> for more information.

1. Specify the database URL in the <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html">`liquibase.properties`</a> file (defaults file), along with other properties you want to set a default value for. Liquibase does not parse the URL. You can  either specify the full database connection string or specify the URL using your database's standard JDBC format:

    Connection String Examples

    ===+ "Azure Cosmos DB"

          Syntax

          ```
          cosmosdb://<cosmosdb-account-name>.documents.azure.com:<AccountKey>@<cosmosdb-account-name>.documents.azure.com:<port>/<database>
          ```

          Example

          ```
          url: cosmosdb://amalik.documents.azure.com:Pza0RV29y...CDbOjTZjg==@amalik.documents.azure.com:443/SampleDB
          ```

          Replace `<cosmosdb-account-name>` with the name you chose for your Azure Cosmos DB. 
          Replace `<accountKey>` with your private account key. 
          
          For more information, see <a href="https://docs.microsoft.com/en-us/azure/private-link/tutorial-private-endpoint-cosmosdb-portal">Tutorial: Connect to an Azure Cosmos account using an Azure Private Endpoint</a>.
          ---

    === "Azure Cosmos DB Emulator"

          Syntax

          ```
          cosmosdb://AccountEndpoint=https://<cosmosdb-account-name>.documents.azure.com:443;AccountKey=<accountKey>;
          ```

          Example

          ```
          url: cosmosdb://AccountEndpoint=https://localhost:8081/;AccountKey=C2y6yDjf5/R...IZnqyMsEcaGQy67XIw/Jw==;
          ```

          This example shows the default account key, formatted for readability. 
          For more information, see <a href="https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator">Install and use the Azure Cosmos DB Emulator for local development and testing</a>.
          ---

    For more information, see <a href="https://github.com/liquibase/liquibase-cosmosdb#adjust-connection-string">GitHub: liquibase-cosmosdb § Adjust connection string</a>.
    
### Test connection

1. Create a text file called <a href="https://docs.liquibase.com/concepts/changelogs/home.html">changelog</a> (`.xml`, `.sql`, `.json`, or `.yaml`) in your project directory and add a <a href="https://docs.liquibase.com/concepts/changelogs/changeset.html">changeset</a>.

    If you already created a changelog using the <a href="https://docs.liquibase.com/commands/init/project.html">`init project`</a> command, you can use that instead of creating a new file. When adding onto an existing changelog, be sure to only add the changeset and to not duplicate the changelog header.
      
    === "XML example"
          ``` xml
          <?xml version="1.0" encoding="UTF-8"?>
          <databaseChangeLog
            xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
            xmlns:pro="http://www.liquibase.org/xml/ns/pro"
            xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
                http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
                http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd">

              <changeSet id="1" author="Liquibase">
                  <createTable tableName="test_table">
                      <column name="test_id" type="int">
                          <constraints primaryKey="true"/>
                      </column>
                      <column name="test_column" type="varchar"/>
                  </createTable>
              </changeSet>

          </databaseChangeLog>
          ```
          ---
        
    === "SQL example"
          ``` sql
          -- liquibase formatted sql

          -- changeset liquibase:1
          CREATE TABLE test_table
          (
            test_id INT, 
            test_column VARCHAR(255), 
            PRIMARY KEY (test_id)
          )
          ```
          ---
        
    === "YAML example"
          ```  yaml
          databaseChangeLog:
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
                             nullable:  false
          ```
          ---
        
    === "JSON example"
          ``` json
          {
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
          }
          ```
          ---

1. Navigate to your project folder in the CLI and run the Liquibase <a href="https://docs.liquibase.com/commands/change-tracking/status.html">status</a> command to see whether the connection is successful:

    ```
    liquibase status --username=test --password=test --changelog-file=<changelog.xml>
    ```

    !!! note
        You can specify arguments in the CLI or keep them in the <a href="https://docs.liquibase.com/concepts/connections/creating-config-properties.html">Liquibase properties file</a>.
        
    If your connection is successful, you'll see a message like this:

    ```
    4 changesets have not been applied to <your_jdbc_url>
    Liquibase command 'status' was executed successfully.
    ```

1. Inspect the SQL with  the <a href="https://docs.liquibase.com/commands/update/update-sql.html">update-sql</a> command. 
    ```
    liquibase update-sql --changelog-file=<changelog.xml>
    ```
    
    Then make changes to your database with the <a href="https://docs.liquibase.com/commands/update/update.html">update</a> command.
    
    ```
    liquibase update --changelog-file=<changelog.xml>
    ```
    
    If your `update` is successful, Liquibase runs each changeset and displays a summary message ending with:
    
    ```
    Liquibase: Update has been successful.
    Liquibase command 'update' was executed successfully.
    ```

1. From a database UI tool, ensure that your database contains the <code>test_table</code> you added along with the <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangelog-table.html" class="MCXref xref">DATABASECHANGELOG table</a> and <a href="https://docs.liquibase.com/concepts/tracking-tables/databasechangeloglock-table.html" class="MCXref xref">DATABASECHANGELOGLOCK table</a>.</li>


Now you're ready to start making deployments with Liquibase!

## Related links

*   [Change Types](https://docs.liquibase.com/change-types/home.html)
*   [Liquibase Commands](https://docs.liquibase.com/commands/home.html)
*   [Azure Cosmos DB Documentation](https://docs.microsoft.com/en-us/azure/cosmos-db/)
*   [Azure Cosmos DB: REST API Reference](https://docs.microsoft.com/en-us/rest/api/cosmos-db/)
*   [Install and use the Azure Cosmos DB Emulator for local development and testing](https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator)
*   [Quickstart: Build a Java app to manage Azure Cosmos DB SQL API data](https://docs.microsoft.com/en-us/azure/cosmos-db/sql/create-sql-api-java)
*   [Azure Cosmos DB Java SDK v4 for Core (SQL) API: release notes and resources](https://docs.microsoft.com/en-us/azure/cosmos-db/sql/sql-api-sdk-java-v4)
*   [Azure Cosmos DB Resource URI Syntax for REST](https://docs.microsoft.com/en-us/rest/api/cosmos-db/cosmosdb-resource-uri-syntax-for-rest)
*   [Tutorial: Connect to an Azure Cosmos account using an Azure Private Endpoint](https://docs.microsoft.com/en-us/azure/private-link/tutorial-private-endpoint-cosmosdb-portal)
