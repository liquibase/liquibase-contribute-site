# Dependencies

## Overall Strategy

Because Liquibase is often used as a library in other applications, we keep our external dependency list as small as possible. 

The problems we are trying to avoid are:
> Liquibase is a library in user's application X, and Liquibase has a dependency on library Y version 4.5.
> 
> **Variation 1:** But application X itself depends on Y version 3.1. Or 5.2.
> The actual version of Y in the classpath is controlled by the application, and they will ensure their version is used which may not be compatible with what Liquibase calls.
> 
> **Variation 2:** But application X explicitly excludes library Y, therefore Liquibase has no access to the expected functions. 

When new functionality could leverage existing 3rd party libraries, the tradeoff to make is weighing the **_value of reuse vs. the danger of incorrect versions_** being used.

## General Guidelines

In making the decision to add a dependency, we use the following guidelines:

- How complex is the code being depended on? 
    - The simpler the usage is, the more we prefer to rewrite the functionality
- How stable is the dependency? 
    - The fewer major/breaking releases there have been historically and/or are on the horizon, the more comfortable we are with the dependency
- How common is the dependency? 
    - The more likely the dependency is to be used by other applications, the **_less_** comfortable we are with the dependency. This is a bit counter-intuitive, but since the root problem is version conflicts then more usage equals more chances for conflicts.  
- How broken will Liquibase be if the dependency is different or missing?
    - The more isolated the use of the dependency is, the more comfortable we are with the dependency. Especially if the code using it can gracefully handle differences in versions and/or missing functions.
- What other dependencies does it bring along?
    - Repeat the review for all transitive dependencies introduced by the new dependency  

## Examples

### Apache Commons Lang

Apache's commons-lang.jar is a very popular (don't include) library containing simple, helper functions (don't include). It is very stable (include). It would be used throughout the entire codebase, so a missing dependency would completely break Liquibase (don't include)

**Verdict:** We did not add it as a dependency because the individual functions as we needed them were simple enough to re-implement. Over time, the number of "copied" functions has grown and perhaps it's worth switching to using the dependency now given how stable it is. 
But nothing has pushed us to make that switch yet.

### OpenCSV

Liquibase's loadData feature requires CSV parsing, which is a complex operation (include). OpenCSV is a popular library for parsing, but CSV parsing is not a super-common requirement in other applications (borderline include).
OpenCSV in general is stable (include), and if the library is missing it is only the loadData change that stops working (include).

**Verdict:** Included OpenCSV as a dependency. CSV parsing is complex code we don't want to maintain, and OpenCSV is a stable library therefore it is best to use it.

### Apache HttpClient

Liquibase's HTTP support requires HTTP client functionality (include). Apache's HttpClient is a popular library for HTTP client functionality, with many applications also using that library (exclude).
The library is growing and improving with major changes between versions 3,4, and 5 (exclude). Currently, the behavior we need is relatively simple and mostly available through the standard `java.net` packages.

**Verdict:** We used the standard `java.net` packages for the HTTP support, and did not add Apache HttpClient as a dependency. If our HTTP needs change over time, we can revisit the decision.


## Don't Shade Dependencies

One option to avoid the above problem is to "shade" the dependencies. This means that the dependencies are copied into the Liquibase jar, with packages renamed to avoid conflicts.

Liquibase avoids this because:

1. The renaming often causes problems within the dependency code
    - If the dependency uses reflection to find classes, the renaming can break that in ways not easily detected
2. It obfuscates the libraries users are exposed to
    - In order to correctly track their own application's security, users need easy and standard ways to see **_all_** the libraries they are using 

