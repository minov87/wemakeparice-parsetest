package com.wemakeprice.parsetest.exception;

import com.wemakeprice.parsetest.model.response.RESPONSE_STATUS;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends Exception {
    private static final long serialVersionUID = 4026561759023362711L;

    private int errorCode = 0;

    public BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(RESPONSE_STATUS responseStatus) {
        super(responseStatus.getMessage());
        this.errorCode = responseStatus.getCode();
    }

    public static final BaseException SUCCESS = new BaseException(
            RESPONSE_STATUS.SUCCESS.getMessage(),
            RESPONSE_STATUS.SUCCESS.getCode()
    );
    public static final BaseException UNKNOWN = new BaseException(
            RESPONSE_STATUS.UNKNOWN.getMessage(),
            RESPONSE_STATUS.UNKNOWN.getCode()
    );
    public static final BaseException NOT_FOUND = new BaseException(
            RESPONSE_STATUS.NOT_FOUND.getMessage(),
            RESPONSE_STATUS.NOT_FOUND.getCode()
    );
    public static final BaseException BAD_REQUEST = new BaseException(
            RESPONSE_STATUS.BAD_REQUEST.getMessage(),
            RESPONSE_STATUS.BAD_REQUEST.getCode()
    );
    public static final BaseException INTERNAL_SERVER_ERROR = new BaseException(
            RESPONSE_STATUS.INTERNAL_SERVER_ERROR.getMessage(),
            RESPONSE_STATUS.INTERNAL_SERVER_ERROR.getCode()
    );
    public static final BaseException HTTP_IO_ERROR = new BaseException(
            RESPONSE_STATUS.HTTP_IO_ERROR.getMessage(),
            RESPONSE_STATUS.HTTP_IO_ERROR.getCode()
    );

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
