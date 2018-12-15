package parser;

import model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ElementFactory {
    XmlDeclarationFactory(null, "<\\?xml version=\"1.0\" \\?>") {
        private static final int NUMBER_OF_REGEX = 0;
        private static final String UNSUPPORTABLE_ERROR_ERROR_MESSAGE =
                "This operation is not supported in XmlDeclarationFactory!";

        @Override
        public Object getInstance(String content, XmlElement parent) {
            throw new UnsupportedOperationException(UNSUPPORTABLE_ERROR_ERROR_MESSAGE);
        }

        @Override
        public String deleteElementFromContent(String content) {
            return deleteElementFromContentSimpleVersion(regexs[NUMBER_OF_REGEX], content, NUMBER_OF_REGEX);
        }
    },
    XmlCommentFactory("This is not XmlComment!",
            "^(<!--([\\w|\\s|\\n|\\t|\\r|(|)|,|<|>|\\&|'|\"|\\*|%|;|\\.|\\-|+|=|\\\\|\\^|?|:|$|#|№|@|!]*)-->)") {

        private static final int NUMBER_OF_FULL_REGEX_GROUP = 1;
        private static final int NUMBER_OF_CONTENT_GROUP = 2;
        private static final int NUMBER_OF_REGEX = 0;

        @Override
        public XmlComment getInstance(String content, XmlElement parent) {
            if (validation(regexs[NUMBER_OF_REGEX], content)) {
                return new XmlComment(getGroup(regexs[NUMBER_OF_REGEX], content, NUMBER_OF_CONTENT_GROUP), parent);
            } else {
                throw new IllegalArgumentException(errorMessage);
            }
        }

        @Override
        public String deleteElementFromContent(String content) {
            return deleteElementFromContentSimpleVersion(regexs[NUMBER_OF_REGEX], content, NUMBER_OF_FULL_REGEX_GROUP);
        }
    },
    XmlCdataFactory("This is not CData!",
            "^(<!\\[CDATA\\[([\\w|\\s|\\n|\\t|\\r|(|)|,|<|>|\\&|'|\"|\\*|%|;|\\.|\\-|+|=|\\\\|\\^|?|:|$|#|№|@|!]*)\\]\\]>)") {

        private static final int NUMBER_OF_FULL_REGEX_GROUP = 1;
        private static final int NUMBER_OF_CONTENT_GROUP = 2;
        private static final int NUMBER_OF_REGEX = 0;

        @Override
        public XmlCdata getInstance(String content, XmlElement parent) {
            if (validation(regexs[NUMBER_OF_REGEX], content)) {
                return new XmlCdata(getGroup(regexs[NUMBER_OF_REGEX], content, NUMBER_OF_CONTENT_GROUP), parent);
            } else {
                throw new IllegalArgumentException(errorMessage);
            }
        }

        @Override
        public String deleteElementFromContent(String content) {
            return deleteElementFromContentSimpleVersion(regexs[NUMBER_OF_REGEX], content, NUMBER_OF_FULL_REGEX_GROUP);
        }
     },
    XmlAttrFactory(null,
            "^<\\s*[-|\\w|\\d|_]+\\s*([-|\\w|\\d|_]+=\"[-|\\w|\\d|\\s|_]+\"(\\s*[-|\\w|\\d|_]+=\"[-|\\w|\\d|\\s|_]+\")*)\\s*/?>",
            "([-|\\w|\\d|_]+)=\"([-|\\w|\\d|\\s|_]+)\"") {

        private static final int NUMBER_OF_GENERAL_REGEX = 0;
        private static final int NUMBER_OF_SPECIFIC_REGEX = 1;
        private static final int NUMBER_OF_GROUP_FOR_GENERAL_REGEX = 1;
        private static final String UNSUPPORTABLE_ERROR_ERROR_MESSAGE = "This operation is not supported in XmlAttr!";
        private static final int COUNT_OF_ATTR_INFO_FOR_ONE_GROUP = 3;
        private static final int NUMBER_OF_ATTR_NAME = 1;
        private static final int NUMBER_OF_ATTR_VALUE = 2;

        @Override
        public List<XmlAttr> getInstance(String content, XmlElement parent) {
            List<XmlAttr> attrList = new ArrayList<>();

            if (validation(regexs[NUMBER_OF_GENERAL_REGEX], content)) {
                String attrs = Objects.requireNonNull(
                        getGroup(regexs[NUMBER_OF_GENERAL_REGEX], content, NUMBER_OF_GROUP_FOR_GENERAL_REGEX));
                List<String> infoFromAttrs = getAllGroups(regexs[NUMBER_OF_SPECIFIC_REGEX], attrs);

                for (int index = 0; index < infoFromAttrs.size(); index += 3) {
                    List<String> tempList = infoFromAttrs.subList(index, Math.min(infoFromAttrs.size(),
                            index + COUNT_OF_ATTR_INFO_FOR_ONE_GROUP));

                    attrList.add(new XmlAttr(tempList.get(NUMBER_OF_ATTR_NAME), tempList.get(NUMBER_OF_ATTR_VALUE),
                            parent));
                }
            }

            return attrList;
        }

        @Override
        public String deleteElementFromContent(String content) {
            throw new UnsupportedOperationException(UNSUPPORTABLE_ERROR_ERROR_MESSAGE);
        }
    },
    XmlTagFactory("This is not XmlTag!", "^(<\\s*([-|\\w|\\d|_]+).*/\\s*>)", "^(<\\s*([-|\\w|\\d|_]+).*>)",
            "^(<\\s*/\\s*([-|\\w|\\d+|_|]+)\\s*>)",
            "^([\\w|\\d|+|-|_|\\(|\\)|\\{|\\}|\\[|\\]|;|\"|:|?|!|@|#|$|%|\\^|\\&|\\*|\\t|\\r|\\n|'|\\s]*)[<\\s*/\\s*[-|\\w|\\d+|_|]+\\s*>|<\\s*([-|\\w|\\d|_]+).*/\\s*>|<\\s*([-|\\w|\\d|_]+).*>]") {

        private static final int NUMBER_OF_LINE_REGEX = 0;
        private static final int NUMBER_OF_MULTI_LINE_REGEX = 1;
        private static final int NUMBER_OF_CLOSING_REGEX = 2;
        private static final int NUMBER_OF_CONTENT_REGEX = 3;
        private static final int NUMBER_OF_FULL_REGEX_GROUP = 1;
        private static final int NUMBER_OF_NAME_GROUP = 2;

        private XmlTag generateXmlTagObject(String content, XmlTag parent) {
            String tagName = getGroup(regexs[NUMBER_OF_MULTI_LINE_REGEX], content, NUMBER_OF_NAME_GROUP);
            XmlTag tag = new XmlTag(tagName, "", parent);
            //noinspection unchecked
            List<XmlAttr> attrs = (List<XmlAttr>) XmlAttrFactory.getInstance(content, tag);

            tag.setAttrs(attrs);
            if (parent != null) {
                parent.setInnerElement(tag);
            }

            return tag;
        }

        @Override
        public Object getInstance(String content, XmlElement parent) {
            if (validation(regexs[NUMBER_OF_LINE_REGEX], content)) {
                XmlTag tag = generateXmlTagObject(content, (XmlTag) parent);
                tag.setAttrs((List<XmlAttr>) XmlAttrFactory.getInstance(content, tag));
                tag.setClosedTag(true);

                return parent == null
                    ? tag
                    : parent;
            } else if (validation(regexs[NUMBER_OF_MULTI_LINE_REGEX], content)) {
                return generateXmlTagObject(content, (XmlTag) parent);
            } else if (validation(regexs[NUMBER_OF_CLOSING_REGEX], content)) {
                String tagName = getGroup(regexs[NUMBER_OF_CLOSING_REGEX], content, NUMBER_OF_NAME_GROUP);

                if (((ComplexXmlElement)parent).getName().equals(tagName)) {
                    ((XmlTag)parent).setClosedTag(true);
                    return parent;
                } else {
                    throw  new IllegalArgumentException("Closing tag does not equal parent tag!");
                }
            } else if (validation(regexs[NUMBER_OF_CONTENT_REGEX], content)){
                parent.setData("\n\t" + Objects.requireNonNull(getGroup(regexs[NUMBER_OF_CONTENT_REGEX], content,
                        NUMBER_OF_FULL_REGEX_GROUP)).trim());

                return parent;
            } else {
                throw new IllegalArgumentException(errorMessage);
            }
        }

        @Override
        public String deleteElementFromContent(String content) {
            String deletingElement = "";

            for (String regex : regexs) {
                if (validation(regex, content)) {
                    deletingElement = getGroup(regex, content, NUMBER_OF_FULL_REGEX_GROUP);
                    break;
                }
            }

            return content.substring(Objects.requireNonNull(deletingElement).length()).trim();
        }
    };

    protected String errorMessage;
    protected String[] regexs;

    ElementFactory(String errorMessage, String...regexs) {
        this.errorMessage = errorMessage;
        this.regexs = regexs;
    }

    public abstract Object getInstance(String content, XmlElement parent);
    public abstract String deleteElementFromContent(String content);

    protected String deleteElementFromContentSimpleVersion(String regex, String content, int numberOfGroup) {
        String deletingElement;

        if (validation(regex, content)) {
            deletingElement = getGroup(regex, content, numberOfGroup);
        } else {
            throw new IllegalArgumentException(errorMessage);
        }

        return content.substring(Objects.requireNonNull(deletingElement).length()).trim();
    }

    public boolean isFirstElement(String content) {
        AtomicBoolean result = new AtomicBoolean(false);

        Arrays.stream(regexs).forEach(regex -> {
            if (!result.get()) {
                result.set(validation(regex, content.trim()));
            }
        });

        return result.get();
    }

    private static boolean validation(String regex, String data) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }

    private static String getGroup(String regex, String content, int indexOfGroup) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(indexOfGroup);
        }

        return null;
    }

    private static List<String> getAllGroups(String regex, String content) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            for (int index = 0; index <= matcher.groupCount(); index++) {
                result.add(matcher.group(index));
            }
        }

        return result;
    }
}
