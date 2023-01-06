package cn.seu.domain.store;

public class Sales {

	private Commodity c;
	private int num;

	public Sales(Commodity c, int num) {
		this.c = c;
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public Commodity getCommodity() {
		return c;
	}
}
