package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class XmlCommentTest {
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestOnNotComment() {
        String inputData = "some text";
        XmlElement comment = new XmlComment(inputData, null);
    }

    @Test
    public void positiveTest() {
        String inputData = "<!--\n" +
                "\tWithin this Character Data block I can\n" +
                "\tuse double dashes as much as I want (along with <, &, ', and \")\n" +
                "\t*and* %MyParamEntity; will be expanded to the text\n" +
                "\t\"Has been expanded\" ... however, I can't use\n" +
                "\tthe CEND sequence. If I need to use CEND I must escape one of the\n" +
                "\tbrackets or the greater-than sign using concatenated CDATA sections.\n" +
                "-->";
        String content = "\n" +
                "\tWithin this Character Data block I can\n" +
                "\tuse double dashes as much as I want (along with <, &, ', and \")\n" +
                "\t*and* %MyParamEntity; will be expanded to the text\n" +
                "\t\"Has been expanded\" ... however, I can't use\n" +
                "\tthe CEND sequence. If I need to use CEND I must escape one of the\n" +
                "\tbrackets or the greater-than sign using concatenated CDATA sections.\n";
        XmlElement comment = new XmlComment(inputData, null);

        assertNull(comment.getParent());
        assertEquals(content, comment.getData());
        assertEquals(inputData, comment.toString());
    }
}