package com.james.initializr.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ValidateAdvice extends AdviceAdapter {

    @Override
    void validate(Object json) {
        // TODO 具体实现
    }
}