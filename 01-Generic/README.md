# Java 泛型

## 1. 泛型的使用

### 1.1 定义泛型

```java
// 定义泛型：类名上加上<T>
public class Dao<T> {
    // 内部使用定义的泛型
    private T t;

    // 内部使用定义的泛型
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

**泛型获取方法**

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

**泛型获取方法**

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

**泛型获取方法**

```java
// add方法必须需要public才能获取到
Method m = MyDao.class.getMethod("add", Dao.class);
Type[] arrType = m.getGenericParameterTypes();
assert ((ParameterizedType)arrType[0]).getRawType().equals(Dao.class);
assert (((ParameterizedType)arrType[0]).getActualTypeArguments())[0].equals(Integer.class);
```

三种不同的场景获取泛型信息除了最开始获取Type对象的方式都不一样而已。

## 2. Class与Type的关系

![mark](http://ol28s5tk9.bkt.clouddn.com/mdimages/20170511/215610381.png)

## 3. Type三种类型

**ParameterizedType**

场景代码：
```java

// 场景一：类继承
public class MyDao extends Dao<String>{
// ......
}

// 场景二：类属性
private List<Integer> list;

// 场景三：类方法
public void update(List<String> ids){}

```

![mark](http://ol28s5tk9.bkt.clouddn.com/mdimages/20170510/215535647.png)

**WildcardType**

泛型中带通配符"?"的都是WildcardType对象，如List<?>、List<? extends Number>、List<? super Number>，WildcardType一般是作为ParameterizedType的部分而存在。

主要是用在集合对象中，如：List<? extends Number>

1. 上限(? extends Class)：只能get不能add，get返回的对象为extends的Class类型。
2. 下限(? super Class)：能get能add，get返回的对象为Object，add的对象只能是super的Class类型的子类。

场景代码：
```java

// 场景一：类继承
public class MyDao extends Dao<? extends Number>{
// ......
}

// 场景二：类属性
private List<? extends Number> list = new ArrayList<Integer>();
private List<? super Integer> list = new ArrayList<Number>();

// 场景三：类方法
public void update(List<? extends Number> ids){}

```

![mark](http://ol28s5tk9.bkt.clouddn.com/mdimages/20170510/215919141.png)

**TypeVariable**

带有泛型变量如："E"、"T"、"K"、"V"等，就是TypeVariable。

主要在定义带泛型的类的内部使用。

场景代码：
```java

// 场景一：定义类内部使用
public class Dao<T> {
	
	// 代表new Dao<String>()里的String
	private T t;
	
	public void set(T t){
		this.t = t;
	}
	
}

// 场景二：方法上使用
// 这种用法可以通过编译但是目前没发现具体使用场景
public <E> void add(E e){....}
// 等同于（若E extends Number则下面的Object替换为Number）
public void add(Object e){....}

```

![mark](http://ol28s5tk9.bkt.clouddn.com/mdimages/20170510/220047138.png)

## 总结

泛型的具体类型只有在使用的时候才知道，故无法通过定义泛型的Class对象直接获取，而对应三种使用场景中可以通过不同方式获取泛型的信息。

获取泛型的类型的大概步骤如下：
1. 首先需要获取Type。
2. 转换为ParameterizedType等Type的子类。
3. 得到泛型类型Class对象。

当你需要对多个集合元素是有继承关系的集合进行统一处理时，若统一处理过程只是读取集合数据则可以考虑使用"? extend Class"，若需要频繁add则考虑"? super Class"。  
如：  
类结构关系：C extends B extends A，需要对"List<C>、List<B>、List<A>"进行统一处理.
