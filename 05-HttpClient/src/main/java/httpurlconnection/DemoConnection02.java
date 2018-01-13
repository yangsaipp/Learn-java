package httpurlconnection;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DemoConnection02 {  
  
    /** 
     * @param args 
     * @throws IOException  
     */  
    public static void main(String[] args) throws IOException {  
    	
    	System.setProperty("http.proxyHost", "127.0.0.1");  
    	System.setProperty("http.proxyPort", "8888");
    	
        String path = "http://10.10.31.166:8080/routes";  
        //1, 得到URL对象  
        URL url = new URL(path);  
          
        //2, 打开连接  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
          
        //3, 设置提交方式  
        conn.setRequestMethod("GET");  
          
        //4, 获取响应信息  
        if(conn.getResponseCode() == 200)  
        {  
            InputStream is = conn.getInputStream();  
              
            byte[] buffer = new byte[1024];  
              
            int len = 0;  
              
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
              
            while ((len = is.read(buffer))!=-1) {  
                  
                baos.write(buffer, 0, len);  
            }  
              
            FileOutputStream fos = new FileOutputStream("d:/test.txt");  
              
            fos.write(baos.toByteArray());  
              
            fos.close();  
              
            System.out.println("图片下载成功!!!");  
        }  
    }
}  