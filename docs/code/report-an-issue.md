---
title: Report an Issue
---

# How to Report an Issue

If something's not working, we want to know. The more detail you can share, the faster we can help.

## Step 1: Search for an Existing Issue

Please search to see if it has already been reported before opening an issue. Duplicate issues split the conversation and make it harder to track progress.

Search [GitHub Issues](https://github.com/liquibase/liquibase/issues?utf8=%E2%9C%93&q=is%3Aissue){:target="_blank"}, the primary and only community tracker we use. **Always include closed issues in your search**, as the problem may have been addressed with a workaround or explanation.

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

- `is:open` / `is:closed`: filter by issue state
- `label:bug`: filter by label
- `author:username`: find issues from a specific reporter

If you find an existing issue that matches your problem:

- **React with :thumbsup:** to signal your interest. This helps the team and community prioritize.
- **Add a comment** with your specific environment details and reproduction steps if they differ from what's already documented.
- **Share new information** such as logs, database versions, workarounds you've discovered, or additional affected scenarios.

## Step 2: Report a New Issue

Open a new issue if the issue you have doesn't match an existing ticket. You can use the [Bug Report or Feature Request](https://github.com/liquibase/liquibase/issues/new/choose){:target="_blank"} template to report the issue.

### What Makes a Great Bug Report?

#### Tell us about your setup

Include as much of the following as possible:

- **Liquibase version:** run `liquibase --version`
- **Database type and version:** e.g., PostgreSQL 15.2, Oracle 19c, MySQL 8.0, SQL Server 2019
- **JDBC driver name and version**
- **Java version:** run `java -version`
- **Operating system and version:** e.g., Windows 11, macOS 14, Ubuntu 22.04
- **How Liquibase is invoked:** CLI, Maven plugin, Gradle plugin, Spring Boot integration, etc.

#### Steps to Reproduce

Keep it as simple as possible:

1. A simple changelog file that demonstrates the issue (reduce it to as few change sets as possible)
2. The exact command or configuration used
3. Clear step-by-step instructions to trigger the problem

!!! tip
    The simpler and more self-contained the reproduction, the faster it can be diagnosed. If your changelog has 50 change sets but only one is relevant, please trim it down. A minimal example is one of the most valuable things you can provide.

#### Actual vs. Expected Behavior

Describe clearly:

- What you **expected** to happen
- What **actually** happened

Avoid vague descriptions like "it doesn't work." Be specific about the outcome, including any error messages or incorrect behavior.

#### Logs and Output

Include complete error messages and stack traces. Use code blocks for readability:

````
```
Your log output or stack trace here
```
````

For more detailed diagnostic output, run Liquibase with `--log-level=DEBUG`. This often reveals what is happening internally and is very helpful when diagnosing unexpected behavior.

!!! note "Have a question rather than a bug?"
    Please ask directly on the issue thread or join us on [Discord](https://discord.gg/pDB5DfE){:target="_blank"}. That is where the Liquibase team and community are most active.

## Step 3: Help Move the Issue Forward

The report is just the start. The issues that get resolved fastest are the ones where the reporter stays involved. After filing, consider:

- **Share where you've seen the issue:** does this happen on other databases or versions? Even sharing that makes a real difference.
- **Share your hunch:** if you have a sense of where the problem might be, say so.
- **If you're a developer, add a failing test:** even without a fix, it goes a long way.

Want to know more about how you can help and what to expect from the team? [Issue Resolution Expectations](issue-resolution-expectations.md) has the full picture.
