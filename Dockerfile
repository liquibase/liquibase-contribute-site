FROM squidfunk/mkdocs-material

RUN pip install --no-cache-dir \
    mkdocs-git-revision-date-localized-plugin \
    mkdocs-awesome-pages-plugin

ENTRYPOINT ["mkdocs"]
CMD ["serve", "--dev-addr=0.0.0.0:8000"]