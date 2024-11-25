[20/11/2024 mer.]

# Working version with src/main/resoures/126-beps13-notification-v1-51.xsd + src/test/samples  
**Execute**  

`$ mvn clean compile exec:java`  
`$ mvn -Phibernate clean compile exec:java`  

Output :  
>[...]
>CompanyNumber : 1234567890  
>CountryCodes :CompanyName  
>test.getId() : 1  
>*** Terminated ***  


**Specify Schema Name** :  
>src\main\resources\META-INF\orm.xml

**Complete src\main\resources\persistence-h2.properties**

```
jakarta.persistence.schema-generation.database.action=drop-and-create
jakarta.persistence.schema-generation.scripts.action=drop-and-create
jakarta.persistence.schema-generation.scripts.create-target=target/test-database-sql/ddl-create.sql
jakarta.persistence.schema-generation.scripts.drop-target=target/test-database-sql/ddl-drop.sql
n
hibernate.cache.provider_class=org.hibernate.cache.HashtableCacheProvider
hibernate.jdbc.batch_size=0
```
**Persistence Unit Name**
- Retrieve the PersistenceUnitName in target\generated-sources\xjc\META-INF\persistence.xml  
- Create a EntityManager Accordingly (App.java) :    
```java
entityManagerFactory = Persistence.createEntityManagerFactory("be.fgov.minfin.beps13.notification.v1:oecd.ties.isocbctypes.v1", persistenceProperties);
```

---
# ChangeLog

Marshall declaration just after persisting it  
to see the generated ID's (cf. attribute Hjid) :  

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns2:Declaration275CBCNOT xmlns="http://beps13.minfin.fgov.be/notification/v1.51" xmlns:ns2="be.fgov.minfin.beps13.entity.notification.v1_51" Hjid="1">
    <Declarer Hjid="1">
        <CompanyName>CompanyName</CompanyName>
        <CompanyNumber>1234567890</CompanyNumber>
        <AssessmentYear>2016</AssessmentYear>
        <ReportingPeriod Hjid="1">
            <StartDate>2014-01-01</StartDate>
            <EndDate>2015-01-01</EndDate>
        </ReportingPeriod>
    </Declarer>
    <UltimateParentCompany Hjid="1">
        <DistinctParentCompany Hjid="1">
            <Name>Name</Name>
            <CountryCode>BE</CountryCode>
            <TIN>TIN</TIN>
            <MotherAdres Hjid="2">
                <Street>Street</Street>
                <Number>Number</Number>
                <PostalCode>PostalCode</PostalCode>
                <City>City</City>
            </MotherAdres>
        </DistinctParentCompany>
    </UltimateParentCompany>
    <IsYourParentCompany>true</IsYourParentCompany>
    <BelgGroupEntITC92 Hjid="1">
        <BelgGroupEnt Hjid="1">
            <NoCBC>true</NoCBC>
            <NoAcord>true</NoAcord>
            <Negligence>true</Negligence>
            <EUOther>false</EUOther>
        </BelgGroupEnt>
    </BelgGroupEntITC92>
    <MotherRep Hjid="1">
        <DistinctReportingCompany Hjid="1">
            <ReportingEntityName>name</ReportingEntityName>
            <ReportingEntityCountrycode>AI</ReportingEntityCountrycode>
            <ReportingEntityTIN></ReportingEntityTIN>
            <ReportingEntityAdress Hjid="1">
                <Street>Street</Street>
                <Number>Number</Number>
                <PostalCode>PostalCode</PostalCode>
                <City>City</City>
            </ReportingEntityAdress>
        </DistinctReportingCompany>
    </MotherRep>
</ns2:Declaration275CBCNOT>
```


---

# Fixes

## javax.persistence.spi::No valid providers found

```xml
<dependency>
   <groupId>org.hibernate</groupId>
   <artifactId>hibernate-core-jakarta</artifactId>
   <version>5.6.9.Final</version>
</dependency>
```
