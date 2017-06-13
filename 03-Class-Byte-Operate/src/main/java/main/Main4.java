package main;

import java.io.File;
import java.io.FileInputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;

/**
 * 问题：ClassA里使用到ClassB，在ClassB不在ClassPath的情况下使用classloader的defineClass方法会报ClassNotFound错误吗？
 * 答：以下场景会报错ClassNotFound错误。
 * 	1. ClassA extends ClassB
 * 	2. ClassA implements ClassB
 * 
 * 总结：defineClass方法会触发要加载类的父类和接口类加载，若它们无法加载那么就会报ClassNotFound错误。
 * @author  杨赛
 * @since   jdk1.7
 * @version 2017年6月5日 杨赛
 */
public class Main4 {

	public static void main(String[] args) throws Exception {
		// 使用ASM获取字节码
		File file = new File("D:\\Program Files\\eclipse-jee-mars-2-win32-x86_64\\workspace\\Test\\bin\\test\\ClassA.class");
		ClassReader cr = new ClassReader(new FileInputStream(file));
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cr.accept(new AopClassAdapter(Opcodes.ASM5, cw),
				ClassReader.EXPAND_FRAMES);
		byte[] retVal = cw.toByteArray();
		
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> clazz = classLoader.defineClassByName("test.ClassA", retVal, 0, retVal.length);
		
//		Object o = clazz.newInstance();
//		System.out.println(o.getClass());
		
		// 调用People的方法，看看断点是否有效
//		Object obj = clazz.newInstance();
//		for(Method m : clazz.getDeclaredMethods()) {
//			if(m.getName().equals("testDriver")) {
//				m.invoke(obj);
//			}
//		}
		
	}

}
