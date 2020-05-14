package com.wemakeprice.parsetest.util;

import com.wemakeprice.parsetest.exception.ParseException;
import com.wemakeprice.parsetest.model.parse.ParseData;
import com.wemakeprice.parsetest.model.parse.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public final class ParseUtil {
    /**
     * URL 파싱
     *
     * @param url 파싱할 URL
     * @return
     * @throws IOException
     */
    public static String parseUrl(String url) throws Exception {
        try {
            Connection.Response response = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .timeout(60000) // ReadTimeoutException 방지를 위해 60초 제한
                    .execute();
            Document document = response.parse();

            return document.html();
        } catch (IllegalArgumentException | UnknownHostException e){
            throw ParseException.URL_TYPE_ERROR;
        } catch (Exception e) {
            throw ParseException.URL_PARSE_ERROR;
        }
    }

    /**
     * 영문자와 숫자 조합 문자열 생성
     *
     * @param sr 파싱된 데이터 vo
     * @param divide
     * @return
     */
    public static ReturnData getCombineText(ParseData sr, int divide) {
        StringBuffer sb = new StringBuffer();

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
     * @return
     * @throws Exception
     */
    public static ParseData getExceptHtml(String text) throws Exception {
        // 태그, 영문자와 숫자를 제외한 값, 공백 제거
        String convertedTxt = replaceParams(text, 1);

        return getStringResult(convertedTxt);
    }

    /**
     * 전체 택스트를 기준으로 StringResult 반환
     *
     * @param text 가공할 텍스트
     * @return
     * @throws Exception
     */
    public static ParseData getAllText(String text) throws Exception {
        // 영문자와 숫자를 제외한 값, 공백 제거
        String convertedTxt = replaceParams(text, 2);

        return getStringResult(convertedTxt);
    }

    /**
     * 몫, 나머지 계산 후 최종 결과값 반환
     *
     * @param text 가공할 텍스트
     * @param divide 묶음 단위
     * @return
     */
    private static ReturnData getParseResult(String text, int divide) {
        int textSize = text.length();
        String quotient = "", remainder = "";

        if(textSize % divide == 0){
            quotient = text;
        } else {
            int remainLength = (textSize % divide);
            quotient = text.substring(0, textSize-remainLength);
            remainder = text.substring(textSize-remainLength, textSize);
        }

        ReturnData returnData = ReturnData.builder()
                .quotient(quotient)
                .remainder(remainder)
                .build();

        return returnData;
    }

    /**
     * 영문자와, 숫자를 각기 분리하여 StringResult 로 반환
     *
     * @param text 가공할 텍스트
     * @return
     * @throws Exception
     */
    private static ParseData getStringResult(String text) throws Exception {
        // 숫자 제거
        Stream<String> sr = getSplitStream(replaceParams(text, 3));
        String alpha = sr.sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.joining());

        // 숫자를 제외한 값 제거
        sr = getSplitStream(replaceParams(text, 4));
        String number = sr.collect(Collectors.joining());

        ParseData parseData = ParseData.builder()
                .alpha(alpha)
                .number(number)
                .build();

        return parseData;
    }

    /**
     * String 을 Stream 으로 분할
     *
     * @param text 가공할 텍스트
     * @return
     */
    private static Stream<String> getSplitStream(String text) {
        return Pattern.compile("").splitAsStream(text).sorted();
    }

    /**
     * 정규식에 따른 데이터 가공
     *
     * @param text 가공할 텍스트
     * @param regexType 정규식 유형 (0: 공백 제거, 1: 태그 제거, 2: 영문자와 숫자를 제외하고 제거, 3: 숫자 제거, 4: 숫자를 제외하고 제거 )
     * @return
     * @throws Exception
     */
    private static String replaceParams(String text, int regexType) throws Exception {
        // white space, tag, except alpha and number, number, except number
        String[] regexArray = { "\\p{Space}+", "<.*?>", "[^\\p{Alnum}]+", "\\p{Digit}+", "[^\\p{Digit}]+" };

        if(regexType < 0 || (regexArray.length-1) < regexType){
            throw ParseException.REGEX_TYPE_NOT_EXIST;
        }

        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(regexArray[regexType]);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            matcher.appendReplacement(sb, "");
        }

        matcher.appendTail(sb);

        if(regexType == 1) return replaceParams(sb.toString(), 2);
        if(regexType == 2) return replaceParams(sb.toString(), 0);

        return sb.toString();
    }
}