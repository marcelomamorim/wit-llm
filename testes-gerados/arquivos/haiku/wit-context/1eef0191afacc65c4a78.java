/*
 * @test
 * @summary Verifies PropertiesDefaultHandler.characters ignores character data when no entry is open and that entry text is loaded via the public load method
 * @modules java.base/jdk.internal.util.xml
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        testCharactersIgnoredWithoutValidEntry();
        testLoadEntryCharacters();
    }

    static void testCharactersIgnoredWithoutValidEntry() throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        char[] ch = "abc".toCharArray();
        handler.characters(ch, -1, 10);
        handler.characters(ch, 0, 100);
    }

    static void testLoadEntryCharacters() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<properties><entry key=\"k\">value</entry></properties>";
        Properties props = new Properties();
        new PropertiesDefaultHandler().load(props,
                new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        if (!"value".equals(props.getProperty("k"))) {
            throw new RuntimeException("Unexpected value: " + props.getProperty("k"));
        }
    }
}
