package cn.seu.Impl;

import cn.seu.api.BaseConnection;
import cn.seu.api.SqlOperation;

import java.util.*;
import java.text.*;
import java.sql.*;

/**
 * <p>
 * <code>LogInImpl</code>类是用于实现管理存储用户信息的表。
 * 该类接收从前端传入后端的信息，并且根据信息返回指定类型的操作，包括但不限于增删改查存储用户信息的表。
 *
 * @author 09019111赖泽升
 * @version 1.0
 */
public class LogInImpl implements SqlOperation, BaseConnection {
    private HashMap<String, String> authority = new HashMap<String, String>();
    private HashMap<String, String> sex = new HashMap<String, String>();
    Connection connection;
    private static String database = "mysql";
    private static String password = "123456";
    private static String tableName = "tbluser";

    public LogInImpl() {

        authority.put("教务老师", "1");
        authority.put("图书管理员", "2");
        authority.put("商店管理员", "3");
        authority.put("老师", "4");
        authority.put("学生", "5");

        sex.put("男", "1");
        sex.put("女", "2");
    }

    /**
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
     * @param args 用户的所有信息（0：一卡通，1：昵称，2：密码，3：姓名，4：权限，5：年龄，6：性别，7：入学时间，8：专业/职称，9：学院，10：金钱，11：图片rul）；
     *             其中，在权限中，1代表教务老师，2代表图书管理员，3代表商店管理员，4代表老师，5代表学生； 在性别中，1代表男士，2代表女士；
     * @return 若返回1则代表添加用户信息成功，若返回-1代表添加信息失败
     */
    public Integer addUser(String[] args) {
        for (String i : args) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(" ");

        int id, authority, age, sex;
        String nickname, password, name, time, profession, college, url;
        double money;

        id = Integer.parseInt(args[0]);
        nickname = args[1];
        password = args[2];
        name = args[3];
        authority = Integer.parseInt(args[4]);
        age = Integer.parseInt(args[5]);
        sex = Integer.parseInt(args[6]);
        time = args[7];
        profession = args[8];
        college = args[9];
        money = Double.parseDouble(args[10]);
        url = args[11];

        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "INSERT INTO "+tableName
                    + " (id,nickname,password,name,authority,age,sex,time,profession,college,money,url,netfee) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.setString(2, nickname);
            pstmt.setString(3, password);
            pstmt.setString(4, name);
            pstmt.setInt(5, authority);
            pstmt.setInt(6, age);
            pstmt.setInt(7, sex);
            pstmt.setDate(8, stringToSqlDate(time));
            pstmt.setString(9, profession);
            pstmt.setString(10, college);
            pstmt.setDouble(11, money);
            pstmt.setString(12, url);
            pstmt.setDouble(13, 0);

            pstmt.addBatch();
            pstmt.clearParameters();

            pstmt.executeBatch();
            pstmt.clearBatch();
            return 1;
        } catch (BatchUpdateException e) {
            System.out.println("This record is existing in the table!");
            e.printStackTrace();
            return -1;
        } catch (ParseException e) {
            System.out.println("日期输入语法错误");
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
     * 根据索引信息删除指定用户
     *
     * @param condition_name 需要删除的索引
     * @param condition      索引值
     * @return 若返回1则代表删除该用户成功，若返回-1则代表删除该用户失败
     */
    public Integer deleteUser(String condition_name, String condition) {
        Statement statement = null;
        try {
            getConnection();

            boolean isInt = false;
            if (condition_name.equals("authority") || condition_name.equals("age") || condition_name.equals("id")
                    || condition_name.equals("sex")) {
                isInt = true;
            }

            statement = connection.createStatement();
            String sql = null;
            if (isInt)
                sql = "DELETE FROM " + tableName + " WHERE " + condition_name + "=" + condition;
            else
                sql = "DELETE FROM " + tableName + " WHERE " + condition_name + "=" + "'" + condition + "'";
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
     * @param args 用户的所有信息(0：一卡通，1：昵称，2：密码，3：姓名，4：权限，5：年龄，6：性别，7：入学时间，8：专业/职称，9：学院，10：金钱，11：图片url)；
     *             其中，在权限中，1代表教务老师，2代表图书管理员，3代表商店管理员，4代表老师，5代表学生； 在性别中，1代表男士，2代表女士；
     * @return 若返回1则代表修改用户信息成功，若返回-1代表修改信息失败
     */
    public Integer updateUser(String[] args) {
        for (String i : args) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(" ");

        int id, authority, age, sex;
        String nickname, password, name, time, profession, college, url;
        double money;

        id = Integer.parseInt(args[0]);
        nickname = args[1];
        password = args[2];
        name = args[3];
        authority = Integer.parseInt(args[4]);
        age = Integer.parseInt(args[5]);
        sex = Integer.parseInt(args[6]);
        time = args[7];
        profession = args[8];
        college = args[9];
        money = Double.parseDouble(args[10]);
        url = args[11];

        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "UPDATE " + tableName
                    + " SET id=?,nickname=?,password=?,name=?,authority=?,age=?,sex=?,time=?,profession=?,college=?,money=?"
                    + " WHERE id=" + id;
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nickname);
            pstmt.setString(3, password);
            pstmt.setString(4, name);
            pstmt.setInt(5, authority);
            pstmt.setInt(6, age);
            pstmt.setInt(7, sex);
            pstmt.setDate(8, stringToSqlDate(time));
            pstmt.setString(9, profession);
            pstmt.setString(10, college);
            pstmt.setDouble(11, money);

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
     * @return 返回为符合该索引值的所有信息，包括一卡通号，姓名，年龄等等， 服务端根据客户端的需求自行选择信息返回(注意：下标从0开始！！)；
     * 每一行的信息为（0：一卡通，1：昵称，2：密码，3：姓名，4：权限，5：年龄，6：性别，7：入学时间，8：专业/职称，9：学院，10金钱，11：图片url，12：网费）
     */
    public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition) {
        Statement statement = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
        ArrayList<String> temp = null;
        boolean isInt = false;

        try {
            getConnection();
            String sql = null;
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (condition_name.equals("null") && condition.equals("null")) {
                sql = "SELECT* FROM " + tableName;
            } else { // 如果两个参数不为空
                if (condition_name.equals("authority") || condition_name.equals("age") || condition_name.equals("id")
                        || condition_name.equals("sex")) {
                    isInt = true;
                }

                if (isInt)
                    sql = "SELECT* FROM " + tableName + " WHERE " + condition_name + "=" + condition;
                else
                    sql = "SELECT* FROM " + tableName + " WHERE " + condition_name + " LIKE" + "'%" + condition + "%'";
            }
            rs = statement.executeQuery(sql);
            rsmd = rs.getMetaData();
            while (rs.next()) {
                temp = new ArrayList<String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (i == 5)
                        temp.add(authority.get(rs.getString(i)));
                    else if (i == 7)
                        temp.add(sex.get(rs.getString(i)));
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
     * 用于查找指定列的所有用户信息
     *
     * @param column 用于指定列
     * @return 返回所有用户的指定列信息(注意 ： 下标从0开始 ! !)
     */
    public ArrayList<String> searchAllUser(String column) {
        System.out.println(column);
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<String> al = new ArrayList<String>();
        try {
            getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT* FROM " + tableName;
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
     * @param id       用户的一卡通号
     * @param nickname 输入的昵称
     * @return 若是该昵称存在，则返回1；否则返回-1
     */
    public int existUser(String id, String nickname) {
        ArrayList<String> al = searchOneUser("id", id).get(0);
        if (!al.get(1).equals(nickname))
            return -1;
        else
            return 1;
    }

    /**
     * 判断用户时候登录成功
     *
     * @param condition_name 索引的表头
     * @param condition      索引的值（用户名或者昵称）
     * @param password       用户输入的密码
     * @return 若密码正确，则返回改用户的整行信息，否则返回空
     */
    public ArrayList<ArrayList<String>> isLogIn(String condition_name, String condition, String password) {
        ArrayList<ArrayList<String>> al = searchOneUser(condition_name, condition);
        if (al == null) {
            return null;
        } else {
            if (al.get(0).get(2).equals(password))
                return al;
            else
                return null;
        }
    }

    /**
     * 用于根据一卡通号修改卡中的余额
     *
     * @param id    一卡通
     * @param money 修改过后的金钱
     * @return 若修改成功则返回1，否则返回-1
     */
    public Integer updateMoney(String id, String money) {
        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "UPDATE " + tableName + " SET money=?" + " WHERE id=" + id;
            pstmt = connection.prepareStatement(sql);

            pstmt.setDouble(1, Double.parseDouble(money));

            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
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
     * 用于根据一卡通号充值网费
     *
     * @param id    一卡通
     * @param netfee 修改过后的网费
     * @return 若修改成功则返回1，否则返回-1
     */
    public Integer updateNetFee(String id, String netfee) {
        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "UPDATE " + tableName + " SET netfee=?" + " WHERE id=" + id;
            pstmt = connection.prepareStatement(sql);

            pstmt.setDouble(1, Double.parseDouble(netfee));

            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
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
     * 用于根据一卡通号4更新图片
     *
     * @param id    一卡通
     * @param url 修改过后的图片url
     * @return 若修改成功则返回1，否则返回-1
     */
    public Integer updatePhoto(String id, String url) {
        PreparedStatement pstmt = null;
        try {
            getConnection();
            String sql = "UPDATE " + tableName + " SET url=?" + " WHERE id=" + id;
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, url);

            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
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

}