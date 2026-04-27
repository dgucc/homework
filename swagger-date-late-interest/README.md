# demo : Calcul de la date des intérêts de retard - Rest Service

- Springboot
- Java 17
- hsqldb

```bash
$ mvn17 clean install
$ mvn17 spring-boot:run
```

Input :   
Année d'exercice : taxYear    
Date de bilan : balanceDate  
Date d'envoi : sendingDate  
Date exécutoire : executionDate  
Taux : Rate  
Intérêts non standard : nonStandardInterest  
Date intérêts de retard : dueDateOfInterest  


Exemples [TODO]  

cf. https://minfin.atlassian.net/wiki/spaces/CALCISOC/pages/6528729593/Tests+code+java+versus+exemples+du+Business


## TODO 

Refactoring : rename DueDateOfInterest -> DateOfLateInterest

<details>
<summary>grep -r 'DueDateOfInterest' --exclude-dir=target --exclude-dir=.git</summary>

```bash
$ grep -r 'DueDateOfInterest' --exclude-dir=target --exclude-dir=.git
src/main/java/demo/controller/DueDateOfInterestController.java:import demo.dto.DueDateOfInterestRequest;
src/main/java/demo/controller/DueDateOfInterestController.java:import demo.dto.DueDateOfInterestResponse;
src/main/java/demo/controller/DueDateOfInterestController.java:import demo.service.DueDateOfInterestService;
src/main/java/demo/controller/DueDateOfInterestController.java:public class DueDateOfInterestController {
src/main/java/demo/controller/DueDateOfInterestController.java:    private DueDateOfInterestService service;
src/main/java/demo/controller/DueDateOfInterestController.java:        @ApiResponse(responseCode = "200", description = "Success : Due Date of Interest calculated",content = @Content(mediaType = "application/json", schema = @Schema(implementation = DueDateOfInterestResponse.class))),
src/main/java/demo/controller/DueDateOfInterestController.java:    public DueDateOfInterestResponse calculateDueDateOfInterest(@Valid @RequestBody DueDateOfInterestRequest request){
src/main/java/demo/controller/DueDateOfInterestController.java:        return service.calculateDueDateOfInterest(request);
src/main/java/demo/dto/DueDateOfInterestRequest.java:public class DueDateOfInterestRequest {
src/main/java/demo/dto/DueDateOfInterestResponse.java:public class DueDateOfInterestResponse {
src/main/java/demo/service/DueDateOfInterestService.java:import demo.dto.DueDateOfInterestRequest;
src/main/java/demo/service/DueDateOfInterestService.java:import demo.dto.DueDateOfInterestResponse;
src/main/java/demo/service/DueDateOfInterestService.java:public class DueDateOfInterestService {
src/main/java/demo/service/DueDateOfInterestService.java:    private static final Logger logger = LoggerFactory.getLogger(DueDateOfInterestService.class);
src/main/java/demo/service/DueDateOfInterestService.java:    public DueDateOfInterestResponse calculateDueDateOfInterest(DueDateOfInterestRequest request){
src/main/java/demo/service/DueDateOfInterestService.java:        DueDateOfInterestResponse response = new DueDateOfInterestResponse();
src/main/java/demo/service/DueDateOfInterestService.java:        response.setDueDateOfInterest(calculateDueDateOfInterest(
src/main/java/demo/service/DueDateOfInterestService.java:    private static LocalDate calculateDueDateOfInterest(LocalDate dateBilan, LocalDate uitvoerDatum, LocalDate verzendingDatum, Integer taux, Integer taxYear) {
```
</details>

---

Swagger-UI
http://localhost:8080/swagger-ui/index.html#/


https://www.geeksforgeeks.org/spring-boot-jpa-native-query-with-example/


# Tips  

CSV to json using  "column"  

`$ cat doc/testcases.csv | column -t -s ';' -N 'number,taxYear,balanceDate,executionDate,sendingDate,dueDateOfInterest' -n testcases  -J > doc/output.json` 


JQ  

```bash
$ cat doc/output.json | jq '[.testcases[] | {taxYear,balanceDate,executionDate,sendingDate,rate:5000,withNonStandardInterest:false,dueDateOfInterest}]' > doc/testcases.json
[
  {
    "taxyear": "Exercice",
    "balancedate": "DateBilan",
    "sendingdate": "DateEnvoi",
    "executiondate": "DateExecutoire",
    "rate": 5000,
    "withNonStandardInterest": false
  },
  {
    "taxyear": "2025",
    "balancedate": "2024-12-31",
    "sendingdate": null,
    "executiondate": "2026-07-10",
    "rate": 5000,
    "withNonStandardInterest": false
  },
  ...
]

```
