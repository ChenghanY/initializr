package com.james.initializr.mapstruct;


import com.james.initializr.mapstruct.D_compare.dto.Customer;
import com.james.initializr.mapstruct.D_compare.dto.CustomerDto;
import com.james.initializr.mapstruct.D_compare.mapper.CompareCustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 对比 BeanUtils 的拷贝效果
 */
@SpringBootTest
@Slf4j
public class D_CompareToBeanUtilsTest {


	@Test
	public void BeanUtils1() {

		CustomerDto customerDto = new CustomerDto();
		customerDto.setId("10");

		// dto -> entity
		Customer customer = new Customer();

		try {
			BeanUtils.copyProperties(customer, customerDto);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		// 不同类型重可以转换
		assertThat( customer.getId() ).isEqualTo( 10 );
	}

	@Test
	public void BeanUtils2() {

		CustomerDto customerDto = new CustomerDto();
		// 包装 -> 基本类型; 包装类型必须非空，否则抛异常
		customerDto.setAge(1);
		// 存在同名的参数，但是类型不同。
		customerDto.setItem("item");

		// dto -> entity
		Customer customer = new Customer();

		try {
			BeanUtils.copyProperties(customer, customerDto);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		// 不同类型重可以转换
		assertThat( customer.getAge() ).isEqualTo( 1 );
	}

	@Test
	public void BeanUtils3() {

		CustomerDto customerDto = new CustomerDto();
		customerDto.setAge(1);

		// dto -> entity
		Customer customer = new Customer();

		try {
			BeanUtils.copyProperties(customer, customerDto);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		// 不同类型重可以转换
		assertThat( customer.getItem() ).isNull();
	}

	/**
	 * MapStruct 保持了最大的调用安全, BeanUtils 抛出异常的场景，这里都不会发生
	 */
	@Test
	public void MapStruct1() {

		CustomerDto customerDto = new CustomerDto();

		// dto -> entity
		Customer customer = CompareCustomerMapper.MAPPER.toCustomer(customerDto);

		// 不同类型重可以转换
		assertThat( customer.getItem().getName() ).isNull();
	}
}
