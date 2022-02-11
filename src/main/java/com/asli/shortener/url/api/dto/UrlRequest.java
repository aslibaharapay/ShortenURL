package com.asli.shortener.url.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ToString
@Data
@ApiModel(description = "Request object for POST method")
public class UrlRequest {

    @ApiModelProperty(required = true, notes = "Url to convert to short")
    @NotNull
    @Pattern(message = "Unable to shorten that link. It is not a valid url.", regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String url;

    private String requestIp;

}
