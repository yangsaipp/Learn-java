package agent;

import java.lang.instrument.Instrumentation;

import com.comtop.encrypt.ClassEncryptLib;


public class MyAgent {
	
	public static void premain(String agentArgs, Instrumentation ins) {
		System.out.printf("MyAgent.premain();agentArgs:%s \n", agentArgs);
		ClassEncryptLib.loadNativeLibrary();
		ins.addTransformer(new MyTransformer());
		
	}
}
