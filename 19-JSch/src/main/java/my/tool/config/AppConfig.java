package my.tool.config;

import java.util.List;

/**
 * 程序配置信息
 * @author yangsai
 *
 */
public class AppConfig {
	
	private List<TaskConfig>  tasks;

	public List<TaskConfig> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskConfig> tasks) {
		this.tasks = tasks;
	}
}
