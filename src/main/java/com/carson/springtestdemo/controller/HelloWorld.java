package com.carson.springtestdemo.controller;


import com.carson.springtestdemo.model.YipFeerateInfo;
import com.carson.springtestdemo.service.YipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author carson
 */
@RestController
@RequestMapping("/hello")
public class HelloWorld {
    @Autowired
    private YipService yipService;
    @RequestMapping("/yip")
    @ResponseBody
    public YipFeerateInfo serachYip(){
        List<YipFeerateInfo> list =yipService.selectYipFee();
        return list.get(0);
    }
}
