package my.tool.command;

import java.util.HashMap;
import java.util.Map;

public class CommandListing {

	private Map<String, ICommand> listing = new HashMap<>();

	public CommandListing() {
		super();
		listing.put("native", new NativeShellCommand());
		listing.put("upload", new UploadCommand());
		listing.put("download", new DownloadCommand());
	}
	
	public ICommand get(String type) {
		return listing.get(type);
	}
	
	public ICommand getNativeShellCommand() {
		return get("native");
	}

	public void add(String type, ICommand c) {
		listing.put(type, c);
	}
}
