package springboot.loader.jar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class JarURLConnection extends java.net.JarURLConnection {
	private NestJarFile nestJarFile;
	private String jarEntryName;	// 要加载的class文件名
	private JarEntry jarEntry;
	protected JarURLConnection(URL url, NestJarFile nestJarFile, String jarEntryName) throws MalformedURLException {
		super(url);
		this.nestJarFile = nestJarFile;
		this.jarEntryName = jarEntryName;
	}

	@Override
	public JarFile getJarFile() throws IOException {
		connect();
		System.out.println("JarURLConnection:getJarFile");
		return nestJarFile;
	}

	@Override
	public void connect() throws IOException {
		jarEntry = (JarEntry) nestJarFile.getEntry(jarEntryName);
		this.connected = true;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if(jarEntry == null) {
			return null;
		}
		return nestJarFile.getInputStream(jarEntry);
	}
}
