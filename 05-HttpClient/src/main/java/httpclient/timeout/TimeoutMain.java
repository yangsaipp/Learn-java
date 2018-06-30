package httpclient.timeout;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeoutMain {
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		MyFuture future = new MyFuture(lock);
		try {
             String str = future.get(2, TimeUnit.MILLISECONDS);
			 System.out.println(str);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			System.out.println("ExecutionException");
        } catch (final TimeoutException ex) {
        	ex.printStackTrace();
        	System.out.println("Timeout waiting for connection from pool");
        }
	}
}
