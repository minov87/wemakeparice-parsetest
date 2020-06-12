package com.wemakeprice.parsetest.util;

import com.wemakeprice.parsetest.exception.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class ParseUtil {
    private ParseUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * URL 파싱
     *
     * @param url 파싱할 URL
     * @return 파싱된 데이터 반환
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
     * String 을 Stream 으로 분할
     *
     * @param text 가공할 텍스트
     * @return Steam으로 쪼개진 데이터 반환
     */
    public static Stream<String> getSplitStream(String text) {
        return Pattern.compile("").splitAsStream(text).sorted();
    }

    /**
     * 정규식에 따른 데이터 가공
     *
     * @param text 가공할 텍스트
     * @param regexType 정규식 유형 (0: 공백 제거, 1: 태그 제거, 2: 영문자와 숫자를 제외하고 제거, 3: 숫자 제거, 4: 숫자를 제외하고 제거 )
     * @return 정규식 처리된 데이터 반환
     * @throws Exception
     */
    public static String replaceParams(String text, int regexType) throws ParseException {
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