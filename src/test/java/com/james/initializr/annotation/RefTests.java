package com.james.initializr.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest(classes = Test.class)
@RunWith(value = SpringRunner.class)
@Slf4j
public class RefTests {

	@Test
	public void test_getDec() {
		Class<InputDTO> aClass = InputDTO.class;
		// 获取所有声明的字段
		Field[] fields = aClass.getDeclaredFields();

		// 收集被注解标识的对象（填充客户端提交通过的input）
		List<Field> annotatedFields = Arrays.stream(fields)
				.filter(field -> field.isAnnotationPresent(JsonSchemaValidated.class))
				.collect(Collectors.toList());

		annotatedFields.forEach(e -> log.info(e.getName()));
	}
}
