package model;

import org.junit.Test;
import parser.ElementFactory;

import static org.junit.Assert.*;
import java.util.List;

public class XmlAttrTest {
   @Test
    public void testOfTagWithoutAttrs() {
        String tag = "<tag />";
        int expectedSize = 0;
        List<XmlAttr> attrs = (List<XmlAttr>) ElementFactory.XmlAttrFactory.getInstance(tag, null);

        assertEquals(expectedSize, attrs.size());
    }

    @Test
    public void testOfLineTagWithAttrs() {
        String tag = "<tag attr1=\"1\" attr2=\"2\" />";
        int expectedSize = 2;
        String expectedToStringForAttr1 = "attr1=\"1\"";
        String expectedToStringForAttr2 = "attr2=\"2\"";
        List<XmlAttr> attrs =(List<XmlAttr>) ElementFactory.XmlAttrFactory.getInstance(tag, null);

        assertEquals(expectedSize, attrs.size());
        assertEquals(expectedToStringForAttr1, attrs.get(0).toString());
        assertEquals(expectedToStringForAttr2, attrs.get(1).toString());
    }
}