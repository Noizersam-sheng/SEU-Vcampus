package cn.seu.Impl;

import cn.seu.api.BaseConnection;
import cn.seu.api.SqlOperation;

import java.util.*;
import java.text.*;
import java.sql.*;

/**
 * <p>
 * <code>RecordImpl</code>类是用于实现管理存储消费记录的表。
 * 该类接收从前端传入后端的信息，并且根据信息返回指定类型的操作，包括但不限于增删改查存储消费记录的表。
 *
 * @author 09019111赖泽升
 * @version 1.0
 */
public class RecordImpl implements BaseConnection, SqlOperation {
    Connection connection;
    private HashMap<String, String> ways2num = new HashMap<String, String>();
    private HashMap<String, String> num2ways = new HashMap<String, String>();
    private static String database = "mysql";
    private static String password = "123456";
    private static String RecordTableName = "tblrecord";

    public RecordImpl() {
        ways2num.put("一卡通充值", "1");
        ways2num.put("商店消费", "2");
        ways2num.put("缴网费", "3");
        ways2num.put("二手商品收入", "5");
        num2ways.put("1", "一卡通充值");
        num2ways.put("2", "商店消费");
        num2ways.put("3", "缴网费");
        num2ways.put("5", "二手商品收入");
    }

    /**
     * @param dateString 输入的日期，格式为"YYYY-MM-DD"
     * @return 返回mysql特定格式的日期
     * @throws ParseException
     */
    public java.sql.Timestamp stringToSqlDate(String dateString) throws ParseException {
        if (dateString == null)
            return null;
        String str = dateString;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Timestamp date = new java.sql.Timestamp(d.getTime());
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
     * 此函数用于增加消费记录
     *
     * @param args 0：一卡通，1：方式，2：花费，3：余额，4：日期
     * @return 若添加成功则返回1，否则返回-1
     */
    public Integer addUser(String[] args) {
        for (String i : args) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(" ");

        String ways, time;
        double cost, surplus;
        int id;

        id = Integer.parseInt(args[0]);
        ways = num2ways.get(args[1]);
        cost = Double.parseDouble(args[2]);
        surplus = Double.parseDouble(args[3]);
        time = args[4];

        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "INSERT INTO " + RecordTableName + "(long_id,id,ways,cost,surplus,time)" + " VALUES(null,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.setString(2, ways);
            pstmt.setDouble(3, cost);
            pstmt.setDouble(4, surplus);
            pstmt.setTimestamp(5, stringToSqlDate(time));

            pstmt.addBatch();
            pstmt.clearParameters();

            pstmt.executeBatch();
            pstmt.clearBatch();
            return 1;
        } catch (BatchUpdateException e) {
            // System.out.println("This record is existing in the table!");
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
     * 根据索引信息删除指定记录
     *
     * @param condition_name 需要删除的索引
     * @param condition      索引值
     * @return 若返回1则代表删除该商品成功，若返回-1则代表删除该商品失败
     */
    public Integer deleteUser(String condition_name, String condition) {
        Statement statement = null;
        try {
            getConnection();

            statement = connection.createStatement();
            String sql = null;

            sql = "DELETE FROM " + RecordTableName + " WHERE " + condition_name + "=" + "'" + condition + "'";
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
     * 用于更新消费记录（可能不需要）
     *
     * @param args 0：一卡通，1：方式，2：花费，3：余额，4：日期
     * @return 若返回1则代表修改商品信息成功，若返回-1代表修改信息失败
     */
    public Integer updateUser(String[] args) {
        for (String i : args) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(" ");

        String time;
        double cost, surplus;
        int id, ways;

        id = Integer.parseInt(args[0]);
        ways = Integer.parseInt(args[1]);
        cost = Double.parseDouble(args[2]);
        surplus = Double.parseDouble(args[3]);
        time = args[4];

        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "UPDATE " + RecordTableName + " SET id=?,ways=?,cost=?,surplus=?,time=?" + " WHERE id=" + id;

            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.setInt(2, ways);
            pstmt.setDouble(3, cost);
            pstmt.setDouble(4, surplus);
            pstmt.setTimestamp(5, stringToSqlDate(time));

            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("该商品不存在");
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
     * @return 返回为符合该索引值的所有信息， 每一行的信息为（0：一卡通，1：方式，2：花费，3：余额，4：日期）
     */
    public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition) {
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
                sql = "SELECT id,ways,cost,surplus,time FROM " + RecordTableName + " ORDER BY time DESC";
            else
                sql = "SELECT id,ways,cost,surplus,time FROM " + RecordTableName + " WHERE " + condition_name + " LIKE" + "'%" + condition + "%'"
                        + "ORDER BY" + " (CASE WHEN " + condition_name + "=" + "'" + condition + "'" + " THEN 1 WHEN "
                        + condition_name + " LIKE" + "'" + condition + "%'" + " THEN 2 WHEN " + condition_name + " LIKE" + "'%"
                        + condition + "%'" + " THEN 3 WHEN " + condition_name + " LIKE" + "'%" + condition + "'"
                        + " THEN 4 ELSE 5 END ),time DESC";
            rs = statement.executeQuery(sql);
            rsmd = rs.getMetaData();
            while (rs.next()) {
                temp = new ArrayList<String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (i == 2)
                        temp.add(ways2num.get(rs.getString(i)));
                    else
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
     * 用于查找指定列的所有记录信息
     *
     * @param column 用于指定列
     * @return 返回所有书籍的指定列信息(注意 ： 下标从0开始 ! !)
     */
    public ArrayList<String> searchAllUser(String column) {
        System.out.println(column);
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<String> al = new ArrayList<String>();
        try {
            getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT id,ways,cost,surplus,time FROM " + RecordTableName;
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
}
