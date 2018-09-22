package main;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
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
		final ReferenceQueue<Book> referenceQueue = testSofeRef();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Reference<Book> reference = (Reference<Book>) referenceQueue.remove();
					System.out.println("reference不为空；" + reference.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		testOldGenFullGC();
//		testOldGenGC();
//		Thread.sleep(Integer.MAX_VALUE);
	}

	private static void testOldGenFullGC() throws InterruptedException {
		int i = 0;
		System.gc();
		System.out.println("GC...");
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
	
	private static ReferenceQueue<Book> testSofeRef() {
		final ReferenceQueue<Book> referenceQueue = new ReferenceQueue<Book>();
		WeakReference<Book> softReference = new WeakReference<>(new Book("深入理解虚拟机"), referenceQueue);
		Book book = softReference.get();
		System.out.println(book);
		
		return referenceQueue;
		
	}
}