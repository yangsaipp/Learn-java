package main;

import asm.AopInteceptor;

public class PeopleAop {
	
	public void testSay(String word){
		AopInteceptor.before(word);
		System.out.println(word);
		AopInteceptor.after();
	}
	
	public void testDriver(){
		AopInteceptor.before();
		System.out.println("Driver.");
		AopInteceptor.after();
		PeopleAop p = new PeopleAop();
		String w = "t";
		p.testSay(w);
		int a = 1;
		int b = 3;
		int c = a + b;
	}
	
}
