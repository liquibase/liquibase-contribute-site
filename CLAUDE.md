# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the source for https://contribute.liquibase.com - a community documentation site that guides contributors to the Liquibase open-source project. Built with MkDocs Material (Python-based static site generator).

## Development Commands

### Run Site Locally
```bash
docker run --rm -it -p 8080:8000 -v ${PWD}:/docs $(docker build -q .)
```
Site available at http://localhost:8080 with hot-reload on file changes.

### Build Site (Strict Mode)
```bash
mkdocs build -s -d _site
```
Uses strict mode (`-s`) to catch broken links and errors.

### Validate Code Snippets
```bash
cd snippets && mvn -B compile
```
Compiles all Java examples to ensure they're valid.

## Architecture

### Directory Structure
- `docs/` - Main content (145 markdown files)
  - `code/` - Developer contribution guides, architecture docs, API reference
  - `extensions-integrations/` - Extension development guides, 20+ database tutorials
  - `answer/`, `write/`, `advocate/` - Community contribution paths
- `snippets/` - Compilable Java code examples (Maven project, Java 8+, liquibase-core 4.20.0)
- `overrides/` - MkDocs theme customizations (home.html, partials/)
- `mkdocs.yml` - Site configuration, plugins, redirects, version variables

### Key Configuration
- **Site theme**: Material for MkDocs with custom overrides
- **Plugins**: git-revision-date-localized (CI only), awesome-pages, macros, redirects
- **Markdown extensions**: admonition, snippets, superfences (mermaid), tabbed, tasklist
- **Version variables** in mkdocs.yml under `extra.liquibase.current_version`

### Navigation
Uses awesome-pages plugin with `.pages` files in directories to control navigation structure.

### Code Snippets System
Java code in `snippets/src/main/java/` is included in docs via pymdownx.snippets extension. All snippets must compile against liquibase-core.

## CI/CD

- **verify.yml**: Runs on PRs - builds site (strict) + compiles Maven snippets
- **publish.yml**: Deploys to GitHub Pages on push to `main`
