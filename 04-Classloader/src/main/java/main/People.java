package main;

public class People {
	
	public void testNew(){
		// People使用MyClassLoader，故main.Sex也调用MyClassLoader加载
		Sex sex = new Sex();
		// 但Sex的实际加载者还是AppClassLoader，是由于myClassloader的parent是它（双亲委托机制）。
		System.out.println("===Sex: " + sex.getClass().getClassLoader());
	}
	
	public void testDriver(){
		System.out.println("Driver.");
	}
	
	public void testClassForName() throws ClassNotFoundException {
		// 与new一样
		Class<?> clazz = Class.forName("main.Sex2");
		System.out.println("===Sex2: " + clazz.getClassLoader());
	}
}
