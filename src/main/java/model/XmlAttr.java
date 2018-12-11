package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import static util.Utility.getGroup;
import static util.Utility.validation;

public class XmlAttr extends XmlElement {
    private static final String GENERAL_REGEX =
            "([\\w|\\d|_]+=\"[\\w|\\d|\\s|_]+\"+(\\s*[\\w|\\d|_]+=\"[\\w|\\d|\\s|_])*\")";
    private static final String SPECIFIC_REGEX = "([\\w|\\d]+=\")([\\w|\\d|\\s|_]+)\"";
    private static final int NUMBER_FOR_FIRST_SPECIFIC_GROUP = 1;
    private static final int NUMBER_FOR_SECOND_SPECIFIC_GROUP = 2;
    private static final String END_ELEM = "\"";

    static class XmlAttrFactory {
        public static List<XmlAttr> getInstance(String data, XmlElement parent) {
            List<XmlAttr> attrList = new ArrayList<>();
            Matcher matcher = validation(GENERAL_REGEX, data);

            if (matcher != null) {
                String[] attrs = getGroup(matcher, 1).split("\\s+");

                Arrays.asList(attrs).forEach(attr -> {
                    Matcher specificMatcher = validation(SPECIFIC_REGEX, attr);

                    if (specificMatcher != null) {
                        attrList.add(new XmlAttr(specificMatcher.group(NUMBER_FOR_SECOND_SPECIFIC_GROUP),
                                parent, specificMatcher.group(NUMBER_FOR_FIRST_SPECIFIC_GROUP), END_ELEM));
                    }
                });
            }

            return attrList;
        }
    }


    private XmlAttr(String data, XmlElement parent, String startTag, String endTag) {
        this.data = data;
        this.parent = parent;
        this.startTag = startTag;
        this.endTag = endTag;
    }
}