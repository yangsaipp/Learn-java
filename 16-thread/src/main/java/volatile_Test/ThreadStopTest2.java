package volatile_Test;
/**
 * 使用volatile修饰i可让线程及时结束。主要利用了volatile禁止指令重排序的特性。
 * @author yangsai
 */
public class ThreadStopTest2 extends Thread {
	// 不使用使用Volatile修饰
	public static MyObject flag = new MyObject();
	// 使用Volatile修饰
	volatile int i = 0;
    
    public void run() {
        	while (!flag.flag) {
                i++;
            }
        	System.out.println("结束工作线程...");
    }
    
    public static void main(String[] args) throws Exception {
        ThreadStopTest2 vt = new ThreadStopTest2();
        vt.start();
        Thread.sleep(1000);
        flag.flag = true;
        System.out.println("结束主线程" + vt.i);
    }
    
    static class MyObject {
    	boolean flag = false;
    }
}