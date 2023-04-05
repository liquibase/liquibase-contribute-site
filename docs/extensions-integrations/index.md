# Getting Started

While Liquibase is open source and [always open for improvements](../code/index.md), functionality can be independently expanded through **_extensions_** and **_integrations_**.

## Extensions vs. Integrations

The difference between [extensions](extensions-overview/index.md) and [integrations](integrations-overview/index.md) is whether they are changing the logic **_within_** Liquibase or whether they are **_controlling_** how that logic is triggered.

```mermaid
flowchart LR

    integrations[Calling Code / Integrations] --> engine[Liquibase Engine] --> extensions[Liquibase Logic / Extensions]
    
```

Extensions allow new functionality to be added or existing functionality changed, and that functionality will consistently be run regardless of the integration.

Integrations allow the same Liquibase functionality to be embedded or driven in whatever ways work best for each user.

## Directory

A list of publicly available extensions and integrations can be found in our [Directory](directory/index.md). 

The Directory is great for both finding what you need and for giving ideas on what you can build.  

## Next Steps

- Looking to create your own extension? Start with the [extensions overview](extensions-overview/index.md)
- Looking to create your own integration? Start with the [integrations overview](integrations-overview/index.md)

