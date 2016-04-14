Kafka
---

## kafka使用

在kafka启动的时候要同时启动zookeeper和kafka server

命令如下：

bin/kafka-server-start.sh config/server.properties

bin/zookeeper-server-start.sh config/zookeeper.properties

具体配置可以修改server.properties和zookeeper.properties.

如果出现下面的错误，则是因为没有启动kafka server.

    Caused by: kafka.admin.AdminOperationException: replication factor: 1 larger than available brokers: 0
        at kafka.admin.AdminUtils$.assignReplicasToBrokers(AdminUtils.scala:70)
        at kafka.admin.AdminUtils$.createTopic(AdminUtils.scala:171)
        at org.springframework.integration.kafka.listener.KafkaTopicOffsetManager.createCompactedTopicIfNotFound(KafkaTopicOffsetManager.java:268)
        at org.springframework.integration.kafka.listener.KafkaTopicOffsetManager.afterPropertiesSet(KafkaTopicOffsetManager.java:210)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1637)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1574)
        ... 22 more

**创建topic**

    bin/kafka-topics.sh  --create --zookeeper 10.160.5.56:2181  --replication-factor 1 --partitions 1 --topic test

    bin/kafka-topics.sh --zookeeper 10.160.5.56:2181 -list

**生产者**

    bin/kafka-console-producer.sh  --broker-list  10.160.5.56:9092 --topic test

**消费者**

    bin/kafka-console-consumer.sh --zookeeper 10.160.5.56:2181  --topic test --from-beginning
