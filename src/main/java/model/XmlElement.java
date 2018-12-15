package model;

public abstract class XmlElement {
    protected String data;
    protected XmlElement parent;
    protected String templateForToStringFunction;

    public XmlElement(String data, XmlElement parent, String templateForToStringFunction) {
        this.data = data;
        this.parent = parent;
        this.templateForToStringFunction = templateForToStringFunction;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public XmlElement getParent() {
        return parent;
    }

    public void setParent(XmlElement parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.format(templateForToStringFunction, data);
    }
}
