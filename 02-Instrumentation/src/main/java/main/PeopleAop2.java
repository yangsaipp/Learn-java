package main;


public class PeopleAop2 extends People {
	
	public void testSay(String word){
		System.out.println(".......before.......");
		super.testSay(word);
		System.out.println(".......after.......");
	}
	
	public void testDriver(){
		System.out.println(".......before.......");
		System.out.println("Driver.");
		System.out.println(".......after.......");
	}
	
}
