package model;

import java.util.List;
import java.util.stream.Collectors;

public class XmlTag extends XmlElement {
    public static final String LINE_TAG_REGEX = "<.+/>";
    public static final String TAG_WITH_BODY_REGEX = "<([\\w|\\d]+).*>[\\t|\\r|\\n]*.*[\\t|\\r|\\n]*</([\\w|\\d]+)>";
    private static final String NEW_LINE = "\n";
    private static final String NEW_LINE_WITH_TAB = "\n\t";
    private static final String WHITE_SPACE = " ";
    private List<XmlAttr> attrs;
    private List<XmlTag> innerTags;
    private boolean isCloseTag = false;

    public XmlTag(String data, XmlElement parent, String startTag, String endTag) {
        this.data = data;
        this.parent = parent;
        this.startTag = startTag;
        this.endTag = endTag;
    }

    public List<XmlAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<XmlAttr> attrs) {
        this.attrs = attrs;
    }

    public List<XmlTag> getInnerTags() {
        return innerTags;
    }

    public void setInnerTags(List<XmlTag> innerTags) {
        this.innerTags = innerTags;
    }

    @Override
    public String toString() {
        return startTag +
                attrs.stream().map(String::valueOf).collect(Collectors.joining(WHITE_SPACE)) + ">" + data +
                innerTags.stream().map(String::valueOf).collect(Collectors.joining(NEW_LINE_WITH_TAB)) + NEW_LINE +
                endTag;
    }
}
