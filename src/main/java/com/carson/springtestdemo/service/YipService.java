package com.carson.springtestdemo.service;

import com.carson.springtestdemo.model.YipFeerateInfo;

import java.util.List;

/**
 * @author carson
 */
public interface YipService {
    /**
     * 查询yip信息
     * @return List<YipFeerateInfo>
     */
    List<YipFeerateInfo> selectYipFee();
}
