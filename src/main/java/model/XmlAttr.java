package model;

public class XmlAttr extends ComplexXmlElement {
    public XmlAttr(String name, String data, XmlElement parent) {
        super(data, parent, "%s=\"%s\"", name);
    }

    @Override
    public String toString() {
        return String.format(templateForToStringFunction, name, data);
    }
}