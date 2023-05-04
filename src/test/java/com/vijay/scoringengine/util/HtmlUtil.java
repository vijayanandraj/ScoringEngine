package com.vijay.scoringengine.util;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class HtmlUtil {

    private static final String REGEX_PATTERN = "xlink:href\\s*=\\s*\"([^\"]*background\\.png)\"";
    private static final Pattern PATTERN = Pattern.compile(REGEX_PATTERN);

    public static String replaceBackgroundImage(String htmlString, String newUrl) {
        String replacement = "xlink:href=\"" + newUrl + "\"";
        Matcher matcher = PATTERN.matcher(htmlString);
        return matcher.replaceAll(replacement);
    }

}
