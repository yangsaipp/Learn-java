package httpclient;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * httpclient
 * @author chenshiyuan
 *
 */
public class HttpClientPool {
    private static PoolingHttpClientConnectionManager cm = null; 
    static{
         cm = new PoolingHttpClientConnectionManager();
         cm.setMaxTotal(1);
         cm.setDefaultMaxPerRoute(1);
    }
    public static CloseableHttpClient getHttpClient(){
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setConnectTimeout(2)
        		.setConnectionRequestTimeout(2)
        		.build();  
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig).build();
        return client;
    }
    
}