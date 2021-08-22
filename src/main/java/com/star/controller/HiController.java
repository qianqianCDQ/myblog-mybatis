package com.star.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 qianqian
 * @创建时间 2021/8/22
 * @描述
 */
@RestController
public class HiController {

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/hi")
    public String sayHi() {
        return "Hi, my port is " + port;
    }
}
