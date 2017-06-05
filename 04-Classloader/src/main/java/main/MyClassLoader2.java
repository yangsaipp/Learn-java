package main;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;

public class MyClassLoader2 extends ClassLoader {
	public Class<?> defineClassByName(String name, byte[] b, int off, int len) {
		Class<?> clazz = super.defineClass(name, b, off, len);
		return clazz;
	}

	/**
	 * 不使用双亲委托机制
	 */
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		// 为保证安全问题问题，java.lang必须得使用Bootstrap ClassLoader加载。
		// 否则会报错java.lang.SecurityException: Prohibited package name: java.lang
		if(name.startsWith("java.lang") || name.startsWith("java.io")) {	
			return super.loadClass(name);
		}
		
		System.out.println("=MyClassLoader2.loadClass():" + name);
		byte[] retVal;
		try {
			retVal = getByteCode(name);
			return defineClassByName(name, retVal, 0, retVal.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] getByteCode(String name) throws IOException {
		// 使用ASM获取people类的字节码
		ClassReader cr = new ClassReader(name);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cr.accept(new AopClassAdapter(Opcodes.ASM5, cw),
				ClassReader.EXPAND_FRAMES);
		byte[] retVal = cw.toByteArray();
		return retVal;
	}
}