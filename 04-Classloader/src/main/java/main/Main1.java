package main;

import java.io.IOException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;
import asm.util.ASMBytecodeDisassembler;

/**
 * 同一个classLoader只能加载一次class
 * 
 * @author  杨赛
 * @since   jdk1.7
 * @version 2017年6月5日 杨赛
 */
public class Main1 {

	public static void main(String[] args) throws Exception {
		byte[] retVal = getByteCode();
		// 使用自己classLoader加载
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> clazz = classLoader.defineClassByName("main.People", retVal, 0, retVal.length);
		// 报错：java.lang.LinkageError: loader (instance of  main/MyClassLoader): attempted  duplicate class definition for name: "main/People"
		Class<?> clazz2 = classLoader.defineClassByName("main.People", retVal, 0, retVal.length);
		
		
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
