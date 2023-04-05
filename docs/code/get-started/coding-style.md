---
title: Coding Style
---
# Liquibase Coding Style Guide

## Overview

While the Liquibase code generally follows the original [Sun Java Style Guide](https://www.oracle.com/technetwork/java/codeconvtoc-136057.html){:target="_blank"},
the overall code goal is "favor readability" over adherence to strict standards. 

Always be asking yourself "If I came back to this code in 6 months, what would make it more readable?" and do that. 

When in doubt, match the style in the rest of the code and/or ask [on the forum](https://forum.liquibase.org){:target="_blank"} or [Discord](https://discord.gg/pDB5DfE){:target="_blank"}. 

For information on pull request style, see the [Create a Pull Request](create-pr.md) page.

## Details

Part of readability is consistency. Some specific examples of code styles we generally follow:

- Line length of up to 120 is a soft limit. 
    - It's fine to go longer than 120 if that's more readable, but consider breaking it up. 
- Curly braces on the same line as `if`, `try`, etc.
- Use curly braces even with single-line `if` statements 
- Spaces, not tabs
- Use native types (`int`, `bool`, etc.) for values that can never have null values
- Include javadoc comments for public methods which describe the expected behavior more than the implementation. 
    - No need for javadocs on methods that override already documented methods without changing the external behavior.
- Include comments on private methods and around code with non-obvious logic. 
    - Before adding a comment, ask yourself "What could I do to make this code more obvious?"
- Don't include per-file license, author, or history information. 
    - That is handled repository wide and in git
- Acronyms/initials in class names don't remain in all caps. Example: `SqlGenerator` not `SQLGenerator`.
    - `DB` and `Db` are both found in the code, but `Db` is preferred.
    - When an acronym is part of a product name, we tend to preserve the product's capitalization. Example: `MySQLDatabase` not `MySqlDatabase`, `DB2Database` not `Db2Database`, `PostgresDatabase` to avoid the question  
- Common class name terms:
    - `*Service` for replaceable singletons that define a block of behavior. Example: `LockService` and `LogService`   
    - `*Factory` for replaceable singletons that create instances of other classes. Example: `LockServiceFactory` and `ChangeFactory`
    - `*Util` for static methods holder classes. NOTE: Not `*Utils`
    -  "Changeset" and "Changelog" vs "Change Set" and "Change Log"
        - **One word** in documentation and output strings. Example: `Found 3 changesets`
        - **Two words** in code references. Example: `liquibase.change.ChangeSet` and `CHANGE_LOG_CSV`
        - The reason for the difference is that the documentation standard changed to one word, but changing the code would be a breaking API change and has not been made yet. We are preserving consistency in naming until we can do a complete code change to the new style. 
- Tests conventions can be found in the [unit tests](../test-your-code/unit-tests.md) and [integration tests](../test-your-code/integration-tests.md) sections




