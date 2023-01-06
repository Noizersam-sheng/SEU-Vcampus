package cn.seu.api;

import java.util.*;

/**
 * <p>
 * <code>StudentDao</code>为对数据库的基本操作接口，里面定义了对数据库的增删改查接口函数。
 * @author 09019111赖泽升
 * @version 1.0
 */
public interface SqlOperation {
  public Integer addUser(String [] args);

  public Integer deleteUser(String condition_name, String condition);

  public Integer updateUser(String [] args);

  public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition);

  public ArrayList<String> searchAllUser(String column);
}