# Extension Anatomy

In [Your First Extension](/extensions/your-first-extension), you were able to get a basic extension running. How does it work under the hood?

Liquibase is implemented in Java and relies on standard Java patterns such as "code to interfaces, dynamically load implementations". 

While the specific methods can vary by extension, the general pattern is:

1. On startup, Liquibase finds all available implementations of extension interfaces
1. As Liquibase runs, when it needs to use a particular interface it finds and uses the implementation with the highest [priority](/extensions/references/priority/) based on the surrounding context

This pattern means that creating extensions consists of:

1. Creating new classes (implementations) for interfaces with the new or changed logic you want.
1. Ensure your new classes return a high enough `priority` value **_in the contexts they should apply_**
1. Register classes, so they are found at startup time

Let's take a closer look at the [HelloWorldChange](../your-first-extension) sample's source code and see how these concepts apply to it.

## Extension File Structure

```
./src/main/java/com/example/change/HelloWorldChange.java
./src/main/resources/META-INF/services/liquibase.change.Change
./pom.xml
```

## HelloWorldChange.java

This class is your new implementation of the [liquibase.change.Change](https://javadocs.liquibase.com/liquibase-core/liquibase/change/Change.html) interface
which defines your new logic in the `generateStatements()`

The priority of this implementation is set via the `priority` attribute on the `@DatabaseChange` annotation. 
It uses the `PRIORITY_DEFAULT` constant which is "1"

## META-INF/services

Because HelloWorldChange is providing a new liquibase.change.Change implementation, we create (or add to) a file named `liquibase.change.Change` in `META-INF/services` and list the class name in there.

This file is used by Liquibase at startup time to know your new implementation exists.

## pom.xml

The pom.xml file contains the build logic Maven uses for compiling and packaging your code into a jar file. 

The jar file contains your new implementation class and adding it to Liquibase's `lib` directory allows the CLI to find it and use it. 