package cn.seu.Impl;

import cn.seu.api.BaseConnection;
import cn.seu.api.SqlOperation;
import java.util.*;
import java.text.*;
import java.sql.*;

/**
 * <p>
 * <code>GradeImpl</code>类是用于实现管理跟成绩有关的表。
 * 该类接收从前端传入后端的信息，并且根据信息返回指定类型的操作，包括但不限于增删改查跟成绩有关的数据库的表。
 * 
 * @author 09019111赖泽升
 * @version 1.0
 */
public class GradeImpl implements SqlOperation, BaseConnection {
  Connection connection;
  private static String database = "mysql";
  private static String password = "123456";
  private static String GradeTableName = "tblgrade";

  /**
   * 
   * @param dateString 输入的日期，格式为"YYYY-MM-DD"
   * @return 返回mysql特定格式的日期
   * @throws ParseException
   */
  public java.sql.Date stringToSqlDate(String dateString) throws ParseException {
    if (dateString.equals("null"))
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
   * @param args 用户的所有信息（0：一卡通号，1：课程号，2：成绩）；
   * 
   * @return 若返回1则代表添加用户信息成功，若返回-1代表添加信息失败
   */
  public Integer addUser(String [] args) {
    for (String i : args) {
      System.out.print(i);
      System.out.print(",");
    }
    System.out.println(" ");

    int id;
    String course_id;
    double score;

    id = Integer.parseInt(args[0]);
    course_id = args[1];
    score = Double.parseDouble(args[2]);

    PreparedStatement pstmt = null;
    try {
      getConnection();
      String sql = "INSERT INTO " + GradeTableName + "(id,course_id,score) VALUES(?,?,?)";
      pstmt = connection.prepareStatement(sql);

      pstmt.setInt(1, id);
      pstmt.setString(2, course_id);
      pstmt.setDouble(3, score);

      pstmt.addBatch();
      pstmt.clearParameters();

      pstmt.executeBatch();
      pstmt.clearBatch();
      return 1;
    } catch (BatchUpdateException e) {
      System.out.println("This record is existing in the table!");
      e.printStackTrace();
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
   * 删除指定学生的成绩
   * 
   * @param stu_id    学生的一卡通号
   * @param course_id 课程号
   * 
   * @return 如果删除成功则返回1，否则返回-1
   */
  public Integer deleteUser(String stu_id, String course_id) {
    Statement statement = null;
    try {
      getConnection();

      statement = connection.createStatement();
      String sql = null;
      sql = "DELETE FROM " + GradeTableName + " WHERE id=" + stu_id + " AND course_id=" + "'" + course_id + "'";
      statement.executeUpdate(sql);
      return 1;
    } catch (SQLException e) {
      System.out.println("输入数据格式错误");
      return -1;
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
   * 用于修改用户再数据库中的信息
   * 
   * @param args 用户的所有信息(0：一卡通号，1：课程号，2：成绩）
   * 
   * @return 若返回1则代表修改用户信息成功，若返回-1代表修改信息失败
   */
  public Integer updateUser(String[] args) {
    for (String i : args) {
      System.out.print(i);
      System.out.print(",");
    }
    System.out.println(" ");

    String id, course_id;
    double score;

    id = args[0];
    course_id = args[1];
    score = Double.parseDouble(args[2]);

    PreparedStatement pstmt = null;
    try {
      getConnection();
      String sql = "UPDATE " + GradeTableName + " SET score=?" + " WHERE id=" + id + " AND course_id=" + course_id;
      pstmt = connection.prepareStatement(sql);
      pstmt.setDouble(1, score);

      pstmt.executeUpdate();
      return 1;
    } catch (SQLException e) {
      System.out.println("该用户不存在");
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
   * 用于查找符合要求的所有信息
   * 
   * @param condition_name 需要查询的索引
   * @param condition      索引值
   * @return 返回为符合该索引值的所有信息，包括一卡通号,课程id，成绩， 服务端根据客户端的需求自行选择信息返回(注意：下标从0开始！！)；
   *         每一行的信息为（0：一卡通，1：课程id，2：成绩）
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
      if (condition_name.equals("null") && condition.equals("null")) {
        sql = "SELECT* FROM " + GradeTableName;
        while (rs.next()) {
          {
            temp = new ArrayList<String>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
              temp.add(rs.getString(i));
            }
            al.add(temp);
          }
        }
        return al;
      } else { // 如果两个参数不为空
        sql = "SELECT* FROM " + GradeTableName + " WHERE " + condition_name + " LIKE" + "'%" + condition + "%'";
        rs = statement.executeQuery(sql);
        rsmd = rs.getMetaData();
        while (rs.next()) {
          {
            temp = new ArrayList<String>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
              temp.add(rs.getString(i));
            }
          }
          al.add(temp);
        }
        return al;
      }
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
   * 用于查找指定列的所有用户信息
   * 
   * @param column 用于指定列
   * @return 返回所有用户的指定列信息(注意：下标从0开始!!)
   */
  public ArrayList<String> searchAllUser(String column) {
    System.out.println(column);
    Statement statement = null;
    ResultSet rs = null;
    ArrayList<String> al = new ArrayList<String>();
    try {
      getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT* FROM " + GradeTableName;
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
   * 根据一卡通号和课程号返回学生成绩
   * 
   * @param id     一卡通号
   * @param cou_id 课程号
   * @return 返回对应的成绩
   */
  public String searchGrade(String id, String cou_id) {
    System.out.print(id + "," + cou_id);
    System.out.println(" ");
    Statement statement = null;
    ResultSet rs = null;
    String score = "null";

    try {
      getConnection();
      String sql = null;
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      sql = "SELECT* FROM " + GradeTableName + " WHERE id=" + id + " AND course_id=" + "'" + cou_id + "'";
      rs = statement.executeQuery(sql);
      while (rs.next()) {
        score = rs.getString(3);
      }
      return score;
    } catch (Exception e) {
      e.printStackTrace();
      return score;
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

}