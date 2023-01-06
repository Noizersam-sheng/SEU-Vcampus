package cn.seu.api;

import cn.seu.bean.PersonBean;
import cn.seu.domain.Consumption;
import cn.seu.domain.Record;
import cn.seu.domain.User;
import cn.seu.domain.store.Commodity;

public interface WriteBackRecord {
    int insertConsumption(Consumption consumption);

    int insertRecord(Record record);

    User searchUserById(Integer id);

    int updateBalanceById(PersonBean personBean);

    Commodity searchCommodityById(String commodityId);
}
