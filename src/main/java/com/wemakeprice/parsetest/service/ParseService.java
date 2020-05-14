package com.wemakeprice.parsetest.service;

import com.wemakeprice.parsetest.exception.BaseException;
import com.wemakeprice.parsetest.model.parse.ReturnData;
import com.wemakeprice.parsetest.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParseService {
    /**
     *
     * @param url
     * @param type
     * @param divide
     * @return
     * @throws Exception
     */
    public ReturnData parseUrl(String url, String type, int divide) throws Exception {
        if (url == null) {
            throw BaseException.BAD_REQUEST;
        }

        // URL 파싱
        String parseResultTxt = ParseUtil.parseUrl(url);

        if (type.equalsIgnoreCase("TEXT")) {
            return ParseUtil.getCombineText(ParseUtil.getAllText(parseResultTxt), divide);
        } else {
            return ParseUtil.getCombineText(ParseUtil.getExceptHtml(parseResultTxt), divide);
        }
    }
}
