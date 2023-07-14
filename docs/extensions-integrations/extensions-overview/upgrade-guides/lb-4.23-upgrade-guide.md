---
title: To Liquibase 4.23
---

# Liquibase 4.23 Extension Upgrade Guide

## Changes introduced in 4.23.0


Before version 4.23.0, Liquibase would work only with one checksum version simultaneously. But upgrading checksums was a process that could cause errors and lead to missing execution of some changesets using "runOnchange," "runAlways," etc., plus validation changes would be disabled. 4.23.0 version introduced breaking changes to support multiple checksums versions fixing those issues.

The changes were:

1. `liquibase.changelog.ChangeLogHistoryService#isDatabaseChecksumsCompatible` -
Short answer: return FALSE, and it will behave as before. 
Long answer: this method is used by the `Update` command family to know if there are old checksum versions in the database to ensure that all checksums are validated, thus skipping fast check validation (we introduced this feature in Liquibase 4.20.0). It leads to 3 behaviors:
    1. IF `isDatabaseChecksumsCompatible` returns true and there are NO unrun changesets, it means the database is up-to-date. It won't update the checksums even if they are from a previous version and will exit.
    2. IF `isDatabaseChecksumsCompatible` returns true, and there are unrun changesets, then update will upgrade old checksums versions (if any) and execute the unrun ones as usual.
    3. IF `isDatabaseChecksumsCompatible` returns false, then update will upgrade old checksums versions (if there are any) and execute the unrun ones (if there are any) as usual.
    4. On core this is done in the `StandardChangelogService.init()` method (select all md5 checksums from null, if any of them is not `latest().getVersion()`, we set the flag and then return it on the method call later on:
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
2. `liquibase.changelog.ChangeLogHistoryService#getRanChangeSets(boolean)`:  FIX: ignore the parameter and return this.getRanChangeSets() . Long answer: this API was created to set MD5sum to null if boolean is true for upgrade purposes, but after some refactoring, we moved the logic to Update commands, and we should removed it as everywhere it is called only with boolean false - and for core, it is the same as `getRanChangeSets()`. It will be deprecated as of the next version. 
3. `liquibase.changelog.ChangeSet#generateCheckSum` now must take a ChecksumVersion argument. This will calculate the checksum for the current version. So using `ChecksumVersion.latest()` will behave as before (and `latest()`  will always return the latest version - so in the next checksum upgrade, it will return v10).  But if you are loading from the database as in `ChangelogHistoryService`, it's recommended to calculate using the version from the database so you'll get the checksum difference. We use the following code:

```
    ChecksumVersion version = changeSet.getStoredCheckSum() != null ? 
        ChecksumVersion.enumFromChecksumVersion(
            changeSet.getStoredCheckSum().getVersion()) : 
        ChecksumVersion.latest();
```

---
In conclusion, Liquibase 4.23.0 brings significant enhancements to checksum handling, providing the capability to manage multiple checksum versions concurrently. This eliminates potential errors that occurred during checksum upgrades and ensures that no changesets are skipped. As you integrate this version, be sure to thoroughly review the code examples and understand the changes to adapt your practices accordingly.

## Questions and Problems
If you have questions on the API changes or issues with your extension supporting Liquibase 4.23, don't hesitate to contact us by email, on [Discord](https://discord.com/login?redirect_to=%2Fchannels%2F700506481111597066%2F700506481572839505), or on the [Liquibase Forum](https://forum.liquibase.org/).</p>
