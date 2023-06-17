package com.james.initializr.annotation;

import com.alibaba.fastjson2.JSONObject;
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

@ControllerAdvice
public class CustomResponseBodyAdviceAdapter extends RequestBodyAdviceAdapter {

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
    public Object afterBodyRead(@Nonnull Object body, @Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        // 校验注解放到方法参数上
        if (parameterAnnotated(parameter)) {
            handleFormat(body);
        }

        // 校验注解放到了字段上
        if (fieldAnnotated(targetType)) {
            handleFieldsFormat(body);
        }

        return body;
    }

    private void handleFormat(Object body) {
        // 暂时不处理 JSONObject 类型之外的数据结构
        if (! (body instanceof JSONObject)) {
            // TODO 抛异常
        }

        // TODO 获取模板id

        // TODO 查询目标id

        // TODO 调用校验

        // TODO 后置处理

    }

    private void handleFieldsFormat(Object body) {
        List<Field> annotatedFields = Arrays.stream(getDeclaredFields(body))
                .filter(field -> Objects.nonNull(field.getAnnotation(JsonSchemaValidated.class)))
                .filter(field -> Objects.equals(JSONObject.class, field.getType()))
                .collect(Collectors.toList());

        annotatedFields.forEach(field -> {
            field.setAccessible(true);
            try {
                Object o = field.get(body);
                handleFormat(o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static boolean fieldAnnotated(Type targetType) {
        return Arrays.stream(getDeclaredFields(targetType))
                .filter(field -> Objects.nonNull(field.getAnnotation(JsonSchemaValidated.class)))
                .anyMatch(field -> Objects.equals(JSONObject.class, field.getType()));
    }

    private static boolean parameterAnnotated(MethodParameter parameter) {
        return Objects.nonNull(parameter.getParameterAnnotation(JsonSchemaValidated.class));
    }

    private static Field[] getDeclaredFields(Object object) {
        return object.getClass().getDeclaredFields();
    }

    @Override
    public Object handleEmptyBody(Object body, @Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}