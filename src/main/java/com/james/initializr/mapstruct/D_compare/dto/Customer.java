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
public class Customer {

    Long id;

    int age;

    String name;

    OrderItemDto item;

}
