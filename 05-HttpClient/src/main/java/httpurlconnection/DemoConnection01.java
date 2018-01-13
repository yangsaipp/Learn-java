/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package httpurlconnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 杨赛
 * @since jdk1.7
 * @version 2018年1月13日 杨赛
 */
public class DemoConnection01 {

	public static void main(String[] args) throws Exception {

		String path = "http://10.10.32.131:8012/web/userTaskService/backOverByReturn";
//		String path = "http://10.10.31.166:8080/routes";

		// 1, 得到URL对象
		URL url = new URL(path);

		// 2, 打开连接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 3, 设置提交类型
		conn.setRequestMethod("POST");
		conn.setRequestProperty("content-type", "application/json");
		conn.setRequestProperty("Charset", "UTF-8");
		// 设置文件字符集:
		// 4, 设置允许写出数据,默认是不允许 false
		conn.setDoOutput(true);
		conn.setDoInput(true);// 当前的连接可以从服务器读取内容, 默认是true
		// 5, 获取向服务器写出数据的流
		OutputStream os = conn.getOutputStream();
		InputStream fis = DemoConnection01.class.getResourceAsStream("/requestBody.json");
		// 参数是键值队 , 不以"?"开始

		byte bt[] = new byte[1024];
		while (fis.read(bt) != -1) {
			os.write(bt);
		}
//		os.write("{\"id\":\"1\",\"serviceName\":\"用户服务\"}".getBytes());
		os.flush();

		// 6, 获取响应的数据
		if (conn.getResponseCode() == 200) {
			// 得到服务器写回的响应数据
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str = br.readLine();
			System.out.println("响应内容为:  " + str);

		} else {
			System.out.println("code:" + conn.getResponseCode());
			// 得到服务器写回的响应数据
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str = br.readLine();
			System.out.println("响应内容为:  " + str);
		}
	}
}
