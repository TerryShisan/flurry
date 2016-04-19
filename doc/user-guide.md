## 启动和配置kafka

kafka的安装包不包含在本工程中，请自行前往官网下载，本项目使用的是kafka_2.10-0.9.0.1版本。kafka server需要先启动zookeeper方能正常使用，kafka的安装包里面带有zookeeper的启动脚本。

- 启动zookeeper

      bin/zookeeper-server-start.sh config/zookeeper.properties

- 启动kafka server

      bin/kafka-server-start.sh config/server.properties

- 创建topic

      bin/kafka-topics.sh  --create --zookeeper 10.160.5.56:2181  --replication-factor 1 --partitions 1 --topic test

- 查看创建的topic是否存在

      bin/kafka-topics.sh --list --zookeeper 10.160.5.56:2181

- 检查kafka是否能正常工作

  - 配置producer

        bin/kafka-console-producer.sh  --topic test --broker-list 10.160.5.56:9092
  - 配置counsumer

        bin/kafka-console-consumer.sh --zookeeper 10.160.5.56:2181  --topic test --from-beginning

  在两个控制台中分别输入以上命令，如果功能正常，则在producer的控制台输入要发给consumer的消息，在consumer控制台立即就能看到消息的回显。

**注意：** 以上消息中的zookeeper的地址可以修改为本地地址或者localhost

## 启动和配置cassandra

cassandra的安装包同样可以从官网上下载到，需要注意的是，我们后面在snow中会用到Spring-data-cassandra,目前Spring-data-cassandra 1.x只支持cassandra 2.x， 不支持3.x，官网描述如下：

    Spring Data Cassandra 1.x binaries requires JDK level 6.0 and above, and Spring Framework 3.2.x and above.

    Currently we support Cassandra 2.X using the DataStax Java Driver (2.0.X)

本项目使用apache-cassandra-2.2.5。切换到cassandra的安装包目录下：

- 启动cassandra

    bin/cassandra -f

- 连接cassandra数据库

    bin/cqlsh localhost

- 在cassandra中建表，建表语句

~~~
$ bin/cqlsh 10.160.5.56
Connected to Test Cluster at 10.160.5.56:9042.
[cqlsh 5.0.1 | Cassandra 2.2.5 | CQL spec 3.3.1 | Native protocol v4]
Use HELP for help.
cqlsh> use mykeyspace ;
cqlsh:mykeyspace> create table journey(name text, date text, type text, credentials text, credentials_no text, contact text, flight text, depart text, dest text, seat text, airport text, carriage text, station text, primary key (name,date,type)) ;
~~~

如果能顺利创建journey表，说明cassandra安装和配置成功。


## 使用Snow和Rain

Snow和Rain是两个不同的功能组件，Snow对外提供REST API供用户操作flurry，并显示最终的结果。Rain提供spring-xd的处理module。

从github上下载flurry的代码后，切换到flurry的目录，执行以下mvn命令：

    mvn install

在snow和rain对应的的target目录下会生成三个jar包：

    snow-1.0-SNAPSHOT.jar
    find-list-processor-1.0-SNAPSHOT.jar
    byte2string-transformer-1.0-SNAPSHOT.jar

其中 find-list-processor-1.0-SNAPSHOT.jar和byte2string-transformer-1.0-SNAPSHOT.jar会在后续使用spring-xd的时候作为module导入。为了对外提供REST API，这里先启动Snow，启动Snow的命令如下：

    cd snow
    mvn spring-boot:run

启动之后提供REST服务的地址是localhost:8080,可以通过rest-api中的接口来操作snow.

通过post /journey提交一条请求之后，请求的内容会通过kafka producer发送给brokers, 这时候如果有一个kafka consumer是可以接收到消息的。

## 使用spring-xd

#### 安装spring-xd

对于OSX用户，如果还没有Homebrew的话，请安装，然后运行：

    brew tap pivotal/tap
    brew install springxd

spring-xd会安装到 /usr/local/Cellar/springxd/1.0.0.M7/libexec （依赖于Spring XD的库）。

注意：如果你随后想要安装更新的版本，那么使用brew upgrade springXD就可以。

红帽或者CentOS的用户可以使用Yum来安装。

Windows用户可以下载最新的.zip文件，解压，安装到文件夹，然后把XD_HOME这个环境变量设置成安装文件夹。

最新的官方文档：

    http://docs.spring.io/spring-xd/docs/1.3.1.RELEASE/reference/html/

#### 配置和使用spring-xd

- 启动spring-xd

