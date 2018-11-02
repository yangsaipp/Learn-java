# 池对象

## 疑问收集

### 1. GenericObjectPool的borrowObject以及returnObject的逻辑？

### 2. GenericObjectPool中borrowObject怎么实现获取超时？

#### Tomcat JDBC
 使用一个java.util.concurrent.CountDownLatch来监听空闲队列（idle）中的数量是否有变化。具体实现见org.apache.tomcat.jdbc.pool.FairBlockingQueue.poll()和offer()方法。

### 3. GenericObjectPool中如何定时驱逐多余的空闲池资源？

#### Tomcat JDBC

使用java.util.Timer与java.util.TimerTask来实现定时器的功能，每个Timer只会启动一个线程。具体代码见org.apache.tomcat.jdbc.pool.ConnectionPool.registerCleaner(PoolCleaner)


### 4. GenericObjectPool中创建池资源时如何保证不超过最大的数量？

#### Tomcat JDBC
使用AtomicInteger。具体实现见：org.apache.tomcat.jdbc.pool.ConnectionPool.borrowConnection(int, String, String)
```
//if we get here, see if we need to create one
//this is not 100% accurate since it doesn't use a shared
//atomic variable - a connection can become idle while we are creating
//a new connection
if (size.get() < getPoolProperties().getMaxActive()) {
    //atomic duplicate check
    if (size.addAndGet(1) > getPoolProperties().getMaxActive()) {
        //if we got here, two threads passed through the first if
        size.decrementAndGet();
    } else {
        //create a connection, we're below the limit
        return createConnection(now, con, username, password);
    }
} //end if
```

### 5. GenericObjectPool中销毁池资源时如何保证保留指定的空闲数量？

## 重点摘要

记录学习过程觉得比较重要文字片段。

## 