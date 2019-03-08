package parser;

import model.XmlTag;
import org.junit.Test;
import writer.Writer;
import java.io.IOException;
import java.net.URISyntaxException;
import static org.junit.Assert.*;

public class XmlDomParserTest {
    @Test(expected = IllegalArgumentException.class)
    public void testOfValidatorOnNotDeclaredDocument() throws IOException, URISyntaxException {
        XmlDomParser xmlDomParser = new XmlDomParser("test2.xml");

        xmlDomParser.parse();
    }

    @Test
    public void testOfParserForSimpleDocument() throws IOException, URISyntaxException {
        XmlDomParser xmlDomParser = new XmlDomParser("test1.xml");
        String expected = "<?xml version=\"1.0\" ?>\n<test >\n</test>";

        xmlDomParser.parse();

        assertEquals(expected, xmlDomParser.getDocument().toString());
    }

    @Test
    public void testOfParserForDocumentWithHardTagStructure() throws IOException, URISyntaxException {
        XmlDomParser xmlDomParser = new XmlDomParser("test3.xml");
        String expectd = "<?xml version=\"1.0\" ?>\n" +
                "<test value_1=\"some value 1\" value_2=\"some value 2\">\n" +
                "\tbody of 'test' tag\n" +
                "\t<test_2 value=\"hard test\">\n" +
                "\t</test_2>\n" +
                "\n" +
                "\t<test_3 >\n" +
                "\t\tbody of \"test_3\" tag\n" +
                "\t</test_3>\n" +
                "\n" +
                "\t<test_4 >\n" +
                "\t\t<test_5 value=\"some value for test_5 teg\">\n" +
                "\t\t</test_5>\n" +
                "\t</test_4>\n" +
                "</test>";

        xmlDomParser.parse();

        assertEquals(expectd, xmlDomParser.getDocument().toString());
    }

    @Test
    public void testOfParserForDocumentWithComplexTagsCommentsAndCDATA() throws IOException, URISyntaxException {
        XmlDomParser xmlDomParser = new XmlDomParser("test4.xml");
        String expected = "<?xml version=\"1.0\" ?>\n" +
                "<test value_1=\"some value 1\" value_2=\"some value 2\">\n" +
                "\tbody of 'test' tag\n" +
                "\t<test_2 value=\"hard test\">\n" +
                "\t</test_2>\n" +
                "\n" +
                "\t<test_3 >\n" +
                "\t\tbody of \"test_3\" tag\n" +
                "\t</test_3>\n" +
                "\n" +
                "\t<!-- test comment #1 -->\n" +
                "\n" +
                "\t<![CDATA[\n" +
                "\t        Within this Character Data block I can\n" +
                "\t        use double dashes as much as I want (along with <, &, ', and \\\")\n" +
                "\t        *and* %MyParamEntity; will be expanded to the text\n" +
                "\t        \"Has been expanded\" ... however, I can't use\n" +
                "\t        the CEND sequence. If I need to use CEND I must escape one of the\n" +
                "\t        brackets or the greater-than sign using concatenated CDATA sections.\n" +
                "\t    ]]>\n" +
                "\n" +
                "\t<test_4 >\n" +
                "\t\t<test_5 value=\"some value for test_5 teg\">\n" +
                "\t\t</test_5>\n" +
                "\t\n" +
                "\t\t<!-- test comment # 2 -->\n" +
                "\t</test_4>\n" +
                "</test>";

        xmlDomParser.parse();

        assertEquals(expected, xmlDomParser.getDocument().toString());

        Writer.write("D:/pivas_xml_lab/XmlParser/src/main/resources/result_test1.xml",
                xmlDomParser.getDocument().toString());
    }

    @Test
    public void testOfParserForDocumentWithComplexTagsCommentsAndCDATAThenAddingNewTagAndWriteInFile() throws IOException, URISyntaxException {
        XmlDomParser xmlDomParser = new XmlDomParser("test4.xml");
        String expected = "<?xml version=\"1.0\" ?>\n" +
                "<test value_1=\"some value 1\" value_2=\"some value 2\">\n" +
                "\tbody of 'test' tag\n" +
                "\t<test_2 value=\"hard test\">\n" +
                "\t\t<test_for_dv_2 >\n" +
                "\t\t\tPizza\n" +
                "\t\t</test_for_dv_2>\n" +
                "\t</test_2>\n" +
                "\n" +
                "\t<test_3 >\n" +
                "\t\tbody of \"test_3\" tag\n" +
                "\t</test_3>\n" +
                "\n" +
                "\t<!-- test comment #1 -->\n" +
                "\n" +
                "\t<![CDATA[\n" +
                "\t        Within this Character Data block I can\n" +
                "\t        use double dashes as much as I want (along with <, &, ', and \\\")\n" +
                "\t        *and* %MyParamEntity; will be expanded to the text\n" +
                "\t        \"Has been expanded\" ... however, I can't use\n" +
                "\t        the CEND sequence. If I need to use CEND I must escape one of the\n" +
                "\t        brackets or the greater-than sign using concatenated CDATA sections.\n" +
                "\t    ]]>\n" +
                "\n" +
                "\t<test_4 >\n" +
                "\t\t<test_5 value=\"some value for test_5 teg\">\n" +
                "\t\t</test_5>\n" +
                "\t\n" +
                "\t\t<!-- test comment # 2 -->\n" +
                "\t</test_4>\n" +
                "\n" +
                "\t<test >\n" +
                "\t\tIce-cream\n" +
                "\t</test>\n" +
                "</test>";

        xmlDomParser.parse();

        XmlTag tempTag = xmlDomParser.getDocument().getRoot();
        tempTag.setInnerElement(
                new XmlTag("test", "\n\tIce-cream", tempTag));

        tempTag = (XmlTag) xmlDomParser.getDocument().getRoot().getInnerElements().get(0);
        tempTag.setInnerElement(
                new XmlTag("test_for_dv_2", "\n\tPizza", tempTag));

        assertEquals(expected, xmlDomParser.getDocument().toString());

        Writer.write("D:/pivas_xml_lab/XmlParser/src/main/resources/result_test2.xml",
                xmlDomParser.getDocument().toString());
    }
}