
#### 服务器上jar包所在目录
~/ygs/week3/hbase-demo-1.0-SNAPSHOT-jar-with-dependencies.jar

#### 服务器上运行日志目录
~/ygs/week3/logs/execution.log.1

#### 在服务器上执行命令:
java -jar java -jar hbase-demo-1.0-SNAPSHOT-jar-with-dependencies.jar

#### 程序运行日志如下：
```text
Current namespace : [{NAME => 'caizhen'}, {NAME => 'caojingwei'}, {NAME => 'chaichaoqun'}, {NAME => 'cheechuen'}, {NAME => 'default'}, {NAME => 'doudou'}, {NAME => 'duxiaowen'}, {NAME => 'evan'}, {NAME => 'guweisheng'}, {NAME => 'hbase'}, {NAME => 'icon'}, {NAME => 'jack'}, {NAME => 'jonas'}, {NAME => 'julianchu'}, {NAME => 'lichengjun'}, {NAME => 'lidazhi'}, {NAME => 'lizhening'}, {NAME => 'ma'}, {NAME => 'pandi'}, {NAME => 'penglei'}, {NAME => 'suhaojie'}, {NAME => 'terry'}, {NAME => 'tranq'}, {NAME => 'wangqiaohui'}, {NAME => 'weifujun'}, {NAME => 'weifujun_test'}, {NAME => 'yen'}, {NAME => 'ysq_test'}, {NAME => 'zanglei'}, {NAME => 'zhanghui'}, {NAME => 'zhangj'}, {NAME => 'zhangyu'}, {NAME => 'zhongwenyu'}, {NAME => 'zhouxingjia'}]
Creating Namespace [ygs].
Namespace created successfully!
Current namespace : [{NAME => 'caizhen'}, {NAME => 'caojingwei'}, {NAME => 'chaichaoqun'}, {NAME => 'cheechuen'}, {NAME => 'default'}, {NAME => 'doudou'}, {NAME => 'duxiaowen'}, {NAME => 'evan'}, {NAME => 'guweisheng'}, {NAME => 'hbase'}, {NAME => 'icon'}, {NAME => 'jack'}, {NAME => 'jonas'}, {NAME => 'julianchu'}, {NAME => 'lichengjun'}, {NAME => 'lidazhi'}, {NAME => 'lizhening'}, {NAME => 'ma'}, {NAME => 'pandi'}, {NAME => 'penglei'}, {NAME => 'suhaojie'}, {NAME => 'terry'}, {NAME => 'tranq'}, {NAME => 'wangqiaohui'}, {NAME => 'weifujun'}, {NAME => 'weifujun_test'}, {NAME => 'yen'}, {NAME => 'ygs'}, {NAME => 'ysq_test'}, {NAME => 'zanglei'}, {NAME => 'zhanghui'}, {NAME => 'zhangj'}, {NAME => 'zhangyu'}, {NAME => 'zhongwenyu'}, {NAME => 'zhouxingjia'}]
Check if Namespace exists now:true
Operation: CREATE, Table Name: ygs:student, procId: 938 completed
create table success, tableName: ygs:student
-----------------------------------------
Insertion
-----------------------------------------
Data insert success for rowKey:Tom
Data insert success for rowKey:Jerry
Data insert success for rowKey:Jack
Data insert success for rowKey:Rose
Data insert success for rowKey:阳广穗
-----------------------------------------
Scanning Table
-----------------------------------------
row key: Jack
info.class::2
info.student_id::20210000000003
score.programming::80
score.understanding::80
-----------------------------------------
row key: Jerry
info.class::1
info.student_id::20210000000002
score.programming::67
score.understanding::85
-----------------------------------------
row key: Rose
info.class::2
info.student_id::20210000000004
score.programming::61
score.understanding::60
-----------------------------------------
row key: Tom
info.class::1
info.student_id::20210000000001
score.programming::82
score.understanding::75
-----------------------------------------
row key: 阳广穗
info.class::1
info.student_id::G20220735020031
score.programming::90
score.understanding::90
-----------------------------------------
-----------------------------------------
-----------------------------------------
Delele Data
-----------------------------------------
Delete Success for rowkey : Tom
Delete Success for rowkey : Jerry
Delete Success for rowkey : Jack
Delete Success for rowkey : Rose
Delete Success for rowkey : 阳广穗
-----------------------------------------
Scanning Table
-----------------------------------------
No row found!
-----------------------------------------
-----------------------------------------
Delete Table
-----------------------------------------
Started disable of ygs:student
Operation: DISABLE, Table Name: ygs:student, procId: 941 completed
Operation: DELETE, Table Name: ygs:student, procId: 944 completed
Table Delete Successful
-----------------------------------------
Check if table exist: false
-----------------------------------------
Namaspace Deleted successfully.
Current namespace : [{NAME => 'caizhen'}, {NAME => 'caojingwei'}, {NAME => 'chaichaoqun'}, {NAME => 'cheechuen'}, {NAME => 'default'}, {NAME => 'doudou'}, {NAME => 'duxiaowen'}, {NAME => 'evan'}, {NAME => 'guweisheng'}, {NAME => 'hbase'}, {NAME => 'icon'}, {NAME => 'jack'}, {NAME => 'jonas'}, {NAME => 'julianchu'}, {NAME => 'lichengjun'}, {NAME => 'lidazhi'}, {NAME => 'lizhening'}, {NAME => 'ma'}, {NAME => 'pandi'}, {NAME => 'penglei'}, {NAME => 'suhaojie'}, {NAME => 'terry'}, {NAME => 'tranq'}, {NAME => 'wangqiaohui'}, {NAME => 'weifujun'}, {NAME => 'weifujun_test'}, {NAME => 'yen'}, {NAME => 'ysq_test'}, {NAME => 'zanglei'}, {NAME => 'zhanghui'}, {NAME => 'zhangj'}, {NAME => 'zhangyu'}, {NAME => 'zhongwenyu'}, {NAME => 'zhouxingjia'}]
Check if Namaspace Exist: false
```