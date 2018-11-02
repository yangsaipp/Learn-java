package agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import com.comtop.encrypt.ClassEncryptLib;

public class MyTransformer implements ClassFileTransformer {

	// 类加载时调用，可将类字节码修改后返回给JVM使用，null表示不修改使用原本的字节码。
	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		if (className.startsWith("com/comtop")) {
			System.out.printf("解密类: %s \n", className);
			return ClassEncryptLib.decrypt(classfileBuffer);
		}
		
		if (className.startsWith("main")) {
			System.out.printf("transform.transform(): %s \n", className);
		}

		return null;
	}
	
}
