package com.carson.springtestdemo.service.Impl;

import com.carson.springtestdemo.dao.YipFeerateInfoDao;
import com.carson.springtestdemo.model.YipFeerateInfo;
import com.carson.springtestdemo.service.YipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author carson
 */
@Service
public class YipServiceImpl implements YipService {

    @Autowired
    private YipFeerateInfoDao yipFeerateInfoDao;
    @Override
    public List<YipFeerateInfo> selectYipFee() {
        return yipFeerateInfoDao.selectAll();
    }
}
