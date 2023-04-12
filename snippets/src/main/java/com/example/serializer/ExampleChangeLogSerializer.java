package com.example.serializer;

import liquibase.GlobalConfiguration;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.LiquibaseSerializable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExampleChangeLogSerializer implements ChangeLogSerializer {

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{".my"};
    }


    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }


    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
        String outputEncoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();

        for (T child : children) {
            out.write(serialize(child, true).getBytes(outputEncoding));
        }
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        if (object instanceof ChangeSet) {
            return "my formatted changeset"; //TODO: format changeset
        } else {
            return "my formatted " + object.getClass().getName(); //TODO: format other object types
        }
    }

    @Override
    public void append(ChangeSet changeSet, File changeLogFile) throws IOException {
        String serialized = serialize(changeSet, true);

        //TODO open changelogFile as a stream and append "serialized"
    }

}
