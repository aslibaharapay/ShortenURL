package com.asli.shortener.url.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    protected String message;

    protected STATUS code;//set default value

    public BaseResponse(String message, STATUS status) {
        this.message = message;
        this.code = status;
    }

    public BaseResponse() {
    }

    public enum STATUS {
        SUCCESSFUL,
        RESOURCE_NOT_FOUND,
        BAD_REQUEST,
        INSUFFICIENT_KEY,
        SERVER_TOO_BUSY
    }


}
