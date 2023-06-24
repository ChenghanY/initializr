package com.james.initializr.adapter;


import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 **/
@Controller
@Slf4j
public class WebTest {

    @PostMapping("withParam")
    @ResponseBody
    @RequestValidated
    public JSONObject withParam(@JsonValidated @RequestBody InputDTO dto) {

        return new JSONObject();
    }
}
