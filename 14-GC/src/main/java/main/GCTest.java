package main;

public class GCTest {
	
	/**
	 * 测试用例：对象优先在eden分配
	 * VM参数：-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:+PrintTenuringDistribution
	 * 说明：heap分配20M，其中年轻代大小10M，年轻代里eden:from:to为8:1:1
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		byte[] allocation1, allocation2, allocation3, allocation4;
		// 分配2M到eden区
		allocation1 = new byte[2 * 1024 * 1024];
		// 再分配2M到eden
		allocation2 = new byte[2 * 1024 * 1024];
		// 再分配2M到eden
		allocation3 = new byte[2 * 1024 * 1024];
		// eden剩余2M空间，无法存放4M，触发youngGC
		// 由于to区空间不足（to区大小为1M<存活对象占6M），只能将allocation1、allocation2、allocation3对象晋升到老年代
		// 然后将allocation4对象放到eden区
		allocation4 = new byte[4 * 1024 * 1024];// 出现一次Minor GC
//		Thread.sleep(Integer.MAX_VALUE);
		System.out.println("ssss");
	}

}
