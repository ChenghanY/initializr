/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.james.initializr.mapstruct.C_difftype.mapper;

import com.james.initializr.mapstruct.C_difftype.dto.Customer;
import com.james.initializr.mapstruct.C_difftype.dto.CustomerDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = DiffTypeCustomerMapper.class)
public interface DiffTypeCustomerMapper {

    DiffTypeCustomerMapper MAPPER = Mappers.getMapper( DiffTypeCustomerMapper.class );

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "customerName")
    Customer toCustomer(CustomerDto customerDto);

    @InheritInverseConfiguration
    CustomerDto fromCustomer(Customer customer);
}
