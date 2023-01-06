package cn.seu.Impl;

import cn.seu.api.BaseConnection;
import cn.seu.api.SqlOperation;
import java.util.*;
import java.text.*;
import java.sql.*;

/**
 * <p>
 * <code>BookImpl</code>类是用于实现管理跟书籍有关的表。
 * 该类接收从前端传入后端的信息，并且根据信息返回指定类型的操作，包括但不限于增删改查跟书籍有关的数据库的表。
 * 
 * @author 09019111赖泽升
 * @version 1.0
 */
public class BooksImpl implements SqlOperation, BaseConnection {
  Connection connection;
  private static String database = "mysql";
  private static String password = "123456";
  private static String booksTableName = "tbllibrary_books";
  private static String borrowTableName = "tblborrow_books";

  /**
   * 
   * @param dateString 输入的日期，格式为"YYYY-MM-DD"
   * @return 返回mysql特定格式的日期
   * @throws ParseException
   */
  public java.sql.Date stringToSqlDate(String dateString) throws ParseException {
    if (dateString == null)
      return null;
    String str = dateString;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date d = null;
    try {
      d = format.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    java.sql.Date date = new java.sql.Date(d.getTime());
    return date;
  }

  public String getDate(Integer year, Integer month, Integer day) {
    String y = Integer.toString(year);
    String m = Integer.toString(month);
    String d = Integer.toString(day);
    return y + "-" + m + "-" + d;
  }

  /**
   * 创建和数据库的连接
   */
  public void getConnection() {
    try {
      // 加载及注册JDBC驱动程序
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 创建JDBC连接
      String dbURL = "jdbc:mysql://localhost:3306/" + database + "?user=root&password=" + password;
      connection = DriverManager.getConnection(dbURL);

    } catch (ClassNotFoundException e) {
      System.out.println("无法找到驱动类");

    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  /**
   * 此函数只供图书馆管理员用于添加书籍
   * 
   * @param args 0：书号，1：书名，2：馆藏数量，3：可借数量，4：总借阅数
   * @return 若添加成功则返回1，否则返回-1
   */
  public Integer addUser(String[] args) {
    for (String i : args) {
      System.out.print(i);
      System.out.print(",");
    }
    System.out.println(" ");

    String book_id;
    String book_name;
    int gross, number, borrowed_num;

    book_id = args[0];
    book_name = args[1];
    gross = Integer.parseInt(args[2]);
    number = Integer.parseInt(args[3]);
    borrowed_num = Integer.parseInt(args[4]);
    PreparedStatement pstmt = null;
    try {
      getConnection();
      String sql = "INSERT INTO " + booksTableName + "(book_id,book_name,gross,num,borrowed_num) VALUES(?,?,?,?,?)";
      pstmt = connection.prepareStatement(sql);

      pstmt.setString(1, book_id);
      pstmt.setString(2, book_name);
      pstmt.setInt(3, gross);
      pstmt.setInt(4, number);
      pstmt.setInt(5, borrowed_num);

      pstmt.addBatch();
      pstmt.clearParameters();

      pstmt.executeBatch();
      pstmt.clearBatch();
      return 1;
    } catch (BatchUpdateException e) {
      System.out.println("This record is existing in the table!");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        pstmt.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 根据索引信息删除指定书籍
   * 
   * @param condition_name 需要删除的索引
   * @param condition      索引值
   * @return 若返回1则代表删除该书籍成功，若返回-1则代表删除该书籍失败
   */
  public Integer deleteUser(String condition_name, String condition) {
    Statement statement = null;
    try {
      getConnection();

      statement = connection.createStatement();
      String sql = null;

      sql = "DELETE FROM " + booksTableName + " WHERE " + condition_name + "=" + "'" + condition + "'";
      statement.executeUpdate(sql);
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        statement.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于修改图书管理员修改图书馆书籍中的信息
   * 
   * @param args 0：书号，1：书名，2：馆藏数量，3：可借数量，4：总借阅数
   * @return 若返回1则代表修改书籍信息成功，若返回-1代表修改信息失败
   */
  public Integer updateUser(String[] args) {
    for (String i : args) {
      System.out.print(i);
      System.out.print(",");
    }
    System.out.println(" ");
    String book_id;
    String book_name;
    int gross, number, borrowed_num;

    book_id = args[0];
    book_name = args[1];
    gross = Integer.parseInt(args[2]);
    number = Integer.parseInt(args[3]);
    borrowed_num = Integer.parseInt(args[4]);

    PreparedStatement pstmt = null;
    try {
      getConnection();
      String sql = "UPDATE " + booksTableName + " SET book_id=?,book_name=?,gross=?,num=?,borrowed_num=?"
          + " WHERE book_id=" + "'" + book_id + "'";
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, book_id);
      pstmt.setString(2, book_name);
      pstmt.setInt(3, gross);
      pstmt.setInt(4, number);
      pstmt.setInt(5, borrowed_num);

      pstmt.executeUpdate();
      return 1;
    } catch (SQLException e) {
      System.out.println("该书籍不存在");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        pstmt.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于查找符合要求的所有信息，如果两个参数全为空，则返回所有图书
   * 
   * @param condition_name 需要查询的索引
   * @param condition      索引值
   * @return 返回为符合该索引值的所有信息， 每一行的信息为（0：书号，1：书名，2：馆藏数量，3：可借数量，4：总借阅数）
   */
  public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition) {
    System.out.print(condition_name + "," + condition);
    System.out.println(" ");
    Statement statement = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;
    ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
    ArrayList<String> temp = null;

    try {
      getConnection();
      String sql = null;
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      if (condition_name.equals("null") && condition.equals("null"))
        sql = "SELECT* FROM " + booksTableName;
      else
        sql = "SELECT* FROM " + booksTableName + " WHERE " + condition_name + " LIKE" + "'%" + condition + "%'";
      rs = statement.executeQuery(sql);
      rsmd = rs.getMetaData();
      while (rs.next()) {
        temp = new ArrayList<String>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          temp.add(rs.getString(i));
        }
        al.add(temp);
      }

      return al;
    } catch (Exception e) {
      e.printStackTrace();
      return al;
    } finally {
      try {
        statement.close();
        rs.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于查找符合要求的所有信息
   * 
   * @param condition_name 需要查询的索引
   * @param condition      索引值
   * @param sortWay        书本的排序方式（1：匹配度，2：总借阅量，3：现存数量）
   * @return 返回为符合该索引值的所有信息， 每一行的信息为（0：书号，1：书名，2：馆藏数量，3：可借数量，4：总借阅数）
   */
  public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition, String sortWay) {
    System.out.print(condition_name + "," + condition + "," + sortWay);
    System.out.println(" ");
    Statement statement = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;
    ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
    ArrayList<String> temp = null;

    String way = null;
    switch (sortWay) {
      case "1":
        way = " ORDER BY" + " (CASE WHEN " + condition_name + "=" 
            + "'" + condition + "'" + " THEN 1 WHEN "+ condition_name 
            + " LIKE" + "'" + condition + "%'" + " THEN 2 WHEN " 
            + condition_name + " LIKE" + "'%"+ condition + "%'" 
            + " THEN 3 WHEN " + condition_name + " LIKE" + "'%" 
            + condition + "'"+ " THEN 4 ELSE 5 END )";
        break;
      case "2":
        way = " ORDER BY borrowed_num DESC";
        break;
      case "3":
        way = " ORDER BY num DESC";
        break;
    }
    try {
      getConnection();
      String sql = null;
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      if (condition_name.equals("null") && condition.equals("null") && sortWay.equals("null"))
        sql = "SELECT* FROM " + booksTableName;
      else if (condition_name.equals("null") && condition.equals("null") && !sortWay.equals("null"))
        sql = "SELECT* FROM " + booksTableName + way;
      else
        sql = "SELECT* FROM " + booksTableName + " WHERE " + condition_name + " LIKE" + "'%" + condition + "%'" + way;
      rs = statement.executeQuery(sql);
      rsmd = rs.getMetaData();
      while (rs.next()) {
        temp = new ArrayList<String>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          temp.add(rs.getString(i));
        }
        al.add(temp);
      }

      return al;
    } catch (Exception e) {
      e.printStackTrace();
      return al;
    } finally {
      try {
        statement.close();
        rs.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于查找指定列的所有书籍信息
   * 
   * @param column 用于指定列
   * @return 返回所有书籍的指定列信息(注意：下标从0开始!!)
   */
  public ArrayList<String> searchAllUser(String column) {
    System.out.println(column);
    Statement statement = null;
    ResultSet rs = null;
    ArrayList<String> al = new ArrayList<String>();
    try {
      getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT* FROM " + booksTableName;
      rs = statement.executeQuery(sql);
      while (rs.next()) {
        al.add(rs.getString(column));
      }
      return al;
    } catch (Exception e) {
      e.printStackTrace();
      return al;
    } finally {
      try {
        statement.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * @param args 学生借书的所有信息（0：一卡通，1：书号，2：书名，3:借阅时间，4：毫秒数）
   * @return 若借书成功则返回1，否则返回-1
   */
  public Integer borrowBook(String args[]) {
    for (String i : args) {
      System.out.print(i);
      System.out.print(",");
    }
    System.out.println(" ");

    String id, book_id, book_name, time, milliseconds;

    id = args[0];
    book_id = args[1];
    book_name = args[2];
    time = args[3];
    milliseconds = args[4];

    ArrayList<String> result;
    Statement statement = null;
    PreparedStatement pstmt = null;
    try {
      getConnection();
      String sql = "INSERT INTO " + borrowTableName + "(id,book_id,book_name,time,milliseconds) VALUES(?,?,?,?,?)";
      statement = connection.createStatement();
      pstmt = connection.prepareStatement(sql);

      pstmt.setString(1, id);
      pstmt.setString(2, book_id);
      pstmt.setString(3, book_name);
      pstmt.setDate(4, stringToSqlDate(time));
      pstmt.setString(5, milliseconds);

      pstmt.addBatch();
      pstmt.clearParameters();

      System.out.println(pstmt.executeBatch());
      pstmt.clearBatch();

      result = searchOneUser("book_id", book_id).get(0);
      Integer num_temp = Integer.parseInt(result.get(3)) - 1;
      Integer borrowed_temp = Integer.parseInt(result.get(4)) + 1;

      String[] temp = new String[5];
      for (int i = 0; i < 5; i++) {
        temp[i] = result.get(i);
      }
      temp[3] = num_temp.toString();
      temp[4] = borrowed_temp.toString();
      updateUser(temp);

      return 1;
    } catch (BatchUpdateException e) {
      e.printStackTrace();
      // System.out.println("This record is existing in the table!");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        statement.close();
        pstmt.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于学生归还书籍
   * 
   * @param id     一卡通号
   * @param bookId 书号
   * @return 若还书成功则返回1，否则返回-1
   */
  public Integer returnBook(String id, String bookId) {
    String book_id;
    book_id = bookId;

    ArrayList<String> result;

    Statement statement = null;
    try {
      getConnection();

      statement = connection.createStatement();
      String sql = null;

      sql = "DELETE FROM " + borrowTableName + " WHERE book_id" + "=" + "'" + book_id + "'" + " AND id=" + id;
      statement.executeUpdate(sql);

      result = searchOneUser("book_id", book_id).get(0);
      Integer num_temp = Integer.parseInt(result.get(3)) + 1;

      String[] temp = new String[5];
      for (int i = 0; i < 5; i++) {
        temp[i] = result.get(i);
      }
      temp[3] = num_temp.toString();

      updateUser(temp);
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        statement.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于查看学生的借阅记录
   * 
   * @param id 学生的一卡通号
   * @return 返回该学生的所有借阅记录
   */
  public ArrayList<ArrayList<String>> borrowRecord(int id) {
    Statement statement = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;
    ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
    ArrayList<String> temp = null;

    try {
      getConnection();
      String sql = null;
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      sql = "SELECT* FROM " + borrowTableName + " WHERE " + "id=" + id;
      rs = statement.executeQuery(sql);
      rsmd = rs.getMetaData();
      while (rs.next()) {
        temp = new ArrayList<String>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          temp.add(rs.getString(i));
        }
        al.add(temp);
      }

      return al;
    } catch (Exception e) {
      e.printStackTrace();
      return al;
    } finally {
      try {
        statement.close();
        rs.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * 查找当前数量不为0的书籍
   * 
   * @param condition_name 索引列
   * @param condition      索引列的值
   * @return 返回符合要求的二维数组
   */
  public ArrayList<ArrayList<String>> bookList_notNull(String condition_name, String condition) {
    ArrayList<ArrayList<String>> temp = searchOneUser(condition_name, condition);
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).get(3).equals("0"))
        temp.remove(i);
    }
    return temp;
  }

}