package volatile_Test;

/**
 * 不加volatile线程VolatileTest不会运行完，加了就才会运行完
 * 
 * @author yangsai
 */
public class ThreadStopTest extends Thread {

	public volatile static MyObject flag = new MyObject();
	int i = 0;

	public void run() {
		// 线程持续运行需要依赖该状态标识
		while (!flag.flag) {
			i++;
		}
		System.out.println("结束线程...");
	}

	public static void main(String[] args) throws Exception {
		ThreadStopTest vt = new ThreadStopTest();
		vt.start();
		Thread.sleep(1000);
		flag.flag = true;
		System.out.println("stope" + vt.i);
	}

	static class MyObject {
		boolean flag = false;
	}
}