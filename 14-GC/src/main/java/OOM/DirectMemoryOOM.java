package OOM;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * 测试直接内存溢出
 * VM Args：-Xmx20M -XX:MaxDirectMemorySize=10M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError
 * 
 * @author zzm
 */
public class DirectMemoryOOM {
	private final static int _1MB = 1024 * 1024;

	public static void main(String[] args) throws Exception {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		while (true) {
			unsafe.allocateMemory(_1MB);
		}
	}
}