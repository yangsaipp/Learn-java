package cli;

/**
 * 对象头：12B（开启指针压缩）
 * 属性：4B * 4 = 16B
 * 总共：12B+16B=28B，要对齐8字节，所以占32B。
 * 测试方式，在bin目录下运行命令：
 * java -jar jol-cli-0.9-full.jar internals -cp . cli.VolatileArray
 */
public final class VolatileBase {
	private byte b = 'c';
	private boolean b2 = true;
	private char c = '中';
	private short s = 11;
	private int i = 11;
	private float f = 0.22F;
	private long l = 2L;
	private double d = 0.22d;
}