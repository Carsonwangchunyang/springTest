package com.carson.springtestdemo.dao;

import com.carson.springtestdemo.model.YipFeerateInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class SpringsteenApplicationTests {
    @Autowired
    private YipFeerateInfoDao yipFeerateInfoDao;

    @Test
    void contextLoads() {
        List<YipFeerateInfo> yipFeerateInfos = yipFeerateInfoDao.selectAll();
        System.out.println("最帅的打印方式:"+yipFeerateInfos.size());
    }

}
