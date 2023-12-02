package com.spomprt.blinkify.service;

import com.spomprt.blinkify.controller.dto.ShortUrlCreateRequest;
import com.spomprt.blinkify.controller.dto.ShortUrlCreatedResponse;
import com.spomprt.blinkify.domain.UrlLink;
import com.spomprt.blinkify.repository.UrlLinkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private final UrlLinkRepository urlLinkRepository;

    public UrlShortenerService(UrlLinkRepository urlLinkRepository) {
        this.urlLinkRepository = urlLinkRepository;
    }


    public ShortUrlCreatedResponse create(ShortUrlCreateRequest request) {
        UrlLink urlLink = UrlLink.createNew(request.getOriginalUrl());

        urlLinkRepository.save(urlLink);

        ShortUrlCreatedResponse response = new ShortUrlCreatedResponse();
        response.setShortUrl(urlLink.shortened());
        return response;
    }


    public String getOriginal(String shortUrl) {
        UrlLink urlLink = urlLinkRepository.findById(shortUrl)
                .orElseThrow(EntityNotFoundException::new);
        return urlLink.original();
    }
}
