spring-xd
===

## 安装spring-xd

**OSX** 用户，使用```Homebrew```安装：
```
brew tap pivotal/tap
brew install springxd
```    

想要安装更新的版本，那么使用```brew upgrade springXD```就可以。

**红帽或者CentOS** 用户可以使用Yum来安装。

**Windows** 用户可以下载最新的.zip文件，解压，安装到文件夹，然后把XD_HOME这个环境变量设置成安装文件夹。

## 使用说明

最新的官方文档：

    http://docs.spring.io/spring-xd/docs/1.3.1.RELEASE/reference/html/

**MAC上使用spring-xd的实践**

    xd-singlenode

    xd-shell

就可以进入xd的界面，如果失败，可以参考下面的解决方式：

> if the server could not be reached, the prompt will read

>     server-unknown:>

>	 You can then use the admin config server <url> to attempt to reconnect to the admin REST endpoint once you’ve figured out what went wrong:

>~~~
admin config server http://localhost:9393
~~~

如果本地在配置kafka的时候已经启动了zookeeper，所以这里需要把zookeeper的地址修改为实际的地址。修改下面的文件即可：

>~~~    
xd/config/servers.yml:
#Zookeeper properties
#client connect string: host1:port1,host2:port2,...,hostN:portN
zk:
  client:
    connect: localhost:2181
~~~

### kafka作为Sink

~~~
xd:>stream create push-to-kafka --definition "http | kafka --topic=myTopic" --deploy

xd:>Created and deployed new stream 'push-to-kafka'

xd:>http post --data "push-messages"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 push-messages
> 200 OK
~~~

### kafka作为Source

Configure a stream that has kafka source with a single topic:

    xd:> stream create myKafkaSource1 --definition "kafka --zkconnect=localhost:2181 --topic=mytopic | log" --deploy

Configure a stream that has kafka source with a multiple topics:

    xd:> stream create myKafkaSource2 --definition "kafka --zkconnect=localhost:2181 --topics=mytopic1,mytopic2 | log" --deploy

