package OOM;

/**
 * 每个线程分配的栈空间越大，可创建的线程数就越少
 * window运行可能出假死。
 * 运行结果：Exception in thread"main"java.lang.OutOfMemoryError：unable to
create new native thread
 */
public class JavaVMStackOOM {
	private void dontStop() {
		while (true) {
		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					dontStop();
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}
}