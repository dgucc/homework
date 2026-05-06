# Migrate ovcogr project API into Spring Boot

- Java 17  
- HSQLDB  
- 


# Run the project

```sh
# Build
mvn clean compile
mvn spring-boot:run
# Test
# http://localhost:8080/
```

# List all related companies for given BCE

Examples :  

```sh
curl --silent -X GET -L http://localhost:8080/api/v1/stk/0408661790 | jq
curl --silent -X GET -L http://localhost:8080/api/v1/stk/0888044106 | jq
curl --silent -X GET -L http://localhost:8080/api/v1/stk/0444745790 | jq
curl --silent -X GET -L http://localhost:8080/api/v1/stk/0538668417 | jq
```

# Test List all descendants for a list of BCE
`curl --silent -X POST -L http://localhost:8080/api/v1/stk/test/down/all -H 'content-type: application/json' --data '["0408661790", "0726908991", "0695982819"]' | jq` 




http://localhost:8080/api/v1/stk/0403091220


```json
[
  {
    "parentBce": "0408661790",
    "parentName": "\"PARTENA - GUICHET D'ENTREPRISES - PARTENA - ONDERNEMINGSLOKET\"",
    "country": "BE",
    "childBce": "0479282740",
    "childName": "\"PARTENA Business Solutions\"",
    "endStakeholding": "2023-12-31",
    "direct": 0.000,
    "indirect": 0.000
  },
  {
    "parentBce": "0423855851",
    "parentName": "\"P - INVEST\"",
    "country": "BE",
    "childBce": "0428359225",
    "childName": "\"EASYPAY\"",
    "endStakeholding": "2023-12-31",
    "direct": 10.000,
    "indirect": null
  },
  ...
]
```


# Setup
git config core.autocrlf false


# Fixes

Hibernate issue : Column ID not found...
Use DTO instead of entities to call StakeholdingRepository :
   List<StakeholdingDto> getAllChildrenForlistOfBce(@Param("listOfBce") List<String> listOfBce);

