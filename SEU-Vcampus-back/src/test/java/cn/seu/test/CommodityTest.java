package cn.seu.test;

import cn.seu.Impl.AliPayImpl;
import cn.seu.Impl.ShoppingCarImpl;
import cn.seu.controller.IndexController;
import cn.seu.domain.store.Commodity;
import cn.seu.mapper.CommodityMapper;
import cn.seu.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityTest {
    @Autowired
    private CommodityMapper commodityMapper;

    @Test
    public void testSearchAll() {
        List<Commodity> commoditys = commodityMapper.searchAll();
        for (Commodity commodity : commoditys) {
            System.out.println(commodity);
        }
    }

    @Test
    public void testSearchById() {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        CommodityMapper mapper = sqlSession.getMapper(CommodityMapper.class);
        Commodity commodity = mapper.searchById("XX0020");
        System.out.println(commodity);
        sqlSession.close();
    }


//    public void testAlipayService() {
//        IndexController indexController = new IndexController();
//        try {
//            indexController.searchCommodity();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test public void testShoppingCar(){
        ShoppingCarImpl scl=new ShoppingCarImpl();
        ArrayList<ArrayList<String>> al=scl.searchOneUser("id","213191246");
        System.out.println(al);
    }
}
