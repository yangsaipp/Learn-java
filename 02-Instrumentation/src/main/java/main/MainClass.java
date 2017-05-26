package main;

import java.lang.reflect.Method;

import asm.MyClassLoader;
import asm.PeopleAop2Dump;

public class MainClass {

	// -javaagent:MyAgent.jar ����
	public static void main(String[] args) throws Exception {
		System.out.println("MainClass.main()");
		People name = new People();
		name.testSay("hello2");
		name.testDriver();
		
		byte[] code = PeopleAop2Dump.dump();
		//ֱ�ӽ������������ص��ڴ���
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> AsmPeopleAop2 = classLoader.defineClassByName(null, code, 0, code.length);
		Object o = AsmPeopleAop2.newInstance();
		System.out.println("=========AsmPeopleAop2=========");
		//ͨ���������main����
		for(Method m : AsmPeopleAop2.getMethods()) {
			if(m.getName().equals("testSay")) {
				m.invoke(o, "AsmPeopleAop2.");
			}
		}
	}

}