有问题，目前不能正常看到输出的消息，log没有能看到producer的消息，不知道是没有收到，还是不能正确地解析！

    2016-03-30T14:36:28+0800 1.3.1.RELEASE INFO pool-23-thread-1 sink.kafkaStream - [B@d378b3
    2016-03-30T14:36:28+0800 1.3.1.RELEASE INFO pool-23-thread-1 sink.kafkaStream - [B@f23f132
    2016-03-30T14:36:28+0800 1.3.1.RELEASE INFO pool-23-thread-1 sink.kafkaStream - [B@1272c626
    2016-03-30T14:36:28+0800 1.3.1.RELEASE INFO pool-23-thread-1 sink.kafkaStream - [B@dab4d9

调整成输出到file，命令如下：

    xd:>stream create goo --definition "kafka --zkconnect=10.160.5.56:2181 --topic=test | file" --deploy
    Created and deployed new stream 'goo'

在/tmp/xd/output/goo.out里面可以看到下面的内容：

~~~
50,58,116,101,115,116,121,111,117
50,58,116,101,115,116,121,111,117
51,58,116,101,115,116,121,111,117
52,58,116,101,115,116,121,111,117
52,58,116,101,115,116,121,111,117

116,101,115,116
116,101,115,116,121,111,117,32,50


27,91,65,27,91,65,27,91,66,27,91,66


116,101,115,116,104,116,116,112,107,97,102,107,97
55,58,116,101,115,116,121,111,117
~~~
正是kafka topic=test中的内容，不过这里不是字符串的形式，而是用ascii码来表示了，看来确实kafka作为source是可行的，只是输出的内容格式需要关注。


### cassandra作为sink
spring xd中已经把cassandra单独作为一个sink的module，所以可以不用jdbc的方式来搞。下面创建和发送数据的过程如下：

~~~
xd:>stream create castest --definition "http | cassandra --ingestQuery='insert into users(user_id, fname, lname) values(?,?,?)' --keyspace=mykeyspace --contactPoints=10.160.5.56" --deploy
Created and deployed new stream 'castest'
xd:>
xd:>http post http://localhost:9000  --data "{\"user_id\":1800,\"fname\":\"bob\", \"lname\":\"david\"}"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 {"user_id":1800,"fname":"bob", "lname":"david"}
> 200 OK
~~~
可以在cassandra的client里面看到最终的结果，可以看到我们插入的数据内容：

~~~
cqlsh:mykeyspace> select * from users ;

user_id | fname | lname
---------+-------+-------
1745 |  john | smith
1746 |   Bob | smith
1800 |   bob | david
~~~

**注意**

***每个http source都要占用一个web 端口，默认是9000。多个流的话要为不同的流绑定不同的web 端口***

### kafka作为source, spark-stream作为processor, cassandra作为sink
从上面的过程中可以看出，单独的过程都已经可以打通，将数据格式做一些转换，正好可以实验一下processor的工作方式。我们通过一个processor来把kafka的输入转换之后输出到cassandra.

因为kafka作为source的时候输出的不是字符串，是二进制数据，所以不能简单地用shell, **shell要求输入和输出都是String**。


~~~
xd:>module list
      Source              Processor                Sink                     Job
  ------------------  -----------------------  -----------------------  -----------------
      file                aggregator               aggregate-counter        filejdbc
      ftp                 bridge                   cassandra                filepollhdfs
      gemfire             filter                   counter                  ftphdfs
      gemfire-cq          find-list                field-value-counter      gpload
      http                header-enricher          file                     hdfsjdbc
      jdbc                http-client              ftp                      hdfsmongodb
      jms                 json-to-tuple            gauge                    jdbchdfs
      kafka               message-transformer      gemfire-json-server      sparkapp
      mail                object-to-json           gemfire-server           sqoop
      mongodb             script                   gpfdist                  timestampfile
      mqtt                scripts                  hdfs
      rabbit              shell                    hdfs-dataset
      reactor-ip          splitter                 jdbc
      reactor-syslog      transform                kafka
      sftp                tweet-test               log
      syslog-tcp                                   mail
      syslog-udp                                   mongodb
      tail                                         mqtt
      tcp                                          null
      tcp-client                                   rabbit
      time                                         redis
      trigger                                      rich-gauge
      twittersearch                                router
      twitterstream                                shell
                                                   splunk
                                                   tcp
                                                   throughput-sampler
~~~

这里我们自定义了两个module作为processor,其中message-transformer用来将kafka consumer接收到的byte数据转换成字符串。find-list是一个spark streaming，用来对message-transformer转换过之后的条目进行过滤，通过过滤的数据作为cassandra的输入，将内容存储到cassandra.

创建一个流来完成整个操作：

~~~
xd:>stream create test --definition "kafka --zkconnect=10.160.5.56:2181 --topic=test | tweet-test | find-list | cassandra --ingestQuery='insert into journey(name, date, type, credentials, credentials_no, contact, flight, depart, dest, seat, airport, carriage, station) values(?,?,?,?,?,?,?,?,?,?,?,?,?)' --keyspace=mykeyspace --contactPoints=10.160.5.56" --deploy
Created and deployed new stream 'test'
~~~

按照rest service api中指定的生产者api提交数据，就可以在cassandra中看到相关数据

~~~
cqlsh:mykeyspace> select * from journey;                                                                                                                                                                    
 name | date     | type  | airport  | carriage | contact | credentials | credentials_no | depart   | dest | flight | seat | station
------+----------+-------+----------+----------+---------+-------------+----------------+----------+------+--------+------+---------
 lisi | 20160404 | plane | 首都机场 |     null |  888888 |      身份证 |        1234567 | hangzhou | null | CA1986 |  15F |    null
 lisi | 20160412 | plane | 首都机场 |     null |  888888 |      身份证 |        1234567 | hangzhou | null | CA1986 |  15F |    null

(2 rows)

~~~

##### 性能测试
使用python脚本测试基于本场景进行性能测试。提交10000条请求，测试请求的处理时间。
Snow部署在一台PC上，提交请求的脚本在相同的PC上运行，Spring xd运行在同局域网中的另外一台Mac book air上，kafka和cassandra运行在同一台Mac book air上。

**测试脚本**

~~~
#!/usr/local/bin/python
# coding:utf-8

import json
import requests
import time

if __name__ == '__main__':
    url = 'http://localhost:8080/journey'
    headers = {'content-type': 'application/json'}
    payload3 = {'airport': '\xca\xd7\xb6\xbc\xbb\xfa\xb3\xa1', 'contact': '888888', 'depart': 'hangzhou', 'name': 'zhangsan', \
                'credentials': '\xc9\xed\xb7\xdd\xd6\xa4', 'date': '20160408', 'flight': 'CA1986', 'type': 'plane', 'credentials_no': '1234567', 'seat': '15F'}
    i = 0;
    print time.time()
    for i in range(0,10000,1):
        payload3['name']= 'zhangsan'+str(i)
        r = requests.post(url, data=json.dumps(payload3, encoding='gb2312'), headers=headers)
    print time.time()
~~~

**测试结果**

~~~
1460973288.54
1460973333.23

Process finished with exit code 0
~~~

10000条请求从提交到处理完成，并最终存入cassandra，共耗时45秒。