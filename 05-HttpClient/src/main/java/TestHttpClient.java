

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {

  // 创建CookieStore实例
  static CookieStore cookieStore = null;
  static HttpClientContext context = null;
  String loginUrl = "http://10.10.31.166:9004/api/currentUser";
  String testUrl = "http://10.10.14.144:7500/api/jadp_test/auth/api/currentUser";
  String loginErrorUrl = "http://127.0.0.1:8080/CwlProClient/login/login.jsp";

  @Test
  public void testLogin() throws Exception {
    System.out.println("----testLogin");

    // // 创建HttpClientBuilder
    // HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    // // HttpClient
    // CloseableHttpClient client = httpClientBuilder.build();
    // 直接创建client
    
    cookieStore = new BasicCookieStore();
    
    BasicClientCookie cookie = new BasicClientCookie("access-token",
            "212.123");
    cookie.setPath("/");
    cookie.setSecure(false);
        cookieStore.addCookie(cookie);
    CloseableHttpClient client = HttpClients.custom()
            .setDefaultCookieStore(cookieStore).build();

    HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
    RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
    
    HttpGet httpPost = new HttpGet(testUrl);
    httpPost.setHeader(new BasicHeader("access-token", "eyJhbGciOiJIUzUxMiJ9.eyJhY2NvdW50IjoiU3VwZXJBZG1pbiIsInVzZXJJZCI6IlN1cGVyQWRtaW4iLCJlbXBsb3llZUlkIjoiU3VwZXJBZG1pbiIsImVtcGxveWVlTmFtZSI6Iui2hee6p-euoeeQhuWRmCIsIm9yZ0lkIjoiMCIsIm9yZ0NvZGUiOiIwMyIsIm9yZ05hbWUiOiLlub_kuJznlLXnvZHlhazlj7giLCJzdWIiOiLotoXnuqfnrqHnkIblkZgiLCJpYXQiOjE1MDI3NjE3MzUsImV4cCI6MTUwMjc2NDI3OCwicmVmcmVzaEludGVydmFsIjozMCwianRpIjoiMTZlYTg1NDgtMWFiNy00YWM4LTllYjItZTJjOWNkZTU0YjZmIiwicmVmcmVzaERhdGUiOjE1MDI3NjI0Nzg0MzJ9.TBLWYnQW16Mi4MUcGOqlRqOCAE2JkQn6zX3pDMTUsuWv5PKQ871EG2MAMgYv-spk0K9-NRO36_NOILpellPJhg"));
    httpPost.setConfig(config);
//    UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
//        getParam(parameterMap), "UTF-8");
//    httpPost.setEntity(postEntity);
    System.out.println("request line:" + httpPost.getRequestLine());
    
    
    
    try {
      // 执行post请求
      HttpResponse httpResponse = client.execute(httpPost);
//      String location = httpResponse.getFirstHeader("Location")
//          .getValue();
//      if (location != null && location.startsWith(loginErrorUrl)) {
//        System.out.println("----loginError");
//      }
      printResponse(httpResponse);

      // 执行get请求
      System.out.println("----the same client");
      HttpGet httpGet = new HttpGet(testUrl);
      System.out.println("request line:" + httpGet.getRequestLine());
      HttpResponse httpResponse1 = client.execute(httpGet);
      printResponse(httpResponse1);


      // cookie store
      setCookieStore(httpResponse);
      // context
      setContext();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭流并释放资源
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testContext() throws Exception {
    System.out.println("----testContext");
    // 使用context方式
    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(testUrl);
    System.out.println("request line:" + httpGet.getRequestLine());
    try {
      // 执行get请求
      HttpResponse httpResponse = client.execute(httpGet, context);
      System.out.println("context cookies:"
          + context.getCookieStore().getCookies());
      printResponse(httpResponse);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭流并释放资源
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testCookieStore() throws Exception {
    System.out.println("----testCookieStore");
    // 使用cookieStore方式
    CloseableHttpClient client = HttpClients.custom()
        .setDefaultCookieStore(cookieStore).build();
    HttpGet httpGet = new HttpGet(testUrl);
    System.out.println("request line:" + httpGet.getRequestLine());
    try {
      // 执行get请求
      HttpResponse httpResponse = client.execute(httpGet);
      System.out.println("cookie store:" + cookieStore.getCookies());
      printResponse(httpResponse);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭流并释放资源
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void printResponse(HttpResponse httpResponse)
      throws ParseException, IOException {
    // 获取响应消息实体
    HttpEntity entity = httpResponse.getEntity();
    // 响应状态
    System.out.println("status:" + httpResponse.getStatusLine());
    System.out.println("headers:");
    HeaderIterator iterator = httpResponse.headerIterator();
    while (iterator.hasNext()) {
      System.out.println("\t" + iterator.next());
    }
    // 判断响应实体是否为空
    if (entity != null) {
      String responseString = EntityUtils.toString(entity);
      System.out.println("response length:" + responseString.length());
      System.out.println("response content:"
          + responseString.replace("\r\n", ""));
    }
  }

  public static void setContext() {
    System.out.println("----setContext");
    context = HttpClientContext.create();
    Registry<CookieSpecProvider> registry = RegistryBuilder
        .<CookieSpecProvider> create()
        .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
        .register(CookieSpecs.BROWSER_COMPATIBILITY,
            new BrowserCompatSpecFactory()).build();
    context.setCookieSpecRegistry(registry);
    context.setCookieStore(cookieStore);
  }

  public static void setCookieStore(HttpResponse httpResponse) {
    System.out.println("----setCookieStore");
    cookieStore = new BasicCookieStore();
    // JSESSIONID
    String setCookie = httpResponse.getFirstHeader("Set-Cookie")
        .getValue();
    String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
        setCookie.indexOf(";"));
    System.out.println("JSESSIONID:" + JSESSIONID);
    // 新建一个Cookie
    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
        JSESSIONID);
    cookie.setVersion(0);
    cookie.setDomain("127.0.0.1");
    cookie.setPath("/CwlProClient");
    // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
    // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
    // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
    // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
    cookieStore.addCookie(cookie);
  }

  public static List<NameValuePair> getParam(Map parameterMap) {
    List<NameValuePair> param = new ArrayList<NameValuePair>();
    Iterator it = parameterMap.entrySet().iterator();
    while (it.hasNext()) {
      Entry parmEntry = (Entry) it.next();
      param.add(new BasicNameValuePair((String) parmEntry.getKey(),
          (String) parmEntry.getValue()));
    }
    return param;
  }
}