package springboot.loader.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 内嵌jar类加载器
 * @author yangsai
 *
 */
public class NestJarClassLoader extends URLClassLoader {

	public NestJarClassLoader(URL[] urls) {
		super(urls);
	}

}
