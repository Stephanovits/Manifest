package com.manifest.Manifest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @GetMapping(value = "/")
    public String helloWorld() {
        return "Welcome to Manifest";
    }

}
