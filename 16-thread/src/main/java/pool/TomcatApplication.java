package pool;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 不带volatile修饰符
 * 1. flag为Boolean类型存在可见性问题
 * 2. flag为Map<String, Boolean>类型不存在可见性问题
 * 3. flag为String类型存在可见性问题
 * 4. flag为对象（修改flag的成员变量）存在可见性问题
 * 5. flag为Map<String, MyObject>类型不存在可见性问题
 * @author  杨赛
 * @since   jdk1.7
 * @version 2019年1月21日 杨赛
 */
@Controller
@SpringBootApplication
public class TomcatApplication {
	private final static Logger logger = LoggerFactory.getLogger(TomcatApplication.class);
	
	public static Map<String,MyObject> flag = new HashMap<>();
//	public static MyObject flag = new MyObject();
//	public static String flag = "true";
//	public static Boolean flag = new Boolean(true);
//	public static Map<String,Boolean> flag = new HashMap<>();
	static {
//		flag.put("flag", true);
		flag.put("flag", new MyObject());
	}
	
	private static int count = 0;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
		count ++;
		if(count == 1) {
			logger.info("flag之前：{}", flag.toString());
			flag.get("flag").setFlag("false");
//			flag.setFlag("false");
//			flag.put("flag", false);
//			flag = "false";
//			flag = new Boolean(false);
			logger.info("改变flag=false。");
			logger.info("flag之后：{}", flag.toString());
		}
        return "Hello World!";
    }
	
	@RequestMapping("/test2")
	@ResponseBody
	String test2() {
		logger.info("=========test2 start ===");
		int i = 0;
		while (flag.get("flag").getFlag().equals("true")) {
//		while (flag) {
//		while (flag.get("flag")) {
            i++;
        }
		logger.info("=========test2 end ==== {}", i);
		return "Hello World! test2";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TomcatApplication.class, args);
	}
	
	static class MyObject {
		public String flag = "true";

		/**
		 * @return 获取 flag属性值
		 */
		public String getFlag() {
			return flag;
		}

		/**
		 * @param flag 设置 flag 属性值为参数值 flag
		 */
		public void setFlag(String flag) {
			this.flag = flag;
		}
		
    }
}
