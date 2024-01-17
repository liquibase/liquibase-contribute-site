---
title: GitLab CD/CD
---

# Using Liquibase with GitLab CI/CD

You can set up Liquibase without a local installation or [remote host](https://www.pcmag.com/encyclopedia/term/remote-host) by using [GitLab CI/CD](https://docs.gitlab.com/ee/ci/).

GitLab CI/CD is a GitLab tool that automates the process of building, testing, and deploying software changes to your repository using **pipelines**, which are made up of jobs and stages.

*   A **job** is an action to the software, such as compiling, testing, and deploying code.
*   A **stage** is a time frame in which a job is run, such as building, testing, staging, and production.

GitLab CI/CD uses [runners](https://docs.gitlab.com/ee/ci/runners/README.html) to run jobs in a pipeline. If a runner successfully runs all jobs in a stage, the pipeline will automatically go to the next stage. If any jobs fail, the pipeline ends. For more information, see [GitLab CI/CD Pipelines](https://docs.gitlab.com/ee/ci/pipelines/index.html).

## Prerequisites

Ensure you have a [GitLab](https://about.gitlab.com/) account.

!!! notes
    For more information on how to create a GitLab account, see [Signing Up for GitLab](https://gitlab.com/users/sign_up).

## Using GitLab CI/CD for Liquibase

You can use GitLab’s [shared runners](https://docs.gitlab.com/ee/ci/runners/runners_scope.html#shared-runners) for your pipelines, which will create a virtual machine for each job and delete it after the job is complete. Alternatively, you can self-host runners by installing [GitLab Runner](https://docs.gitlab.com/runner/).

The [LiquibaseGitLabCICD](https://gitlab.com/szandany/liquibasegitlabcicd) repository contains a README and files for different setup pipelines:

*   Liquibase Command Line Interface (CLI)
*   Liquibase Maven commands with a Springboot app
*   Liquibase Gradle commands
*   Liquibase running in Docker
*   Liquibase running in a [NodeJS wrapper](https://www.npmjs.com/package/liquibase)

The repository contains the following configuration files for each project:

| Project | Folder | Files |
| ------- | ------ | ----- |
| Docker | Docker | The SQL changelog file |
| Gradle | Gradle\_h2 | The `build.gradle` and SQL changelog files |
| Liquibase CLI commands | H2\_project | The XML changelog file |
| NodeJS | NodeJS | The `index.js` file and other pipeline files for different environments |
| Maven | SalesManager\_h2\_version | The `pom.xml`, `application.properties`, Liquibase properties file, and SQL changelog files |

The repository also contains a `[.gitlab-ci.yml](https://gitlab.com/szandany/liquibasegitlabcicd/-/blob/master/.gitlab-ci.yml)` file containing the jobs that runners will execute. All pipelines in the repository use runners hosted on [Docker Images GitLab](https://docs.gitlab.com/ee/ci/docker/using_docker_images.html).

## How to Use the Liquibase GitLab CI/CD Repository

To use the Liquibase GitLab CI/CD repository, follow these steps:

*   Open the repository and click the **Fork** button in the top-right corner of the page to create a copy for yourself. For more information about forking, see [Project forking workflow](https://docs.gitlab.com/ee/user/project/repository/forking_workflow.html).
*   Go to your newly forked `liquibasegitlabcicd` repository and follow the steps on [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/).
*   To run your job, click **CI/CD** on the left panel -> **Pipelines** -> **Branches** -> select a branch -> **Run Pipeline**.

You can adjust your CI/CD pipeline by adding more commands and flags to the `.gitlab-ci.yml` file.