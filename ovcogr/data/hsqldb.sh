#!/usr/bin/env bash

java -cp ./hsqldb-2.6.0.jar org.hsqldb.server.Server -database.0 file:bnb -dbname.0 bnb
java -cp ./hsqldb-2.7.4.jar org.hsqldb.server.Server -database.0 file:bnb -dbname.0 bnb

