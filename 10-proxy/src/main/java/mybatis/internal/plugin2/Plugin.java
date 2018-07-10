package mybatis.internal.plugin2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 插件对象，通过动态代理模式，对需要处理的方法进行控制。具体如何处理交由拦截器去处理
 * @author yangsai
 *
 */
public class Plugin implements InvocationHandler {
	
	// 插件处理的目标对象
	private Object target;
	
	private Map<Class<?>, Set<Method>> signatureMap;
	
	//拦截器
	private Interceptor interceptor;
	
	public Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
		super();
		this.target = target;
		this.interceptor = interceptor;
		this.signatureMap = signatureMap;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// 通过method参数定义的类 去signatureMap当中查询需要拦截的方法集合
		Set<Method> methods = signatureMap.get(method.getDeclaringClass());
		// 判断是否是需要拦截的方法，如果需要拦截的话就执行实现的Interceptor的intercept方法
		if (methods != null && methods.contains(method)) {
			return interceptor.intercept(new Invocation(target, method, args));
		}
		
		// 否则直接执行原有的方法
		return method.invoke(target, args);
	}

}
