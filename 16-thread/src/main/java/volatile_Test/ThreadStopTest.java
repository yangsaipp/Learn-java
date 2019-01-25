package volatile_Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 无Volatile、Boolean -> 无法停止
 * 无Volatile、String -> 无法停止
 * 无Volatile、MyObject -> 无法停止
 * 
 * 无Volatile、ArrayList、实时get -> 无法停止
 * 有Volatile、ArrayList、实时get -> 正常停止
 * 有Volatile、ArrayList、不实时get -> 无法停止
 * 
 * 无Volatile、HashMap、实时get -> 正常停止
 * 无Volatile、HashMap、不实时get -> 无法停止
 * 有Volatile、HashMap、不实时get -> 无法停止
 * @author yangsai
 */
public class ThreadStopTest extends Thread {

	public volatile static Map<String, MyObject> flag = new HashMap<>();
	
	static {
		flag.put("flag",new MyObject());
	}
	
	int i = 0;

	public void run() {
		// 线程持续运行需要依赖该状态标识
		MyObject obj = flag.get("flag");
		while (!obj.flag) {
			i++;
		}
		System.out.println("结束线程...");
	}

	public static void main(String[] args) throws Exception {
		ThreadStopTest vt = new ThreadStopTest();
		vt.start();
		Thread.sleep(1000);
		flag.get("flag").flag = true;
		System.out.println("结束主线程" + vt.i);
	}

	static class MyObject {
		boolean flag = false;
	}
}