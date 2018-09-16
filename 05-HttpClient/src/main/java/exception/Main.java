package exception;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/**
 * 重现服务器上大量的CLOSE_WAIT状态	
 * https://blog.csdn.net/shootyou/article/details/6615051
 * @author yangsai
 *
 */
public class Main {
	
	public static void main(String[] args) {
		System.out.println(readNet("http://14.215.177.38/test.html"));
	}

	public static String readNet (String urlPath)
	{
		StringBuffer sb = new StringBuffer ();
		HttpClient client = null;
		InputStream in = null;
		InputStreamReader isr = null;
		try
		{
			client = HttpConnectionManager.getHttpClient();
			HttpGet get = new HttpGet();
            get.setURI(new URI(urlPath));
            HttpResponse response = client.execute(get);
            if (response.getStatusLine ().getStatusCode () != 200) {
                return null;
            }
            HttpEntity entity =response.getEntity();
            
            if( entity != null ){
            	in = entity.getContent();
            }
            return sb.toString ();
			
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return null;
		}
		finally
		{
			if (isr != null){
				try
				{
					isr.close ();
				}
				catch (IOException e)
				{
					e.printStackTrace ();
				}
			}
			if (in != null){
				try
				{
					in.close ();
				}
				catch (IOException e)
				{
					e.printStackTrace ();
				}
			}
		}
	}
}
