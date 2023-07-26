---
title: To Liquibase 4.23
---

# Liquibase 4.23 Extension Upgrade Guide

## Changes introduced in 4.23.0


Before version 4.23.0, Liquibase would only work with the most recent checksum version. Upgrading checksums was a process that could cause errors and lead to missing execution of some changesets using "runOnchange" and "runAlways". Plus, validation changes would be disabled during the upgrade.

Liquibase 4.23.0 significantly enhances checksum handling. By managing multiple checksum versions concurrently, Liquibase provides a seamless checksum upgrade experience that eliminates potential errors and ensures that no changesets are skipped.

If you are a Liquibase user, there is nothing more you need to do. Liquibase will handle the checksum upgrade in the background.

If you are a Liquibase extension or integration creator, this guide is for you. Before you update your extension or integration with this version of Liquibase, review the code examples and understand the changes so you can adapt your practices accordingly.

Liquibase 4.23.0 introduces breaking changes to the Liquibase Java API to support multiple checksums versions. You should update your extension or integration if your code implements any of the services or methods listed below.

The changes were:

1. `liquibase.changelog.ChangeLogHistoryService#isDatabaseChecksumsCompatible` -
Short answer: return FALSE, and it will behave as before. 
Long answer: this method is used by the `Update` command family to know if there are old checksum versions in the database to ensure that all checksums are validated, thus skipping fast check validation (we introduced this feature in Liquibase 4.20.0). It leads to 3 behaviors:
    1. IF `isDatabaseChecksumsCompatible` returns true and there are NO unrun changesets, it means the database is up-to-date. It won't update the checksums even if they are from a previous version and will exit.
    2. IF `isDatabaseChecksumsCompatible` returns true, and there are unrun changesets, then update will upgrade old checksums versions (if any) and execute the unrun ones as usual.
    3. IF `isDatabaseChecksumsCompatible` returns false, then update will upgrade old checksums versions (if there are any) and execute the unrun ones (if there are any) as usual.
    4. For Liquibase Core this is done in the `StandardChangelogService.init()` method (select all md5 checksums from null, if any of them is not `latest().getVersion()`, we set the flag and then return it on the method call later on:
```
SqlStatement databaseChangeLogStatement = 
    new SelectFromDatabaseChangeLogStatement(
        new SelectFromDatabaseChangeLogStatement.ByNotNullCheckSum(),
        new ColumnConfig().setName("MD5SUM"));
List<Map<String, ?>> md5sumRS = 
    ChangelogJdbcMdcListener.query(
        getDatabase(), 
        ex -> ex.queryForList(databaseChangeLogStatement)); 
if (!md5sumRS.isEmpty()) {
    //check if any checksum is not using the current version 
    databaseChecksumsCompatible = 
        md5sumRS.stream().allMatch(
            m -> m.get("MD5SUM").toString().startsWith(
                ChecksumVersion.latest().getVersion() + ":"));
}
``` 
2. `liquibase.changelog.ChangeLogHistoryService#getRanChangeSets(boolean)`:  FIX: ignore the parameter and return this.getRanChangeSets() . Long answer: this API was created to set MD5sum to null if boolean is true for upgrade purposes, but after some refactoring, we moved the logic to Update commands, and we removed it since it is called only with boolean false - and for Liquibase Core, it is the same as `getRanChangeSets()`. It will be deprecated as of the next version. 
3. `liquibase.changelog.ChangeSet#generateCheckSum` now must take a ChecksumVersion argument. This will calculate the checksum for the current version. So using `ChecksumVersion.latest()` will behave as before (and `latest()` will always return the latest version - so in the next checksum upgrade, it will return v10).  But if you are loading from the database as in `ChangelogHistoryService`, it's recommended to calculate using the version from the database so you'll get the checksum difference. We use the following code:

```
    ChecksumVersion version = changeSet.getStoredCheckSum() != null ? 
        ChecksumVersion.enumFromChecksumVersion(
            changeSet.getStoredCheckSum().getVersion()) : 
        ChecksumVersion.latest();
```

## Questions and Problems
If you have questions on the API changes or issues with your extension supporting Liquibase 4.23, don't hesitate to contact us on the [Liquibase Forum](https://forum.liquibase.org/) or [Discord](https://discord.com/login?redirect_to=%2Fchannels%2F700506481111597066%2F700506481572839505).</p>
