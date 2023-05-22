package com.james.initializr.easyexcel;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * web读写案例
 *
 * @author Jiaju Zhuang
 **/
@Controller
@Slf4j
public class WebTest {

    @Autowired
    private UploadDAO uploadDAO;

    /**
     * 多个Excel文件上传
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(@RequestParam("files") List<MultipartFile> files) throws IOException {

        return "success";
    }

    /**
     * 多个Excel文件上传, 且key不同，每个key对应一个文件
     */
    @PostMapping("upload4MultiKey")
    @ResponseBody
    public String upload4MultiKey(@RequestParam() Map<String, MultipartFile> files) throws IOException {

        return "success";
    }

    /**
     * 多个Excel文件上传, 且key不同，每个key对应一个文件
     */
    @PostMapping("upload4MultiKeyAndValue")
    @ResponseBody
    public String upload4MultiKeyAndValue(@RequestParam MultiValueMap<String, MultipartFile> files, @RequestParam("orderId") String orderId) throws IOException {
        for (Map.Entry<String, List<MultipartFile>> entry : files.entrySet()) {
            List<MultipartFile> fileList = entry.getValue();
            for (MultipartFile multipartFile : fileList) {
                log.info("key : {} filename : {}", entry.getKey(), multipartFile.getOriginalFilename());
                // TODO 可以根据 key 实现策略模式, 每个key还可以对应多个文件
            }
        }
        return "success";
    }

    /**
     * 多个Excel文件上传, 且key不同，每个key对应一个文件
     */
    @PostMapping("uploadAndOtherParams")
    @ResponseBody
    public String uploadAndOtherParams(@RequestParam() MultiValueMap<String, MultipartFile> files, @RequestParam("orderId") String orderId) throws IOException {

        return "success";
    }
}
