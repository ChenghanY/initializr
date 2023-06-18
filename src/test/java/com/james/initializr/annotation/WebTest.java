package com.james.initializr.annotation;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSONObject;
import com.james.initializr.easyexcel.UploadDAO;
import com.james.initializr.easyexcel.UploadData;
import com.james.initializr.easyexcel.UploadDataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 **/
@Controller
@Slf4j
public class WebTest {

    /**
     * WithJsonSchemaInput 标识 JsonSchema 参与的请求
     * 有 {@link RequestWithJsonSchema} 标识的方法参数
     * 要配合 {@link JsonSchemaValidated} 标识的入参
     * 该入参必须是 JSONObject 类型的。
     *</p>
     * 含义：
     * 	 {@link RequestWithJsonSchema} 表示会该方法的入参有JsonSchema参与
     * 	 {@link JsonSchemaValidated} 表示哪个实体需要校验
     * 	 {@link JsonSchemaValidated} 校验通过后，会把前端的input全存到该注解的JSONObject中
     */
    @PostMapping("withParam")
    @ResponseBody
    @RequestWithJsonSchema
    public JSONObject withParam(@RequestBody @JsonSchemaValidated InputDTO dto) {

        return new JSONObject();
    }

    @PostMapping("withField")
    @ResponseBody
    @RequestWithJsonSchema
    public String withField(@RequestBody InputDTO dto) {

        return "success";
    }

    @PostMapping("normal")
    @ResponseBody
    public String normal(@RequestBody InputDTO dto) {

        return "success";
    }

    @GetMapping("get")
    @ResponseBody
    @RequestWithJsonSchema
    public String get(InputDTO dto) {

        return "success";
    }
}
