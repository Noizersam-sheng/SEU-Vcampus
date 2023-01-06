package cn.seu.mapper;

import cn.seu.bean.PersonBean;
import cn.seu.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /*
    列出所有用户
     */
    List<User> selectAll();

    /*
    根据id搜索用户
     */
    User selectById(@Param("id") Integer id);

    /*
    修改用户余额
     */
    int updateBalanceById(PersonBean personBean);
}
