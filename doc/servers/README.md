# Spark部署
***
Spark可以以多个模式运行，不同模式下Spark的部署不尽相同。

## local模式
local模式下，不需要安装Spark。这是因为xd运行时已经加载了Spark相关包，相关jar包详见xd/lib。
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

## FAQ
* 1 曾经发现一个问题，在一个从没有运行Spark的环境中，创建Spark processor流的processor功能失效。在运行一次Spark后，问题消失。
