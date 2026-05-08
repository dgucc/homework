# Insert a extra autonumber column "id" 

```sh
# one-liner : 
$ awk -F',' 'BEGIN{RS="\n";OFS=","} NR==1{print "id",$0;next} NF{print ++c","$0}' input.csv > output.csv
```

# Remove extra double quotes

```sh
$ sed -i s'/"""/"/g' data/csv/participations-2025.csv
```