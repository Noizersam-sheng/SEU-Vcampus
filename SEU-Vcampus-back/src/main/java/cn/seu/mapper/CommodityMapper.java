package cn.seu.mapper;

import cn.seu.domain.store.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommodityMapper {

    List<Commodity> searchAll();

    Commodity searchById(@Param("commodityId") String commodityId);
}
