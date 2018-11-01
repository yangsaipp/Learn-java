package OOM;

import java.util.ArrayList;
import java.util.List;

public class StaticOOM {
	
	public static List<Object> staticObj = new ArrayList<Object>();
	public static final int M_1 = 1024 * 1024;
	
	/**
	 * 测试用例：对象优先在eden分配
	 * VM参数：-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError
	 * 说明：heap分配20M，其中年轻代大小10M，年轻代里eden:from:to为8:1:1
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// 分配2M
		staticObj.add(new byte[2 * M_1]);
		staticObj.add(new byte[4 * M_1]);
		staticObj.add(new byte[8 * M_1]);
		staticObj.add(new byte[10 * M_1]);
	}
}
