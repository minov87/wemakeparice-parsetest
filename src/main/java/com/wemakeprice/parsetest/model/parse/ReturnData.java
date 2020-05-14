package com.wemakeprice.parsetest.model.parse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class ReturnData implements Serializable {
    private static final long serialVersionUID = -8251502326693891593L;

    private String quotient;
    private String remainder;
}
