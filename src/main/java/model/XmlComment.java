package model;

import java.util.regex.Matcher;
import static util.Utility.getGroup;
import static util.Utility.validation;

public class XmlComment extends XmlElement {
    public static final String REGEX =
            "<!--([\\w|\\s|\\n|\\t|\\r|(|)|,|<|>|\\&|'|\"|\\*|%|;|\\.|\\-|+|=|\\\\|\\^|?|:|$|#|№|@|!]*)-->";
    private static final int NUMBER_OF_GROUP = 1;
    private static final String ERROR_MESSAGE = "This is not comment!";

    public XmlComment(String data, XmlElement parent) {
        Matcher matcher = validation(REGEX, data);

        if (matcher != null) {
            this.data = getGroup(matcher, NUMBER_OF_GROUP);
            this.parent = parent;
            startTag = "<!--";
            endTag = "-->";
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
