package springboot.loader.jar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 代表一个内嵌的jar文件
 * @author yangsai
 *
 */
public class NestJarFile extends java.util.jar.JarFile {
	/** 内嵌的jar文件名 */
	private String name;	

	/** 内嵌jar里的所有类名 */
	private Map<String, NestJarEntry> mapNestJarEntry = new HashMap<String, NestJarEntry>();

	public NestJarFile(File root, JarFile jarFile, JarEntry jarEntry) throws IOException {
		super(root);
		try {
			InputStream is = jarFile.getInputStream(jarEntry);
			name = jarEntry.getName();
//			
			ZipInputStream zin = new ZipInputStream(is);
			ZipEntry ze;  
	           while ((ze = zin.getNextEntry()) != null) {  
	               if (ze.isDirectory()) {
//	            	   System.out.println(ze.getName());
	               } else {
	            	   NestJarEntry objNestJarEntry = createNestJarEntry(ze, zin);
	            	   if(ze.getName().endsWith("ObjectMapper.class")) {
	            		   System.out.println(name + ":" + ze.getName());
	            	   }
	            	   mapNestJarEntry.put(objNestJarEntry.getName(), objNestJarEntry);
	               }  
	           }  
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private NestJarEntry createNestJarEntry(ZipEntry ze, ZipInputStream zin) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = zin.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, bytesRead);
		}
		byte[] byteCode = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		return new NestJarEntry(ze.getName(), byteCode, ze);
	}

	@Override
	public JarEntry getJarEntry(String name) {
		return (JarEntry) getEntry(name);
	}

	public boolean containsEntry(String name) {
		return mapNestJarEntry.containsKey(name);
	}

	@Override
	public ZipEntry getEntry(String name) {
		return mapNestJarEntry.get(name);
	}

	@Override
	public synchronized InputStream getInputStream(ZipEntry ze) throws IOException {
		if(mapNestJarEntry.containsKey(ze.getName())) {
			NestJarEntry objNestJarEntry = mapNestJarEntry.get(ze.getName());
			return new ByteArrayInputStream(objNestJarEntry.getByteCode());
		}
		return null;
	}

//	public void listClass() {
//		ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(
//				byteCode));
//		ZipEntry entry = null;
//		try {
//			while ((entry = zis.getNextEntry()) != null) {
//				System.out.println(String.format("%s:%s", name,
//						entry.getName()));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public Manifest getManifest() throws IOException {
		System.out.println("getManifest");
		return super.getManifest();
	}
}
