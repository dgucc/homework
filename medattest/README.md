# MEDATTEST - Export a complete tree object 


  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

## Run
`$ mvn clean compile && mvn spring-boot:run`   

## Test
Export data from database to json form:   

> http://localhost:8080/api/v1/sample/19234011000/0827098610  
> or  
> http://localhost:8080/api/v1/sample/19720494004/0539969009  
> or  
> http://localhost:8080/swagger-ui/index.html  



```sh
$ curl --silent -X GET -L http://localhost:8080/api/v1/sample/19720494004/0539969009 -o temp/json/19720494004-0539969009.json`  

$ curl --silent -X GET -L http://localhost:8080/api/v1/sample/12956329004/0813921060 -o temp/json/12956329004-0813921060.json
```


Visualize json:  
in browser:  temp/json/view-json.html  


---
## Use of generated model based on database

Copy generated model  

```bash
$ cd /c/home/projects/medattest/workspace/sandbox/src/main/java/minfin/sandbox/model
$ cp /c/home/projects/medattest/hibernate-tools/target/generated-sources/*.java .
```

Replace default package name :   
`$ find * -name *.java -exec sed -i -e 's/\/\/ default package/package minfin.sandbox.model;/' {} \; `  

> TODO : fix this in hibernate-tools 

---

DEBUG
- how to marshall : 
https://ramj2ee.blogspot.com/2019/01/xml-marshalling-and-unmarshalling-using_28.html

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ram.core.config.AppConfig;
import com.ram.core.model.Company;

public class App
{
    private static final String XML_FILE_NAME = "company.xml";

    public static void main(String[] args) throws IOException
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                AppConfig.class);
        XMLConverter converter = (XMLConverter) context
                .getBean(XMLConverter.class);

        Company company = new Company();
        company.setId(201);
        company.setCompanyName("Google");
        company.setCeoName("Peter");
        company.setNumberOfEmployees(50000);

        // from object to XML file
        converter.convertFromObjectToXML(company, XML_FILE_NAME);
        System.out.println("Converted Object to XML!");

        // from XML to object
        Company company2 = (Company) converter
                .convertFromXMLToObject(XML_FILE_NAME);
        System.out.println(company2);
        System.out.println("Converted XML to Object!");
       
    }
}


https://github.com/camelya58/spring-jaxb/tree/master/src/main ?
https://medium.com/@schajjaoui/spring-boot-avec-jaxb-46f4faf5561b ?
