package cn.seu.domain.courses;

public class Course {
	private String name;// 课程名
	private String courseID;// 课程号
	private String teacher;// 授课老师 用ID号表示确定唯一性
	private int date;// 周几
	private int start_t, end_t;// 开始时间和结束时间
	private int date_2;// 周几
	private int start_t2, end_t2;// 开始时间和结束时间
	private int status;// 0 必修；1 选修
	private int max_num;// 最大选课人数
	private int count = 0;// 已选该课程人数
	private String classroom;// 教室名称

	public Course(String name, String id, String teacher, int date, int start, int end, int date2, int start2,
			int end2, int status, int max_num, int count, String classroom) {
		this.count = count;
		this.courseID = id;
		this.date = date;
		this.start_t = start;
		this.end_t = end;
		this.date_2 = date2;
		this.start_t2 = start2;
		this.end_t2 = end2;
		this.max_num = max_num;
		this.name = name;
		this.status = status;
		this.teacher = teacher;
		this.classroom = classroom;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMax_num() {
		return max_num;
	}

	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getDate_2() {
		return date_2;
	}

	public void setDate_2(int date_2) {
		this.date_2 = date_2;
	}

	public int getStart_t2() {
		return start_t2;
	}

	public void setStart_t2(int start_t2) {
		this.start_t2 = start_t2;
	}

	public int getEnd_t2() {
		return end_t2;
	}

	public void setEnd_t2(int end_t2) {
		this.end_t2 = end_t2;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

}
