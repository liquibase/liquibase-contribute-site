---
title: JEE CDI
---

# JEE CDI Integration

Liquibase can be run in a <a href="https://weld.cdi-spec.org/">JEE CDI</a> environment by implementing a number of CDI Producer methods. 
The CDI Liquibase integration is a simple CDI extension that performs a Liquibase update when the CDI container boots.

### How to Configure

```java
public class LiquibaseProducer {

    @Resource
    private DataSource myDataSource;

    @Produces
    @LiquibaseType
    public CDILiquibaseConfig createConfig() {
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setChangeLog("liquibase/parser/core/xml/simpleChangeLog.xml");
        return config;
    }

    @Produces
    @LiquibaseType
    public DataSource createDataSource() throws SQLException {
        return myDataSource;
    }

    @Produces
    @LiquibaseType
    public ResourceAccessor create() {
        return new ClassLoaderResourceAccessor(getClass().getClassLoader());
    }

}
```

### CDILiquibaseConfig Available Attributes

- changelog
- contexts
- parameters
- defaultSchema
- dropFirst

If you don't want Liquibase to run you can configure the following system property: `liquibase.should.run=false`
