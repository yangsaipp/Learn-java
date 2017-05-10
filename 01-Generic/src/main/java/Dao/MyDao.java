package Dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

// 继承
public class MyDao extends Dao<String>{
	
	// 类属性
	private Dao<Long> list;

	private Long l;
	
	// 方法中使用
	public void add(Dao<Integer> set) {
		
	}
	
	public static void main(String[] args) throws Exception, SecurityException {
		Field f = MyDao.class.getDeclaredField("list");
		Type t = (f.getGenericType());
		assert t instanceof ParameterizedType;
		assert ((ParameterizedType)t).getRawType().equals(Dao.class);
		assert (((ParameterizedType)t).getActualTypeArguments())[0].equals(Long.class);
		
		f = MyDao.class.getDeclaredField("l");
		t = (f.getGenericType());
		assert t instanceof Class;
		assert t.equals(Long.class);
		
		// add方法必须需要public才能获取到
		Method m = MyDao.class.getMethod("add", Dao.class);
		Type[] arrType = m.getGenericParameterTypes();
		assert ((ParameterizedType)arrType[0]).getRawType().equals(Dao.class);
		assert (((ParameterizedType)arrType[0]).getActualTypeArguments())[0].equals(Integer.class);
		
		// 获取Dao<String>
		t = MyDao.class.getGenericSuperclass();
		assert t instanceof ParameterizedType;
		assert ((ParameterizedType)t).getRawType().equals(Dao.class);
		assert (((ParameterizedType)t).getActualTypeArguments())[0].equals(String.class);
	}
}
