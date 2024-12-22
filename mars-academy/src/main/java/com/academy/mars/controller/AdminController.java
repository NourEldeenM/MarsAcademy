package com.academy.mars.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController {

    @GetMapping("/index")
    public String index() {
        return "hello admin";
    }
}
