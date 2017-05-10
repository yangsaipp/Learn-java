package Dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Set;

// 继承
public class MyDao2 extends Dao<Number>{
	
	// 类属性
	// 上界<? extends T>不能往里存，只能往外取
	private Dao<? extends Number> list = new Dao<Integer>();
	
	//下界<? super T>不影响往里存，但往外取只能放在Object对象里
	private Dao<? super Number> list2;
	
	// 方法中使用
	public void add(List<? extends Number> list) {
	}
	
	public static void main(String[] args) throws Exception, SecurityException {
		// Dao<? extends Number> list
		Field f = MyDao2.class.getDeclaredField("list");
		Type t = (f.getGenericType());
		assert t instanceof ParameterizedType;
		assert ((ParameterizedType)t).getRawType().equals(Dao.class);
		Type[] aType = (((ParameterizedType)t).getActualTypeArguments());
		assert aType[0] instanceof WildcardType;
		assert (((WildcardType)aType[0]).getUpperBounds())[0].equals(Number.class);
		
		// Dao<? super Number> list2
		f = MyDao2.class.getDeclaredField("list2");
		t = (f.getGenericType());
		assert t instanceof ParameterizedType;
		assert ((ParameterizedType)t).getRawType().equals(Dao.class);
		aType = (((ParameterizedType)t).getActualTypeArguments());
		assert aType[0] instanceof WildcardType;
		assert (((WildcardType)aType[0]).getLowerBounds())[0].equals(Number.class);
		
		
		// public void add(List<? extends Number> list) {}
		Method m = MyDao2.class.getMethod("add", List.class);
		Type[] arrType = m.getGenericParameterTypes();
		assert arrType[0] instanceof ParameterizedType;
		assert ((ParameterizedType)arrType[0]).getRawType().equals(List.class);
		aType = ((ParameterizedType)arrType[0]).getActualTypeArguments();
		assert aType[0] instanceof WildcardType;
		assert (((WildcardType)aType[0]).getUpperBounds())[0].equals(Number.class);
		
	}
}
