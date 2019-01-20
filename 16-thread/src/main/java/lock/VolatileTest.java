package lock;
/**
 * 不加volatile线程VolatileTest不会运行完，加了就才会运行完
 * @author yangsai
 */
public class VolatileTest extends Thread {
    
	public volatile static MyObject flag = new MyObject();
//    volatile boolean flag = false;
    int i = 0;
    
    public void run() {
        	while (!flag.flag) {
                i++;
            }
    	
    }
    public static void main(String[] args) throws Exception {
        VolatileTest vt = new VolatileTest();
        vt.start();
        Thread.sleep(1000);
        flag.flag = true;
        System.out.println("stope" + vt.i);
    }
    
    static class MyObject {
    	boolean flag = false;
    }
}