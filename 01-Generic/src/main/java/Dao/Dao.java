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
	
	public static void main(String[] args) throws Exception {
		Field f = Dao.class.getDeclaredField("t");
		Type t = (f.getGenericType());
		assert t instanceof TypeVariable;
		assert ((TypeVariable<?>)t).getGenericDeclaration().equals(Dao.class);
		
		// 获取方法中的set(T t)
		Method m = Dao.class.getMethod("set", Object.class);
		Type[] arrType = m.getGenericParameterTypes();
		assert arrType[0] instanceof TypeVariable;
	}
	
}
