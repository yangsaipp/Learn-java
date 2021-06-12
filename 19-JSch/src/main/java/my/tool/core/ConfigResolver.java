package my.tool.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.tool.config.AppConfig;
import my.tool.config.SessionConfig;
import my.tool.config.TaskConfig;
import my.tool.model.SessionResp;
import my.tool.model.TSession;
import my.tool.model.Task;

public class ConfigResolver {
	
	private ObjectMapper objMapper = new ObjectMapper();

	public SessionResp parseSession(String sessionConfigPath) {
		InputStream is = null;
		try {
			is = ConfigResolver.class.getClassLoader().getResourceAsStream(sessionConfigPath);
			SessionResp resp = new SessionResp();
			List<SessionConfig> list = objMapper.readValue(is, new TypeReference<List<SessionConfig>>() {});
			for(SessionConfig config : list) {
				resp.add(new TSession(config));
			}
			return resp;
		} catch (IOException e) {
//			throw new RuntimeException(e);
			throw new RuntimeException("解析配置文件错误。sessionConfigPath=" + sessionConfigPath,e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public List<Task> parseAppConfig(String[] appConfigPaths) {
		List<Task> tasks = new ArrayList<>();
		for(String path : appConfigPaths) {
			tasks.addAll(parseAppConfig(path));
		}
		return tasks;
	}

	private List<? extends Task> parseAppConfig(String appConfigPath) {
		InputStream is = null;
		try {
			is = ConfigResolver.class.getClassLoader().getResourceAsStream(appConfigPath);
			AppConfig appConfig = objMapper.readValue(is, AppConfig.class);
			List<Task> tasks = new ArrayList<>(appConfig.getTasks().size());
			for(TaskConfig objTaskConfig : appConfig.getTasks()) {
				tasks.add(new Task(objTaskConfig));
			}
			return tasks;
		} catch (IOException e) {
			throw new RuntimeException("解析配置文件错误。appConfigPath=" + appConfigPath,e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
