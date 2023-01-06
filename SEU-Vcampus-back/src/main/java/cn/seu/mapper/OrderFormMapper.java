package cn.seu.mapper;

import cn.seu.bean.OrderFormBean;
import cn.seu.domain.OrderForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderFormMapper {

    List<OrderForm> searchAll();

    OrderForm searchFormById(@Param("id") Long id);

    OrderForm searchFormByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    OrderForm searchFormByIdAndTradeNo(OrderFormBean orderFormBean);

    int addOrderForm(OrderForm orderForm);

    int updateOrderForm(OrderForm orderForm);
}
