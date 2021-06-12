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
public class UploadCommand implements ICommand {
	private static final Logger log = LoggerFactory.getLogger(UploadCommand.class);

	@Override
	public void exec(Object desc, TSession session) {
		@SuppressWarnings("unchecked")
		Map<String, String> mapArgs = (Map<String, String>) desc;
		log.info("执行上传命令。desc={}, host={}", desc, session.getConfig().getHost());
		JSchCommandHelper.scpTo(mapArgs.get("from"), session.getSession(), mapArgs.get("to"));
		log.info("执行上传命令成功。desc={}, host={}", desc, session.getConfig().getHost());
	}

}
