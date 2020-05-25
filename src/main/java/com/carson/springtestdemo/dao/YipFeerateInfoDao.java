package com.carson.springtestdemo.dao;

import com.carson.springtestdemo.dto.YipFeerateInfoDto;
import com.carson.springtestdemo.model.YipFeerateInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author carson
 */
public interface YipFeerateInfoDao extends Mapper<YipFeerateInfo> {

    /**
     * 删除历史数据
     */
    int deleteFeerateHistory();

    /**
     * 备份历史数据
     *
     */
    int addBatchYipServiceFeerateInfHistory();

    /**
     * 清空数据
     */
    int deleteAll();

    int addBatchYipServiceFeerateInf(@Param("list") List<YipFeerateInfoDto> list);

}