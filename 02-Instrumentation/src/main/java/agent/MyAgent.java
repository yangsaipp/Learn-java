package agent;

import java.lang.instrument.Instrumentation;


public class MyAgent {
	
	// ����premain�����÷�����main����֮ǰִ��
	public static void premain(String agentArgs, Instrumentation ins) {
		System.out.printf("MyAgent.premain();agentArgs:%s \n", agentArgs);
		ins.addTransformer(new MyTransformer());
	}
}
