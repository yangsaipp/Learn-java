package asm;
public class AopInteceptor {

	public static void before(){
        System.out.println(".......before.......");
    }
	
    public static void before(String word){
        System.out.println(".......before......." + word);
    }

    public static void after(){
        System.out.println(".......after().......");
    }


}