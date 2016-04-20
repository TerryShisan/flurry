#!/usr/bin/env bash
BASE=/Users/erichan/sourcecode/feuyeux/flurry
cd ${BASE}
mvn clean install -DskipTests
rm -f ${BASE}/bin/*.jar
cp ${BASE}/rain/byte2string-transformer/target/*.jar /opt/flurry
cp ${BASE}/rain/find-list-processor/target/*.jar /opt/flurry
cp ${BASE}/snow/target/*.jar /opt/flurry
