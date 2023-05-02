/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.james.initializr.mapstruct.A_simple.mapper;


import com.james.initializr.mapstruct.A_simple.dto.OrderItem;
import com.james.initializr.mapstruct.A_simple.dto.OrderItemDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OrderItemMapper {

    OrderItemMapper MAPPER = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "name", source = "name")
    OrderItem toOrder(OrderItemDto orderItemDto);

    @InheritInverseConfiguration
    OrderItemDto fromOrder(OrderItem orderItem);
}
