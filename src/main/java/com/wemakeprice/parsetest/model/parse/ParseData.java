package com.wemakeprice.parsetest.model.parse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class ParseData implements Serializable {
    private static final long serialVersionUID = 8890008630054217260L;

    private String alpha;
    private String number;
}
