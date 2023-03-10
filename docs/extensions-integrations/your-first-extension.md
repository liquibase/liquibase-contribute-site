# Your First Extension

This page will teach you the fundamental concepts for building extensions.

Liquibase extensions are built with Java, so you will likely want a 
Java-friendly IDE and a build tool like [Maven](https://maven.apache.org/){:target="_blank"} or [Gradle](https://gradle.org/){:target="_blank"}

For a starting Maven-based project, you can create a personal repository based on the [example extension template](https://github.com/liquibase/liquibase-extension-example){:target="_blank"}.

The example extension defines:

- A new `clearPasswords` change type
- A new `prefix` attribute for `createTable` 
- A new `hasPasswordColumn` precondition

From the base directory, run `mvn package` to build the extension as `target/my-example-0.0.1.jar`.

Copy `my-example-0.0.1.jar` to your `LIQUIBASE_HOME/lib` directory and run the changelog found in the `project` directory.
That will create a table called `secure_user_table`, check that the `password` column exists, and clear out any values in that column.

## Developing the extension

Let's make a "hello world" change type:

### Create HelloWorldChange.java

Create `src/main/java/com/example/change/HelloWorldChange.java` as

```java
package com.example.change;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;

@DatabaseChange(name = "helloWorld", description = "Says hi", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class HelloWorldChange extends AbstractChange {
    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[] {
                new RawSqlStatement("create table hello(world int)")
        };
    }

    @Override
    public String getConfirmationMessage() {
        return "Said hello";
    }
}
```
and add `com.example.change.HelloWorldChange` to `src/main/resources/META-INF/services/liquibase.change.Change` so it looks like:

```
com.example.change.ClearPasswordsChange
com.example.change.PrefixedCreateTableChange
com.example.change.HelloWorldChange
```

then rebuild with `mvn package` and re-copy `target/my-extension-0.0.1.jar` to `LIQUIBASE_HOME/lib`.

Finally, create a changelog file like:

```xml
<?xml version="1.1" encoding="UTF-8" standalone="no"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
		       http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
    
    <changeSet id="1" author="example">
        <ext:helloWorld/>
    </changeSet>
</databaseChangeLog>
```
and when you run `liquibase update` you will now have a table named `hello` with a column named `world`. 

**Here are some ideas for things you can try:**

- Change the name of the table created by `helloWorld`
- Add a "dataType" parameter to `helloWorld`

## Next Steps

In the next topic, [Extension Anatomy](extension-anatomy.md), we look closer at the source code of the helloWorld extension and explain key concepts.