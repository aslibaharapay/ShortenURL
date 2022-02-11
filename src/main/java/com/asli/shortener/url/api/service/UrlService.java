package com.asli.shortener.url.api.service;

import com.asli.shortener.url.api.dto.UrlRequest;
import com.asli.shortener.url.api.dto.UrlResponse;
import com.asli.shortener.url.api.entity.Url;
import com.asli.shortener.url.api.exception.ExpiredKeyException;
import com.asli.shortener.url.api.exception.KeyNotFoundException;
import com.asli.shortener.url.api.repository.UrlRepository;
import com.asli.shortener.url.api.util.Base62;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String createShortUrl(UrlRequest request) {
        Url url = new Url(request);
        Url savedUrl = urlRepository.save(url);
        return Base62.encodeToString(savedUrl.getId());
    }

    public String getLongUrl(String shortUrl) throws KeyNotFoundException, ExpiredKeyException {
        UrlResponse selectedURL = getSelectedURL(shortUrl);

        return selectedURL.getUrl();
    }

    public UrlResponse getSelectedURL(String shortUrl) throws KeyNotFoundException, ExpiredKeyException {
        long id = Base62.decodeFromString(shortUrl);
        Url selectedURL = findURL(shortUrl, id);

        if (selectedURL.getExpiresDate().isBefore(LocalDate.now())) {
            urlRepository.deleteById(selectedURL.getId());
            throw new ExpiredKeyException();
        }

        return new UrlResponse(selectedURL.getLongUrl(), selectedURL.getExpiresDate());
    }

    private Url findURL(String shortUrl, long id) throws KeyNotFoundException {
        return urlRepository.findById(id)
                .orElseThrow(() -> new KeyNotFoundException(shortUrl));
    }
}
