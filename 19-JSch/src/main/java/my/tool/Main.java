package my.tool;

import com.jcraft.jsch.JSchException;

import my.tool.core.App;

public class Main {

	public static void main(String[] args) throws JSchException {
		App app = new App("session.json",new String[]{"demo.json"});
		try {
			app.run();
		}finally {
			app.stop();
		}
	}
}
