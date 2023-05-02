/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.james.initializr.mapstruct.D_compare.dto;

import lombok.Data;


/**
 * @author Filip Hrisafov
 */
@Data
public class CustomerDto {

    /**
     * 与 entitiy 的类型不同
     */
    private String id;

    /**
     * 包装类型为null, 接收类型会抛异常
     */
    private Integer age;
    private String customerName;

    private String item;
}
