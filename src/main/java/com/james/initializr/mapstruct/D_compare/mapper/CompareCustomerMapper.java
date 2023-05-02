/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.james.initializr.mapstruct.D_compare.mapper;

import com.james.initializr.mapstruct.D_compare.dto.Customer;
import com.james.initializr.mapstruct.D_compare.dto.CustomerDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = CompareCustomerMapper.class)
public interface CompareCustomerMapper {

    CompareCustomerMapper MAPPER = Mappers.getMapper( CompareCustomerMapper.class );

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "customerName")
    @Mapping(target = "item.name", source = "item")
    Customer toCustomer(CustomerDto customerDto);

    @InheritInverseConfiguration
    CustomerDto fromCustomer(Customer customer);
}
