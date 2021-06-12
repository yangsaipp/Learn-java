package my.tool.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import my.tool.config.SessionConfig;
import my.tool.jsch.JSchCommandHelper;

public class TSession {
	
	private SessionConfig config;
	
	private Session session;
	
	public TSession(SessionConfig config) {
		super();
		this.config = config;
	}

	public SessionConfig getConfig() {
		return config;
	}

	public void setConfig(SessionConfig config) {
		this.config = config;
	}

	public synchronized Session getSession() {
		if(session == null) {
			try {
				session = JSchCommandHelper.getSession(config);
			} catch (JSchException e) {
				throw new RuntimeException("获取JSch的session异常。config=" + ToStringBuilder.reflectionToString(config), e);
			}
		}
		return session;
	}
	
	public synchronized void close() {
		if(session != null && session.isConnected()) {
			session.disconnect();
		}
	}
}
