package cn.seu.domain.library;

/**
 * 这是一个借书记录类
 * @author 顾深远
 * @version 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book_borrowing_record {
	private String account_number;
	private String studnet_na;
	private String book_nu;
	private String book_na;
	private Date date = new Date();

	/**
	 * @param account_number 账号
	 */
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public void setBook_na(String book_na) {
		this.book_na = book_na;
	}

	public void setBook_nu(String book_nu) {
		this.book_nu = book_nu;
	}

	public void setStudnet_na(String studnet_na) {
		this.studnet_na = studnet_na;
	}

	public void setDate(String gt) {
		long l = Long.parseLong(gt);
		this.date.setTime(l);
	}

	public Book_borrowing_record(String account_number, String book_nu, String book_na, Date date) {
		this.account_number = account_number;
		this.book_nu = book_nu;
		this.book_na = book_na;
		this.studnet_na = "";
		this.date = date;
	}

	public String toDate(Date date) {
		SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
		return date1.format(date);
	}

	public String tostring() {
		String gt = Long.toString(date.getTime());
		return account_number + "\n" + book_nu + "\n" + book_na + "\n" + toDate(date) + "\n" + gt;
	}

	public static void main(String[] args) {

	}
}
