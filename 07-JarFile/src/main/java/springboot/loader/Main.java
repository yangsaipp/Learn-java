package springboot.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import springboot.loader.jar.Handler;
import springboot.loader.jar.NestJarFile;

/**
 * 加载B02-ConfigRead.jar里内嵌的jackson包里ObjectMapper类
 * @author yangsai
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
//		com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//		loadOuterJar();
		loadNestJar();
		
	}

	/**
	 * 加载内嵌的jar，这里将会加载加载B02-ConfigRead.jar里内嵌的jackson包里ObjectMapper类
	 * @throws Exception
	 */
	static void loadNestJar() throws Exception {
//		System.setProperty("java.protocol.handler.pkgs", "springboot.loader");
		URL[] urls = getNestJarURL();
		URLClassLoader classLoader = new URLClassLoader(urls, Main.class.getClassLoader());
		Class<?> classMapper = classLoader.loadClass("com.fasterxml.jackson.databind.ObjectMapper");
		Object o = classMapper.newInstance();
		System.out.println(o);
	}
	
	private static URL[] getNestJarURL() throws IOException {
		List<URL> lstURL = new ArrayList<URL>();
		File root = new File("E:\\Workspaces\\workspace_java\\Learn-java\\07-JarFile\\src\\main\\resources\\B02-ConfigRead.jar");
		JarFile jf = new JarFile(root);
	    Enumeration e = jf.entries();
	    while (e.hasMoreElements()) {
	    	JarEntry je = (JarEntry) e.nextElement();
	    	if(je.getName().endsWith(".jar")) {
	    		String file = root.toURI() + "!/" +je.getName() + "!/";
	    		NestJarFile jarFile = new NestJarFile(root, jf, je);
//	    		jarFile.listClass();
	    		lstURL.add(new URL("jar", "", -1, file, new Handler(jarFile)));
	    	}
	    }
		return lstURL.toArray(new URL[lstURL.size()]);
	}

	static void loadOuterJar() throws Exception {
		File jarFile = new File("E:/gradleRepository/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-databind/2.8.6/c43de61f74ecc61322ef8f402837ba65b0aa2bf4/jackson-databind-2.8.6.jar");
		URL url = jarFile.toURI().toURL();
		File jarFile2 = new File("E:\\gradleRepository\\caches\\modules-2\\files-2.1\\com.fasterxml.jackson.core\\jackson-core\\2.8.6\\2ef7b1cc34de149600f5e75bc2d5bf40de894e60\\jackson-core-2.8.6.jar");
		URL url2 = jarFile2.toURI().toURL();
		File jarFile3 = new File("E:\\gradleRepository\\caches\\modules-2\\files-2.1\\com.fasterxml.jackson.core\\jackson-annotations\\2.8.0\\45b426f7796b741035581a176744d91090e2e6fb\\jackson-annotations-2.8.0.jar");
		URL url3 = jarFile3.toURI().toURL();
		URLClassLoader classLoader = new URLClassLoader(new URL[] {url, url2, url3}, Main.class.getClassLoader());
		Class<?> classMapper = classLoader.loadClass("com.fasterxml.jackson.databind.ObjectMapper");
		Object o = classMapper.newInstance();
		System.out.println(o);
	}
}
