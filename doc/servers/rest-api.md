## 生产者API


### 添加出行记录
##### Url

    POST  /journey

##### Params
~~~
{
  "name":  "张三",               //必选，姓名
  "type": "plane",              //必选，行程的类型
  "date":  "20160411",          //必选，日期
  "credentials":  "身份证"，     //证件类型
  "credential_no"："1234567",         //证件号码
  "contact":"888888",        //联系方式
  "flight": "CA1986",        //航班号
  "depart":  "beijing",       //出发地
  "dest":  "hangzhou",         //目的地
  "seat": "15F",             //座位号
  "airport":"首都机场"        //机场信息，飞机才有的信息
  "carriage": "",           //车厢号，火车才有的信息
  "station":""              //乘车车站，火车才有的信息
}
~~~

##### Result
~~~
{
    code: 200,              //标准的http错误返回码
    success: true,          //是否成功
    message: "successful",  //返回值信息，可以是成功或者获取失败之后的详细信息
}
~~~


## 消费者API
### 获取出行记录

##### Url

    GET  /journey

##### Params
~~~
  {
    "type":"plane",           //出行方式，plane或者train
    "name":"张三"，            //出行人姓名
    "start":"2016-04-02",     //出行起始日期
    "end":"2016-04-12",       //出行截止日期,可选，缺省等于起始日期
    currentPage:1,            //可选，当前页，缺省为1
    pageSize:20               //可选，每页记录数，缺省为20
  }
~~~

##### Result
~~~
{
    code: 200,              //标准的http错误返回码
    success: true,          //是否成功
    message: "successful",  //返回值信息，可以是成功或者获取失败之后的详细信息
    pageInfo:
    {
       currentPage: 1,     //当前页
       pageSize: 20,       //每页记录数
       total: 500          //总记录数
    }
    count:  20,             //本次获取的数量
    list:[
      {
        "name":  "张三",            //姓名
        "type": "plane",           //行程的类型
        "id":  "身份证"，           //证件类型
        "idno"："1234567",         //证件号码
        "contact":"888888",        //联系方式
        "date":  "20160411",     //日期
        "flight": "CA1986",        //航班号
        "depart":  "beijing",       //出发地
        "dest":  "hangzhou",         //目的地
        "seat": "15F",             //座位号
        "airport":"首都机场"        //机场信息，飞机才有的信息
        "carriage": "",           //车厢号，火车才有的信息
        "station":""              //乘车车站，火车才有的信息
      }
        ......
    ]
}
~~~
