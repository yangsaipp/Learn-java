package baiduai;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;

public class Sample {
    //设置APPID/AK/SK
    public static String APP_ID = null;
    public static String API_KEY = null;
    public static String SECRET_KEY = null;
    
  //方法一
    static {
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream("/code.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        APP_ID = (String) properties.get("APP_ID");
        API_KEY = (String) properties.get("API_KEY");
        SECRET_KEY = (String) properties.get("SECRET_KEY");
    }
    
    public static void main(String[] args) throws IOException {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "log4j.properties");

//        asr(client, "20191224_154703.m4a");
//        asr(client, "20191224_154957.m4a");
//        asr(client, "20191224_155102.m4a");
//        asr(client, "20191224_155143.m4a");
//        asr(client, "20191224_155224.m4a");
        asr(client, "16k.wav");
//        synthesis(client);
        
    }
    
    public static void asr(AipSpeech client, String fileName) throws IOException
    {
        // 对本地语音文件进行识别
        String path = "D:\\Code\\workspace\\Learn-java\\baiduAI\\src\\main\\resources\\sample-files\\" + fileName;
        JSONObject asrRes = client.asr(path, "wav", 16000, null);
        System.out.println(asrRes);
        System.out.println("==================" + fileName);
        // 对语音二进制数据进行识别
        byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        JSONObject asrRes2 = client.asr(data, "wav", 16000, null);
        System.out.println(asrRes2);

    }
    
    public static void synthesis(AipSpeech client) {
    	
    	// 调用接口
        TtsResponse res = client.synthesis("在【折叠面板01内】创建标准环形图", "zh", 1, null);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, "output.mp3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }
        
    	
    }
}