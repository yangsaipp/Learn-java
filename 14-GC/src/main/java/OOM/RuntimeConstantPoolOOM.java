package OOM;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试PermGen space区域内存溢出。测试原理：利用String.intern()方法往常量池增加方法直至使用完PermGen区域的内存
 * VM Args：-XX:PermSize=2M -XX:MaxPermSize=2M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError
 * 
 * @author zzm
 */
public class RuntimeConstantPoolOOM {
	public static void main(String[] args) {
		//使用List保持着常量池引用，避免Full GC回收常量池行为
		List<String> list = new ArrayList<String>();
		//10MB的PermSize在integer范围内足够产生OOM了
		int i = 0;
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}
}