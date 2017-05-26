package agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;

public class MyTransformer implements ClassFileTransformer {

	// 类加载时调用，可将类字节码修改后返回给JVM使用，null表示不修改使用原本的字节码。
	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		if (className.equals("main/People")) {
			System.out.printf("MyTransformer.transform(): %s \n", className);
			byte[] retVal = null;
			ClassReader cr = new ClassReader(classfileBuffer);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			cr.accept(new AopClassAdapter(Opcodes.ASM5, cw),
					ClassReader.SKIP_DEBUG);
			retVal = cw.toByteArray();
			return retVal;
		}

		return null;
	}
	
}
