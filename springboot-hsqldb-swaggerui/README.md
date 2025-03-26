# demo : Périodes VA - Rest Service

- Springboot
- Java 17
- hsqldb

$ mvn17 clean install
$ mvn17 spring-boot:run

```json
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

Swagger-UI
http://localhost:8080/swagger-ui/index.html#/


https://www.geeksforgeeks.org/spring-boot-jpa-native-query-with-example/
