package com.wemakeprice.parsetest.service;

import com.wemakeprice.parsetest.exception.BaseException;
import com.wemakeprice.parsetest.model.parse.ParseData;
import com.wemakeprice.parsetest.model.parse.ReturnData;
import com.wemakeprice.parsetest.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ParseService {
    /**
     * 몫, 나머지 계산 후 최종 결과값 반환
     *
     * @param text 가공할 텍스트
     * @param divide 묶음 단위
     * @return 몫, 나머지 계산된 조합 데이터 반환
     */
    private static ReturnData getParseResult(String text, int divide) {
        int textSize = text.length();
        String quotient = "";
        String remainder = "";

        if(textSize % divide == 0){
            quotient = text;
        } else {
            int remainLength = (textSize % divide);
            quotient = text.substring(0, textSize-remainLength);
            remainder = text.substring(textSize-remainLength, textSize);
        }

        return ReturnData.builder()
                .quotient(quotient)
                .remainder(remainder)
                .build();
    }

    /**
     * 영문자와, 숫자를 각기 분리하여 StringResult 로 반환
     *
     * @param text 가공할 텍스트
     * @return 영문자와 숫자로 분리된 조합 데이터 반환
     * @throws Exception
     */
    private static ParseData getStringResult(String text) throws Exception {
        // 숫자 제거
        Stream<String> sr = ParseUtil.getSplitStream(ParseUtil.replaceParams(text, 3));
        String alpha = sr.sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.joining());

        // 숫자를 제외한 값 제거
        sr = ParseUtil.getSplitStream(ParseUtil.replaceParams(text, 4));
        String number = sr.collect(Collectors.joining());

        return ParseData.builder()
                .alpha(alpha)
                .number(number)
                .build();
    }

    /**
     * 영문자와 숫자 조합 문자열 생성
     *
     * @param sr 파싱된 데이터 vo
     * @param divide 묶음 단
     * @return위 조합된 데이터 반환
     */
    public static ReturnData getCombineText(ParseData sr, int divide) {
        StringBuilder sb = new StringBuilder();

        for(int i = 1; i <= sr.getAlpha().length(); i++) {
            if((i > 1) && (sr.getNumber().length() >= i)) {
                sb.append(sr.getNumber().charAt(i-2));
            }
            sb.append(sr.getAlpha().charAt(i-1));
        }

        return getParseResult(sb.toString(), divide);
    }

    /**
     * TAG 제거 처리된 텍스트를 기준으로 StringResult 반환
     *
     * @param text 가공할 텍스트
     * @return 가공된 데이터 반환
     * @throws Exception
     */
    public static ParseData getExceptHtml(String text) throws Exception {
        // 태그, 영문자와 숫자를 제외한 값, 공백 제거
        String convertedTxt = ParseUtil.replaceParams(text, 1);

        return getStringResult(convertedTxt);
    }

    /**
     * 전체 택스트를 기준으로 StringResult 반환
     *
     * @param text 가공할 텍스트
     * @return 가공된 데이터 반환
     * @throws Exception
     */
    public static ParseData getAllText(String text) throws Exception {
        // 영문자와 숫자를 제외한 값, 공백 제거
        String convertedTxt = ParseUtil.replaceParams(text, 2);

        return getStringResult(convertedTxt);
    }

    /**
     * URL 파싱
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
            return getCombineText(getAllText(parseResultTxt), divide);
        } else {
            return getCombineText(getExceptHtml(parseResultTxt), divide);
        }
    }
}
