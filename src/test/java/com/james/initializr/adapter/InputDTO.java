package com.james.initializr.adapter;

import com.alibaba.fastjson2.JSONObject;

/**
 * controller方法标识
 */
public class InputDTO {

    @JsonValidated
    private JSONObject input;

    public JSONObject getInput() {
        return input;
    }

    public void setInput(JSONObject input) {
        this.input = input;
    }
}
