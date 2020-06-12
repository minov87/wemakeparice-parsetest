package com.wemakeprice.parsetest.controller;

import com.wemakeprice.parsetest.exception.BaseException;
import com.wemakeprice.parsetest.model.parse.ParseParams;
import com.wemakeprice.parsetest.model.parse.ReturnData;
import com.wemakeprice.parsetest.model.response.RESPONSE_STATUS;
import com.wemakeprice.parsetest.model.response.Response;
import com.wemakeprice.parsetest.service.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ParseController {

    @Autowired
    ParseService parseService;

    /**
     * URL 파싱 API
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/parse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response parseUrl(
            @RequestBody(required = true) ParseParams param) throws Exception {

        if(param.getUrl() == null || param.getType() == null || param.getDivide() <= 0) {
            throw BaseException.BAD_REQUEST;
        }

        ReturnData returnData = parseService.parseUrl(param.getUrl(), param.getType(), param.getDivide());
        return Response.builder().code(RESPONSE_STATUS.SUCCESS.getCode()).data(returnData).build();
    }
}
