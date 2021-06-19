package com.star.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MusicShowController {

    @GetMapping("/music")
    public String about() {
        return "music";
    }

}