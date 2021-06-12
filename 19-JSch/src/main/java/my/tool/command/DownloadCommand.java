package my.tool.command;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.tool.jsch.JSchCommandHelper;
import my.tool.model.TSession;

/**
 * 上传文件
 * @author yangsai
 *
 */
public class DownloadCommand implements ICommand {
	private static final Logger log = LoggerFactory.getLogger(DownloadCommand.class);
	
	@Override
	public void exec(Object desc, TSession session) {
		@SuppressWarnings("unchecked")
		Map<String, String> mapArgs = (Map<String, String>) desc;
		log.info("执行下载命令。desc={}, host={}", desc, session.getConfig().getHost());
		JSchCommandHelper.scpFrom(session.getSession(), mapArgs.get("from"), mapArgs.get("to"));
		log.info("执行下载命令成功。desc={}, host={}", desc, session.getConfig().getHost());
	}

}
