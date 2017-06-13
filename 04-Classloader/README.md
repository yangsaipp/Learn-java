# ClassLoader

Java ClassLoader 测试

# 问题
**1. Java new关键字所使用的classloader是哪个？**

new、Class.forName加载类时使用的是当前类所使用的classLoader。

**2. java热加载隔离原理是什么？**


**3. tomcat应用类隔离原理是什么？**

Tomcat为每个应用会实例化一个WebappClassLoader去加载Servlet、Filter、JSP，根据问题1可以推论出我们在Servlet里使用到的类也都是Servlet所使用的classloader即实例化的WebappClassLoader，这保证了每个应用下的所有代码都是不同的ClassLoader加载而来，根据问题4可以得知就是不同应用的里出现同样的类也不会出现错误使用问题。

**4. ClassLoaderA加载A，ClassLoaderB加载A的子类B，B能转换为A吗？**

不能，不同classCloader加载的类不能转换，问题中的ClassLoaderB加载的B类也不是ClassLoaderA加载的A类的子类。

**5. ClassA里使用到ClassB，在ClassB不在ClassPath的情况下使用classloader的defineClass方法会报ClassNotFound错误吗？**

Demo见main/main4.java。以下场景会报错ClassNotFound错误，

- ClassA extends ClassB
- ClassA implements ClassB

 总结：defineClass方法会触发要加载类的父类和接口类加载，若它们无法加载那么就会报ClassNotFound错误。

**6. 什么代码会触发类的加载？**
 
以ClassA为例，以下代码会触发：

- 实例化对象
```java
    new ClassA();
```
- 直接访问Class对象
```java
    ClassA.class.getName();
```
- 父类或者接口，当子类加载时会触发父类或接口加载
```java
ClassB extends ClassA {

}

ClassB implements ClassA {

}
```
- 静态方法调用
```java
ClassA.getInstaces();
```
- Class.forName("ClassA");
- ClassLoader.loaderClass方法或者ClassLoader.defineClass方法
```java
ClassLoader.loaderClass("ClassA");
ClassLoader.defineClass("ClassA", codeByteArray, 0, codeByteArray.length);
```