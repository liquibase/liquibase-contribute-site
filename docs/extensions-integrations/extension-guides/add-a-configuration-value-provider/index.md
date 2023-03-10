---
title: Overview
---

# Creating New ConfigurationValueProviders

## Overview

`liquibase.configuration.ConfigurationValueProvider` implementations allow you to define where and how to look up configuration settings exposed through the `liquibase.configuration.LiquibaseConfiguration` facade.

The configuration settings looked up through these providers are used by both global and command-specific settings. 

## ConfigurationValueProvider Precedence

When a specific configuration key is looked for, Liquibase will check for the value in all registered ConfigurationValueProviders.

Each ConfigurationValueProvider has a `getPrecedence()` method which controls which value "wins" if it is configured in multiple places.

The standard provider precedence is:

- 400 `liquibase.configuration.core.ScopeValueProvider`
- 250 Integration-specific providers such as "CLI Arguments"
- 200 `liquibase.configuration.core.SystemPropertyValueProvider`
- 150 `liquibase.configuration.core.EnvironmentValueProvider`
- 50 `liquibase.configuration.core.DefaultsFileValueProvider`

## Prerequisites

Implementing support for additional databases requires an understanding of Java. You will be creating classes, overriding methods, and working with inheritance hierarchies.

## Project Setup

If you have not already created a repository to hold your code, see [Your First Extension](../../your-first-extension.md) in the Getting Started guide.

## Next Steps

When you are ready to create your new `ConfigurationValueProvider`, head to the [Create a ConfigurationValueProvider](create.md) page.