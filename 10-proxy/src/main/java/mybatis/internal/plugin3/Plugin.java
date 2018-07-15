package mybatis.internal.plugin3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 插件对象，通过动态代理模式，对需要处理的方法进行控制。具体如何处理交由拦截器去处理
 * @author yangsai
 *
 */
public abstract class Plugin<E> implements InvocationHandler {
	
	// 插件处理的目标对象
	protected E target;
	
	public Plugin(E target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return method.invoke(this, args);
	}

}
