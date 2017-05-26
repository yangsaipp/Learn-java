package agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import asm.AopClassAdapter;

public class MyTransformer implements ClassFileTransformer {

	// �����ʱ���ã��ɽ����ֽ����޸ĺ󷵻ظ�JVMʹ�ã�null��ʾ���޸�ʹ��ԭ�����ֽ��롣
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
