package com.asli.shortener.url.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@ApiModel(description = "Request object for POST method")
public class UrlResponse extends BaseResponse {

    @ApiModelProperty(required = true, notes = "Short url convert to long")
    private String url;

    @ApiModelProperty(required = true, notes = "Short Url Expire Date")
    private LocalDate expiresDate;
}
