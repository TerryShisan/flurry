# Spring Cloud Data Flow简介
Data Flow和XD的最大区别

# 操作
##

java -jar time-source-1.0-SNAPSHOT.jar --spring.cloud.stream.bindings.output.binder=redis --spring.cloud.stream.bindings.output.destination=ticktock
java -jar cassandra-sink-1.0-SNAPSHOT.jar --spring.cloud.stream.bindings.input.binder=redis --spring.cloud.stream.bindings.input.destination=ticktock --ingestQuery='insert into te(time) values(?)' --keyspace=mykeyspace --contactPoints=127.0.0.1 --server.port=8801

java -jar time-source-1.0-SNAPSHOT.jar --spring.cloud.stream.bindings.output.binder=kafka --spring.cloud.stream.bindings.output.destination=ticktock
java -jar cassandra-sink-1.0-SNAPSHOT.jar --spring.cloud.stream.bindings.input.binder=kafka --spring.cloud.stream.bindings.input.destination=ticktock --ingestQuery='insert into te(time) values(?)' --keyspace=mykeyspace --contactPoints=127.0.0.1 --server.port=8801
