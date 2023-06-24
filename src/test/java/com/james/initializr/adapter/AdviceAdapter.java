package com.james.initializr.adapter;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AdviceAdapter extends RequestBodyAdviceAdapter {
	public static void main(String[] args) {
	}

	abstract void validate(Object input);

	@Override
	public boolean supports(MethodParameter methodParameter, @Nullable Type targetType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
		return Objects.nonNull(methodParameter.getMethodAnnotation(RequestValidated.class));
	}

	@Override
	@Nonnull
	public Object afterBodyRead(@Nonnull Object input, @Nullable HttpInputMessage inputMessage, MethodParameter parameter,
								@Nullable Type targetType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
		// 方法参数上加了注解, 直接解析并返回
		if (Objects.nonNull(parameter.getParameterAnnotation(JsonValidated.class))) {
			validate(input);
			return input;
		}

		// 方法参数内部的field上加了注解, 依次解析
		List<Field> annotatedFields = Arrays.stream(input.getClass().getDeclaredFields())
				.filter(field -> Objects.nonNull(field.getAnnotation(JsonValidated.class)))
				.filter(field -> Objects.equals(JSONObject.class, field.getType()))
				.collect(Collectors.toList());

		annotatedFields.forEach(field -> {
			field.setAccessible(true);
			try {
				Object needValidateJson = field.get(input);
				validate(needValidateJson);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});

		return input;
	}
}