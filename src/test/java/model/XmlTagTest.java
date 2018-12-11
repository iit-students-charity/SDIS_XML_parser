package model;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class XmlTagTest {
    @Test
    public void testForOutputSimpleTag() {
        List<XmlAttr> emptyAttrs = new ArrayList<>();
        List<XmlTag> emptyTags = new ArrayList<>();
        String expected = "<test >\n\tbody\n</test>";
        XmlElement tag = new XmlTag("\n\tbody", null, "<test ", "</test>");

        ((XmlTag) tag).setAttrs(emptyAttrs);
        ((XmlTag) tag).setInnerTags(emptyTags);

        assertEquals(expected, tag.toString());
    }
}