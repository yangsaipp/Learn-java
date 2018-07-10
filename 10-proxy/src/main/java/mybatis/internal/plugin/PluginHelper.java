package mybatis.internal.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

import mybatis.custom.plugin.Log2Plugin;
import mybatis.custom.plugin.LogPlugin;

public class PluginHelper {
	
	private static Class<?>[] arrPluginClass = {LogPlugin.class, Log2Plugin.class};
	
	/**
	 * 增加插件功能。
	 * @param obj
	 * @return
	 */
	public static Object wrap(Object target) {
		
		for(Class<?> pluginClass : arrPluginClass) {
			
			try {
				// 构建插件对象
				Constructor c = pluginClass.getConstructor(Object.class);
				Plugin plugin = (Plugin) c.newInstance(target);
				// 使用动态代理生成代理类
				target = wrap(target, plugin);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return target;
		
	}

	
	private static Object wrap(Object target, Plugin plugin) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), plugin);
	}
}
