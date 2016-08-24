package by.shelude.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dkanarsky on 24.08.2016.
 */
public class StringHandler {

    public static String getParameterValue(String targetURL, String parameter) {
        String regExp = parameter + "=\\w+\\W";
        String fullParameter = extractString(targetURL, regExp);
        String valRegExp = "=\\w+";
        String value = extractString(fullParameter, valRegExp).substring(1);
        return value;
    }

    public static String extractString(String targetString, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(targetString);
        matcher.find();
        String result = matcher.group();
        return result;
    }

    public static List<String> extractStrings(String targetString, String regExp) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(targetString);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

}
