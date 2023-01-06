package cn.seu.domain.personnel;

/**
 * 这是一个学生类
 * @author 顾深远
 * @version 1.0
 */

import java.util.ArrayList;
import java.io.*;

public class Student extends Person implements Serializable {

	/**
	 *
	 * @param account_number 账号
	 * @param nickname       昵称
	 * @param password       密码
	 * @param name           姓名
	 * @param jurisdiction   权限
	 * @param age            年龄
	 * @param gender         性别
	 * @param year           年
	 * @param month          月
	 * @param day            日
	 * @param major          专业
	 * @param college        学院
	 * @param money          金额
	 * @param picture        图片
	 * @param web_money      网费
	 */
	public Student(String account_number, String nickname, String password, String name, int jurisdiction, int age,
			int gender, int year, int month, int day, String major, String college, double money, String picture,
			double web_money) {
		this.account_number = account_number;
		this.setpassword(password);
		this.setjurisdiction(5);
		this.name = name;
		this.age = age;
		this.year = year;
		this.month = month;
		this.day = day;
		this.nickname = nickname;
		this.major = major;
		this.college = college;
		this.money = money;
		this.gender = gender;
		this.picture = picture;
		this.web_money = web_money;
	}

	private int gender;// 性别：女2男1
	private String name;// 姓名
	private int age;// 年龄
	private int year, month, day;// 日期

	public String toDate(int year, int month, int day) {
		String y, m, d;
		y = Integer.toString(year);
		m = Integer.toString(month);
		d = Integer.toString(day);
		return y + "-" + m + "-" + d;
	}

	private String picture;// 图片
	public String nickname;// 昵称
	private String major;// 专业
	private String college;// 学院
	private double money;// 金额
	private double web_money;// 网费
	public ArrayList<ArrayList> books = new ArrayList<ArrayList>();

	public void setCollege(String college) {
		this.college = college;
	}

	public String getCollege() {
		return college;
	}

	public void setgender(int gender) {
		this.gender = gender;
	}

	public int getgender() {
		return gender;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getname() {
		return name;
	}

	public void setage(int age) {
		this.age = age;
	}

	public int getage() {
		return age;
	}

	public void settime(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public int getyear() {
		return year;
	}

	public int getmonth() {
		return month;
	}

	public int getday() {
		return day;
	}

	public void setnickname(String nickname) {
		this.nickname = nickname;
	}

	public String getnickname() {
		return nickname;
	}

	public void setmajor(String major) {
		this.major = major;
	}

	public String getmajor() {
		return major;
	}

	public double getmoney() {
		return money;
	}

	public String getPicture() {
		return picture;
	}

	public String toString() {
		return account_number + "\n" + nickname + "\n" + getpassword() + "\n" + name + "\n"
				+ Integer.toString(getJurisdiction()) + "\n" + Integer.toString(age) + "\n" + Integer.toString(gender)
				+ "\n" + toDate(year, month, day) + "\n" + major + "\n" + college + "\n" + Double.toString(getmoney())
				+ "\n" + picture + "\n" + Double.toString(web_money);
	}
}
