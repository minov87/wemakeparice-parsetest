package com.wemakeprice.parsetest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ViewController {
    @GetMapping("/")
    public String parse(Model model) {
        model.addAttribute("title", "Wemakeprice Parse Test");
        return "parse";
    }
}
