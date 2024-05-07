---
title: Liquibase container on Amazon ECS and EKS
---

# Using the Liquibase Container in the AWS Marketplace with ECS or EKS

These instructions will guide you through the initial setup, management, and monitoring of your Liquibase Docker container in an AWS environment using ECS or EKS.

## Location of Sensitive Information

The following is the list of potentially sensitive information encountered when using the Liquibase container:

* Database credentials
* Connection encryption keys
* Configuration files
* SQL scripts

Database credentials and connection encryption keys are typically stored in an AWS-managed credential store and can be passed to [Liquibase using environment variables](https://docs.liquibase.com/concepts/connections/liquibase-environment-variables.html).

User-created configuration files and SQL scripts are secured in user-owned, mounted volumes configured at container runtime.

### Database Credentials and Encryption Keys

All data encryption should be configured to use AWS-managed keys by default, with the option to configure customer-managed keys if preferred. Follow these steps to set up encryption:

* AWS Managed Keys
    * Use default AWS encryption features available in services like Amazon EKS and ECS.
    * Sensitive data stored in Amazon S3, Amazon RDS, and other services are encrypted under the hood using keys managed by AWS.
* Customer-Managed Keys
    * Create a new key using AWS Key Management Service (KMS).
    * Configure the Liquibase Docker container to use this key by setting the appropriate environment variables.

### Rotate Programmatic System Credentials and Cryptographic Keys

To rotate credentials and keys for your application, follow these steps:

1. Rotate AWS IAM Credentials
    * Navigate to the IAM console.
    * Locate the IAM user or role used by your Liquibase container.
    * Generate a new set of credentials and update your container configuration with the new credentials.
2. Rotate Cryptographic Keys
    * If using customer-managed keys in KMS, create a new key.
    * Update your application to use the new key by adjusting the environment variables or configuration files.
    * Retire the old key following AWS best practices.
    
### Mount Volume Containing Liquibase Files

To mount volumes for your Liquibase Docker container, you typically use the `-v` or `--volume` option in the Docker run command to specify the local directory and the corresponding path in the container where it should be mounted.

This allows you to provide the following Liquibase files to the container.

* [Liquibase changelog](https://docs.liquibase.com/concepts/changelogs/home.html)
* [Properties file](https://docs.liquibase.com/concepts/connections/creating-config-properties.html)
* [Database drivers](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html)
* [Get a Liquibase Pro License](https://www.liquibase.com/trial) and the [Liquibase Pro License](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html#sql_example) for your container.

Here’s a basic example of how to run a Liquibase Docker container with a mounted volume:

1. **Prepare your local directory:** Ensure you have a local directory with all necessary files such as the [Liquibase changelog file](https://docs.liquibase.com/concepts/changelogs/home.html), [properties file](https://docs.liquibase.com/concepts/connections/creating-config-properties.html), any [JDBC drivers](https://docs.liquibase.com/workflows/liquibase-community/adding-and-updating-liquibase-drivers.html) required for your database, and apply the [Liquibase Pro License](https://docs.liquibase.com/workflows/liquibase-pro/how-to-apply-your-liquibase-pro-license-key.html#sql_example)] to your container.

2. **Run the Docker container with mounted volumes:**

     ```bash
     docker run --rm \
       -v /path/to/local/directory:/liquibase/changelog \
       liquibase/liquibase \
       --defaultsFile=/liquibase/changelog/liquibase.properties \
       --changeLogFile=/liquibase/changelog/changelog.xml \
       update
     ```

     Replace `/path/to/local/directory` with the path to your local directory containing the Liquibase files.

     - `--rm`: Automatically remove the container when it exits.
     - `-v /path/to/local/directory:/liquibase/changelog`: Mounts the local directory to `/liquibase/changelog` inside the container.
     - `liquibase/liquibase`: The Docker image name.
     - `--defaultsFile`, `--changeLogFile`, and other options are used to specify paths inside the container and Liquibase commands.


## Run Liquibase Commands

After the volume is mounted and the container is running, Liquibase can interact with the files in the specified directory as if they were inside the container, allowing it to execute database schema changes specified in your changelog file.

This setup allows you to manage your database schema migrations using Liquibase in a Dockerized environment while keeping your changelogs and configurations on your host machine for easy editing and version control.

Refer to the Liquibase documentation for the full list of [available Liquibase commands](https://docs.liquibase.com/commands/home.html).

## Assess and Monitor Application Health
To monitor the health and proper function of your Liquibase Docker container on AWS:

1. Navigate to your Amazon EC2 Console:
    * Ensure you're in the correct AWS region.
2. Choose 'Instances' and Select Your Launched Instance:
    * Access the instance running your Docker container.
3. Review Instance Status:
    * Select the server to view your metadata page.
    * Choose the 'Status Checks' tab at the bottom of the page to see if your status checks passed or failed.



