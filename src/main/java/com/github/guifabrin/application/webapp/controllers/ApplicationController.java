package com.github.guifabrin.application.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApplicationController {
    /**
     * This method returns template index.html from src/main/resourses/templates
     * when get main URL is called.
     *
     * @return String template
     * @see String
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * This method returns a partial template _{template}.html from
     * src/main/resourses/templates/ when get MAIN_URL/partial/{template} is called.
     *
     * @param String template
     * @return String template
     * @see String
     */
    @GetMapping("/partial/{template}")
    public String partial(@PathVariable String template) {
        return "_" + template;
    }
}
