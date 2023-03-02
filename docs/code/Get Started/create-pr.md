---
title: Create a Pull Request
---

# Creating a Pull Request

## Contributing Etiquette

Please see our [Contributor Code of Conduct](https://github.com/liquibase/liquibase/blob/master/CODE_OF_CONDUCT.md){:target="_blank"} for information on our rules of conduct.

## Requirements

Before creating a pull request, please read our requirements that explain the minimal details to have your PR considered and merged into the codebase.

1. PRs should reference an existing issue(s) that describes the issue or feature being submitted 
    - Include `Fixes #xyz` in your PR description to correctly [link the issue and the PR together in GitHub](https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue){:taget="_blank"}   
    - A PR should be about the "how" not the "what". Allows discussions on "what/why" to change to be separate from the options on "how" to do it  
    - A PR for code cleanup/refactoring/quick-bugfixes can be self-contained and not reference a separate issue
2. PRs must have the description template filled out, describing the changes you are making and why plus the choices and considerations you made
3. PRs must include tests covering the changed behavior or a description of why tests need not/cannot be written
    - See the [unit test](../Test Your Code/unit-tests.md) and [integration test](../Test Your Code/integration-tests.md) pages for more information 

!!! Note
    We appreciate you taking the time to contribute! 

    Before submitting a pull request, please take the time to comment on the issue you are wanting to resolve. 
    This helps us prevent duplicate effort or advise if the team is already addressing the issue.

## Suggestions

- Generally wait to submit your PR until it is complete, but if you want to open the PR early for feedback [mark it as a "draft"](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/changing-the-stage-of-a-pull-request){:target="_blank"}
- If you have questions, ask [on the forum](https://forum.liquibase.org){:target="_blank"} or [Discord](https://discord.gg/pDB5DfE){:target="_blank"}
- Take a look at our [coding style guide](coding-style.md)

## PR Review Process

After submitting your PR, the Liquibase team will add it to the ["Liquibase Open Source" GitHub project](https://github.com/orgs/liquibase/projects/3/views/9){:target="_blank"}.

The normal flow through this board is:

1. The team will do an initial review of PRs in the "New" column
    - The goal is to:
        1. Get feedback to you as quickly as possible while the code is still fresh in your mind
        2. Understand the incoming proposed changes enough to prioritize them in the review process 
    - Is there anything we notice offhand that you are missing? Any surprises in the files changed for a given fix? Any questions we have on the choices you made?
    - This is not the full code review yet, as that can take time. Throughput is the goal at this stage
    - If everything looks good, the PR gets moved to "Planned"
2. The review team works on two-weeks sprints, so every two weeks PRs are moved from the "Planned" column to the "Foundation Team Tickets"
    - PRs in "Planned" are prioritized as part of sprint planning
3. During the sprint, PRs in the "Foundation Team Tickets" column are fully reviewed
    - The goal is to ensure the PR is "ready to ship"
    - This review checks:
        - Completeness of the change -- does it fully address the issue including non-happy paths?
        - Fit and finish -- is functionality, wording, and error messages as people would expect?  
        - Side effects -- is it incorrectly impacting other areas of the code?
        - Documentation -- are documentation changes needed as part of this change?
        - Correctness -- is it doing what it is supposed to? Are the automated tests complete enough?
        - Compatibility -- is it introducing any functional or API-breaking changes?
        - PR description -- does the PR describe the change well enough?
    - This is a multi-person process and the PR will move through several columns as everyone looks at it
        - "Development" as the technical review is done
        - "Code Review" as style and functional reviews are done
        - "Test" as manual testing is done (if required)
    - If there are any questions or concerns found during this process, the team will comment on the PR so please watch your notifications
    - When everything passes, the PR will be moved to "Ready to Merge"
4. After the PR has moved to the "Ready to Merge" column, it will be merged to "master"
    - The master branch is always kept in an "always releasable" state, regardless of when the next release will be
    - As release dates approach, the team may keep some PRs in "Ready to Merge" until the next release depending on the code freeze status
5. Once the PR is merged, it moves to "Done"
    - Merged PRs will go out in the next release
        - The "Milestone" is set to "NEXT" after it has been merged to signify this. The "NEXT" milestone will be renamed to the final version as part of the release process. 
    - The "Done" column is cleaned out after most releases to prepare  
