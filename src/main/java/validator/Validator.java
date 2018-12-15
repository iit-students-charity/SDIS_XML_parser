package validator;

import model.XmlDocument;

public class Validator {
    public static final String xmlDeclaration = "<?xml version=\"1.0\" ?>";

    public static boolean validateOnXmlDeclaration(String content) {
        return content.startsWith(xmlDeclaration);
    }

    public static boolean validateOnCorrectXmlDocument(XmlDocument document) {
        return document.getRoot().isClosedTag();
    }
}
