package httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class RequestTools {

	public static void main(String[] args) {
		String str = processHttpRequest("http://www.baidu.com", "get", new HashMap<String, String>());
		System.out.println(str);
	}
	
    public static String processHttpRequest(String url, String requestMethod, Map<String, String> paramsMap) {
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        if ("post".equals(requestMethod)) {
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader("Content-Type", "application/json");
            for (Iterator<String> it = paramsMap.keySet().iterator(); it.hasNext();) {
                String key = it.next();
                String value = paramsMap.get(key);
                formparams.add(new BasicNameValuePair(key, value));
            }
            return doRequest(httppost, null, formparams);
        } else if ("get".equals(requestMethod)) {
            HttpGet httppost = new HttpGet(url);
            for (Iterator<String> it = paramsMap.keySet().iterator(); it.hasNext();) {
                String key = it.next();
                String value = paramsMap.get(key);
                formparams.add(new BasicNameValuePair(key, value));
            }
            return doRequest(null, httppost, formparams);
        }
        return "";
    }

    private static String doRequest(HttpPost httpPost, HttpGet httpGet, List<BasicNameValuePair> formparams) {

        try {
            CloseableHttpResponse response = null;
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams);
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(25000).setConnectTimeout(3000)
                    .setConnectionRequestTimeout(200).build();
            if (null != httpPost) {
                uefEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(uefEntity);
                httpPost.setConfig(requestConfig);
                response = HttpClientPool.getHttpClient().execute(httpPost);
            } else {
                httpGet.setConfig(requestConfig);
                response = HttpClientPool.getHttpClient().execute(httpGet);
            }
            HttpEntity entity = response.getEntity();
            String str = EntityUtils.toString(entity, "UTF-8");
            if (null == str || "".equals(str)) {
                return "";
            } else {
                return str;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }
}