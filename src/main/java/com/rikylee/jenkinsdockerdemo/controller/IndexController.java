package com.rikylee.jenkinsdockerdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping(value = "/")
    public String hello(Model model) {
        model.addAttribute("name", "Riky");
        model.addAttribute("age", "28");
        logger.info("get user {} info", "Riky");
        return "index";
    }

    @GetMapping(value = "/ex")
    public String ex(Model model) {

        model.addAttribute("name", "Riky");

        try {

            String age = "28-";
            model.addAttribute("age", Long.parseLong(age));

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            model.addAttribute("age", "18");
        }
        return "index";
    }


}
