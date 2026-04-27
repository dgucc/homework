# Demo

$ mvn17 clean install
$ mvn17 spring-boot:run

## Testcases...

```bash
# Ex 1
curl -X POST 'http://localhost:8080/article415/' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"taxYear":2024,"balanceDate":"2023-12-31","sendingDate":"2025-07-10","executionDate":"2025-07-11","rate":5000,"withNonStandardInterest":false}'
{"dateOfLateInterest":"2025-07-01"}
# 01/07/Bord.TAXYEAR+1

# Ex 1
curl -X POST 'http://localhost:8080/article415/' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"taxYear":2024,"balanceDate":"2024-09-30","sendingDate":"2025-11-10","executionDate":"2025-11-11","rate":5000,"withNonStandardInterest":false}'
{"dateOfLateInterest":"2025-10-01"}
# 01/ DATEBILAN+13mois / DATEBILAN+1an

# Ex 2
curl -X POST 'http://localhost:8080/article415/' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"taxYear":2024,"balanceDate":"2024-09-30","sendingDate":"2025-08-10","executionDate":"2025-08-11","rate":5000,"withNonStandardInterest":false}'
{"dateOfLateInterest":"2025-11-01"}
#  01/ VERZENDINGDATUM + 3 mois

# Ex 3
curl -X POST 'http://localhost:8080/article415/' -H 'accept: application/json' -H 'Content-Type: application/json' -d '"taxYear": 2024,"balanceDate": "2024-12-30","sendingDate": "2026-02-06","executionDate": "2026-02-07","rate": 5000,"withNonStandardInterest": false,"dueDateOfInterest":"2026-01-01"}'
{"dateOfLateInterest":"2026-01-01"}
# 01/ DATEBILAN+13mois / DATEBILAN+1an

# Ex 4
curl -X POST 'http://localhost:8080/article415/' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"taxYear":2024,"balanceDate":"2023-12-31","sendingDate":"2025-07-10","executionDate":"2025-07-10","rate":5000,"withNonStandardInterest":false}'
{"dateOfLateInterest":"2025-07-01"}
# 01/07/Bord.TAXYEAR+1

# Ex 5
curl -X POST 'http://localhost:8080/article415/' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"taxYear":2024,"balanceDate":"2024-09-30","sendingDate":"2025-11-10","executionDate":"2025-11-11","rate":5000,"withNonStandardInterest":false}'
{"dateOfLateInterest":"2025-10-01"}
# 01/ DATEBILAN+13mois / DATEBILAN+1an

# Ex 6
curl -X POST 'http://localhost:8080/article415/' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"taxYear":2024,"balanceDate":"2024-09-30","sendingDate":"2025-08-10","executionDate":"2025-08-11","rate":5000,"withNonStandardInterest":false}'
{"dateOfLateInterest":"2025-11-01"}
# 01/ VERZENDINGDATUM + 3 mois
```