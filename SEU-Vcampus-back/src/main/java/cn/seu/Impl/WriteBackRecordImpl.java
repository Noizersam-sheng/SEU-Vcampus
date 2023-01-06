package cn.seu.Impl;

import cn.seu.api.WriteBackRecord;
import cn.seu.bean.PersonBean;
import cn.seu.domain.Consumption;
import cn.seu.domain.Record;
import cn.seu.domain.User;
import cn.seu.domain.store.Commodity;
import cn.seu.mapper.CommodityMapper;
import cn.seu.mapper.ConsumptionMapper;
import cn.seu.mapper.RecordMapper;
import cn.seu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WriteBackRecordImpl implements WriteBackRecord {
    @Autowired
    private ConsumptionMapper consumptionMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public int insertConsumption(Consumption consumption) {
        return consumptionMapper.insert(consumption);
    }

    @Override
    public int insertRecord(Record record) {
        return recordMapper.insert(record);
    }

    @Override
    public User searchUserById(Integer id){
        return userMapper.selectById(id);
    }

    @Override
    public int updateBalanceById(PersonBean personBean){
        return userMapper.updateBalanceById(personBean);
    }

    @Override
    public Commodity searchCommodityById(String commodityId){
        return commodityMapper.searchById(commodityId);
    }
}
