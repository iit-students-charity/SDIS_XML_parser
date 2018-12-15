package model;

public abstract class ComplexXmlElement extends XmlElement {
    protected String name;

    public ComplexXmlElement(String data, XmlElement parent, String templateForToStringFunction, String name) {
        super(data, parent, templateForToStringFunction);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
