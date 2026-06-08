package com.paulhan.ticketing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Welcome to the Ticketing API");
    }
}
