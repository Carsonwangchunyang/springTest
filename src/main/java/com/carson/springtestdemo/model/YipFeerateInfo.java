package com.carson.springtestdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author carson
 */
@Table(name = "YIP_FEERATE_INFO")
@Getter
@Setter
@ToString
public class YipFeerateInfo implements Serializable {

    private static final long serialVersionUID = -8427881437560886784L;

    @Column(name = "PRD_CODE")
    private String prdCode;

    @Column(name = "TRANS_CODE")
    private String transCode;

    @Column(name = "MIN_AMT")
    private String minAmt;

    @Column(name = "MAX_AMT")
    private String maxAmt;

    @Column(name = "RATE")
    private String rate;

    @Column(name = "MIN_FEE")
    private String minFee;

    @Column(name = "MAX_FEE")
    private String maxFee;

    @Column(name = "MIN_DAYS")
    private String minDays;

    @Column(name = "MAX_DAYS")
    private String maxDays;

    @Column(name = "REGULAR_FEE")
    private String regularFee;

    @Column(name = "FEE_RANGE")
    private String feeRange;

    @Column(name = "UPDATE_TIME")
    private String updateTime;
}