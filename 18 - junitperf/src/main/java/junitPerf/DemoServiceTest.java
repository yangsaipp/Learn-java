package junitPerf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;

/**
 * https://testerhome.com/topics/15197
 * @author yangsai
 *
 */
public class DemoServiceTest {
	@Rule
	public JUnitPerfRule perfTestRule = new JUnitPerfRule();

	DemoPerfService demoPerfService;

	@Before
	public void setupService() {
		this.demoPerfService = new DemoPerfService();
	}
	
	@Test
    @JUnitPerfTest(threads = 50,durationMs = 1200,warmUpMs = 100,maxExecutionsPerSecond = 110)
    @JUnitPerfTestRequirement(percentiles = "90:7,95:7,98:7,99:8", executionsPerSec = 10_000, allowedErrorPercentage = 0.10f)
    public void getServiceId() {

        String result =demoPerfService.getServiceId("userid");
        System.out.println(result);
        Assert.assertNotNull(result);
    }

	/**
	 * hreads：测试使用的线程数	
		durationMs：测试持续时间	
		warmUpMs：测试热身时间，热身时间的测试数据不会计算进最后的测试结果
		maxExecutionsPerSecond：方法执行的上限，RateLimiter，控制TPS上限
	 */
	@Test
    @JUnitPerfTest(threads = 50,durationMs = 1200,warmUpMs = 100,maxExecutionsPerSecond = 110)
    public void getServiceId_withoutTestRequirement() {

        String result =demoPerfService.getServiceId("userid");
        System.out.println(result);
        Assert.assertNotNull(result);
    }
	
	@Test
	public void test() {
		int i = 10_000;
		System.out.println(i);
	}
}