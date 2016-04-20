#!/usr/bin/env bash
BASE=/Users/erichan/sourcecode/feuyeux/flurry
cd ${BASE}
mvn clean install -DskipTests
rm -f ${BASE}/bin/*.jar
sudo cp ${BASE}/rain/byte2string-transformer/target/*.jar /opt/flurry
sudo cp ${BASE}/rain/find-list-processor/target/*.jar /opt/flurry
sudo cp ${BASE}/snow/target/*.jar /opt/flurry
