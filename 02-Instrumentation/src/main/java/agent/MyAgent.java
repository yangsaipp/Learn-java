package agent;

import java.lang.instrument.Instrumentation;


public class MyAgent {
	
	// 定义premain方法该方法在main方法之前执行
	public static void premain(String agentArgs, Instrumentation ins) {
		System.out.printf("MyAgent.premain();agentArgs:%s \n", agentArgs);
		ins.addTransformer(new MyTransformer());
	}
}
