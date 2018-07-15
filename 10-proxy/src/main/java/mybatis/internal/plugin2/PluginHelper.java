package mybatis.internal.plugin2;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.plugin.PluginException;

import mybatis.custom.plugin2.Log2Interceptor;
import mybatis.custom.plugin2.LogInterceptor;
import mybatis.internal.plugin2.annotation.Intercepts;
import mybatis.internal.plugin2.annotation.Signature;

public class PluginHelper {
	
	private static Interceptor[] arrInterceptor = {new LogInterceptor(), new Log2Interceptor()};
	
	/**
	 * 增加插件功能。
	 * @param obj
	 * @return
	 */
	public static Object wrap(Object target) {
		for(Interceptor interceptor : arrInterceptor) {
			// 获取拦截器上的注解
			Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
			
			Class<?> type = target.getClass();
			// 获取注解里指定要拦截的接口类
		    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
		    if(interfaces.length > 0) {
		    	try {
					// 构建插件对象
					Plugin plugin = new Plugin(target, interceptor, signatureMap);
					// 使用动态代理生成代理类
					target = Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, plugin);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
			
		}
		return target;
		
	}
	
	private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
	    Set<Class<?>> interfaces = new HashSet<Class<?>>();
	    while (type != null) {
	      for (Class<?> c : type.getInterfaces()) {
	        if (signatureMap.containsKey(c)) {
	          interfaces.add(c);
	        }
	      }
	      type = type.getSuperclass();
	    }
	    return interfaces.toArray(new Class<?>[interfaces.size()]);
	  }
	
	private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
	    Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
	    if (interceptsAnnotation == null) {
	      throw new PluginException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());      
	    }
	    Signature[] sigs = interceptsAnnotation.value();
	    Map<Class<?>, Set<Method>> signatureMap = new HashMap<Class<?>, Set<Method>>();
	    for (Signature sig : sigs) {
	      Set<Method> methods = signatureMap.get(sig.type());
	      if (methods == null) {
	        methods = new HashSet<Method>();
	        signatureMap.put(sig.type(), methods);
	      }
	      try {
	        Method method = sig.type().getMethod(sig.method(), sig.args());
	        methods.add(method);
	      } catch (NoSuchMethodException e) {
	        throw new PluginException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
	      }
	    }
	    return signatureMap;
	  }
}
