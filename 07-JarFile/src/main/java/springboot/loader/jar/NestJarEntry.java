package springboot.loader.jar;

import java.util.zip.ZipEntry;

/**
 * 代表内嵌jar里的class类
 * @author yangsai
 *
 */
public class NestJarEntry extends java.util.jar.JarEntry {
	// 类名，包括包路径
	private String name;
	
	// 类二进制代码
	private byte[] byteCode;

	public String getName() {
		return name;
	}

	public NestJarEntry(String name, byte[] byteCode, ZipEntry ze) {
		super(ze);
		this.name = name;
		this.byteCode = byteCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getByteCode() {
		return byteCode;
	}

	public void setByteCode(byte[] byteCode) {
		this.byteCode = byteCode;
	}
}
