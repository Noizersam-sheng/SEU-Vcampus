package cn.seu.domain.personnel;

/**
 * 这是一个教务老师类
 * 
 * @author 顾深远
 * @version 1.0
 */

public class Academic_affairs_teacher extends Person {
	PassData pd = new PassData();

	public Academic_affairs_teacher(String account_number, String password, int jurisdiction) {
		this.account_number = account_number;
		this.setpassword(password);
		this.setjurisdiction(1);
	}

	public int r_t(String account_number, String nickname, String password, String name, int jurisdiction, int age,
			int gender, String college)// 注册老师
	{
		return pd.register_t(account_number, nickname, password, name, jurisdiction, age, gender, college);
	}

	public int r_s(String account_number, String nickname, String password, String name, int jurisdiction, int age,
			int gender, int year, int month, int day, String major, String college)// 注册学生
	{
		return pd.register_s(account_number, nickname, password, name, jurisdiction, age, gender, year, month, day,
				major, college);
	}

	public String c_s(String account_number)// 注销学生
	{
		return pd.cancel_s(account_number);
	}

}
