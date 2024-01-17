---
title: Overview
---

# Using Liquibase with Spinnaker

[Spinnaker](https://spinnaker.io/) is an open source and multi-cloud continuous delivery platform for releasing software changes with high velocity and confidence. Spinnaker provides two core sets of features:

*   [Application management](https://spinnaker.io/docs/concepts/#application-management)
*   [Application deployment](https://spinnaker.io/docs/concepts/#application-deployment)

!!! note
    For more information on how Spinnaker works, see [Spinnaker Guides](https://spinnaker.io/guides/).

Liquibase can be executed within Spinnaker using one of the [pipeline stages](https://spinnaker.io/docs/reference/pipeline/stages/). You will see two examples of using pipeline stages described in this documentation. For more information see:

* [Using the Jenkins Pipeline Stage with Spinnaker](spinnaker-jenkins-pipeline-stage.md)
* [Using the Run Job Pipeline Stage with Spinnaker](spinnaker-runjob-pipeline-stage.md)

You can execute Liquibase within Spinnaker using the [Run Job (Manifest) pipeline stage](https://spinnaker.io/docs/reference/pipeline/stages/#run-job). This pipeline stage uses Liquibase Docker image to execute Liquibase commands.

!!! note
    The same workflow is used for the [Liquibase GitHub Actions](https://github.com/liquibase/liquibase-github-action) and [Travis CI](https://github.com/liquibase/liquibase-travisci-example).

Alternatively, you can integrate Liquibase using [Jenkins](https://spinnaker.io/docs/reference/pipeline/stages/#jenkins) to deploy database changesets.

## Related links

*   [How to Set Up Spinnaker in Liquibase](https://www.liquibase.com/blog/set-up-liquibase-in-spinnaker)