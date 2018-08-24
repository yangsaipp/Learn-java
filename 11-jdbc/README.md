# JDBC如何高性能的进行批处理操作？

结论：
1. mySQL的驱动包升级到5.1.13或者以上
2. mysql连接参数使用rewriteBatchedStatements=true
3. 虽然使用事务区别不大，但是还是建议使用事务。

测试类见本目录工程，批处理测试结果。
![mark](http://ol28s5tk9.bkt.clouddn.com/mdimages/20180715/151548892.png)