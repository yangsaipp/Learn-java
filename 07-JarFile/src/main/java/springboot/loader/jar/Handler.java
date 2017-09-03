package springboot.loader.jar;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;


public class Handler extends java.net.URLStreamHandler {
	private NestJarFile nestJarFile;
	
	public Handler(NestJarFile nestJarFile) {
		this.nestJarFile = nestJarFile;
	}

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
//		System.out.println(u);
		String urlPath = u.getFile();
		String jarEntryName = urlPath.substring(urlPath.lastIndexOf("!/") + 2);
		if(StringUtils.isBlank(jarEntryName)) {		// 为空的时候是获取证书
			return new JarURLConnection(u, nestJarFile, jarEntryName); 
		}
		if(!nestJarFile.containsEntry(jarEntryName)) {
			throw new RuntimeException("xxx===xx");
		}
		System.out.println("=========");
		System.out.println(u);
		return new JarURLConnection(u, nestJarFile, jarEntryName);
	}

}
