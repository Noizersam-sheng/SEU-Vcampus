package cn.seu.domain.personnel;

/**
 * 这是一个图书管理员类
 * 
 * @author 顾深远
 * @version 1.0
 */

public class Librarian extends Person {
	PassData pd = new PassData();

	public Librarian(String account_number, String password, int jurisdiction) {
		this.account_number = account_number;
		this.setpassword(password);
		this.setjurisdiction(2);
	}
}
