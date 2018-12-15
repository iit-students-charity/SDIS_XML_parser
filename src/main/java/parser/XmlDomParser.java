package parser;

import model.XmlDocument;
import model.XmlElement;
import model.XmlTag;
import reader.Reader;
import validator.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class XmlDomParser {
    private String content;
    private XmlDocument document;
    private static final String ERROR_MESSAGE_FOR_NOT_VALID_XML_DOCUMENT = "This not valid XML document!";
    private static final String ERROR_MESSAGE_FOR_FIRST_ELEMENT = "The first element is not tag!";
    private static final String ERROR_MESSAGE_FOR_NOT_CLOSED_TAG = "The XML document is not valid, " +
            "because it have elements which not closed!";

    public XmlDomParser() { }

    public XmlDomParser(String pathToFile) throws IOException, URISyntaxException {
        content = Reader.readFileByPath(pathToFile);
    }

    public XmlDomParser(InputStream inputStream) throws IOException {
        content = Reader.readFileByStream(inputStream);
    }

    public XmlDocument parse() {
        XmlTag parent = null;

        if (Validator.validateOnXmlDeclaration(content)) {
            document = new XmlDocument();
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NOT_VALID_XML_DOCUMENT);
        }

        while (!content.isEmpty()) {
            if (ElementFactory.XmlTagFactory.isFirstElement(content)) {
                if (parent != null) {
                    parent.setInnerElement((XmlElement) ElementFactory.XmlTagFactory.getInstance(content, parent));
                    parent = (XmlTag) parent.getInnerElements().get(parent.getInnerElements().size() - 1);
                } else {
                    document.setRoot((XmlTag) ElementFactory.XmlTagFactory.getInstance(content, null));
                    parent = document.getRoot();
                }

                content = ElementFactory.XmlTagFactory.deleteElementFromContent(content);
            } else if (ElementFactory.XmlCdataFactory.isFirstElement(content)
                    || ElementFactory.XmlCommentFactory.isFirstElement(content)) {
                if (parent == null) {
                    throw new IllegalArgumentException(ERROR_MESSAGE_FOR_FIRST_ELEMENT);
                }

                if (ElementFactory.XmlCdataFactory.isFirstElement(content)) {
                    parent.setInnerElement((XmlElement) ElementFactory.XmlCdataFactory.getInstance(content, parent));
                    content = ElementFactory.XmlCdataFactory.deleteElementFromContent(content);
                } else {
                    parent.setInnerElement((XmlElement) ElementFactory.XmlCommentFactory.getInstance(content, parent));
                    content = ElementFactory.XmlCommentFactory.deleteElementFromContent(content);
                }
            }else {
                ElementFactory.XmlTagFactory.getInstance(content, parent);
                content = ElementFactory.XmlTagFactory.deleteElementFromContent(content);
            }
        }

        if (!Validator.validateOnCorrectXmlDocument(document)) {
            throw new IllegalStateException(ERROR_MESSAGE_FOR_NOT_CLOSED_TAG);
        }

        return document;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public XmlDocument getDocument() {
        return document;
    }
}
