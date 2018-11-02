package com.comtop.encrypt;

import java.util.Properties;

public class ClassEncryptLib {

	public static void loadNativeLibrary() {
		Properties props = System.getProperties();
		Object os = props.get("os.name");
		if (os != null && os.toString().toLowerCase().indexOf("windows") != -1) {
			System.loadLibrary("libClassEncrypt");
			System.out.println("System load ClassEncryptLib sucess on "+ os.toString() +"!");
		} else {
			System.loadLibrary("ClassEncrypt");
			System.out.println("System load ClassEncryptLib sucess on "+ os.toString() +"!");
		}
	}

	/**
	 * 本地加密接口
	 * 
	 * @param b
	 *            待加密的byte数组
	 * @return 已加密的byte数组
	 */
	public native static byte[] encrypt(byte[] b);

	/**
	 * 本地解密接口
	 * 
	 * @param b
	 *            待解密的byte数组
	 * @return 已解密的byte数组
	 */
	public native static byte[] decrypt(byte[] b);

}
