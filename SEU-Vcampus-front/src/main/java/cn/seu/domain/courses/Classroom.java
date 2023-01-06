package cn.seu.domain.courses;

public class Classroom {

	private String name;// 名称
	private int start_t;// 开始时间
	private int end_t;// 结束时间
	private int date;// 周几

	public Classroom() {

	}

	public Classroom(String name, int date, int start, int end) {
		this.date = date;
		this.end_t = end;
		this.start_t = start;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStart_t() {
		return start_t;
	}

	public void setStart_t(int start_t) {
		this.start_t = start_t;
	}

	public int getEnd_t() {
		return end_t;
	}

	public void setEnd_t(int end_t) {
		this.end_t = end_t;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

}
