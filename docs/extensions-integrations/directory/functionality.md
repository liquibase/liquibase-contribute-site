# Expanded Functionality

While Liquibase ships with many features, that functionality can be expanded and/or replaced through extensions.

!!! note

    **Support and Documentation**
    
    Extensions are maintained and documented by their respective owners. Visit the below extension pages for more information.

## Available Extensions

| Extension                                                                                                                                     | Notes                                                                  |
|-----------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------|
| [Asciidoc Changelog Format](https://florent.biville.net/liquibase-asciidoc/){:target="_blank"}                                                | Write Change log files in Asciidoc format                              |
| [AWS Secrets Manager](https://docs.liquibase.com/tools-integrations/extensions/secrets-management/aws-secrets-mgr.html){:target="_blank"}     | Read secrets from AWS Secrets Manager                                  |
| [AWS S3](https://docs.liquibase.com/tools-integrations/extensions/remote-files/s3.html){:target="_blank"}                                     | Access files stored in AWS S3                                          |
| [Custom Hosts](https://github.com/liquibase/custom-hosts-extension){:target="_blank"}                                                         | Allows configuring hostname to IP mapping as a configuration parameter |
| [CyberArk](https://docs.liquibase.com/tools-integrations/extensions/secrets-management/cyberark-pam-vault.html){:target="_blank"}             | Read CyberArk PAM vault secrets                                        |
| [Feature Flags: CloudBees](https://docs.liquibase.com/feature-flags/readmes/cloudbees%20feature%20management.html){:target="_blank"}          | Controls execution based on CloudBees feature flags                    |
| [Feature Flags: ConfigCat](https://docs.liquibase.com/feature-flags/readmes/configcat%20feature%20flags%20extension.html){:target="_blank"}   | Controls execution based on ConfigCat feature flags                    |
| [Feature Flags: FF4j](https://github.com/liquibase/ff4j-extension){:target="_blank"}                                                          | Controls execution based on FF4j feature flags                         |
| [Feature Flags: Flagr](https://github.com/liquibase/flagr-extension){:target="_blank"}                                                        | Controls execution based on Flagr feature flags                        |
| [Feature Flags: FlagSmith](https://docs.liquibase.com/feature-flags/readmes/flagsmith%20feature%20flags%20extension.html){:target="_blank"}   | Controls execution based on FlagSmith feature flags                    |
| [Feature Flags: Flipt](https://github.com/liquibase/flipt-extension){:target="_blank"}                                                        | Controls execution based on Flipt feature flags                        |
| [Feature Flags: GitLab](https://docs.liquibase.com/feature-flags/readmes/gitlab%20feature%20flags%20extension.html){:target="_blank"}         | Controls execution based on GitLab feature flags                       |
| [Feature Flags: GrowthBook](https://docs.liquibase.com/feature-flags/readmes/growthbook%20feature%20flags%20extension.html){:target="_blank"} | Controls execution based on GrowthBook feature flags                   |
| [Feature Flags: Launch Darkly](https://docs.liquibase.com/feature-flags/readmes/launchdarkly%20feature%20flags.html){:target="_blank"}        | Controls execution based on Launch Darkly feature flags                |
| [File Changelog History](https://github.com/liquibase/liquibase-filechangelog){:target="_blank"}                                              | Use a CSV file instead of the DATABASECHANGELOG table                  |
| [GenericSequence](https://github.com/liquibase/liquibase-sequencetable){:target="_blank"}                                                     | Support sequence logic on databases without native sequence support    |
| [HashiCorp Vault](https://docs.liquibase.com/tools-integrations/extensions/secrets-management/hashicorp-vault.html){:target="_blank"}         | Read HashiCorp Vault secrets                                           |
| [Hibernate](https://github.com/liquibase/liquibase-hibernate){:target="_blank"}                                                               | Allows diff/diffChangeLog to compare databases to Hibernate models     |
| [Liquibase Data](https://github.com/liquibase/liquibase-data){:target="_blank"}                                                               | Brings git-like functionality to your database                         |
| [ModifyColumn](https://github.com/liquibase/liquibase-modify-column){:target="_blank"}                                                        | Adds `<modifyColumn>` change type                                      |
| [No-op Changelog Lock](https://github.com/liquibase/liquibase-nochangeloglock){:target="_blank"}                                              | Removes "lock" functionality                                           |
| [No-op ChangeLog History](https://github.com/liquibase/liquibase-nochangelogupdate){:target="_blank"}                                         | Removes changeset tracking functionality                               |
| [Percona Online Schema Change](https://github.com/liquibase/liquibase-percona){:target="_blank"}                                              | Support `pt-online-schema-change` from Percona Toolkit                 |
| [SessionLock](https://github.com/blagerweij/liquibase-sessionlock){:target="_blank"}                                                          | Use native locks instead of the DATABASECHANGELOGLOCK table            |
| [SLF4j Logging](https://github.com/mattbertolini/liquibase-slf4j){:target="_blank"}                                                           | Log through SLF4j rather than java.util.Logging                        |
| [Spatial Datatypes](https://lonnyj.github.io/liquibase-spatial/){:target="_blank"}                                                            | Adds support for spatial indexing and geometric types                  |

## Something Missing?

Know of an extension not listed here?

Use the "Edit the Page" icon in the top right to add more.

## Create Your Own

Looking to create your own extension?

Start with the [overview](../extensions-overview/index.md) today!