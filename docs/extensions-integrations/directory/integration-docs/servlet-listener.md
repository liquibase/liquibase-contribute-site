# Servlet Listener

Liquibase can be run via a servlet listener. This allows you to have your database update automatically whenever the site is deployed. BecauseLiquibase uses distributed locking, this method will work even if you have a cluster of application servers.

To configure the servlet listener, add the `liquibase.jar` file to your `WEB-INF/lib` directory. Then add the following to your `web.xml` file:

```xml
<context-param>
    <param-name>liquibase.changelog</param-name>  
    <param-value>com/example/db.changelog.xml</param-value>  
</context-param>  

<context-param>  
    <param-name>liquibase.datasource</param-name>  
    <param-value>java:comp/env/jdbc/default</param-value>  
</context-param>  

<context-param>  
    <param-name>liquibase.host.includes</param-name>  
    <param-value>production1.example.com, production2.example.com</param-value>  
</context-param>  

<context-param>  
    <param-name>liquibase.onerror.fail</param-name>  
    <param-value>true</param-value>  
</context-param>  

<context-param>  
    <param-name>liquibase.contexts</param-name>  
    <param-value>production</param-value>  
</context-param>  

<listener>  
    <listener-class>liquibase.integration.servlet.LiquibaseServletListener</listener-class>  
</listener>
```

Note: If your application server uses the jakarta.servlet packages, use this listener instead:

```xml
<listener>
	<listener-class>liquibase.integration.servlet.LiquibaseJakartaServletListener</listener-class>
</listener>
```
## Available context-parameters
<table>
    <tr>
        <th style="width: 30%">Parameter</th>
        <th>Description</th>
    </tr>
    <tr>
        <td><code>liquibase.changelog</code>
        </td>
        <td>Specifies the changelog file to run. <b>Required</b></td>
    </tr>
    <tr>
        <td><code>liquibase.datasource</code>
        </td>
        <td>JNDI data source to use for running Liquibase. Note that the <code>LIQUIBASE_DATA_SOURCE</code> can be different than the data source that the rest of your web app uses if that data source does not have sufficient privileges to create or alter tables. <b>Required</b></td>
    </tr>
    <tr>
        <td><code>liquibase.host.excludes</code>
        </td>
        <td>Specify host names on which you do not want Liquibase to run. Specifying this parameter allows you to deploy the same WAR/EAR to multiple machines in different environments and not have Liquibase run on all of them.</td>
    </tr>
    <tr>
        <td><code>liquibase.host.includes</code>
        </td>
        <td>Specify the only host names on which want Liquibase to run. Specifying this parameter allows you to deploy the same WAR/EAR to multiple machines in different environments and not have Liquibase run on all of them.</td>
    </tr>
    <tr>
        <td><code>liquibase.onerror.fail</code>
        </td>
        <td>Specify if an exception is thrown by Liquibase if an error occurs. Setting the value to <code>true</code> (default) will cause the exception to be thrown and keep the site from initializing properly. Setting the value to <code>false</code> will allow the site to deploy as normal, but the database will be in an undefined state.</td>
    </tr>
    <tr>
        <td><code>liquibase.contexts</code>
        </td>
        <td>A comma-separated lists of the <a href="https://docs.liquibase.com/concepts/changelogs/attributes/contexts.html">Contexts</a> to run in.</td>
    </tr>
</table>

If you want to control servers that run Liquibase but do not want to set the <code>liquibase.host.includes/liquibase.host.excludes</code> attributes, specify the <code>liquibase.should.run=[true/false]</code> system property.