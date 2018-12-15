package model;

public class XmlComment extends XmlElement {
    public XmlComment(String data, XmlElement parent) {
        super(data, parent, "<!--%s-->");
    }
}
