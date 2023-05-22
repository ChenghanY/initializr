package com.james.initializr.easyexcel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class UploadData {
    private String string;
    private Date date;
    private BigDecimal decimal;
}
