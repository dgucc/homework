# Insert a extra autonumber column "id" 

```bash
# one-liner : 
$ awk -F',' 'BEGIN{RS="\n";OFS=","} NR==1{print "id",$0;next} NF{print ++c","$0}' input.csv > output.csv
```
