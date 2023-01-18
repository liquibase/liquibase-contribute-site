---
title: Unit Tests
---

# How to write unit tests for Liquibase

Liquibase code contains both unit tests and integration tests. Here are the basics of creating Unit tests used by the
automated build process to validate Liquibase and reduce regressions.

## What are unit tests?

Unit tests validate functionality in isolation, without any external system interaction. Because there are no external
dependencies, these tests need no additional setup and run fast.

Liquibase unit tests are written using the [Spock testing framework.](https://spockframework.org/){:target="_blank"}

## Naming and location of the unit test

Unit tests are written at the Java class level. There is a 1-1 mapping between the unit test class and the Java class
being tested.
The unit test class has the same package and name as the Java class, but with Test.groovy appended.
All test classes are stored using the `src/test/groovy` structure expected by Maven.

Here is the example for liquibase-core/src/main/java/liquibase/util/StringUtil.java

1. Replace main/java in the file path with the new substring src/test/groovy:
   `liquibase-core/src/main/java/liquibase/util/` -> `liquibase-core/src/test/groovy/liquibase/util/`
1. If a unit test doesn’t already exist, create a new unit test class with the same base filename as the Java class and
   append Test.groovy:
   `liquibase-core/src/test/groovy/liquibase/util/StringUtilTest.groovy`
1. The next step is to create the actual test

## Creating the unit test

In this example, we added a new method in this class named `public static String StringUtil.trimToNull(String)`.

The goal of the method is to return a new string with leading and trailing whitespace removed. If the resulting string
is empty, then return null.

### The Java class to test

See the actual Java class in
GitHub [StringUtil.java](https://github.com/liquibase/liquibase/blob/master/liquibase-core/src/main/java/liquibase/util/StringUtil.java)
{target="_blank"}
or the edited example below:

liquibase-core/src/main/java/liquibase/util/StringUtil.java

```java
package liquibase.util;

import liquibase.ExtensibleObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/** * Various utility methods for working with strings. */
public class StringUtil { // ...

    public static String trimToNull(String string) {
        if (string == null) {
            return null;
        }
        String returnString = string.trim();
        if (returnString.isEmpty()) {
            return null;
        } else {
            return returnString;
        }
    } // ...
}
```

### The unit test

See the actual Unit Test class in
GitHub [StringUtilTest.groovy](https://github.com/liquibase/liquibase/blob/master/liquibase-core/src/test/groovy/liquibase/util/StringUtilTest.groovy)
{target="_blank"}
or the edited example below:

**1. In the `liquibase-core/src/test/groovy/liquibase/util/` directory, create or edit the StringUtilTest.groovy file
and add the following new test method.**

The test method name should descriptive of what is being tested. At a minimum it should be the name of the method under
test, but ideally it should describe what is being tested by the method.

```groovy
class StringUtilTest extends Specification {
    // ...

    def "trimToNull"() {}

    // ...
}
```

**2. Add a simple assertion. In this case, there is no setup needed so a simple “expect” works best.**

If you are new to the Spock testing framework and need more information on the syntax below, please
read [Spock Test Framework – Getting Started](https://spockframework.org/spock/docs/1.3/spock_primer.html).

!!! tip

    Focus on test readability. What is being tested and why should be immediately obvious to anyone looking at the test.

```groovy
class StringUtilTest extends Specification {
    def "trimToNull"() {
        expect:
        StringUtil.trimToNull("test string") == "test string"
    }
}
```

**3. Refactor and expand your test with more test cases.**

Try to think of test for all the edge cases. Specify the test variations in the where clause.

!!! tip

    Use more "where" data over additional test methods. Before adding a new test method, look at the existing tests and see
    if any can be refactored to include your scenario.

```groovy

@Unroll("#featureName: '#input'")
def "trimToNull"() {
    expect:
    StringUtil.trimToNull(input) == expected

    where:
    input                       | expected
    "test string"               | "test string"
    " test string "             | " test string"
    " "                         | null
    " \n\r\ttest string\r\n\t " | " test string"
}
```

## Running and reviewing the unit test

### Running via IDE

The Spock framework is an extension of JUnit. During development, it is normally executed through your IDE just like any other test. Your IDE will let you choose which test(s) you want to run.

### Running via Maven

You are also able to run all unit tests via maven.

From the root of the liquibase repository, run `mvn test` which will build the project and run all the tests.

The output will show the tests executing like this:

```
[INFO] ------------------------------------------------------------------------ 
[INFO] Reactor Build Order: 
[INFO] 
[INFO] liquibase-core [jar] 
[INFO] liquibase-maven-plugin [maven-plugin] 
[INFO] liquibase-cdi [jar]
[INFO] liquibase-integration-tests [jar]
[INFO] liquibase-pro-base [pom]
[INFO] liquibase-pro [jar]
[INFO] licensing [pom]
[INFO] licensing-keymgr [jar]
[INFO] liquibase-root [pom]
[INFO] liquibase-dist [jar]
[INFO] licensing-keygen [jar]
[INFO]
[INFO] --------------------< org.liquibase:liquibase-core >--------------------
[INFO] Building liquibase-core 0-SNAPSHOT [1/11]
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] ..........[ skipped output ].....................
[INFO] -------------------------------------------------------
[INFO] T E S T S
[INFO] -------------------------------------------------------
[INFO] Running liquibase.CatalogAndSchemaTest
[INFO] Tests run: 38, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.781 s - in liquibase.CatalogAndSchemaTest
[INFO] Running liquibase.change.AbstractChangeTest
[INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.415 s - in liquibase.change.AbstractChangeTest
[INFO] Running liquibase.change.AbstractSQLChangeTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.68 s - in liquibase.change.AbstractSQLChangeTest
[INFO] Running liquibase.change.AddColumnChangeTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 s - in liquibase.change.AddColumnChangeTest
[INFO] Running liquibase.change.AddColumnConfigTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.027 s - in liquibase.change.AddColumnConfigTest
[INFO] Running liquibase.change.ChangeFactoryTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.131 s - in liquibase.change.ChangeFactoryTest
[INFO] Running liquibase.change.ChangeParameterMetaDataTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.021 s - in liquibase.change.ChangeParameterMetaDataTest
[INFO] Running liquibase.change.CheckSumTest
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.007 s - in liquibase.change.CheckSumTest
[INFO] Running liquibase.change.ColumnConfigTest
[INFO] Tests run: 91, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.246 s - in liquibase.change.ColumnConfigTest
[INFO] Running liquibase.change.ConstraintsConfigTest
[INFO] Tests run: 32, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.015 s - in liquibase.change.ConstraintsConfigTest ..........[ skipped output ].....................
[INFO] Reactor Summary for liquibase-root:
[INFO]
[INFO] liquibase-core ..................................... SUCCESS [01:09 min]
[INFO] liquibase-maven-plugin ............................. SUCCESS [ 9.587 s]
[INFO] liquibase-cdi ...................................... SUCCESS [ 9.642 s]
[INFO] liquibase-integration-tests ........................ SUCCESS [ 56.768 s]
[INFO] liquibase-root ..................................... SUCCESS [ 0.020 s]
[INFO] liquibase-dist ..................................... SUCCESS [ 7.143 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 02:48 min
[INFO] Finished at: 2020-04-14T16:24:28-05:00
[INFO] ------------------------------------------------------------------------
```

!!! tip

    If any tests failed, you’ll see it say `BUILD FAILED` at the end and the `TESTS` section will list the failing tests.