package cn.seu.test;

import cn.seu.bean.PersonBean;
import cn.seu.domain.User;
import cn.seu.mapper.UserMapper;
import cn.seu.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserTest {
    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        System.out.println(users);
        sqlSession.close();
    }

    @Test
    public void testUpdateBalance(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        PersonBean personBean=new PersonBean();
        personBean.setId(213191246);
        personBean.setBalance(572);
        int result=mapper.updateBalanceById(personBean);
        System.out.println(result);
        sqlSession.close();
    }
}