这里因为是单机模式，使用xd-singlenode，命令如下：

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

- 添加module

进入xd-shell之后，就可以通过module的命令添加上面通过rain生成的两个jar包了。

命令如下：

~~~
xd:>module upload --file /Users/bj-yf/zhouhb/jar/find-list-processor-1.0-SNAPSHOT.jar --type processor --name find-list
Successfully uploaded module 'processor:find-list'
xd:>module upload --file /Users/bj-yf/zhouhb/jar/byte2string-transformer-1.0-SNAPSHOT.jar --type processor --name byte2string
Successfully uploaded module 'processor:byte2string'
xd:>module list
      Source              Processor            Sink                     Job
  ------------------  -------------------  -----------------------  -----------------
      file                aggregator           aggregate-counter        filejdbc
      ftp                 bridge               cassandra                filepollhdfs
      gemfire             byte2string          counter                  ftphdfs
      gemfire-cq          filter               field-value-counter      gpload
      http                find-list            file                     hdfsjdbc
      jdbc                header-enricher      ftp                      hdfsmongodb
      jms                 http-client          gauge                    jdbchdfs
      kafka               json-to-tuple        gemfire-json-server      sparkapp
      mail                object-to-json       gemfire-server           sqoop
      mongodb             script               gpfdist                  timestampfile
      mqtt                scripts              hdfs
      rabbit              shell                hdfs-dataset
      reactor-ip          splitter             jdbc
      reactor-syslog      transform            kafka
      sftp                                     log
      syslog-tcp                               mail
      syslog-udp                               mongodb
      tail                                     mqtt
      tcp                                      null
      tcp-client                               rabbit
      time                                     redis
      trigger                                  rich-gauge
      twittersearch                            router
      twitterstream                            shell
                                               splunk
                                               tcp
                                               throughput-sampler
~~~

- 创建stream

将rain中的jar包添加为module之后，接着就可以创建stream了，这里我们要创建的steam的目的是从kafka中读取消息，然后送到spark-streaming中进行过滤，并将过滤结果放到cassandra数据库中的journey表中。

创建流的命令如下：

~~~
xd:>stream create test --definition "kafka --zkconnect=10.160.5.56:2181 --topic=test | byte2string | find-list --blackName='zhangsan,lili' | cassandra --ingestQuery='insert into journey(name, date, type, credentials, credentials_no, contact, flight, depart, dest, seat, airport, carriage, station) values(?,?,?,?,?,?,?,?,?,?,?,?,?)' --keyspace=mykeyspace --contactPoints=10.160.5.56" --deploy
Created and deployed new stream 'test'
~~~

- 验证

至此就将所有的组件串联起来了，我们可以通过curl或者python的Requests提交一个http post请求看一下效果。这里通过requests进行验证。可以看到效果符合预期。

**发送post请求**
~~~
>>> import requests
>>> payload = {"name":"zhangsan","ID":"身份证","IDNo":"1234567","contact":"888888","date":"2016-04-11","flight":"CA1986","from":"beijing","to":"hangzhou","seat": "15F","airport":"首都机场"}
>>> url = "http://localhost:8080/journey"
>>> headers = {'content-type': 'application/json'}
>>> r = requests.post(url, data=json.dumps(payload, encoding='gb2312'), headers=headers)
>>> r.text
u'{"code":200,"message":"successful","success":true}'
>>>
>>> payload2 = {"name":"lisi","ID":"身份证","IDNo":"1234567","contact":"888888","date":"2016-04-11","flight":"CA1986","from":"beijing","to":"hangzhou","seat": "15F","airport":"首都机场"}
>>> url = "http://localhost:8080/journey"
>>> headers = {'content-type': 'application/json'}
>>> r = requests.post(url, data=json.dumps(payload, encoding='gb2312'), headers=headers)
>>> r.text
u'{"code":200,"message":"successful","success":true}'
>>>
~~~

从上面创建stream创建时候可以看出，黑名单添加的是"zhangsan,lili",有这两个字符串的数据将被过滤出来存储到cassandra.

**查看结果**
~~~
>>> import requests
>>> r = requests.get("http://10.160.5.56:8080/journey")
>>> r.text
u'[{"name":"zhangsan","date":"20160408","type":"plane","credentials":"\u8eab\u4efd\u8bc1","credentials_no":"1234567","contact":"888888","flight":"CA1986","depart":"hangzhou","dest":null,"seat":"15F","airport":"\u9996\u90fd\u673a\u573a","carriage":null,"station":null}]'
>>>
~~~
