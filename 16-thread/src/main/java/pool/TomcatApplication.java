package pool;

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
 * 2. flag为Map类型不存在可见性问题
 * @author  杨赛
 * @since   jdk1.7
 * @version 2019年1月21日 杨赛
 */
@Controller
@SpringBootApplication
public class TomcatApplication {
	private final static Logger logger = LoggerFactory.getLogger(TomcatApplication.class);
	
	public static Boolean flag = new Boolean(true);
//	public static Map<String,Boolean> flag = new HashMap<>();
//	static {
//		flag.put("flag", true);
//	}
	
	private static int count = 0;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
		count ++;
		if(count == 1) {
			logger.info("flag之前：{}", flag.toString());
//			flag.put("flag", false);
			flag = new Boolean(false);
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
		while (flag) {
//		while (flag.get("flag")) {
            i++;
        }
		logger.info("=========test2 end ==== {}", i);
		return "Hello World! test2";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TomcatApplication.class, args);
	}
}
