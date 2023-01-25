---
title: Compatibility
---

## Functional Compatibility

As your extension grows and evolves, it is important to not break your existing users.
New versions should strive to introduce new functionality without changing the behavior people rely on.

### User Expectations

Your users should be able to rely on the following from your extension when they upgrade:

1. Running with their existing changelogs and unchanged command arguments will continue to work
1. Bugs that were troubling them before may be fixed now
1. They can take advantage of new features if and when they want to
1. [Communication](#communicate) from you on what is changing 

## Functional Compatibility Strategies

### Be Additive

The natural progression of an extension is to start with the simplest use cases and build on those. 
Take advantage of that progression to think of each change you want to make as adding to what is already there -- preserving the existing functionality while enhancing it.

You are adding new change types and attributes. You are adding new settings. You are adding new preconditions. You are **_NOT_** renaming or removing things.

This requires a little thought and planning as you are creating your features because you should always be thinking about possible directions you may go.
Don't paint yourself into a corner. 

Some tips to keep future work additive:

- **Take a few minutes up front to think about names.** You are "stuck" with them long-term, don't make future-you upset with your choices. Make sure they are spelled correctly and as simple to understand as possible 
- **Avoid simple true/false values for settings unless they will only ever be true or false values.** For example, instead of a `readFromFile=true|false` setting consider a `readFrom=FILE|INPUT` setting. Not only can that be more user-friendly, but it also allows you to add more options down the road like "HTTP" without breaking compatibility
- **Brainstorm future directions.** Think about where your extension _may_ go and whether those directions fit cleanly into what you are adding. If they won't, is there a way to shift what you are adding now? Most ideas will never happen so don't get too wrapped up in this, but make sure you have at least general ideas of where you can go. 

### Be Optional

Related to "be additive", any new functionality should be optional. 

This means new settings **_cannot_** be required. If you added a new `readFrom` setting, it needs a default behavior of wherever the original behavior was. 
If you made it a required field, it will block existing users who will not have the setting in their existing setup. 

Adding a sensible default to a previously-required field to make it optional does not have the same problem. All existing users will have the flag, and it will continue to work even though it's no longer required. 

This also means that changes to behavior should be opt-in. Extensions are able to define additional global configuration options which can be used as feature flags for your extension.
For example, if you used to always lowercase and collapse whitespace and want to change that behavior, instead of forcing that change on everyone make a new global flag to optionally enable it.

### Provide a Deprecation Phase

Despite your best efforts, there will be times you need to make changes that are not additive. 

- Perhaps you had originally created a `modifyColumn` change type and later realized it is too complex and can only be supported as more targeted changes like `renameColumn` and `modifyDataType`.
- Perhaps you had originally created a `readFromFile=true|false` setting and later realized it should be `readFrom=FILE|INPUT`. 

In these scenarios, don't suddenly drop support for the old behavior but instead deprecate it for a period of time to give your users a chance to make the needed changes.
Keep the existing code but add a warn-level log message specifying it has been deprecated and will be removed in a future version along with what they need to change.

For configuration options, the Liquibase supports "alias" definitions. This allows you to rename the setting to what you want it to be now and add the old version as an "alias". This keeps the old version working while guiding people to the new version.

The answer to **_when_** to remove the deprecated functionality is always "as long from now as possible". 
You will have users who see your message, but will not act on it because it's currently working and they have bigger fires to fight. You will also have users who rarely upgrade and are not even seeing your deprecation warning yet.
The longer the deprecation phase the fewer people will be impacted when you remove the now-unwanted functionality, so push it off as long as you can.  

### Load Bearing Bugs 

A tough area to handle with preserving compatibility is behavior that is "broken" for most people but happens to work for some and those people rely on it.

For example, maybe you defined a field on your change type like `dropIfExists` and there are some databases where you can't support that but forgot to throw a validation error when you can't.
This is a bug in that a user will put `dropIfExists="true"` and it won't actually be dropped as they want. But, there may be people who know it's not supported and are OK with it just being skipped on databases it's not supported on.
If you leave the behavior, it confuses people. If you change the behavior, it breaks existing users. What do you do?

Another example is a bad default value. Default values should be the most reasonable for the most number of people, but sometimes that ends up not the case.
When you find that a default value needs to be changed it's generally obvious it should be different and causes problems for users, but there are people expecting that "non-obvious" current behavior. What do you do?    

The answer will depend on how many people are impacted and what their recovery path looks like. 

The fewer the people impacted, or the easier the recovery pattern the closer it is to a "regular" bug you can just fix.

- How recently was this behavior introduced? The more recently it was introduced, the less reliance there will be.
- Is the feature something commonly used? Or more of an edge case? The more common, the more careful you have to be.
- Is there a way to make the recovery easier? Can you detect when they are possibly impacted and tell them what to change via the program output?

## Communicate

When in doubt, over-communicate. Make sure you users know what is going on and why.

**Release notes** are your first line for communicating changes so make sure you are clear and explicit in anything that could possibly cause problems for users.
It's usually best to call out a "Breaking Changes" section at the top rather than burying them in the details of feature changes.
Don't just list the break, but make sure to give people easy instructions on whether they are impacted and what they should do if they are.

However, remember that **people don't always read your release notes**. Especially when they are jumping several versions in an upgrade.
Therefore, think about the error messages and warnings you provide to users and make sure they are good guides for impacted users.

Finally, take advantage of **semantic versioning**. The version number changes should give users a hint as to how much they should worry about the upgrade.

- If you are synchronizing your extension's version to Liquibase's version, you'll have to coordinate your potentially-breaking changes with non-patch-level releases of Liquibase.
Because Liquibase only rarely does "major version" increases, you will have to use "x.y.0" versions for your potentially breaking changes. 
- If you have a version number that is completely independent of the corresponding Liquibase version, consider major version increases when making breaking changes.

## API Compatibility

Most extensions are adding specific functionality into Liquibase, and not trying to act as a library for other extensions to build on.
Therefore, extensions generally do not have to worry about API compatibility between releases like liquibase-core does. 
That means you can rename classes and methods as needed to keep your code maintainable without worrying about breaking other extensions.

However, keep an eye on how your extension is being used over time. If it becomes popular enough, it may itself be extended and you may
need to start thinking about API compatibility in addition to functional compatibility. 

## Breaking Changes to Avoid

These are some specific examples of what constitutes a breaking change. Use the above strategies to avoid them when possible. 

- Bumped minimum JDK major version. Example: JDK 17 required instead of JDK 8
- Rename of a serialized change name or any of its child elements. Example: `<cypher>` is renamed to `<query>`
- Rename of a serialized attribute. Example: `<extractProperty property="foo">` is renamed to `<extractProperty key="foo">`
- Rename of a serialized Java type serialized in snapshot yaml. Example: `com.example.change.refactoring.RelationshipDirection` is moved to `graph.RelationshipDirection`)<sup>1</sup>
