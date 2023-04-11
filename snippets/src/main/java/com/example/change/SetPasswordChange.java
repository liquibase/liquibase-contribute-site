package com.example.change;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.UpdateStatement;

@DatabaseChange(
        name = "setPassword",
        description = "Sets a user's password",
        priority = ChangeMetaData.PRIORITY_DEFAULT
)
public class SetPasswordChange extends AbstractChange {
    private String username;
    private String password;

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{
                new UpdateStatement(null, null, "app_users")
                        .addNewColumnValue("password", password)
                        .setWhereClause("username='" + database.escapeStringForDatabase(username) + "'")
        };
    }

    @Override
    public String getConfirmationMessage() {
        return "Password has been changed for " + username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}