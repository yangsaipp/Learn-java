package main;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JVM运行参数：-server -Xms10M -Xmx10M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.log
 * @author yangsai
 */
public class GCMain {
	static Map<String, String> oldMap = new HashMap<String, String>();
	
	public static void main(String args[]) throws InterruptedException {
		printGC();
		
		testOldGenFullGC();
//		testOldGenGC();
//		Thread.sleep(Integer.MAX_VALUE);
	}

	private static void testOldGenFullGC() throws InterruptedException {
		int i = 0;
		while (true) {
			String str = new String(i + "");
			Thread.sleep(1);
			oldMap.put(i + "", str);
			if (i++ % 5000 == 0) {
				System.out.println("暂停线程10s");
				Thread.sleep(10000);
			}
		}
	}

	private static void printGC() {
		List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean b : l) {
			System.out.println(b.getName());
		}
	}
}