package com.james.initializr.mapstruct;

import com.james.initializr.mapstruct.A_simple.dto.Customer;
import com.james.initializr.mapstruct.A_simple.dto.CustomerDto;
import com.james.initializr.mapstruct.A_simple.dto.OrderItem;
import com.james.initializr.mapstruct.A_simple.dto.OrderItemDto;
import com.james.initializr.mapstruct.A_simple.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@SpringBootTest
class A_SimpleMappingTests {

	@Test
	public void testMapDtoToEntity() {

		CustomerDto customerDto = new CustomerDto();
		customerDto.id = 10L;
		customerDto.customerName = "Filip";

		OrderItemDto order1 = new OrderItemDto();
		order1.setName("Table");
		order1.setQuantity(2L);
		// list类型的也可以赋值
		customerDto.orders = new ArrayList<>( Collections.singleton( order1 ) );

		// dto -> entity
		Customer customer = CustomerMapper.MAPPER.toCustomer( customerDto );

		assertThat( customer.getId() ).isEqualTo( 10 );
		assertThat( customer.getName() ).isEqualTo( "Filip" );
		assertThat( customer.getOrderItems() )
				// 提取字段的值
				.extracting( "name", "quantity" )
				// 对值进行判断
				.containsExactly( tuple( "Table", 2L ) );
	}

	@Test
	public void testEntityDtoToDto() {

		Customer customer = new Customer();
		customer.setId( 10L );
		customer.setName( "Filip" );
		OrderItem order1 = new OrderItem();
		order1.setName( "Table" );
		order1.setQuantity( 2L );
		customer.setOrderItems( Collections.singleton( order1 ) );

		// entity -> dto
		CustomerDto customerDto = CustomerMapper.MAPPER.fromCustomer( customer );

		assertThat( customerDto.id ).isEqualTo( 10 );
		assertThat( customerDto.customerName ).isEqualTo( "Filip" );
		assertThat( customerDto.orders )
				.extracting( "name", "quantity" )
				.containsExactly( tuple( "Table", 2L ) );
	}

}
