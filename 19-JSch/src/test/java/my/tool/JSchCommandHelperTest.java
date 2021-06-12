
package my.tool;

import static my.tool.jsch.JSchCommandHelper.getSession;
import static my.tool.jsch.JSchCommandHelper.scpFrom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import my.tool.config.SessionConfig;

public class JSchCommandHelperTest {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws JSchException {
		SessionConfig remote = new SessionConfig();
//	    remote.setHost("192.168.124.20");
//	    remote.setPassword("123456");
	    remote.setHost("192.168.1.100");
	    remote.setUser("root");
	    remote.setPassword("12345678");
	    Session session = getSession(remote);
	    
	    
	    // 执行shell命令
//	    remoteExecute(session, "pwd");
//	    remoteExecute(session, "mkdir /root/jsch-demo");
//	    remoteExecute(session, "ls /root/jsch-demo");
//	    remoteExecute(session, "touch /root/jsch-demo/test1; touch /root/jsch-demo/test2");
//	    remoteExecute(session, "echo 'It a test file.' > /root/jsch-demo/test-file");
//	    remoteExecute(session, "ls -all /root/jsch-demo");
//	    remoteExecute(session, "ls -all /root/jsch-demo | grep test");
//	    remoteExecute(session, "cat /root/jsch-demo/test-file");
//	    
//	    // 上传、下载文件
//	    remoteExecute(session, "ls /root/jsch-demo/");
//	    scpTo("test.txt", session, "/root/jsch-demo/");
//	    remoteExecute(session, "ls /root/jsch-demo/");
//	    remoteExecute(session, "echo ' append text.' >> /root/jsch-demo/test.txt");
//	    scpFrom(session, "/root/jsch-demo/test.txt", "file-from-remote.txt");
	    scpFrom(session, "/root/jadp-all/log/disconf.log", "e:/shaoting/");
	    
	    
	    
	    session.disconnect();

	}
}
