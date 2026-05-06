---
title: Report an Issue
---

# How to Report an Issue

The Liquibase community is stronger when issues are well-documented. Whether you've encountered a bug or have a new feature idea, following these guidelines helps everyone — from the person who will triage it to the contributor who will eventually fix it.

## Step 1: Search for an Existing Issue

Before opening a new issue, please search to see if it has already been reported. Duplicate issues split the conversation and make it harder to track progress.

Search [GitHub Issues](https://github.com/liquibase/liquibase/issues?utf8=%E2%9C%93&q=is%3Aissue){:target="_blank"} — the primary and only community tracker we use. **Always include closed issues in your search**, as the problem may have been addressed with a workaround or explanation.

### Search Tips

Different search terms can surface different results. Try multiple approaches:

| Search by | Example queries |
|---|---|
| Error message | `"Unexpected error running Liquibase"` |
| Change type | `createTable`, `addColumn`, `dropIndex` |
| Database name | `postgres`, `oracle`, `mysql`, `sqlserver` |
| Command or operation | `update`, `rollback`, `diff`, `generateChangelog` |
| Combined terms | `createTable postgres "column not found"` |

GitHub's search filters can narrow results further:

- `is:open` / `is:closed` — filter by issue state
- `label:bug` — filter by label
- `author:username` — find issues from a specific reporter

If you find an existing issue that matches your problem:

- **React with :thumbsup:** to signal your interest — this helps the team and community prioritize.
- **Add a comment** with your specific environment details and reproduction steps if they differ from what's already documented.
- **Share new information** such as logs, database versions, workarounds you've discovered, or additional affected scenarios.

## Step 2: Report a New Issue

If no existing issue matches what you've found, [open a new issue](https://github.com/liquibase/liquibase/issues/new/choose){:target="_blank"} and select the "Bug Report or Feature Request" template.

### What Makes a Great Bug Report?

A great bug report allows someone who has never seen your environment to reproduce and understand the problem. The more context you provide, the faster the community and team can help.

#### Environment Information

Include as much of the following as possible:

- **Liquibase version** — run `liquibase --version`
- **Database type and version** — e.g., PostgreSQL 15.2, Oracle 19c, MySQL 8.0, SQL Server 2019
- **JDBC driver name and version**
- **Java version** — run `java -version`
- **Operating system and version**
- **How Liquibase is invoked** — CLI, Maven plugin, Gradle plugin, Spring Boot integration, etc.

#### Steps to Reproduce

Provide a minimal, self-contained reproduction:

1. A simple changelog file that demonstrates the issue (reduce it to as few change sets as possible)
2. The exact command or configuration used
3. Clear step-by-step instructions to trigger the problem

!!! tip
    The simpler and more self-contained the reproduction, the faster it can be diagnosed. If your changelog has 50 change sets but only one is relevant, please trim it down. A minimal example is one of the most valuable things you can provide.

#### Actual vs. Expected Behavior

Describe clearly:

- What you **expected** to happen
- What **actually** happened

Avoid vague descriptions like "it doesn't work" — be specific about the outcome, including any error messages or incorrect behavior.

#### Logs and Output

Include complete error messages and stack traces. Use code blocks for readability:

````
```
Your log output or stack trace here
```
````

For more detailed diagnostic output, run Liquibase with `--log-level=DEBUG`. This often reveals what is happening internally and is very helpful when diagnosing unexpected behavior.

!!! note "Have a question rather than a bug?"
    Please ask directly on the issue thread or join us on [Discord](https://discord.gg/pDB5DfE){:target="_blank"} — that is where the Liquibase team and community are most active.

## Step 3: Help Move the Issue Forward

Filing an issue is just the beginning. Issues get resolved much more quickly when reporters and other community members stay engaged. After filing, consider:

- **Clarifying the scope** — does this affect only the database you tested, or other databases as well?
- **Providing an analysis** — where do you think the problem might be in the code?
- **Adding a failing test** — even without a fix, a test that reproduces the issue is a meaningful contribution.

For more on how you can help move an issue toward a fix and what to expect from the team, see [Issue Resolution Expectations](issue-resolution-expectations.md).