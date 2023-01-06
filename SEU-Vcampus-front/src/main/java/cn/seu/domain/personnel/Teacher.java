package cn.seu.domain.personnel;

/**
 * 这是一个老师类
 * @author 顾深远
 * @version 1.0
 */

import java.io.*;

public class Teacher extends Person implements Serializable {

	/**
	 *
	 * @param account_number 账号
	 * @param nickname       昵称
	 * @param password       密码
	 * @param name           姓名
	 * @param jurisdiction   权限
	 * @param age            年龄
	 * @param gender         性别
	 * @param college        学院
	 * @param money          余额
	 */
	public Teacher(String account_number, String nickname, String password, String name, int jurisdiction, int age,
			int gender, String college, double money) {
		this.account_number = account_number;
		this.setpassword(password);
		this.nickname = nickname;
		this.setjurisdiction(4);
		this.college = college;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.money = money;
	}

	private int age;
	private int gender;
	private String college;// 学院
	private String name;
	private String nickname;
	private double money;

	public void setnickname(String nickname) {
		this.nickname = nickname;
	}

	public void setcollege(String college) {
		this.college = college;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String toString() {
		return account_number + "\n" + nickname + "\n" + getpassword() + "\n" + name + "\n"
				+ Integer.toString(getJurisdiction()) + "\n" + Integer.toString(age) + "\n" + Integer.toString(gender)
				+ "\n" + "null\n" + "null\n" + college + "\n" + Double.toString(money) + "\n" + "null\n" + "null";
	}

}
