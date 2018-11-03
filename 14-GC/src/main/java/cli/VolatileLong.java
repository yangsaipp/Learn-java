package cli;

/**
 *  计算公式：java对象的大小=对象头+属性+补齐空间
 *  对象头：12B（开启指针压缩）
 *  属性：8B * 7 = 56B
 *  总共：12B+56B=68B，要对齐8字节，所以占72B。
 *  测试方式，在bin目录下运行命令：
 *  java -jar jol-cli-0.9-full.jar internals -cp . cli.VolatileLong
 */
public final class VolatileLong {
	public volatile long value = 0L;
	public long p1, p2, p3, p4, p5, p6; // comment out
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public long getP1() {
		return p1;
	}
	public void setP1(long p1) {
		this.p1 = p1;
	}
	public long getP2() {
		return p2;
	}
	public void setP2(long p2) {
		this.p2 = p2;
	}
	public long getP3() {
		return p3;
	}
	public void setP3(long p3) {
		this.p3 = p3;
	}
	public long getP4() {
		return p4;
	}
	public void setP4(long p4) {
		this.p4 = p4;
	}
	public long getP5() {
		return p5;
	}
	public void setP5(long p5) {
		this.p5 = p5;
	}
	public long getP6() {
		return p6;
	}
	public void setP6(long p6) {
		this.p6 = p6;
	}
}