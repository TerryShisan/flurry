# Spark部署
***
Spark可以以多个模式运行，不同模式下Spark的部署不尽相同。

## local模式
local模式下，不需要安装Spark。这是因为xd运行时已经加载了Spark相关包，相关jar包详见xd/lib。目前xd最新版本1.3.1.RELEASE，其使用的是spark 1.3.1的版本，Spark的版本略老，目前最新版本是1.6.1版本。
```
-rw-r--r-- 1 root root  8873578 Feb 23 16:11 spark-core_2.10-1.3.1.jar
-rw-r--r-- 1 root root  2301222 Feb 23 16:11 spark-network-common_2.10-1.3.1.jar
-rw-r--r-- 1 root root    57599 Feb 23 16:11 spark-network-shuffle_2.10-1.3.1.jar
-rw-r--r-- 1 root root  1314869 Feb 23 16:11 spark-streaming_2.10-1.3.1.jar
-rw-r--r-- 1 root root    32271 Feb 23 16:11 spring-xd-spark-streaming-1.3.1.RELEASE.jar
```
**如果spring xd以xd-singlenode运行，那么Spark仅能使用local模式运行。** 否则xd里deploy stream时，会报异常。
## standalone模式
- [ ] 待补充

## mesos模式
- [ ] 待补充

## 调优
Spark集群资源决定了Spark Streaming的最大处理能力，集群规模应根据处理数据的量级决定。在高并发下Spark Streaming极限使用Spark集群资源，合理的提高并发度可以大幅调优处理性能。  
此外，Spark各个参数的调整，尤其是batch interval的大小，也决定了Spark Streaming是否最优处理数据。该值需要保证生产者和消费者的供需平衡。
>The batch processing time should be less than the batch interval.  

batch interval的优化方法如下：
>A good approach to figure out the right batch size for your application is to test it with a conservative batch interval (say, 5-10 seconds) and a low data rate. To verify whether the system is able to keep up with the data rate, you can check the value of the end-to-end delay experienced by each processed batch (either look for “Total delay” in Spark driver log4j logs, or use the StreamingListener interface). If the delay is maintained to be comparable to the batch size, then system is stable. Otherwise, if the delay is continuously increasing, it means that the system is unable to keep up and it therefore unstable. Once you have an idea of a stable configuration, you can try increasing the data rate and/or reducing the batch size. Note that a momentary increase in the delay due to temporary data rate increases may be fine as long as the delay reduces back to a low value (i.e., less than batch size).

当前版本XD中的batch interval为2秒，并在代码中写死。待进一步实验。
```
public static final String SPARK_STREAMING_DEFAULT_BATCH_INTERVAL = "2000";

private void startSparkStreamingContext(Properties sparkConfigs,
											final SparkStreamingSupport sparkStreamingSupport,
											final Module module) {
    ...
    env.getProperty(SparkStreamingSupport.SPARK_STREAMING_BATCH_INTERVAL_MODULE_OPTION,
				env.getProperty(SparkStreamingSupport.SPARK_STREAMING_BATCH_INTERVAL_PROP,
						SparkStreamingSupport.SPARK_STREAMING_DEFAULT_BATCH_INTERVAL));
		final SparkStreamingListener streamingListener = new SparkStreamingListener();
```

## FAQ
* 1 曾经发现一个问题，在一个从没有运行Spark的环境中，创建Spark processor流的processor功能失效。在运行一次Spark后，问题消失。
