# RabbitMQ
开源健壮的消息中间件，官网http://www.rabbitmq.com/
## 安装
### 安装erLang
rabbitMQ的运行需要erLang。
```
sudo brew install erlang
```
测试：安装成功后，输入erl可进入erlang视图。
### 安装rabbitMQ
```
sudo brew install rabbitmq
```

##＃  运行rabbitMQ
```
sudo /usr/local/sbin/rabbitmq-server –detached
```
#### 测试rabbitMQ
GUI地址http://127.0.0.1:15672/。   
在http://127.0.0.1:15672/#/exchanges中可以看到Spring Cloud Stream创建的exchange，例如ktestout。

### 停止rabbitMQ
```
rabbitmqctl stop
```
