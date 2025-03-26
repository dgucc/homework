# demo : Périodes VA - Rest Service

- Springboot
- Java 17
- hsqldb

## Run Application
`$ mvn17 clean install`  
`$ mvn17 spring-boot:run`  

## Test du service rest avec curl

```bash
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "payments": [
    {
      "amount": 1000.0,
      "paymentDate": "2024-03-01"
    },
    {
      "amount": 5000.0,
      "paymentDate": "2024-05-15"
    }
  ],
  "firstActivity": false
}' | jq
```

```json
{
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "fictiveBalanceDate": "2024-09-30",
  "vaPeriods": [
    {
      "amount": 1000.00000000000000000000000000000000,
      "paymentDate": "2024-03-01",
      "vaPeriod": "VA_PERIOD_2",
      "vaMaxDate": "2024-04-11",
      "taxYearType": "SPEC"
    },
    {
      "amount": 5000.00000000000000000000000000000000,
      "paymentDate": "2024-05-15",
      "vaPeriod": "VA_PERIOD_3",
      "vaMaxDate": "2024-07-11",
      "taxYearType": "SPEC"
    }
  ],
  "firstActivity": false
}
```

## Changlog

```bash
 git --no-pager log --all --decorate --graph --pretty="%C(blue)%h %C(yellow)%s %C(white)%ad" --date=short
* cdcdb2b Added tags to document swagger with springdoc-openapi-ui 2025-03-26
* 7bb4f67 Repositoriy : add missing cast( as date) in SQL 2025-03-26
* 357057d Redirect root page to swagger-ui/index.html 2025-03-26
* 0ac76e7 Documentations 2025-03-26
* aa90c45 Refactor naming of classes 2025-03-26
* 2f300d0 working with POST and list of payments 2025-03-26
* 0a9b886 structured project 2025-03-26
* f24eb5c getVAPeriod() : reworked query 2025-03-26
* 59d11cf Added unit tests over fictiveBalanceDate, TaxYear spanning over 1 year 2025-03-26
* 4d8e257 prevent hsqldb lock : ";hsqldb.lock_file=false" 2025-03-25
* 48ecdd6 working version with springboot hsqldb 2025-03-25

```

---

## References  
Swagger-UI
http://localhost:8080/swagger-ui/index.html#/


https://www.geeksforgeeks.org/spring-boot-jpa-native-query-with-example/
