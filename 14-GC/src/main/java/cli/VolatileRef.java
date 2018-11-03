package cli;

/**
 * 对象头：12B（开启指针压缩）
 * 属性：4B * 4 = 16B
 * 总共：12B+16B=28B，要对齐8字节，所以占32B。
 * 测试方式，在bin目录下运行命令：
 * java -jar jol-cli-0.9-full.jar internals -cp . cli.VolatileRef
 */
public final class VolatileRef {
	private A a1 = new A();
	private A a2 = new A();
	private A a3 = new A();
	private A a4;
}