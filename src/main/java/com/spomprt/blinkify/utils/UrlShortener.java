package com.spomprt.blinkify.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UrlShortener {
    public static String shortenUrl(String originalUrl) {
        try {
            // Create a SHA-256 message digest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Get the hash bytes for the original URL
            byte[] hashBytes = digest.digest(originalUrl.getBytes(StandardCharsets.UTF_8));

            // Use Base64 encoding to convert the hash bytes to a string
            String shortUrl = Base64.getUrlEncoder().encodeToString(hashBytes);

            // Remove any non-alphanumeric characters from the string
            shortUrl = shortUrl.replaceAll("[^a-zA-Z0-9]", "");

            // Take the first 8 characters as the short URL
            shortUrl = shortUrl.substring(0, 8);

            return shortUrl;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
