package com.wemakeprice.parsetest.model.response;

import lombok.Getter;

@Getter
public enum RESPONSE_STATUS {
    // Basic
    SUCCESS(200, "Success"),
    UNKNOWN(201, "Unknown error"),
    NOT_FOUND(202, "Not found"),
    BAD_REQUEST(203, "Wrong Parameter"),
    INTERNAL_SERVER_ERROR(204, "Internal server error"),
    HTTP_IO_ERROR(205, "Internal server error"),

    // Parse
    REGEX_TYPE_NOT_EXIST(206, "Regex Type is not exist"),
    URL_PARSE_ERROR(207, "URL parse error. please try later"),
    URL_TYPE_ERROR(208, "This is not a normal URL");

    private int code;
    private String message;

    RESPONSE_STATUS(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
