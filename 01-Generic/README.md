# Java 泛型

# 使用泛型的三种场景以及获取方式

### 1. 类继承

### 2. 方法中使用

### 3. 类属性

# Class与Type的关系

![mark](http://ol28s5tk9.bkt.clouddn.com/mdimages/20170510/001154541.png)

# 总结

由于在使用的时候才知道具体的类型，故无法通过Class对象直接获取，在三种使用场景中可以分别获取泛型的信息。

若想通过反射获取泛型的类型，首先需要获取Type，在转换为ParameterizedType最后得到泛型类型Class。