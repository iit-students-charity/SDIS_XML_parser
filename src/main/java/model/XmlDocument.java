package model;

public class XmlDocument {
    private final static String XML_DECLARATION = "<?xml version=\"1.0\" ?>\n%s";
    private XmlTag root;

    public XmlTag getRoot() {
        return root;
    }

    public void setRoot(XmlTag root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return String.format(XML_DECLARATION, root.toString());
    }
}
