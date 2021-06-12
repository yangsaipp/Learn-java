package my.tool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.tool.jsch.JSchCommandHelper;
import my.tool.model.TSession;

public class NativeShellCommand implements ICommand {

	private static final Logger log = LoggerFactory.getLogger(NativeShellCommand.class);
	
	@Override
	public void exec(Object desc, TSession session) {
		log.info("执行原生shell脚本。desc={}, host={}", desc, session.getConfig().getHost());
		JSchCommandHelper.remoteExecute(session.getSession(), (String)desc);
	}

}
