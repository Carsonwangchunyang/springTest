package com.carson.springtestdemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author carson
 */
@Getter
@Setter
@ToString
public class YipFeerateInfoDto implements Serializable {

    private static final long serialVersionUID = -8427881437560886784L;

    private String prdCode;

    private String transCode;

    private String minAmt;

    private String maxAmt;

    private String rate;

    private String minFee;

    private String maxFee;

    private String minDays;

    private String maxDays;

    private String regularFee;

    private String feeRange;

    private String updateTime;
}