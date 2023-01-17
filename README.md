## Repo for contribute.liquibase.com

This contains the source for https://contribute.liquibase.com, written using https://squidfunk.github.io/mkdocs-material/

The source for the site is in `docs`

To run the site locally, run `docker run --rm -it -p 8080:8000 -v ${PWD}:/docs $(docker build -q .)` 
in bash or powershell, then go to `http://localhost:8080`