package OOM;

/**
 * 用于测试JVM的StackOverflowError异常。
 * 当线程无法为方法分配栈空间时将抛出java.lang.StackOverflowError异常。
 * -Xss128k：设置每个线程的堆栈大小。JDK5.0以后每个线程堆 栈大小为1M，以前每个线程堆栈大小为256K
 */
public class JavaVMStackSOF {
	private int stackLength = 1;
	public void stackLeak(){
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length：" + oom.stackLength);
			throw e;
		}
	}
}