package mybatis.internal.plugin2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invocation {
	private Object target;
	private Method method;
	private Object[] args;
	
	public Invocation(Object target, Method method, Object[] args) {
		super();
		this.target = target;
		this.method = method;
		this.args = args;
	}
	
	/**
	 * 继续执行被拦截的方法
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Object proceed() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(target, args);
	}

}
