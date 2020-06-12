package com.wemakeprice.parsetest.model.parse;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;

@Data
public class ParseParams {
    @URL
    private String url;
    private String type;
    @Min(value = 1)
    private int divide;
}
