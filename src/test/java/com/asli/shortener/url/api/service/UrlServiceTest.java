package com.asli.shortener.url.api.service;

import com.asli.shortener.url.api.dto.UrlRequest;
import com.asli.shortener.url.api.entity.Url;
import com.asli.shortener.url.api.exception.ExpiredKeyException;
import com.asli.shortener.url.api.exception.KeyNotFoundException;
import com.asli.shortener.url.api.repository.UrlRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @Mock
    UrlRepository mockUrlRepository;

    @InjectMocks
    UrlService urlService;

    @Test
    public void should_returnShortenUrl_whenSendLongUrl() {
        Url url = generateURL();

        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);

        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setUrl("https://www.upwork.com/ab/payments/reports/certificate-of-earnings.pdf");

        assertEquals("m", urlService.createShortUrl(urlRequest));

    }

    @Test
    public void should_throwsException_whenSendNonValidLongKey() {
        assertThrows(KeyNotFoundException.class,
                () -> urlService.getLongUrl("key_not_found"),
                "https://www.upwork.com/ab/payments/reports/certificate-of-earnings.pdf");
    }

    @Test
    public void should_throwException_when_shortUrlIsExpired() {
        Url url = generateURL();
        url.setExpiresDate(LocalDate.now().minusMonths(1));
        when(mockUrlRepository.findById(12L)).thenReturn(java.util.Optional.of(url));
        assertThrows(ExpiredKeyException.class,
                () -> urlService.getLongUrl("m"),
                "https://www.upwork.com/ab/payments/reports/certificate-of-earnings.pdf");

    }

    private Url generateURL() {
        Url url = new Url();
        url.setLongUrl("https://www.upwork.com/ab/payments/reports/certificate-of-earnings.pdf");
        url.setCreatedDate(LocalDate.now());
        url.setId(12L);
        url.setExpiresDate(LocalDate.now().plusMonths(1));
        return url;
    }

}
