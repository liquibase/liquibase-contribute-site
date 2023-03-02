---
title: XSD Files
---

# Extension XSD Files

## Overview

If your extension adds functionality that is exposed through the changelog file, you will need an XSD to define your new elements and attributes.

Examples of features that require an XSD

- New Change tags
- Additional attributes on existing Change tags
- New preconditions

The standard Liquibase XSD schema defines only what ships as part of standard Liquibase, therefore without an XSD your users will not be able to use your extension with XML-based changelog files.

!!! note

    The XSD file does not take the place of validation within your code because it is only used in XML parsing -- not for YAML, JSON, or any other changelog formats.
    
    Think of the XSD as primarily an intellisence helper for IDEs that support them.

## Generic Extension XSD

There is an existing `dbchangelog-ext` XML schema at [http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd](http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd) which allows anything.
It makes for an easy way to get started because does not get in the way of any attributes or elements you want users to use. 

However, the downside with this XSD allowing anything is that it therefore does not provide any intellisense or guidance to users. 

### Example

```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
		http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
		http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">

    <changeSet id="1" author="example">
        <ext:setPassword username="test" password="change_on_install"/>
    </changeSet>

</databaseChangeLog>
```

## Custom Extension XSD

If you would like to provide an XSD schema to your users which specifies only what is allowed, you can create your own XSD file.

You can use any `targetNamespace` you prefer, but a common format is `http://www.liquibase.org/xml/ns/dbchangelog-ext/YOUR-EXTENSION`. 

!!! tip

    The namespace name looks like a URL but is not really, and is unrelated to where the XSD is published

### Shipping your XSD 

Many tools such as IDEs will auto-download XSDs from the URL specified in the XML file. 
For this reason, you will want to host your XSD on the Internet at a stable URL.

For security and reliability reasons, Liquibase does **_not_** download XSDs from the network and relies on them being packaged in your .jar file.
Liquibase will look for the file using the URL's hostname + path without the protocol. 

For example, if your XSD is stored at `http://example.com/xml/my/custom-extension.xsd`, Liquibase will look for the file at `/example.com/xml/my/custom-extension.xsd`

### Versioning your XSD

Your XSD schema will likely change as your extension grows, and it's important to let your users know which XSD they should be using. Otherwise, it could be providing them with wrong intellisense and validating incorrectly.

A good pattern is to provide an explicitly named "latest" XSD which always corresponds to your current version. Users can specify it as `http://example.com/xml/my/custom-extension-latest.xsd` and know they will be up to date.

As your extension grows in popularity and releases, you may want to consider also providing versioned XSDs by replacing "latest" with the extension version. For example `http://example.com/xml/my/custom-extension-1.8.xsd`   

Starting with the "-latest" pattern even in your initial releases will make it easier for you to support versioning if and when you need it down the road.

### Example

```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:custom="http://www.liquibase.org/xml/ns/dbchangelog-ext/custom-extension"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
		http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
		http://www.liquibase.org/xml/ns/dbchangelog-ext/custom-extension http://example.com/xml/my/custom-extension.xsd">

    <changeSet id="1" author="example">
        <custom:setPassword username="test" password="change_on_install"/>
    </changeSet>

</databaseChangeLog>
```
