import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumHello {
	public static void main(String[] args) {
		// 1. 设置chrome driver
		System.setProperty("webdriver.chrome.driver", "d:\\tool\\selenium-drivers\\chromedriver.exe");
		// 2. 初始化一个chrome driver
		WebDriver driver = new ChromeDriver();
//	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
		try {
			// 3. 打开百度
			driver.get("http://www.baidu.com/");
			
			String titile = driver.getTitle();
			System.out.println("title is => " + titile);

			// 4. 搜索关键字“易百教程”
			driver.findElement(By.id("kw")).sendKeys("易百教程");
			// Click on the search button
			driver.findElement(By.id("su")).click();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			// 5. 退出
			driver.quit();
		}
	}
}
