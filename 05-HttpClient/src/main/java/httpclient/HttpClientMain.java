package httpclient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class HttpClientMain {

	public static void main(String[] args) {
		FutureTask<String> task1 = new FutureTask<String>(new BaiduCallable());
		FutureTask<String> task2 = new FutureTask<String>(new BaiduCallable());
		FutureTask<String> task3 = new FutureTask<String>(new BaiduCallable());
		new Thread(task1).start();
		new Thread(task2).start();
		new Thread(task3).start();
		try {
            Thread.sleep(1000);// 可能做一些事情
            System.out.println(task1.get());
            System.out.println(task2.get());
            System.out.println(task3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
		
	}

	
	
	
}
