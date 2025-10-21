package com.cinehub.cinehub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testConnection() {
        return "✅ CineHub backend is running on port 8081!";
    }
}
