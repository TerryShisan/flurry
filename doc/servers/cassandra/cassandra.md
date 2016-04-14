# cassandra

### 官网
    http://cassandra.apache.org/download/

### 安装指南

    http://www.cnblogs.com/preftest/archive/2011/02/02/1948841.html


简单使用可以参考下面的wiki:

    http://wiki.apache.org/cassandra/GettingStarted

和Spring-boot的集成参考下面的例子：

    https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-data-cassandra


Spring-data-cassandra 1.x只支持cassandra 2.x， 不支持3.x，官网描述如下：

    Spring Data Cassandra 1.x binaries requires JDK level 6.0 and above, and Spring Framework 3.2.x and above.

    Currently we support Cassandra 2.X using the DataStax Java Driver (2.0.X)


错误：
    Error:tried: /192.168.x.x:9042 (com.datastax.driver.core.exceptions.InvalidQueryException: unconfigured table schema_keyspaces))

**注意**
cql和sql并不相同。一个简单的例子是，where中用来过滤的字段必须是primaryKey，否则会提示如下错误：

    com.datastax.driver.core.exceptions.InvalidQueryException: No secondary indexes on the restricted columns support the provided operators:

StackFlow上有人解释了这个原因：

    There is one constraint in cassandra: any field you want to use in the where clause has to be the primary key of the table or there must be a secondary index on it. So you have to create an index to firstname and only after that you can use firstname in the where condition and you will get the result you were expecting.



      19:21:37.806 [http-nio-8080-exec-1] ERROR o.a.c.c.C.[.[.[.[dispatcherServlet] - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.c
      execute this query as it might involve data filtering and thus may have unpredictable performance. If you want to execute this query despite the performance unpredictability, use ALLOW FILTERING; nested exception is com.datastax.driver.cor
      t might involve data filtering and thus may have unpredictable performance. If you want to execute this query despite the performance unpredictability, use ALLOW FILTERING] with root cause
      com.datastax.driver.core.exceptions.InvalidQueryException: Cannot execute this query as it might involve data filtering and thus may have unpredictable performance. If you want to execute this query despite the performance unpredictability
            at com.datastax.driver.core.Responses$Error.asException(Responses.java:102)
            at com.datastax.driver.core.DefaultResultSetFuture.onSet(DefaultResultSetFuture.java:149)
            at com.datastax.driver.core.RequestHandler.setFinalResult(RequestHandler.java:183)
            at com.datastax.driver.core.RequestHandler.access$2300(RequestHandler.java:44)
            at   com.datastax.driver.core.RequestHandler$SpeculativeExecution.setFinalResult(RequestHandler.java:751)
            at com.datastax.driver.core.RequestHandler$SpeculativeExecution.onSet(RequestHandler.java:573)
            at com.datastax.driver.core.Connection$Dispatcher.channelRead0(Connection.java:1009)
            at com.datastax.driver.core.Connection$Dispatcher.channelRead0(Connection.java:932)
            at io.netty.channel.SimpleChannelInboundHandler.channelRead(SimpleChannelInboundHandler.java:105)
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
            at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
            at io.netty.handler.timeout.IdleStateHandler.channelRead(IdleStateHandler.java:254)
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
            at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
            at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:103)
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
            at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
            at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:242)
            at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
            at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
            at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:847)
            at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:131)
            at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:511)
            at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:468)
            at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:382)
            at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:354)
            at io.netty.util.concurrent.SingleThreadEventExecutor$2.run(SingleThreadEventExecutor.java:111)
            at java.lang.Thread.run(Thread.java:744)


关于上面的这个问题的一个说明：

http://www.datastax.com/dev/blog/allow-filtering-explained-2

需要在select语句后面加上allow filtering属性字段。


另外，需要注意的是，entity类中的字段名称必须和获取到的表中的一样，否则可能会存在找不到字段的问题。

**启动cassandra**

    bin/cassandra -f

**连接cassandra数据库**

    bin/cqlsh localhost

### 建表语句
~~~
$ bin/cqlsh 10.160.5.56
Connected to Test Cluster at 10.160.5.56:9042.
[cqlsh 5.0.1 | Cassandra 2.2.5 | CQL spec 3.3.1 | Native protocol v4]
Use HELP for help.
cqlsh> use mykeyspace ;
cqlsh:mykeyspace> create table journey(name text, date text, type text, credentials text, credentials_no text, contact text, flight text, depart text, dest text, seat text, airport text, carriage text, station text, primary key (name,date,type)) ;
~~~