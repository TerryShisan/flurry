Demo演示流程拆解
---

## first 3 minutes
介绍flurry.png中的内容，也就是flurry的整体架构，图可以重新优化一下。

主要突出下面几点内容：

1. kafka/cassandra/spark/web/spring-xd的位置和作用。

1. 数据流的走向和最终达成的效果说明。

## Second 1 minutes

列出用到的各种开源技术组件以及对应的版本号，用列表的形式展示。

## Third 5 minutes

说明一下目前的代码机构，以及每个部分的作用。代码主要包含三部分：

1. rain/byte-string
1. rain/black
1. snow

强调一下上面前两个是jar包，作为processor插入到xd中，snow是一个可执行jar包，提供HTTP REST服务。

说明一下打包的过程和每个包的作用以及和前面的架构图的对应关系。

这边的问题可以后边进行答疑。不做过多发散。

## Four 2 minutes
简单说明一下会用到的spring-xd命令,包括：
1. spring-xd的启动的两个命令
2. module加载的命令
3. stream创建删除的命令

上述命令只说明一下具体的形式和完成的功能，并不实际执行，实际上环境已经准备好。

## Five 10 minutes
实际演示，应该至少包含下面几个步骤:
  1. 介绍一下组网和配置
  1. 开始的时候看cassandra数据库和kafka consumer,都是空的
  1. 描述生产者和消费者REST api，说明一下要提交的消息以及实际应该出现的结果。最少应该包含三条消息的提交（张三/火车，张三/飞机， 李四/任意) ，过滤列表只过滤出张三的信息。
  1. 通过curl提交上述描述的三条消息。
  1. 看结果：
    - 看kafka consumer消息证明消息已经发送出来
    - 看casandra数据库证明消息已经经过过滤存储到数据库中
    - 通过rest接口或者web显示结果，证明可以从casandra中读出来相关信息

## Six 5 minutes
答疑


需要准备的材料
---

## 演示文档
其中包含：
- 架构图
- 组网图，单机可以简单标明一下端口信息
- 所用到的技术列表和版本号
- spring xd的两个启动命令
- spring xd shell中管理module的命令
- spring xd shell中管理stream的命令
- REST API
- 要提交的三条消息
- curl命令

## 环境准备
1. 将所有环境布置到一台机器上。
2. 比较详细的step by step的环境搭建文档。
3. 备用环境
