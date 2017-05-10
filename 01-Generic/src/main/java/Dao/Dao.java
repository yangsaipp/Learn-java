package Dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class Dao<T> {
	private T t;
	
	public void set(T t){
		this.t = t;
	}
	
	// 该用法无意义
	public <E> void get(E e) {
		System.out.println(e);
	}
	
	public <E extends Number> E get2(E e) {
		return e;
	}
	
	public static void main(String[] args) throws Exception {
		
		Field f = Dao.class.getDeclaredField("t");
		Type t = (f.getGenericType());
		assert t instanceof TypeVariable;
		assert ((TypeVariable<?>)t).getGenericDeclaration().equals(Dao.class);
		
		// 获取方法：set(T t)
		Method m = Dao.class.getMethod("set", Object.class);
		Type[] arrType = m.getGenericParameterTypes();
		assert arrType[0] instanceof TypeVariable;
		
		// 获取方法：public <E> void get(E e) 
		m = Dao.class.getMethod("get", Object.class);
		arrType = m.getGenericParameterTypes();
		System.out.println(arrType[0].getClass());
		assert arrType[0] instanceof TypeVariable;

		// 获取方法：public <E extends Number> void get2(E e)
		m = Dao.class.getMethod("get2", Number.class);
		arrType = m.getGenericParameterTypes();
		System.out.println(arrType.length);
		assert arrType[0] instanceof TypeVariable;
	}
	
}
