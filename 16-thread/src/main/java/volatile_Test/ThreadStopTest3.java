package volatile_Test;
/**
 * 测试单例模式下修改单例是否能及时可见。
 * 使用volatile后才会停止工作线程，否则会一直循环
 * @author yangsai
 */
public class ThreadStopTest3 extends Thread {
	
	// 预先初始化单例对象
	private static SingletionObject obj = SingletionTest.getInstance();
	
	int i = 0;
    
	// 线程不会调出循环结束线程
    public void run() {
        	while (!SingletionTest.getInstance().getFlag()) {
                i++;
            }
        	System.out.println("工作线程结束..." + i);
    }
    
    public static void main(String[] args) throws Exception {
        ThreadStopTest3 vt = new ThreadStopTest3();
        vt.start();
        Thread.sleep(1000);
        SingletionTest.getInstance().setFlag(true);
        System.out.println("主线程结束... " + vt.i);
    }
    
}