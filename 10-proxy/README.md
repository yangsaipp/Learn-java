# MyBaties插件原理

MyBaties插件实现采用了Java的动态代理。

# 什么是动态代理？

动态代理采用的设计模式是代理模式，与动态代理相对应的是静态代理。


# 如何通过动态代理实现插件？

由于需求会变化，所以一般程序或者软件都会提供一种扩展机制来应对需求的变化。插件就是程序对外提供扩展的一个实现方式。

mybaties中的插件机制就可以让用户在几个关键的点进行扩展。比如说翻页插件可以在业务SQL上加上翻页SQL，其扩展的点就是在获取SQL时可以由扩展者对SQL进行处理。

插件的本质上就是让插件实现者可以对程序里的特定类的对象的方法进行自定义的逻辑处理。通过代理模式刚好可以达到这个目的。

## 方式一：直接使用代理模式实现插件

### 实现方式

关键实现说明
1. 新建mybatis.internal.plugin.Plugin抽象类继承java.lang.reflect.InvocationHandler。

完整示例见本目录工程，运行测试用例：mybatis.Main.test1()。

### 插件扩展方式

扩展者创建自定义插件类并继承mybatis.internal.plugin.Plugin，实现invoke方法即可。具体代码见类
1. mybatis.custom.plugin.LogPlugin
2. mybatis.custom.plugin.Log2Plugin

### 评价
优点：实现简单  
缺点：实现的插件类会代理对象的所有方法，插件实现者需要额外处理不需要被代理的方法，同时还需考虑下个版本拦截的方法可能会变多。若是实现不当，会导致代理到未期望的方法，引发系统问题。

## 方式二：拦截器代理模式实现插件

### 实现方式

关键实现说明
1. 新建mybatis.internal.plugin2.Interceptor类
2. 新建mybatis.internal.plugin2.Plugin类继承java.lang.reflect.InvocationHandler。
3. mybatis.internal.plugin2.Plugin类实现invoke方法，实现的逻辑是根据配置去判断是否需要调用Interceptor的对象intercept方法。

完整示例见本目录工程，运行测试用例：mybatis.Main.test2()。

### 插件扩展方式

扩展者创建自定义拦截器并实现mybatis.internal.plugin2.Interceptor接口，在自定义拦截器上申明要拦截的对象和方法。具体代码见：
1. mybatis.custom.plugin2.LogPlugin
2. mybatis.custom.plugin2.Log2Plugin

### 评价
优点：只会拦截指定的方法，只需要实现一个拦截器即可实现多个插件功能  
缺点：拦截器里需要自行判断拦截的对象和方法，若插件功能需要拦截多个对象，则拦截器得实现逻辑会比较复杂。


## 方式三：插件代理模式实现插件

### 实现方式

关键实现说明
1. 新建mybatis.internal.plugin3.Plugin<E>抽象类，实现invoke方法。
```java

@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	return method.invoke(this, args);
}
```
2. 针对每个要扩展的类各新建一个插件抽象类并继承对应扩展类，示例见：mybatis.internal.plugin3.ExecutorPlugin。

完整示例见本目录工程，运行测试用例：mybatis.Main.test3()。

### 插件扩展方式

扩展者创建自定义插件并实现对应扩展类mybatis.internal.plugin3.ExecutorPlugin接口，重新需要扩展的方法即可。具体代码见：
1. mybatis.custom.plugin3.LogPlugin
2. mybatis.custom.plugin3.Log2Plugin

### 评价

优点：只会拦截指定的方法，实现插件时无需判断要拦截的对象和方法。  
缺点：每个插件都需要编写一个类继承对拦截对象对应的插件类。若一个插件需要拦截多个对象，则需要编写多个类。

## 总结

推荐使用方式二来实现插件机制，mybatis就是采用方式二。相对于方式三来说，方式二更灵活，方式二中拦截器只拦截一个对象中的一个方法，也无需判断要拦截的对象和方法。
