package main;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;
import asm.util.ASMBytecodeDisassembler;

/**
 * 问题：使用ASM修改字节码后如何保证行号正常保留，并能正常debug？
 * 
 * ASM访问时使用ClassReader.EXPAND_FRAMES，代码如下：
 * <p>
 * <code>
 * cr.accept(new AopClassAdapter(Opcodes.ASM5, cw), ClassReader.EXPAND_FRAMES);
 * </code>
 * </p>
 * 
 * @author  杨赛
 * @since   jdk1.7
 * @version 2017年6月5日 杨赛
 */
public class Main1 {

	public static void main(String[] args) throws Exception {
		ASMBytecodeDisassembler utils = new ASMBytecodeDisassembler();
		// 使用ASM修改People类的字节码
		ClassReader cr = new ClassReader("main.People");
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cr.accept(new AopClassAdapter(Opcodes.ASM5, cw),
				ClassReader.EXPAND_FRAMES);
		byte[] retVal = cw.toByteArray();
		
		// 输出修改后字节码
		System.out.println("===================");
		System.out.println(utils.dumpBytecode(retVal));
		System.out.println("===================");
		
		FileOutputStream fos = new FileOutputStream("E:/Data/Test.class");
		//用FileOutputStream 的write方法写入字节数组
		fos.write(retVal);
		
		// 加载修改后People
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> clazz = classLoader.defineClassByName("main.People", retVal, 0, retVal.length);
		
		// 调用People的方法，看看断点是否有效
		Object obj = clazz.newInstance();
		for(Method m : clazz.getDeclaredMethods()) {
			if(m.getName().equals("testDriver")) {
				m.invoke(obj);
			}
		}
		
	}

}
