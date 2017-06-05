package main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;

/**
 * 同一个类的字节码被不同的Classloader加载到jvm中所生产的Class对象是不同的。
 * 
 * @author  杨赛
 * @since   jdk1.7
 * @version 2017年6月5日 杨赛
 */
public class Main3 {

	
	public static void main(String[] args) throws Exception {
		byte[] retVal = getByteCode("main.People");
		// People.class中使用到Sex.class
		// 使用自己classLoader加载People.class
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> clazz = classLoader.defineClassByName("main.People", retVal, 0, retVal.length);
		
		MyClassLoader classLoader2 = new MyClassLoader();
		Class<?> clazz2 = classLoader2.defineClassByName("main.People", retVal, 0, retVal.length);
		
		// People2 extends People
		retVal = getByteCode("main.People2");
		Class<?> people2 = classLoader2.defineClassByName("main.People2", retVal, 0, retVal.length);

		System.out.println(clazz.equals(clazz2));	// false
		System.out.println(clazz.isAssignableFrom(people2)); 	// false
		System.out.println(clazz2.isAssignableFrom(people2));	// true
	}

	private static byte[] getByteCode(String name) throws IOException {
		// 使用ASM获取people类的字节码
		ClassReader cr = new ClassReader(name);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cr.accept(new AopClassAdapter(Opcodes.ASM5, cw),
				ClassReader.EXPAND_FRAMES);
		byte[] retVal = cw.toByteArray();
		return retVal;
	}

}
