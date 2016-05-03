#!/usr/bin/env bash
sudo ls
BASE=/Users/erichan/sourcecode/feuyeux/flurry
cd ${BASE}
mvn clean install -DskipTests
rm -f ${BASE}/bin/*.jar
sudo rm -f /opt/flurry/*.jar
sudo cp ${BASE}/rain/find-list-processor/target/*.jar /opt/flurry
sudo cp ${BASE}/snow/target/*.jar /opt/flurry
