package main;

import java.lang.reflect.Method;

import asm.MyClassLoader;
import asm.PeopleAop2Dump;

public class MainClass {

	// -javaagent:MyAgent.jar 启动
	public static void main(String[] args) throws Exception {
		System.out.println("MainClass.main()");
		People name = new People();
		name.testSay("hello2");
		name.testDriver();
		
		byte[] code = PeopleAop2Dump.dump();
		//直接将二进制流加载到内存中
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> AsmPeopleAop2 = classLoader.defineClassByName(null, code, 0, code.length);
		Object o = AsmPeopleAop2.newInstance();
		System.out.println("=========AsmPeopleAop2=========");
		//通过反射调用main方法
		for(Method m : AsmPeopleAop2.getMethods()) {
			if(m.getName().equals("testSay")) {
				m.invoke(o, "AsmPeopleAop2.");
			}
		}
	}

}
