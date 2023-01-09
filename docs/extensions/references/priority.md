# Priority

Liquibase uses "priority" to determine the correct implementation classes to use at runtime.
While the specific methods used to return the priority can vary depending on the interface, they all follow a consistent pattern.

"Priority" is an integer, where the highest value wins.
We allow a range of priority values to allow more and more specific implementations.

!!! example

    - An implementation that provides generic database support for a feature may return priority "1"
    - An implementation for most Postgresql variants returns a priority of "5"
    - An implementation for Redshift specifically returns a priority of "10"
    - An implementation for how you specifically use Redshift returns a priority of "15"
    - An implementation for how you specifically use Redshift in production returns a priority of "20"

Extensions allow the existence of other extensions by [leaving gaps](../../best-practices) above and below their priority for other extensions to fit into. 

