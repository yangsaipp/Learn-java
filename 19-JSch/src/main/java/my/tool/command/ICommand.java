package my.tool.command;

import my.tool.model.TSession;

public interface ICommand {

	void exec(Object desc, TSession session);
}
