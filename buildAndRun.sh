#!/bin/sh
mvn clean package && docker build -t nl.han.ica.oose.dea.dewihu/SpotitubeHerkansing .
docker rm -f SpotitubeHerkansing || true && docker run -d -p 8080:8080 -p 4848:4848 --name SpotitubeHerkansing nl.han.ica.oose.dea.dewihu/SpotitubeHerkansing 
