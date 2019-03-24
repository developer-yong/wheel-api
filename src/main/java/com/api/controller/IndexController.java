package com.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coderyong
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "启动成功！";
    }
}
