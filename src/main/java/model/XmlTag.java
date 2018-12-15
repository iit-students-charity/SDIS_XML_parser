package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XmlTag extends ComplexXmlElement {
    private List<XmlAttr> attrs = new ArrayList<>();
    private List<XmlElement> innerElements = new ArrayList<>();
    private boolean isClosedTag = false;
    private static final String NEW_LINE_WITH_TAB = "\n\t";
    private static final String NEW_LINE = "\n";
    private final static String WHITE_SPACE = " ";

    public XmlTag(String name, String data, XmlElement parent) {
        super(data, parent, "<%s %s>%s%s\n</%s>", name);
    }

    public List<XmlAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<XmlAttr> attrs) {
        this.attrs = attrs;
    }

    public List<XmlElement> getInnerElements() {
        return innerElements;
    }

    public void setInnerElement(XmlElement innerElement) {
        innerElements.add(innerElement);
    }

    public void setClosedTag(boolean closeTag) {
        isClosedTag = closeTag;
    }

    public boolean isClosedTag() {
        return isClosedTag;
    }

    @Override
    public String toString() {
        return  String.format(templateForToStringFunction, name,
                attrs.stream().map(String::valueOf).collect(Collectors.joining(WHITE_SPACE)), data,
                innerElements.stream().map(element -> NEW_LINE_WITH_TAB + element.toString().replace(NEW_LINE, NEW_LINE_WITH_TAB)).collect(Collectors.joining(NEW_LINE)),
                name);
    }
}
