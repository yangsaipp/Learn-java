package my.tool.model;

import java.util.HashMap;
import java.util.Map;


public class SessionResp {
	
	private Map<String, TSession> resp = new HashMap<>();
	

	public void close() {
		resp.forEach((host, session) -> {
			session.close();
		});
	}
	
	/**
	 * 
	 * @param host
	 * @return session
	 */
	public TSession get(String host) {
		return resp.get(host);
	}

	public void add(TSession session) {
		resp.put(session.getConfig().getHost(), session);
	}
}
