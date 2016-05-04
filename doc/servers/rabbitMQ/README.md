# RabbitMQ
开源健壮的消息中间件，官网http://www.rabbitmq.com/
## 安装
### 安装rabbitMQ
```
$ brew install rabbitmq

==> Installing dependencies for rabbitmq: xz, unixodbc, jpeg, libpng, libtiff, wxmac, erlang

==> Installing rabbitmq dependency: xz
==> Downloading https://homebrew.bintray.com/bottles/xz-5.2.2.el_capitan.bottle.tar.gz
  /usr/local/Cellar/xz/5.2.2: 59 files, 1.4M

==> Installing rabbitmq dependency: unixodbc
==> Downloading https://homebrew.bintray.com/bottles/unixodbc-2.3.2_1.el_capitan.bottle.1.tar.gz
  /usr/local/Cellar/unixodbc/2.3.2_1: 31 files, 947.9K

==> Installing rabbitmq dependency: jpeg
==> Downloading https://homebrew.bintray.com/bottles/jpeg-8d.el_capitan.bottle.2.tar.gz
 /usr/local/Cellar/jpeg/8d: 18 files, 712.4K

==> Installing rabbitmq dependency: libpng
==> Downloading https://homebrew.bintray.com/bottles/libpng-1.6.21.el_capitan.bottle.tar.gz
 /usr/local/Cellar/libpng/1.6.21: 17 files, 1.2M

==> Installing rabbitmq dependency: libtiff
==> Downloading https://homebrew.bintray.com/bottles/libtiff-4.0.6.el_capitan.bottle.tar.gz
 /usr/local/Cellar/libtiff/4.0.6: 259 files, 3.4M

==> Installing rabbitmq dependency: wxmac
==> Downloading https://homebrew.bintray.com/bottles/wxmac-3.0.2_2.el_capitan.bottle.tar.gz
 /usr/local/Cellar/wxmac/3.0.2_2: 778 files, 23.7M

==> Installing rabbitmq dependency: erlang
==> Downloading https://homebrew.bintray.com/bottles/erlang-18.2.1.el_capitan.bottle.1.tar.gz
==> Caveats
Man pages can be found in:
  /usr/local/opt/erlang/lib/erlang/man
Access them with `erl -man`, or add this directory to MANPATH.
==> Summary
 /usr/local/Cellar/erlang/18.2.1: 7,476 files, 272.9M

==> Installing rabbitmq
==> Downloading https://www.rabbitmq.com/releases/rabbitmq-server/v3.6.1/rabbitmq-server-generic-unix-3.6.1.tar.xz
==> /usr/bin/unzip -qq -j /usr/local/Cellar/rabbitmq/3.6.1/plugins/rabbitmq_management-3.6.1.ez rabbitmq_management-3.6.1/priv/www/cli/rabbitmqadmin
==> Caveats
Management Plugin enabled by default at http://localhost:15672

Bash completion has been installed to:
  /usr/local/etc/bash_completion.d

To have launchd start rabbitmq at login:
  ln -sfv /usr/local/opt/rabbitmq/*.plist ~/Library/LaunchAgents
Then to load rabbitmq now:
  launchctl load ~/Library/LaunchAgents/homebrew.mxcl.rabbitmq.plist
Or, if you don't want/need launchctl, you can just run:
  rabbitmq-server
```

##  运行

### 启动rabbitMQ
```
sudo /usr/local/sbin/rabbitmq-server –detached
```
### 测试rabbitMQ
GUI地址http://127.0.0.1:15672/。   
在http://127.0.0.1:15672/#/exchanges中可以看到Spring Cloud Stream创建的exchange，例如ktestout。

### 停止rabbitMQ
```
rabbitmqctl stop
```
