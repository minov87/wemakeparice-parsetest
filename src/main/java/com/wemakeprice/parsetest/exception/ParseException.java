package com.wemakeprice.parsetest.exception;

import com.wemakeprice.parsetest.model.response.RESPONSE_STATUS;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ParseException extends BaseException {
    private static final long serialVersionUID = -3731142330909050874L;

    public ParseException(RESPONSE_STATUS responseStatus) {
        super(responseStatus.getMessage(), responseStatus.getCode());
    }

    public ParseException(String message, int errorCode) {
        super(message, errorCode);
    }

    public static final ParseException REGEX_TYPE_NOT_EXIST = new ParseException(
            RESPONSE_STATUS.REGEX_TYPE_NOT_EXIST.getMessage(),
            RESPONSE_STATUS.REGEX_TYPE_NOT_EXIST.getCode()
    );
    public static final ParseException URL_TYPE_ERROR = new ParseException(
            RESPONSE_STATUS.URL_TYPE_ERROR.getMessage(),
            RESPONSE_STATUS.URL_TYPE_ERROR.getCode()
    );
    public static final ParseException URL_PARSE_ERROR = new ParseException(
            RESPONSE_STATUS.URL_PARSE_ERROR.getMessage(),
            RESPONSE_STATUS.URL_PARSE_ERROR.getCode()
    );
}
