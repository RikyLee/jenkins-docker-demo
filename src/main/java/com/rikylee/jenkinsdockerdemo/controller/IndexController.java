package com.rikylee.jenkinsdockerdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String hello(Model model) {
        model.addAttribute("name", "Riky");
        model.addAttribute("age", "28");
        return "index";
    }
}
