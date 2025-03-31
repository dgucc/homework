# demo : Périodes VA - Rest Service

- Springboot
- Java 17
- hsqldb

$ mvn17 clean install
$ mvn17 spring-boot:run

Exemples  


## Exercice classique de 12 mois 

### Request:

```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-06-12",
  "balanceDate": "2024-11-30",
  "isFirstActivity": false,
  "payments": [
    {
      "amount": 4000,
      "paymentDate": "2023-12-12"
    }
  ]
}' | jq '.vaPeriods[] |= (.amount |= (. * 100 | round / 100.0))'
```
### Response:  
Exercice classique de 12 mois 
- exercice NORMAL
- pas de date bilan fictive à définir
- période VA identifiée 

```json
{
  "isFirstActivity": false,
  "taxYearStartDate": "2023-06-12",
  "balanceDate": "2024-11-30",
  "fictiveBalanceDate": null,
  "vaPeriods": [
    {
      "amount": 4000,
      "paymentDate": "2023-12-12",
      "vaPeriod": "VA_PERIOD_1",
      "vaMaxDate": "2024-03-12",
      "taxYearType": "NORM",
      "messages": []
    }
  ]
}
```

## Exercice de 7 mois mais 1re activité de la société
### Request:
```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-06-01",
  "balanceDate": "2023-12-31",
  "isFirstActivity": true,
  "payments": [
    {
      "amount": 4500,
      "paymentDate": "2023-09-01"
    }
  ]
}' | jq '.vaPeriods[] |= (.amount |= (. * 100 | round / 100.0))'
```

### Response:  
Exercice de 7 mois mais 1re activité de la société
- 1re activité → exercice NORMAL
- pas de date bilan fictive à définir
- période VA identifiée 
```json
{
  "isFirstActivity": true,
  "taxYearStartDate": "2023-06-01",
  "balanceDate": "2023-12-31",
  "fictiveBalanceDate": null,
  "vaPeriods": [
    {
      "amount": 4500,
      "paymentDate": "2023-09-01",
      "vaPeriod": "VA_PERIOD_3",
      "vaMaxDate": "2023-10-11",
      "taxYearType": "NORM",
      "messages": []
    }
  ]
}
```

## Exercice de 4 mois -> date bilan fictive
### Request:
```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-06-01",
  "balanceDate": "2023-09-30",
  "isFirstActivity": false,
  "payments": [
    {
      "amount": 3000,
      "paymentDate": "2023-08-01"
    }
  ]
}' | jq '.vaPeriods[] |= (.amount |= (. * 100 | round / 100.0))'

```

### Response:  
Exercice de 4 mois -> date bilan fictive
- exercice de 4 mois -> SPECIAL  
- date bilan fictive calculée  
- période VA identifiée  
```json
{
  "isFirstActivity": false,
  "taxYearStartDate": "2023-06-01",
  "balanceDate": "2023-09-30",
  "fictiveBalanceDate": "2023-11-30",
  "vaPeriods": [
    {
      "amount": 3000,
      "paymentDate": "2023-08-01",
      "vaPeriod": "VA_PERIOD_3",
      "vaMaxDate": "2023-09-12",
      "taxYearType": "SPEC",
      "messages": []
    }
  ]
}
```

## Exemple exercice de 10 mois avec plusieurs paiements
### Request:
```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "isFirstActivity": false,
  "payments": [
    {
      "amount": 1000.0,
      "paymentDate": "2024-03-01"
    },
    {
      "amount": 5000.0,
      "paymentDate": "2024-05-15"
    }
  ]
 }' | jq '.vaPeriods[] |= (.amount |= (. * 100 | round / 100.0))'

```

### Response:  
Exemple exercice de 10 mois avec plusieurs paiements
- exercice de 10 mois -> SPECIAL  
- date bilan fictive calculée  
- périodes VA identifiées  
```json
{
  "isFirstActivity": false,
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "fictiveBalanceDate": "2024-09-30",
  "vaPeriods": [
    {
      "amount": 1000,
      "paymentDate": "2024-03-01",
      "vaPeriod": "VA_PERIOD_2",
      "vaMaxDate": "2024-04-11",
      "taxYearType": "SPEC",
      "messages": []
    },
    {
      "amount": 5000,
      "paymentDate": "2024-05-15",
      "vaPeriod": "VA_PERIOD_3",
      "vaMaxDate": "2024-07-11",
      "taxYearType": "SPEC",
      "messages": []
    }
  ]
}
```

## Exercice de 10 mois avec erreur dans un des paiements

### Request:
```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "isFirstActivity": false,
  "payments": [
    {
      "amount": 4500,
      "paymentDate": "2024-03-01"
    },
    {
      "amount": 666,
      "paymentDate": "2024-10-11"
    }
  ]
}' | jq '.vaPeriods[] |= (.amount |= (. * 100 | round / 100.0))'
```

