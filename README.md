## Source for contribute.liquibase.com

This contains the source for https://contribute.liquibase.com, written using https://squidfunk.github.io/mkdocs-material/

## Code Structure

- The `docs` directory contains the main content for the site
- The `overrides` directory contains custom template configurations
- The `snippets` directory contains reusable documentation blocks

## Contributing Changes

### Use the "Rate" Button

The fastest way to contribute changes is to use the "How was your experience?" button on the bottom of each page. 
This let you suggest changes directly from your browser, and they will be reviewed by the team. 

Suggestions can range from simple "This sentence is confusing" to larger "I'm trying to understand X, and am not able to find the content I need. What I'd really like is...".

This works great for small and/or scattered suggestions

### Open an Issue

An alternative to the "feedback" button for general suggestions is to [open an issue on this repository](https://github.com/liquibase/liquibase-contribute-site/issues).

It's a bit more work than the feedback button, but it will be publicly visible and trackable, which allows for easier collaboration with the overall community. 

### Use the "Edit this Page" Button

On the top right of every page is a grey "Edit this Page" icon. 

The button will open a web editor for the page you are on, allowing you to make changes directly in your browser. The changes you suggest will be submitted as a pull request for review.

It requires no local setup, but only allows you to include a single page's changes in your PR and you can't really see the final content as you work.
That means it works well for small and/or scattered suggestions, but if you are making larger changes that cross pages and/or add significant content you will likely want to make and test the changes locally.

## How to Edit and Test Changes Locally

### Clone the repo to your local machine
1. [Download GitHub Desktop](https://desktop.github.com)

2. Clone this repository to your local machine
<img src="https://github.com/liquibase/liquibase-contribute-site/assets/5502833/2bb707f0-a0b2-41cd-9f23-96bc76324b23" width="50%"/>

3. Create a branch for your changes
  - Select the "Current Branch" dropdown
  <img src="https://github.com/liquibase/liquibase-contribute-site/assets/5502833/7373c4a6-4288-446c-893a-b59ce8ebd265" width="50%"/>
  
  - Enter the branch name and press the "Create Branch" button
   <img src="https://github.com/liquibase/liquibase-contribute-site/assets/5502833/c3e7e9cb-3f95-4e1b-b026-1231324c906e" width="50%"/>

  - Press the "Publish branch" button
   <img src="https://github.com/liquibase/liquibase-contribute-site/assets/5502833/21f4977d-94b0-4a6d-b475-d5fb8af65e6a" width="50%"/>

4. You should be on the new branch and are ready to start making changes.

### Run the Site Locally

1. `cd <contribute site github folder>`
1. In bash or powershell, type `docker run --rm -it -p 8080:8000 -v ${PWD}:/docs $(docker build -q .)`
1. If successful, you should see `Serving on http://0.0.0.0:8000/` in the output. 
1. Open a browser to `http://localhost:8080` to view the site 

### Edit Content

Edit the markdown files in the `docs` directory using whatever editor your prefer. Generally one with spelling and grammar check is good. Markdown syntax highlighting is helpful, but it does not need "markdown preview" support.

Each time you save a file, the site will automatically rebuild and refresh in your browser. The cycle of edit, save, refresh is very fast and works well.

Commit your changes and push them to your fork as you work.

### Submit Your Changes

When you are ready for the team to review your changes, open a pull request from your fork's branch to this repository's `master` branch. 

The team will review your changes and provide feedback, and as soon as it's merged to `master` your changes will be live.

