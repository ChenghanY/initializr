package com.james.initializr.annotation;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
@Slf4j
public class JsonSchemaValidateRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        Executable executable = methodParameter.getExecutable();
        Annotation[] declaredAnnotations = executable.getDeclaredAnnotations();
        if (ArrayUtils.isEmpty(declaredAnnotations)) {
            return false;
        }
        // 方法参数上存在 RequestWithJsonSchema 则要校验
        return Arrays.stream(declaredAnnotations).anyMatch(annotation -> annotation instanceof RequestWithJsonSchema);
    }

    @Override
    @Nonnull
    public Object afterBodyRead(@Nonnull Object input, @Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        // 校验注解放到方法参数上
        if (annotated(parameter)) {
            handleMethodParam(input);
            return input;
        }
        // 注解在input的fields上
        if (annotated(targetType)) {
            handleFields(input);
            return input;
        }
        log.error("@RequestWithJsonSchema should used in combination with @JsonSchemaValidated");
        return input;
    }

    /**
     * 核心解析方法
     *
     * @param needValidateJson 需要校验的json
     */
    private void handleMethodParam(Object needValidateJson) {
        // 暂时不处理 JSONObject 类型之外的数据结构
        if (! (needValidateJson instanceof JSONObject)) {
            // TODO 抛异常
        }

        // TODO 获取 jsonSchemaId

        // TODO 查询 schema.json

        // TODO 使用 github 框架 schema.json 校验 needValidateJson

        // TODO 读取自定义的校验规则 List<Validator>

        // TODO 循环 List<Validator> 任意一个失败则中止

        // TODO 每个 Validator 含有多个format策略

        // TODO 为 format 策略提供一对一的实现

    }

    private void handleFields(Object input) {
        // 反射获取有注解标识的field
        List<Field> annotatedFields = canHandleFieldStream(input).collect(Collectors.toList());

        // 所有 field 都要处理
        annotatedFields.forEach(field -> {
            handleField(field, input);
        });
    }

    /**
     * input 中能被处理的 field
     *
     * @param input input
     * @return Field
     */
    private static Stream<Field> canHandleFieldStream(Object input) {
        return Arrays.stream(input.getClass().getDeclaredFields())
                .filter(field -> Objects.nonNull(field.getAnnotation(JsonSchemaValidated.class)))
                .filter(field -> Objects.equals(JSONObject.class, field.getType()));
    }

    private void handleField(Field field, Object input) {
        field.setAccessible(true);
        try {
            Object needValidateJson = field.get(input);
            handleMethodParam(needValidateJson);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean annotated(Type input) {
        // 反射确认 input 中是否有被注解标识的 field
        return canHandleFieldStream(input).findAny().isPresent();
    }

    private static boolean annotated(MethodParameter parameter) {
        // 使用Spring MVC 的能确认 input 是否被注解标识
        return Objects.nonNull(parameter.getParameterAnnotation(JsonSchemaValidated.class));
    }

    @Override
    public Object handleEmptyBody(Object body, @Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}