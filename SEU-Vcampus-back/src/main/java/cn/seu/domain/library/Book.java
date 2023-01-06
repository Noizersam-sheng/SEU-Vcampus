package cn.seu.domain.library;

public class Book {
    public String bookname; 
    public String book_nu;
    public int whole_nu;
    public int book_number;
    public int borrowing_times;
    public Book(String book_nu,String bookname,int whole_nu,int book_number,int borrowing_times){
        this.bookname=bookname;
        this.book_nu=book_nu;
        this.book_number=book_number;
        this.whole_nu=whole_nu;
        this.borrowing_times=borrowing_times;
    }

    public String getBookname() {
        return bookname;
    }

    public String getBook_nu() {
        return book_nu;
    }

    public String toString(){
        return book_nu+"\n"+bookname+"\n"+Integer.toString(whole_nu)+"\n"+Integer.toString(book_number)+"\n"+Integer.toString(borrowing_times);
    }


}
