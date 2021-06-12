package my.tool.model;

import my.tool.config.TaskConfig;

public class Task {

	private TaskConfig config;

	public TaskConfig getConfig() {
		return config;
	}

	public void setConfig(TaskConfig config) {
		this.config = config;
	}

	public Task(TaskConfig config) {
		super();
		this.config = config;
	}
}
