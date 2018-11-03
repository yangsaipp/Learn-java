package cli;

/**
 * 对象头：12B（开启指针压缩）
 * 属性：4B * 4 = 16B
 * 总共：12B+16B=28B，要对齐8字节，所以占32B。
 * 测试方式，在bin目录下运行命令：
 * java -jar jol-cli-0.9-full.jar internals -cp . cli.VolatileString
 */
public final class VolatileString {
	private String str1 = "中国";
	private String str2 = "中国";
	private String str3 = "中国";
	private String str4 = "中国";
}