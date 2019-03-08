package reader;

import org.junit.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import static org.junit.Assert.*;

public class ReaderTest {
    @Test
    public void  testForReadingByPathToFile() throws IOException, URISyntaxException {
        String result = Reader.readFileByPath("test1.xml");
        String expected = "<?xml version=\"1.0\" ?>\n<test />";

        assertEquals(expected, result);
    }

    @Test
    public void testForReadingByInputStream() throws IOException {
        String result =  Reader.readFileByStream(new FileInputStream("D:/pivas_xml_lab/XmlParser/src/main/resources/test1.xml"));
        String expected = "<?xml version=\"1.0\" ?>\n<test />";

        assertEquals(expected, result);
    }
}