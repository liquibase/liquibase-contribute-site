## Contributing

Thank you for your interest in contributing to Liquibase! :tada:


- [Contributing Etiquette](#contributing-etiquette)
- [Creating an Issue](#creating-an-issue)
    * [Creating a Good Code Reproduction](#creating-a-good-code-reproduction)
- [Creating a Pull Request](#creating-a-pull-request)
    * [Requirements](#requirements)
    * [Setup](#setup)
    * [Core](#core)
        + [Modifying Components](#modifying-components)
        + [Preview Changes](#preview-changes)
        + [Lint Changes](#lint-changes)
        + [Modifying Documentation](#modifying-documentation)
        + [Modifying Tests](#modifying-tests)
            - [Screenshot Tests](#screenshot-tests)
        + [Building Changes](#building-changes)
    * [Angular, React, and Vue](#angular-react-and-vue)
        + [Modifying Files](#modifying-files)
        + [Preview Changes](#preview-changes-1)
        + [Lint Changes](#lint-changes-1)
        + [Modifying Tests](#modifying-tests-1)
        + [Building Changes](#building-changes-1)
    * [Submit Pull Request](#submit-pull-request)
- [Commit Message Guidelines](#commit-message-guidelines)
    * [Commit Message Format](#commit-message-format)
    * [Revert](#revert)
    * [Type](#type)
    * [Scope](#scope)
    * [Subject](#subject)
    * [Body](#body)
    * [Footer](#footer)
    * [Examples](#examples)
- [License](#license)


## Contributing Etiquette

Please see our [Contributor Code of Conduct](https://github.com/liquibase/liquibase/blob/master/CODE_OF_CONDUCT.md) for information on our rules of conduct.


## Creating an Issue

* If you have a question about using the liquibase, please ask on the [Liquibase Forum](http://forum.liquibase.org/) or in the [Liquibase Discord](https://discord.gg/9yBwMtj).
* It is required that you clearly describe the steps necessary to reproduce the issue you are running into. Although we would love to help our users as much as possible, diagnosing issues without clear reproduction steps is extremely time-consuming and simply not sustainable.
* The issue list of this repository is exclusively for bug reports and feature requests. Non-conforming issues will be closed immediately.
* If you think you have found a bug, or have a new feature idea, please start by making sure it hasn't already been [reported](https://github.com/liquibase/liquibase/issues?utf8=%E2%9C%93&q=is%3Aissue). You can search through existing issues to see if there is a similar one reported. Include closed issues as it may have been closed with a solution.

* Next, [create a new issue](https://github.com/liquibase/liquibase/issues/new/choose) that thoroughly explains the problem. Please fill out the populated issue form before submitting the issue.

## Creating a Pull Request

Before creating a pull request, please read our requirements that explains the minimal details to have your PR considered and merged into the codebase.

### Requirements
1. PRs should reference a existing issue(s) that describes the issue or feature being submitted. A PR should be about the "how" not the "what", but for code cleanup/refactoring/quick-bugfixes the PR can be self-contained and not reference an issue. 
2. PRs must have the description template filled out, describing the changes you are making and why plus choices and considerations you made.
3. PRs must include tests covering the changed behavior or a description of why tests cannot be written.

> Note: We appreciate you taking the time to contribute! Before submitting a pull request, please take the time to comment on the issue you are wanting to resolve. This helps us prevent duplicate effort or advise if the team is already addressing the issue.

* Looking for an issue to fix? Look through our issues with the [good first issue](https://github.com/liquibase/liquibase/issues?q=is%3Aopen+is%3Aissue+label%3A%22good+first+issue%22) label!

### Setup

#### Minimal Setup

1. [Install Java](https://adoptium.net/). Any version or distribution of Java will work, but the current LTS version is usually best
1. [Install Maven](https://maven.apache.org/download.cgi). Unzip the archive to a directory on your system. This provides the `mvn` CLI. 
1. Fork this repository.
1. Clone your fork.
1. Create a new branch from master for your change. Name it something descriptive (but short).
1. Run `mvn package` which compiles Liquibase, runs the tests, and creates the jar/tar.gz packages

#### IDE Setup

The "Minimal Setup" section builds and tests your local branch, but generally you will want to use and IDE like [VS Code](https://code.visualstudio.com/), 
[IntelliJ IDEA](https://www.jetbrains.com/idea/), or your own favorite environment.  

Both IDEs allow you to import that base pom.xml file as your "project" which keeps the libraries and build logic the IDE uses in sync with
what is defined in canonical pom.xml that Maven uses.

### Running Builds

#### Maven Builds

Running `mvn clean install` will build your local code and package the CLI archive as `liquibase-dist/target/liquibase-0-SNAPSHOT.tar.gz`.
Untarring this file will get you a complete "liquibase home" directory which you can use like any other release distribution for running your local code.

This will also install the `liquibase-maven-plugin` to your local Maven cache as version `0-SNAPSHOT` which allows you to run the local build of the maven plugin code.
To use it in a test project, update the plugin reference to:

```xml
<plugin>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-maven-plugin</artifactId>
    <version>0-SNAPSHOT</version>
    <!-- ... additional settings ... -->
</plugin>
```
#### Running the CLI In your IDE

The maven-built package listed above works, but it's the most efficient way of working. The build process takes a while, and it's difficult to enable debug support.
Instead, when using an IDE with more "native" support for running classes, you can run the CLI class more directly.

Exactly how you configure your IDE to run the CLI depend on the version you are using, but the general process is to create a new "Run Configuration" with the following settings:
- Class to run: `liquibase.integration.commandline.LiquibaseLauncher`
- Module Classpath: `liquibase-cli`
- Environment Variable: `LIQUIBASE_HOME=<PATH_TO_A_LIQUIBASE_HOME>`
- Environment Variable: `LIQUIBASE_LAUNCHER_PARENT_CLASSLOADER=thread`
- Working Directory: `<PATH_TO_A_LIQUIBASE_HOME>`
- Program Arguments: Whatever you are looking to test

Both the LIQUIBASE_HOME and working directory should be set to a directory where you installed a previous version of Liquibase as a starting point. 
When Liquibase runs, it will use the jars in `lib` and `internal/lib` so you can add any needed drivers or extensions to those directories like you would
in a production build.

Your run configuration should allow you to successfully run Liquibase in either regular mode or debug mode. 
It's always good to ensure you can successfully run the CLI from the current master branch before making changes locally. 

### Working with the Code

#### High Level Layout

The Liquibase repository is divided into a series of submodules, including:
- `liquibase-core` which contains most of the code
- `liquibase-cli` and `liquibase-emaven-plugin` which include the CLI and Maven Plugin integrations respectively
- `liquibase-dist` which contains the final "distributions"
- `liquibase-integration-tests` which contains tests that run against databases
- And more

Within each submodule, the code is structured following the Maven standard layout, with:
- `src` containing all the source code
- `src/main` containing the "production" code
- `src/main/java` containing the "production" Java code
- `src/main/resources` containing static files for the "production" artifacts
- `src/test` containing the "test" code
- `src/test/java` and `src/test/groovy` containing the "test" java and groovy code
- `src/test/resources`containing static files for use in tests


#### Modifying Components

1. Locate the component(s) to modify inside `/core/src/components/`.
2. Take a look at the [Stencil Documentation](https://stenciljs.com/docs/introduction/) and other components to understand the implementation of these components.
3. Make your changes to the component. If the change is overly complex or out of the ordinary, add comments so we can understand the changes.
4. [Preview your changes](#preview-changes) locally.
5. [Modify the documentation](#modifying-documentation) if needed.
6. [Run lint](#lint-changes) on the directory and make sure there are no errors.
7. [Build the project](#building-changes).
8. After the build is finished, commit the changes. Please follow the [commit message format](#commit-message-format) for every commit.
9. [Submit a Pull Request](#submit-pull-request) of your changes.


#### Preview Changes

1. Run `npm start` from within the `core` directory.
2. A browser should open at `http://localhost:3333/`.
3. From here, navigate to one of the component's tests to preview your changes.
4. If a test showing your change doesn't exist, [add a new test or update an existing one](#modifying-tests).
5. To test in RTL mode, once you are in the desired component's test, add `?rtl=true` at the end of the url; for example: `http://localhost:3333/src/components/alert/test/basic?rtl=true`.


#### Lint Changes

1. Run `npm run lint` to lint the TypeScript and Sass.
2. If there are lint errors, run `npm run lint.fix` to automatically fix any errors. Repeat step 1 to ensure the errors have been fixed, and manually fix them if not.
3. To lint and fix only TypeScript errors, run `npm run lint.ts` and `npm run lint.ts.fix`, respectively.
4. To lint and fix only Sass errors, run `npm run lint.sass` and `npm run lint.sass.fix`, respectively.


#### Modifying Documentation

- Changes to manually written documentation should be made in the `ionic-docs` repo: https://github.com/ionic-team/ionic-docs/tree/main/docs
    - In your `ionic-docs` PR, please add a link back to the related `ionic-framework` PR.
- Changes to auto generated documentation should be made in the `ionic-framework` repo. These can be done in the same PR as your fix or feature.
    - Run `npm run build` and commit all updates to ensure your changes make it into the generated documentation.
    - `Usage`: update the component's usage examples in the component's `usage/` directory.
    - `Properties`, `Events`, or `Methods`: update the component's TypeScript file (`*.tsx`).
    - `CSS Custom Properties`: update the component's main Sass file (`*.scss`).


#### Modifying Tests

1. Locate the test to modify inside the `test/` folder in the component's directory.
2. If a test exists, modify the test by adding an example to reproduce the problem fixed or feature added.
3. If a new test is needed, the easiest way is to copy the `basic/` directory from the component's `test/` directory, rename it, and edit the content in both the `index.html` and `e2e.ts` file (see [Screenshot Tests](#screenshot-tests) for more information on this file).
4. The `preview/` directory is used in the documentation as a demo. Only update this test if there is a bug in the test or if the API has a change that hasn't been updated in the test.

##### Screenshot Tests

1. If the test exists in screenshot, there will be a file named `e2e.ts` in the directory of the test.
2. A screenshot test can be added by including this file and adding one or more `test()` calls that include a call to `page.compareScreenshot()`. See [Stencil end-to-end testing](https://stenciljs.com/docs/end-to-end-testing) and existing tests in `core/` for examples.
3. **Important:** each `test()` should have only one screenshot (`page.compareScreenshot()`) call **or** it should check the expect at the end of each test. If there is a mismatch it will fail the test which will prevent the rest of the test from running, i.e. if the first screenshot fails the remaining screenshot calls would not be called _unless_ they are in a separate test or all of the expects are called at the end.
4. To run screenshot locally, use the following command: `npm run test.screenshot`.
    - To run screenshot for a specific test, pass the path to the test or a string to search for.
    - For example, running all `alert` tests: `npm run test.screenshot alert`.
    - Or, running the basic `alert` tests: `npm run test.screenshot src/components/alert/test/basic/e2e.ts`.


#### Building Changes

1. Once all changes have been made and the documentation has been updated, run `npm run build` inside of the `core` directory. This will add your changes to any auto-generated files, if necessary.
2. Review the changes and, if everything looks correct, [commit](#commit-message-format) the changes.
3. Make sure the build has finished before committing. If you made changes to the documentation, properties, methods, or anything else that requires an update to a generate file, this needs to be committed.
4. After the changes have been pushed, publish the branch and [create a pull request](#creating-a-pull-request).

### Submit Pull Request

1. [Create a new pull request](https://github.com/ionic-team/ionic/compare) with the `main` branch as the `base`. You may need to click on `compare across forks` to find your changes.
2. See the [Creating a pull request from a fork](https://help.github.com/articles/creating-a-pull-request-from-a-fork/) GitHub help article for more information.
3. Please fill out the provided Pull Request template to the best of your ability and include any issues that are related.

### Review Process for Feature PRs

The team has an internal design process for new Ionic features, which must be completed before the PR can be reviewed or merged. As a result of the design process, community feature PRs are subject to large changes. In some cases, the team may instead create a separate PR using pieces of the community PR. Either way, you will always receive co-author commit credit when the feature is merged.

To expedite the process, please ensure that all feature PRs have an associated issue created, with a clear use case for why the feature should be added to Ionic.


## Commit Message Guidelines

We have very precise rules over how our git commit messages should be formatted. This leads to readable messages that are easy to follow when looking through the project history. We also use the git commit messages to generate our [changelog](https://github.com/ionic-team/ionic/blob/main/CHANGELOG.md). Our format closely resembles Angular's [commit message guidelines](https://github.com/angular/angular/blob/main/CONTRIBUTING.md#commit).

### Commit Message Format

We follow the [Conventional Commits specification](https://www.conventionalcommits.org/). A commit message consists of a **header**, **body** and **footer**.  The header has a **type**, **scope** and **subject**:

```
<type>(<scope>): <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

The **header** is mandatory and the **scope** of the header is optional.

### Revert

If the commit reverts a previous commit, it should begin with `revert: `, followed by the header of the reverted commit. In the body it should say: `This reverts commit <hash>.`, where the hash is the SHA of the commit being reverted.

### Type

If the prefix is `feat`, `fix` or `perf`, it will appear in the changelog. However if there is any [BREAKING CHANGE](#footer), the commit will always appear in the changelog.

Must be one of the following:

* **feat**: A new feature
* **fix**: A bug fix
* **docs**: Documentation only changes
* **style**: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
* **refactor**: A code change that neither fixes a bug nor adds a feature
* **perf**: A code change that improves performance
* **test**: Adding missing tests
* **chore**: Changes to the build process or auxiliary tools and libraries such as documentation generation

### Scope

The scope can be anything specifying place of the commit change. Usually it will refer to a component but it can also refer to a utility. For example `action-sheet`, `button`, `css`, `menu`, `nav`, etc. If you make multiple commits for the same component, please keep the naming of this component consistent. For example, if you make a change to navigation and the first commit is `fix(nav)`, you should continue to use `nav` for any more commits related to navigation. As a general rule, if you're modifying a component use the name of the folder.

### Subject

The subject contains succinct description of the change:

* use the imperative, present tense: "change" not "changed" nor "changes"
* do not capitalize first letter
* do not place a period `.` at the end
* entire length of the commit message must not go over 50 characters
* describe what the commit does, not what issue it relates to or fixes
* **be brief, yet descriptive** - we should have a good understanding of what the commit does by reading the subject

### Body

Just as in the **subject**, use the imperative, present tense: "change" not "changed" nor "changes".
The body should include the motivation for the change and contrast this with previous behavior.

### Footer

The footer should contain any information about **Breaking Changes** and is also the place to
reference GitHub issues that this commit **Closes**.

**Breaking Changes** should start with the word `BREAKING CHANGE:` with a space or two newlines. The rest of the commit message is then used for this.

### Examples

Does not appear in the generated changelog:

```
docs(changelog): update steps to update
```

Appears under "Features" header, toast subheader:

```
feat(toast): add 'buttons' property
```

Appears under "Bug Fixes" header, skeleton-text subheader, with a link to issue #28:

```
fix(skeleton-text): use proper color when animated

closes #28
```

Appears under "Performance Improvements" header, and under "Breaking Changes" with the breaking change explanation:

```
perf(css): remove all css utility attributes

BREAKING CHANGE: The CSS utility attributes have been removed. Use CSS classes instead.
```

Appears under "Breaking Changes" with the breaking change explanation:

```
refactor(animations): update to new animation system

BREAKING CHANGE:

Removes the old animation system to use the new Ionic animations.
```

The following commit and commit `667ecc1` do not appear in the changelog if they are under the same release. If not, the revert commit appears under the "Reverts" header.

```
revert: feat(skeleton-text): add animated property

This reverts commit 667ecc1654a317a13331b17617d973392f415f02.
```


## License

By contributing your code to the ionic-team/ionic GitHub Repository, you agree to license your contribution under the MIT license.

