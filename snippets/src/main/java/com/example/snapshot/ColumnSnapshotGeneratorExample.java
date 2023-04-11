package com.example.snapshot;

import com.example.database.ExampleDatabase;
import liquibase.database.Database;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.jvm.ColumnSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;

public class ColumnSnapshotGeneratorExample extends ColumnSnapshotGenerator {

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof ExampleDatabase) {
            return super.getPriority(objectType, database);
        } else {
            return PRIORITY_NONE;
        }
    }

    @Override
    public Class<? extends SnapshotGenerator>[] replaces() {
        return new Class[] {
                ColumnSnapshotGenerator.class
        };
    }

    @Override
    protected Object readDefaultValue(CachedRow columnMetadataResultSet, Column columnInfo, Database database) {
        if (columnInfo.getType().getTypeName().equalsIgnoreCase("json")) {
            return columnMetadataResultSet.get("default_value_json");
        } else {
            return super.readDefaultValue(columnMetadataResultSet, columnInfo, database);
        }
    }
}