package ElementUI;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementHello {
	public static void main(String[] args) throws Exception {
		// 1. 设置chrome driver
		System.setProperty("webdriver.chrome.driver", "d:\\tool\\selenium-drivers\\chromedriver.exe");
		// 2. 初始化一个chrome driver
		WebDriver driver = new ChromeDriver();
		try {
			// 3. 打开网站
			driver.get("http://localhost:8010/");
			
			String titile = driver.getTitle();
			System.out.println("title is => " + titile);

			// 4. 点击开始按钮
			System.out.println("开始按钮");
			driver.findElement(By.xpath("//div[@id='app']/div/button")).click();
			// 5. 等待消息框出来
			new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds()).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='alert']")));
			// 6. 获取消息标题
			String title = driver.findElement(By.xpath("//div[@role='alert']/div/h2")).getText();
			System.out.println("===" + title);
			
			// 7. 点击radio，选择女
			driver.findElement(By.xpath("//div[@id='app']/div/label[2]")).click();
			// 8. 输出radio
			String value = driver.findElement(By.xpath("//div[@id='app']/div/label[@aria-checked='true']/span[@class='el-radio__label']")).getText();
			System.out.println("==== " + value);
			Thread.sleep(10000);
		} finally {
			// 5. 退出
			driver.quit();
		}
	}
}
