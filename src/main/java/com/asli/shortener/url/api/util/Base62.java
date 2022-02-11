package com.asli.shortener.url.api.util;

import org.springframework.stereotype.Service;

@Service
public class Base62 {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int base = ALPHABET.length();

    public static String encodeToString(long buffer) {
        if (buffer == 0) {
            return String.valueOf(ALPHABET.charAt(0));
        }

        StringBuilder encodedString = new StringBuilder();

        while (buffer > 0) {
            encodedString.append(ALPHABET.charAt((int) (buffer % base)));
            buffer = buffer / base;
        }

        return encodedString.reverse().toString();
    }

    public static long decodeFromString(String input) {
        char[] characters = input.toCharArray();
        var length = characters.length;

        int decoded = 0, counter = 1;

        for (int i = 0; i < length; i++) {
            decoded += ALPHABET.indexOf(characters[i]) * Math.pow(base, length - counter);
            counter++;
        }
        return decoded;
    }
}
