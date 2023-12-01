package com.spomprt.blinkify.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/blinkify/api/v1")
public class BlinkifyController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping
    public void redirect(
            HttpServletResponse response
    ) {
        try {
            response.sendRedirect("https://vk.com/feed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
