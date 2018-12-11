package model;

import java.util.regex.Matcher;
import static util.Utility.getGroup;
import static util.Utility.validation;

public class XmlCdata extends XmlElement {
    public static final String REGEX =
            "<!\\[CDATA\\[([\\w|\\s|\\n|\\t|\\r|(|)|,|<|>|\\&|'|\"|\\*|%|;|\\.|\\-|+|=|\\\\|\\^|?|:|$|#|№|@|!]*)\\]\\]>";
    private static final int NUMBER_OF_GROUP = 1;
    private static final String ERROR_MESSAGE = "This is not CDATA type!";

    public XmlCdata(String data, XmlElement parent) {
        Matcher matcher = validation(REGEX, data);

        if (matcher != null) {
            this.data = getGroup(matcher, NUMBER_OF_GROUP);
            this.parent = parent;
            startTag = "<![CDATA[";
            endTag = "]]>";
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
