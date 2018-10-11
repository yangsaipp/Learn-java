package main;

public class GCTest {
	
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		testAllocation();
	}

	/**
	 * 对象优先在eden分配
	 * VM参数：-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:SurvivorRatio=8
	 * 说明：heap分配20M，其中年轻代大小10M，年轻代里eden:from:to为8:1:1
	 */
	public static void testAllocation() {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[4 * _1MB];// 出现一次Minor GC
	}
}
