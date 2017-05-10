# Java 泛型

## 1. 泛型的使用

### 1.1 定义泛型

```java
// 定义泛型：类名上加上<T>
public class Dao<T> {
    // 内部直接定义的泛型
    private T t;

    // 内部直接定义的泛型
    public void set(T t){
        this.t = t;
    }
}
```

### 1.2 外部使用泛型

#### a. 类继承

```java
public class MyDao extends Dao<String>{
    //..
}
```

*泛型获取方法*

```java
// 获取Dao<String>
Type t = MyDao.class.getGenericSuperclass();
assert t instanceof ParameterizedType;
assert ((ParameterizedType)t).getRawType().equals(Dao.class);
assert (((ParameterizedType)t).getActualTypeArguments())[0].equals(String.class);
```

#### b. 类属性

```java
public class MyDao extends Dao<String>{
    // 类属性
    private Dao<Long> list;
    //..
}

```

*泛型获取方法*

```java
Field f = MyDao.class.getDeclaredField("list");
Type t = (f.getGenericType());
assert t instanceof ParameterizedType;
assert ((ParameterizedType)t).getRawType().equals(Dao.class);
assert (((ParameterizedType)t).getActualTypeArguments())[0].equals(Long.class);
```

#### c. 类方法

```java
public class MyDao extends Dao<String>{
    // 方法中使用
    public void add(Dao<Integer> set) {
        
    }
    //..
}
```

*泛型获取方法*

```java
// add方法必须需要public才能获取到
Method m = MyDao.class.getMethod("add", Dao.class);
Type[] arrType = m.getGenericParameterTypes();
assert ((ParameterizedType)arrType[0]).getRawType().equals(Dao.class);
assert (((ParameterizedType)arrType[0]).getActualTypeArguments())[0].equals(Integer.class);
```

## 2. Class与Type的关系

![mark](http://ol28s5tk9.bkt.clouddn.com/mdimages/20170510/001154541.png)

### 3. Type的4种子类

## 总结

泛型的具体类型只有在使用的时候才知道，故无法通过定义泛型的Class对象直接获取，而对应三种使用场景中可以通过不同方式获取泛型的信息。

获取泛型的类型的大概步骤如下：
1. 首先需要获取Type。
2. 转换为ParameterizedType等Type的子类。
3. 得到泛型类型Class对象。