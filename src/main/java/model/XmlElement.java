package model;

public abstract class XmlElement {
    protected String data;
    protected XmlElement parent;
    protected String startTag;
    protected String endTag;

    public String getData() {
        return data;
    }

    public XmlElement getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return startTag + data + endTag;
    }
}
