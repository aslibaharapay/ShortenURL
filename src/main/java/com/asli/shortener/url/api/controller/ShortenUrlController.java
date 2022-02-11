package com.asli.shortener.url.api.controller;


import com.asli.shortener.url.api.dto.BaseResponse;
import com.asli.shortener.url.api.dto.UrlRequest;
import com.asli.shortener.url.api.dto.UrlResponse;
import com.asli.shortener.url.api.exception.ExpiredKeyException;
import com.asli.shortener.url.api.exception.KeyNotFoundException;
import com.asli.shortener.url.api.service.UrlService;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/shortenApi/v1")
public class ShortenUrlController {

    private final UrlService urlService;

    public ShortenUrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    @ApiOperation(value = "create short url from long url")
    @PostMapping("/create/shorturl")
    public ResponseEntity<BaseResponse> shortenURL(@Valid @RequestBody UrlRequest urlRequestData, HttpServletRequest servletRequest) throws URISyntaxException {
        urlRequestData.setRequestIp(servletRequest.getRemoteAddr());
        String key = urlService.createShortUrl(urlRequestData);
        return ResponseEntity.created(new URI("/api/v1/" + key))
                .body(new BaseResponse(key, BaseResponse.STATUS.SUCCESSFUL));

    }

    @ApiOperation(value = "Redirect", notes = "taking generated short Url, return corresponding long url")
    @GetMapping(value = "/{key}")
    @Cacheable(value = "longUrl", key = "#key")
    public ResponseEntity<Object> redirectUrl(@PathVariable String key) throws ExpiredKeyException, KeyNotFoundException {
        String longUrl = urlService.getLongUrl(key);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }

    @GetMapping("/status/{key}")
    public ResponseEntity<BaseResponse> getStatus(@PathVariable String key) throws KeyNotFoundException, ExpiredKeyException {
        UrlResponse dto = urlService.getSelectedURL(key);
        return ResponseEntity.ok().body(dto);
    }
}
