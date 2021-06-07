package my.tool;

import static my.tool.ShellCommand.remoteExecute;
import static my.tool.ShellCommand.scpFrom;
import static my.tool.ShellCommand.scpTo;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Main {

	private static final int CONNECT_TIMEOUT = 5000;
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws JSchException {
		Remote remote = new Remote();
	    remote.setHost("192.168.124.20");
	    remote.setPassword("123456");
	    Session session = getSession(remote);
	    session.connect(CONNECT_TIMEOUT);
	    if (session.isConnected()) {
	    	log.info("Host({}) connected." , remote.getHost());
	    }
	    
	    // 执行shell命令
	    remoteExecute(session, "pwd");
	    remoteExecute(session, "mkdir /root/jsch-demo");
	    remoteExecute(session, "ls /root/jsch-demo");
	    remoteExecute(session, "touch /root/jsch-demo/test1; touch /root/jsch-demo/test2");
	    remoteExecute(session, "echo 'It a test file.' > /root/jsch-demo/test-file");
	    remoteExecute(session, "ls -all /root/jsch-demo");
	    remoteExecute(session, "ls -all /root/jsch-demo | grep test");
	    remoteExecute(session, "cat /root/jsch-demo/test-file");
	    
	    // 上传、下载文件
	    remoteExecute(session, "ls /root/jsch-demo/");
	    scpTo("test.txt", session, "/root/jsch-demo/");
	    remoteExecute(session, "ls /root/jsch-demo/");
	    remoteExecute(session, "echo ' append text.' >> /root/jsch-demo/test.txt");
	    scpFrom(session, "/root/jsch-demo/test.txt", "file-from-remote.txt");
	    
	    
	    session.disconnect();

	}
	
	public static Session getSession(Remote remote) throws JSchException {
	    JSch jSch = new JSch();
	    if (Files.exists(Paths.get(remote.getIdentity()))) {
	        jSch.addIdentity(remote.getIdentity(), remote.getPassphrase());
	    }
	    Session session = jSch.getSession(remote.getUser(), remote.getHost(),remote.getPort());
	    session.setPassword(remote.getPassword());
	    session.setConfig("StrictHostKeyChecking", "no");
	    return session;
	}
	
	
}
