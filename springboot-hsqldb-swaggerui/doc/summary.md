# Déterminer la période VA

## Exercie normal : [au moins 1 an] ou [1re activité]


#### [Date bilan] : ok

##### VA_PERIOD [ANNEE=taxYear, TYPE=NORM]

###### YEAR_BILAN=YEAR(Date bilan)
###### MONTH_BILAN=MONTH(Date bilan)
###### Min(VA_DATES > Date paiement)


#### [Date bilan fictive] : [x]

---

## Exercice spécial : [moins d'1 an]


### [Date bilan] : [x]
### [Date bilan fictive] : calculated

#### VA_PERIOD [ANNEE=taxYear, TYPE=SPEC]

##### YEAR_BILAN = YEAR(Date bilan fictive) 
##### MONTH_BILAN = MONTH(Date bilan fictive) 
##### Min(VA_DATES > Date paiement) 
