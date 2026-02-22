package com.finance.portfolio.manager.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("api/v1/ping")
    public String checkConnection() {
        return "Ping Successful";
    }
}
