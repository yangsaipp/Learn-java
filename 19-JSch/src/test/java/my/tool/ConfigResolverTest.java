package my.tool;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import my.tool.config.SessionConfig;
import my.tool.config.TaskConfig;
import my.tool.core.ConfigResolver;
import my.tool.model.SessionResp;
import my.tool.model.Task;

public class ConfigResolverTest {

	private ConfigResolver configResolver = new ConfigResolver();
	
	@Test
	public void testParseSession() {
		SessionResp sessionResp = configResolver.parseSession("session.json");
		SessionConfig config = sessionResp.get("192.168.1.100").getConfig();
		assertEquals("192.168.1.100", config.getHost());
		assertEquals("root", config.getUser());
		assertEquals("12345678", config.getPassword());
		
	}

	@Test
	public void testParseAppConfig() {
		List<Task> tasks = configResolver.parseAppConfig(new String[] {"filePullConfig.json"});
		TaskConfig config = tasks.get(0).getConfig();
		assertEquals("192.168.1.100", config.getHost());
		Map<String, String> map = (Map<String, String>) config.getCommandDescribes().get(0);
		assertEquals("/root/jadp-all/log.disconf.log", map.get("from"));
		assertEquals("e:/jadp-all/log/disconf.log", map.get("to"));
		assertEquals("ls /root", config.getCommandDescribes().get(1));
		
	}

}