### Response:  
Exercice de 10 mois avec erreur dans un des paiements
- exercice de 10 mois -> SPECIAL  
- date bilan fictive calculée  
- périodes VA identifiées  
- ERROR : paymentDate=vaMaxDate de VA_PERIOD_4  
```json
{
  "isFirstActivity": false,
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "fictiveBalanceDate": "2024-09-30",
  "vaPeriods": [
    {
      "amount": 4500,
      "paymentDate": "2024-03-01",
      "vaPeriod": "VA_PERIOD_2",
      "vaMaxDate": "2024-04-11",
      "taxYearType": "SPEC",
      "messages": []
    },
    {
      "amount": 666,
      "paymentDate": "2024-10-11",
      "vaPeriod": "VA_PERIOD_4",
      "vaMaxDate": "2024-10-11",
      "taxYearType": "SPEC",
      "messages": [
        "ERROR : the payment date is outside the year covered by the VA"
      ]
    }
  ]
}
```


## Exemple avec pré-validations -> HTTP 400 
### Request:
```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-10-01",
  "isFirstActivity": true,
  "payments": [
    {
      "amount": -4500,
      "paymentDate": "2024-03-01"
    },
    {
      "amount": 666,
      "paymentDate": "2024-10-11"
    }
  ] 
}' | jq 
```

### Response:  
Exemple avec pré-validations -> HTTP 400 
- HTTP 400
- Messages d'erreur

```json
{
  "balanceDate": "balanceDate cannot be null",
  "payments[0].amount": "amount must be positive"
}
```

---

## Exemple avec un vaPeriod = null (cas purement théorique ?)
### Request:
```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "isFirstActivity": true,
  "payments": [
    {
      "amount": 4500,
      "paymentDate": "2024-03-01"
    },
    {
      "amount": 666,
      "paymentDate": "2024-10-11"
    }
  ] 
}' | jq '.vaPeriods[] |= (.amount |= (. * 100 | round / 100.0))'
```

### Response:  
Exemple avec un vaPeriod = null (cas purement théorique ?)
- avec une période VA null parce qu'aucune période VA valide ne peut être identifiée

```json
{
  "isFirstActivity": true,
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "fictiveBalanceDate": null,
  "vaPeriods": [
    {
      "amount": 4500,
      "paymentDate": "2024-03-01",
      "vaPeriod": "VA_PERIOD_3",
      "vaMaxDate": "2024-05-11",
      "taxYearType": "NORM"
    },
    null
  ]
}
```

### Details
Le paiement du 2024-10-11 n’a pas de VA_PERIOD  correspondant :  

```sql
N_I_ANNEE|C_I_TYPE|N_I_YEAR_BILAN|N_I_MONTH_BILAN|D_I_DATE_VA1 |D_I_DATE_VA2 |D_I_DATE_VA3 |D_I_DATE_VA4 |
---------+--------+--------------+---------------+-------------+-------------+-------------+-------------+
     2024|NORM    |          2024|              7|   2023-11-11|   2024-02-13|   2024-05-11|   2024-07-23|
```


### Correction ?

Par contre, en indiquant isFirstActivity : false   
- On passe en exercice SPECIAL
- une date bilan fictive est calculée
- les périodes VA identifiables…

#### Request:

```json
curl --silent 'http://localhost:8080/va/periods/' -H 'Content-Type: application/json' -d '{
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "isFirstActivity": true,
  "payments": [
    {
      "amount": 4500,
      "paymentDate": "2024-03-01"
    },
    {
      "amount": 666,
      "paymentDate": "2024-10-11"
    }
  ] 
}' | jq '.vaPeriods[] |= (.amount |= (. * 100 | round / 100.0))'
```

#### Response:  
```json
{
  "isFirstActivity": false,
  "taxYearStartDate": "2023-10-01",
  "balanceDate": "2024-07-04",
  "fictiveBalanceDate": "2024-09-30",
  "vaPeriods": [
    {
      "amount": 4500,
      "paymentDate": "2024-03-01",
      "vaPeriod": "VA_PERIOD_2",
      "vaMaxDate": "2024-04-11",
      "taxYearType": "SPEC",
      "messages": []
    },
    {
      "amount": 666,
      "paymentDate": "2024-10-11",
      "vaPeriod": "VA_PERIOD_4",
      "vaMaxDate": "2024-10-11",
      "taxYearType": "SPEC",
      "messages": [
        "ERROR : the payment date is outside the year covered by the VA"
      ]
    }
  ]
}
```

---

Swagger-UI
http://localhost:8080/swagger-ui/index.html#/


https://www.geeksforgeeks.org/spring-boot-jpa-native-query-with-example/
