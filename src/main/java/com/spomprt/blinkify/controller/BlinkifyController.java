package com.spomprt.blinkify.controller;

import com.spomprt.blinkify.controller.dto.ShortUrlCreateRequest;
import com.spomprt.blinkify.controller.dto.ShortUrlCreatedResponse;
import com.spomprt.blinkify.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/blinkify/api/v1")
public class BlinkifyController {

    private static final Logger log = LoggerFactory.getLogger(BlinkifyController.class);
    private final UrlShortenerService urlShortenerService;

    public BlinkifyController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ShortUrlCreatedResponse createShortUrl(
            @RequestBody ShortUrlCreateRequest request
    ) {
        log.info("Create short url request");
        return urlShortenerService.create(request);
    }

    @GetMapping(value = "/{shortUrl}")
    public void redirect(
            HttpServletResponse response,
            @PathVariable("shortUrl") String shortUrl
    ) {
        log.debug("Get original url from {}", shortUrl);
        try {
            String original = urlShortenerService.getOriginal(shortUrl);
            response.sendRedirect(original);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
