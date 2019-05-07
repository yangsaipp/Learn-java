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
 * Boolean存在可见性问题，但是Map对象没有出现可见性问题。
 * @author yangsai
 *
 */
@Controller
@SpringBootApplication
public class DemoApplication {
	private final static Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
	public static Map<String,Boolean> flag = new HashMap<>();
	static {
		flag.put("flag", true);
	}
	private static int count = 0;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
		logger.info("============= {}",flag);
		count ++;
		if(count == 1) {
			flag.put("flag", false);
			logger.info("改变flag=false。");
		}
        return "Hello World!";
    }
	
	@RequestMapping("/test2")
	@ResponseBody
	String test2() {
		logger.info("=========test2 start===");
		int i = 0;
		while (flag.get("flag")) {
            i++;
        }
		logger.info("=========test2 ==== {}",i);
		return "Hello World! test2";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
