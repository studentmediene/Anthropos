package no.smint.anthropos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by adrianh on 22.02.15.
 */
@Controller
@RequestMapping("/")
public class DefaultController {
    @RequestMapping
    public String defaultReturn() {
        return "index";
    }
}
