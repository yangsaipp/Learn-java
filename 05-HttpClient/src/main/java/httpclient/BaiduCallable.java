package httpclient;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class BaiduCallable implements Callable<String> {

		@Override
		public String call() throws Exception {
			String str = RequestTools.processHttpRequest("http://www.baidu.com", "get", new HashMap<String, String>());
			return str;
		}
		
	}