package cn.seu.domain.library;

/**
 * 这是一个书籍类
 * @author 顾深远
 * @version 1.0
 */

public class Book {
	public String bookname;
	public String book_nu;
	public int whole_nu;
	public int book_number;
	public int borrowing_times;

	/**
	 * 构造函数
	 * @param book_nu         书号
	 * @param bookname        书名
	 * @param whole_nu        馆藏
	 * @param book_number     现有数量
	 * @param borrowing_times 借阅次数
	 */

	public Book(String book_nu, String bookname, int whole_nu, int book_number, int borrowing_times) {
		this.bookname = bookname;
		this.book_nu = book_nu;
		this.book_number = book_number;
		this.whole_nu = whole_nu;
		this.borrowing_times = borrowing_times;
	}

	public String getBookname() {
		return bookname;
	}

	public String getBook_nu() {
		return book_nu;
	}

	public String toString() {
		return book_nu + "\n" + bookname + "\n" + Integer.toString(whole_nu) + "\n" + Integer.toString(book_number)
				+ "\n" + Integer.toString(borrowing_times);
	}

}
