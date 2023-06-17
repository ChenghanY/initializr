package com.james.initializr.annotation;

import com.alibaba.fastjson2.JSONObject;

/**
 * controller方法标识
 */
public class InputDTO {

    @JsonSchemaValidated
    private JSONObject input;

    public JSONObject getInput() {
        return input;
    }

    public void setInput(JSONObject input) {
        this.input = input;
    }
}
