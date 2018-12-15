package model;

public class XmlCdata extends XmlElement {
    public XmlCdata(String data, XmlElement parent) {
        super(data, parent, "<![CDATA[%s]]>");
    }
}
