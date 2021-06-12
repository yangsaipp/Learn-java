package my.tool.core;

import java.util.List;

import my.tool.command.CommandListing;
import my.tool.model.SessionResp;
import my.tool.model.Task;

public class TashRunner {
	
	private CommandExecutor commandExecutor;
	
	public TashRunner(SessionResp sessionResp, CommandListing commandListing) {
		super();
		this.commandExecutor = new CommandExecutor(sessionResp, commandListing);
	}

	public void run(List<Task> tasks) {
		for(Task task : tasks) {
			run(task);
		}
	}

	private void run(Task task) {
		for(Object desc : task.getConfig().getCommandDescribes()) {
			commandExecutor.execute(desc, task.getConfig().getHost());
		}
	}
}
