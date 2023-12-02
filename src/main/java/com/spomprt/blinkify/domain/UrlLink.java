package com.spomprt.blinkify.domain;

import com.spomprt.blinkify.utils.UrlShortener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "url_links")
public class UrlLink {

    @Id
    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "original_url")
    private String originalUrl;

    protected UrlLink() {

    }

    public static UrlLink createNew(String originalUrl) {
        UrlLink urlLink = new UrlLink();
        urlLink.originalUrl = originalUrl;
        urlLink.shortUrl = UrlShortener.shortenUrl(originalUrl);
        return urlLink;
    }

    public String shortened() {
        return shortUrl;
    }

    public String original() {
        return originalUrl;
    }

}
