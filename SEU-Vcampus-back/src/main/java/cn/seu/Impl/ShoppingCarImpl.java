package cn.seu.Impl;

import cn.seu.api.BaseConnection;
import cn.seu.api.SqlOperation;

import java.util.*;
import java.text.*;
import java.sql.*;


/**
 * <p>
 * <code>ShoppingCarImpl</code>类是用于实现管理跟购物车有关的表。
 * 该类接收从前端传入后端的信息，并且根据信息返回指定类型的操作，包括但不限于增删改查跟购物车有关的数据库的表。
 *
 * @author 09019111赖泽升
 * @version 1.0
 */
public class ShoppingCarImpl implements BaseConnection, SqlOperation {
    Connection connection;
    private HashMap<String, String> hand = new HashMap<String, String>();
    private static String database = "mysql";
    private static String password = "123456";
    private static String CarTableName = "tblshopping_car";
    private static String StoreTableName = "tblcommodity";

    public ShoppingCarImpl() {
        hand.put("一手", "1");
        hand.put("二手", "2");
    }

    /**
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
     * 此函数只供商店管理员用于添加商品
     *
     * @param args 0:商品id，1：商品类型，2：商品名字，3：售价，4：进货价格，5：折扣，6：进货时间，7：数量
     * @return 若添加成功则返回1，否则返回-1
     */
    public Integer addUser(String[] args) {
        for (String i : args) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(" ");

        String name, commodity_id, type, commodity_name, time, image;
        double price, value, discount;
        int id, number, hand;

        id = Integer.parseInt(args[0]);
        name = args[1];
        commodity_id = args[2];
        type = args[3];
        commodity_name = args[4];
        price = Double.parseDouble(args[5]);
        value = Double.parseDouble(args[6]);
        discount = Double.parseDouble(args[7]);
        time = args[8];
        number = Integer.parseInt(args[9]);
        hand = Integer.parseInt(args[10]);
        image = args[11];

        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "INSERT INTO " + CarTableName
                    + "(id,name,commodity_id,type,commodity_name,price,value,discount,time,number,hand,image)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, commodity_id);
            pstmt.setString(4, type);
            pstmt.setString(5, commodity_name);
            pstmt.setDouble(6, price);
            pstmt.setDouble(7, value);
            pstmt.setDouble(8, discount);
            pstmt.setDate(9, stringToSqlDate(time));
            pstmt.setInt(10, number);
            pstmt.setInt(11, hand);
            pstmt.setString(12, image);

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
     * 根据索引信息删除指定商品
     *
     * @param stu_id       学生一卡通号
     * @param commodity_id 商品号
     * @return 若返回1则代表删除该商品成功，若返回-1则代表删除该商品失败
     */
    public Integer deleteUser(String stu_id, String commodity_id) {
        Statement statement = null;
        try {
            getConnection();

            statement = connection.createStatement();
            String sql = null;

            sql = "DELETE FROM " + CarTableName + " WHERE id=" + stu_id + " AND commodity_id=" + "'" + commodity_id + "'";
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
     * 用于商店管理员修改商店中商品的信息
     *
     * @param args 0：一卡通号，1：名字，2：商品id，3：类型，4：商品名字，
     *             5：售价，6：进价，7：折扣，8：进货时间，9：数量，10：来源（1：一手，2：二手），11：图片url
     * @return 若返回1则代表修改商品信息成功，若返回-1代表修改信息失败
     */
    public Integer updateUser(String[] args) {
        for (String i : args) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(" ");

        String name, commodity_id, type, commodity_name, time, image;
        double price, value, discount;
        int id, number, hand;

        id = Integer.parseInt(args[0]);
        name = args[1];
        commodity_id = args[2];
        type = args[3];
        commodity_name = args[4];
        price = Double.parseDouble(args[5]);
        value = Double.parseDouble(args[6]);
        discount = Double.parseDouble(args[7]);
        time = args[8];
        number = Integer.parseInt(args[9]);
        hand = Integer.parseInt(args[10]);
        image = args[11];

        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "UPDATE " + CarTableName + " SET type=?,commodity_name=?,price=?,value=?,discount=?,time=?,number=?,hand=?,image=?"
                    + " WHERE id=" + args[0] + " AND commodity_id=" + "'" + commodity_id + "'";

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, commodity_id);
            pstmt.setString(4, type);
            pstmt.setString(5, commodity_name);
            pstmt.setDouble(6, price);
            pstmt.setDouble(7, value);
            pstmt.setDouble(8, discount);
            pstmt.setDate(9, stringToSqlDate(time));
            pstmt.setInt(10, number);
            pstmt.setInt(11, hand);
            pstmt.setString(12, image);

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
     * @return 返回为符合该索引值的所有信息， 每一行的信息为（0：一卡通号，1：名字，2：商品id，3：类型，4：商品名字，
     * 5：售价，6：进价，7：折扣，8：进货时间，9：数量，10：来源（1：一手，2：二手），11：图片url
     */
    public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition) {
        System.out.println("condition_name:" + condition_name + ", condition:" + condition);
        Statement statement = null;
        ResultSet commodityIdRs = null;
        ResultSet commodityRs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
        ArrayList<String> commodityIdList = null;
        ArrayList<String> temp = null;

        try {
            getConnection();
            String sql = null;
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (condition_name.equals("null") && condition.equals("null"))
                sql = "SELECT commodity_id FROM " + CarTableName;
            else
                sql = "SELECT commodity_id FROM " + CarTableName + " WHERE " + condition_name + " LIKE" + "'%" + condition + "%'"
                        + "ORDER BY" + " (CASE WHEN " + condition_name + "=" + "'" + condition + "'" + " THEN 1 WHEN "
                        + condition_name + " LIKE" + "'" + condition + "%'" + " THEN 2 WHEN " + condition_name + " LIKE" + "'%"
                        + condition + "%'" + " THEN 3 WHEN " + condition_name + " LIKE" + "'%" + condition + "'"
                        + " THEN 4 ELSE 5 END )";
            commodityIdRs = statement.executeQuery(sql);
//            rsmd = commodityIdRs.getMetaData();
            commodityIdList = new ArrayList<String>();
            while (commodityIdRs.next()) {
                commodityIdList.add(commodityIdRs.getString(1));
            }
//            System.out.println(commodityIdList);

            String batch = "(";
            for (int i = 0; i < commodityIdList.size(); i++) {
                if (i < commodityIdList.size() - 1)
                    batch += "'" + commodityIdList.get(i) + "'" + ", ";
                else
                    batch += "'" + commodityIdList.get(i) + "'" + ")";
            }
            sql = "SELECT * FROM " + StoreTableName + " WHERE commodity_id in " + batch;
            commodityRs = statement.executeQuery(sql);
            rsmd = commodityRs.getMetaData();
            while (commodityRs.next()) {
                temp = new ArrayList<String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (i == 9)
                        temp.add(hand.get(commodityRs.getString(i)));
                    else
                        temp.add(commodityRs.getString(i));
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
                commodityIdRs.close();
                commodityRs.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于查找符合要求的所有信息，参数为约束条件，如果不需要，则传入null
     *
     * @param condition_name 需要查询的索引
     * @param lower          价格的下限
     * @param upper          价格的上限
     * @param name           商品的名字
     * @param type           商品的类型
     * @param hands          商品是否来自二手市场（1：一手，2：二手）
     * @return 返回为符合该索引值的所有信息， 每一行的信息为（0：一卡通号，1：名字，2：商品id，3：类型，4：商品名字，
     * 5：售价，6：进价，7：折扣，8：进货时间，9：数量，10：来源（1：一手，2：二手），11：图片url
     */
    public ArrayList<ArrayList<String>> searchOneUser(String lower, String upper, String name, String type,
                                                      String hands) {
        Statement statement = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
        ArrayList<String> temp = null;

        String priceSql = null;
        if (lower.equals("null") && upper.equals("null"))
            priceSql = " WHERE " + " price between " + "0" + " and " + "2147483647";
        else if (!lower.equals("null") && !upper.equals("null"))
            priceSql = " WHERE " + " price between " + lower + " and " + upper;
        if (!name.equals("null"))
            priceSql += " and commodity_name LIKE" + "'%" + name + "%'";
        if (!type.equals("null"))
            priceSql += " and type LIKE" + "'%" + type + "%'";
        if (!hands.equals("null"))
            priceSql += " and hand=" + hands;
        try {
            getConnection();
            String sql = null;
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            sql = "SELECT* FROM " + CarTableName + priceSql;
            rs = statement.executeQuery(sql);
            rsmd = rs.getMetaData();
            while (rs.next()) {
                temp = new ArrayList<String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (i == 11)
                        temp.add(hand.get(rs.getString(i)));
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
     * 用于查找指定列的所有书籍信息
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
            String sql = "SELECT* FROM " + CarTableName;
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