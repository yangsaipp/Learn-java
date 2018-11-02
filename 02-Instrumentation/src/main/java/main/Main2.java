package main;

import java.util.Properties;

public class Main2 {

	public static void main(String[] args) { 
		java.lang.management.MemoryUsage usage = java.lang.management.ManagementFactory.getMemoryMXBean()
		.getHeapMemoryUsage(); 
		System.out.println("Max: " + usage.getMax()); 
		System.out.println("Init: " + usage.getInit()); 
		System.out.println("Committed: " + usage.getCommitted()); 
		System.out.println("Used: " + usage.getUsed());
		System.out.println(System.getProperty("java.library.path"));
//		ClassEncryptLib.loadNativeLibrary();
	} 

	public static void load() {
		Properties props = System.getProperties();
		String baseUrl = "D:\\Code\\workspace\\Learn-java\\02-Instrumentation\\src\\main\\resources";
		Object os = props.get("os.name");
		if (os != null && os.toString().toLowerCase().indexOf("windows") != -1) {
			System.load(baseUrl + "\\libClassEncryptAgentlib.dll");
			System.out.println("System load ClassEncryptLib sucess on "+ os.toString() +"!");
		} else {
			System.load(baseUrl + "\\libClassEncrypt.dll");
			System.out.println("System load ClassEncryptLib sucess on "+ os.toString() +"!");
		}
	}
}
