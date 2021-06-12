package my.tool.config;

import java.util.List;

public class TaskConfig {
	
	private String host;
	
	/** 命令描述信息 */
	private List<Object> commandDescribes;

	
	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public List<Object> getCommandDescribes() {
		return commandDescribes;
	}


	public void setCommandDescribes(List<Object> commandDescribes) {
		this.commandDescribes = commandDescribes;
	}


	
}
