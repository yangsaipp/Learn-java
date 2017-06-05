package main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;

/**
 * new或者Class.forName(String name)加载class时使用的是当前Class所使用的classloader。
 * 
 * @author  杨赛
 * @since   jdk1.7
 * @version 2017年6月5日 杨赛
 */
public class Main2 {

	
	public static void main(String[] args) throws Exception {
		byte[] retVal = getByteCode();
		// People.class中使用到Sex.class
		// 使用自己classLoader加载People.class
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> clazz = classLoader.defineClassByName("main.People", retVal, 0, retVal.length);
		Object o = clazz.newInstance();		
		// MyClassLoader加载main.Sex
		// Sex的实际加载者是AppClassLoader，是由于myClassloader的parent是它（双亲委托机制）。
		getMethod(clazz, "testNew").invoke(o);
		
		// ClassForName和new一样
		getMethod(clazz, "testClassForName").invoke(o);
		
		System.err.println();
		
		// 使用不带有双亲委托机制的classLoader
		MyClassLoader2 classLoader2 = new MyClassLoader2();
		Class<?> clazz2 = classLoader2.defineClassByName("main.People", retVal, 0, retVal.length);
		Object o1 = clazz2.newInstance();		
		// MyClassLoader2加载main.Sex
		// Sex的实际加载者还是AppClassLoader2。
		getMethod(clazz2, "testNew").invoke(o1);
		
		// ClassForName和new一样
		getMethod(clazz2, "testClassForName").invoke(o1);
	}

	private static Method getMethod(Class<?> clazz, String methodName)
			throws IllegalAccessException, InvocationTargetException {
		for(Method m : clazz.getMethods()){
			if(m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}

	private static byte[] getByteCode() throws IOException {
		// 使用ASM获取people类的字节码
		ClassReader cr = new ClassReader("main.People");
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cr.accept(new AopClassAdapter(Opcodes.ASM5, cw),
				ClassReader.EXPAND_FRAMES);
		byte[] retVal = cw.toByteArray();
		return retVal;
	}

}
