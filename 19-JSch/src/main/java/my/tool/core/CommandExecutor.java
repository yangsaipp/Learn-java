package my.tool.core;

import java.util.Map;

import my.tool.command.CommandListing;
import my.tool.command.ICommand;
import my.tool.model.SessionResp;
import my.tool.model.TSession;

public class CommandExecutor {
	
	
	private SessionResp sessionResp;
	
	private CommandListing commandListing;

	public CommandExecutor(SessionResp sessionResp, CommandListing commandListing) {
		super();
		this.sessionResp = sessionResp;
		this.commandListing = commandListing;
	}

	public void execute(Object desc, String host) {
		TSession session  = sessionResp.get(host);
		if(desc instanceof String) {
			ICommand command = commandListing.getNativeShellCommand();
			command.exec(desc, session);
		}else {
			@SuppressWarnings("unchecked")
			String type = ((Map<String, String>)desc).get("type");
			ICommand command = commandListing.get(type);
			command.exec(desc, session);
		}
	}
	
	
}
