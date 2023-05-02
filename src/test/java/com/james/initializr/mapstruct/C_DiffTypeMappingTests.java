package com.james.initializr.mapstruct;


import com.james.initializr.mapstruct.C_difftype.dto.Customer;
import com.james.initializr.mapstruct.C_difftype.dto.CustomerDto;
import com.james.initializr.mapstruct.C_difftype.mapper.DiffTypeCustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 实体的 id 类型一个是String, 一个是Long, 也可以平滑转换
 */
@SpringBootTest
@Slf4j
class C_DiffTypeMappingTests {


	@Test
	public void testMapDtoToEntity() {

		CustomerDto customerDto = new CustomerDto();
		customerDto.setId("10");
		customerDto.setCustomerName("Filip");

		// dto -> entity
		Customer customer = DiffTypeCustomerMapper.MAPPER.toCustomer( customerDto );

		assertThat( customer.getId() ).isEqualTo( 10 );
		assertThat( customer.getName() ).isEqualTo( "Filip" );
	}

	@Test
	public void testEntityDtoToDto() {

		Customer customer = new Customer();
		customer.setId( 10L );
		customer.setName( "Filip" );

		// entity -> dto
		CustomerDto customerDto = DiffTypeCustomerMapper.MAPPER.fromCustomer( customer );

		assertThat( customerDto.getId() ).isEqualTo( "10" );
		assertThat( customerDto.getCustomerName() ).isEqualTo( "Filip" );

	}

}
