package com.example.olginregister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TestController {
    

    @GetMapping("/signin")
    public String signin() {
        return "login.html";
    }
    
}
