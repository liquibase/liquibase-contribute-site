---
title: Best Practices
---

# Extension Best Practices

## Play Nicely With Others

Remember that users will install a variety of extensions, not just yours. That means you should always be enabling your functionality only when it should be used.

For example, in `getPriority()` methods, make sure you are returning `NOT_APPLICABLE` when your code is not applicable. Or in `supports()` calls, make sure you return `false`
if that is not a database your code is trying to support.

## Be Open to Extension

Remember that just like you are looking to improve Liquibase, others will want to improve your extension. Be open to extension with both your code and your configuration.

For example, don't return `Integer.MAX_VALUE` in `getPriority()`. Also, consider extracting portions of your logic to protected methods that someone else can subclass and override in their own extensions.

## Dependencies

Carefully consider dependencies using the [guidelines from liquibase-core](../../code/architecture/dependencies.md). 