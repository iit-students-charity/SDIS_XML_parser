package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static Matcher validation(String regex, String data) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.find()
                ? matcher
                : null;
    }

    public static String getGroup(Matcher matcher, int index) {
        return matcher.group(index);
    }

    public static int getCountOfGroups(Matcher matcher) {
        return matcher.groupCount();
    }
}
