package com.asli.shortener.url.api.entity;

import com.asli.shortener.url.api.dto.UrlRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Entity
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String longUrl;

    private String requestIp;

    @NotNull
    private LocalDate createdDate;

    private LocalDate expiresDate;

    public Url(UrlRequest request) {
        this.setLongUrl(request.getUrl());
        this.setRequestIp(request.getRequestIp());
        this.setExpiresDate(LocalDate.now().plusMonths(1));
        this.setCreatedDate(LocalDate.now());
    }

    public Url() {
    }

}
