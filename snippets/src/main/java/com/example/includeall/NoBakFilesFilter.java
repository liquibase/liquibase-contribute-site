package com.example.includeall;

import liquibase.changelog.IncludeAllFilter;

public class NoBakFilesFilter implements IncludeAllFilter {

    @Override
    public boolean include(String changeLogPath) {
        return !changeLogPath.contains(".bak.");
    }
}
