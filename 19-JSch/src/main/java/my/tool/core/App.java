package my.tool.core;

import java.util.List;

import my.tool.command.CommandListing;
import my.tool.command.ICommand;
import my.tool.model.SessionResp;
import my.tool.model.Task;

public class App {

	private ConfigResolver configResolver = new ConfigResolver();
	
	private TashRunner tashRunner;
	
	private SessionResp sessionResp = null;
	
	private CommandListing commandListing = new CommandListing();
	
	private List<Task> tasks = null;
	
	public App(String sessionConfigPath, String[] appConfigPath) {
		initConfig(sessionConfigPath, appConfigPath);
		tashRunner = new TashRunner(sessionResp, commandListing);
	}
	
	public void initConfig(String sessionConfigPath, String[] appConfigPath) {
		sessionResp = configResolver.parseSession(sessionConfigPath);
		tasks = configResolver.parseAppConfig(appConfigPath);
	}

	public void run() {
		tashRunner.run(tasks);
	}

	public void stop() {
		sessionResp.close();
	}
	
	public void loadCommand(String type, ICommand c) {
		commandListing.add(type, c);
	}

}
