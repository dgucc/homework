# HYPERJAXB3 

## Working version with src/main/resoures/126-beps13-notification-v1-51.xsd + src/test/samples  
**Execute**  

`$ mvn -Phibernate clean compile exec:java`  

Output :  
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

## Fixes

### javax.persistence.spi::No valid providers found

```xml
<dependency>
   <groupId>org.hibernate</groupId>
   <artifactId>hibernate-core-jakarta</artifactId>
   <version>5.6.9.Final</version>
</dependency>
```
