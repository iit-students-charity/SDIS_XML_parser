package model;

import org.junit.Test;
import parser.ElementFactory;
import static org.junit.Assert.*;

public class XmlTagTest {
    @Test
    public void testForLineTagWithoutAttrs() {
        String inputData = "<test />";
        XmlTag tag = (XmlTag) ElementFactory.XmlTagFactory.getInstance(inputData, null);
        String expected = "<test >\n</test>";

        assertEquals(expected, tag.toString());
    }

    @Test
    public void testForTagWithoutValueAndAttrs() {
        String inputData = "<test></test>";
        XmlTag tag = (XmlTag) ElementFactory.XmlTagFactory.getInstance(inputData, null);
        String expected = "<test >\n</test>";

        assertEquals(expected, tag.toString());
    }

    @Test
    public void testForOutputSimpleTag() {
        String inputData = "<test value_1=\"some value\" value_2=\"dick for shunia\" />";
        XmlTag tag = (XmlTag) ElementFactory.XmlTagFactory.getInstance(inputData, null);
        String expected = "<test value_1=\"some value\" value_2=\"dick for shunia\">\n</test>";

        assertEquals(expected, tag.toString());
    }

    @Test
    public void testForTagWithAttrsAndValue() {
        String inputData = "<test value_1=\"some value 1\" value_2=\"some value 2\">\n\tbody of 'test' tag\n<test_2 value=\"hard test\" />\n</test>";
        String expected = "<test value_1=\"some value 1\" value_2=\"some value 2\">\n" +
                "\tbody of 'test' tag\n" +
                "\t<test_2 value=\"hard test\">\n" +
                "\t</test_2>\n" +
                "</test>";
        XmlTag tag = null;

        while (tag == null || !tag.isClosedTag()) {
            tag = (XmlTag) ElementFactory.XmlTagFactory.getInstance(inputData, tag);
            inputData = ElementFactory.XmlTagFactory.deleteElementFromContent(inputData);
        }

        assertEquals(expected, tag.toString());
    }

    @Test
    public void testForTagWithAttrsValueAndInnerTags() {
        String inputData = "<test value_1=\"some value 1\" value_2=\"some value 2\">\n" +
                "\tbody of 'test' tag\n" +
                "\n" +
                "\t<test_2 value=\"hard test\" />\n" +
                "\n" +
                "\t<test_3>\n" +
                "\t\tbody of \"test_3\" tag\n" +
                "\t</test_3>\n" +
                "\n" +
                "\t<test_4>\n" +
                "\t\t<test_5 value=\"pizda\" />\n" +
                "\t</test_4>\n" +
                "</test>";
        String expected = "<test value_1=\"some value 1\" value_2=\"some value 2\">\n" +
                "\tbody of 'test' tag\n" +
                "\t<test_2 value=\"hard test\">\n" +
                "\t</test_2>\n" +
                "\n" +
                "\t<test_3 >\n" +
                "\t\tbody of \"test_3\" tag\n" +
                "\t</test_3>\n" +
                "\n" +
                "\t<test_4 >\n" +
                "\t\t<test_5 value=\"pizda\">\n" +
                "\t\t</test_5>\n" +
                "\t</test_4>\n" +
                "</test>";
        XmlTag tag = null;
        XmlTag daughter = null;

        while (!inputData.isEmpty()) {
            tag = (XmlTag) ElementFactory.XmlTagFactory.getInstance(inputData, tag);
            if (tag.isClosedTag()) {
                daughter = tag;
                tag = (XmlTag) tag.getParent();
            }
            inputData = ElementFactory.XmlTagFactory.deleteElementFromContent(inputData);
        }

        assertEquals(expected, daughter.toString());
    }
}