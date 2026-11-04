/*
 * @test
 * @summary Verify that PropertiesDefaultHandler.fatalError propagates a fatal XML error as InvalidPropertiesFormatException with a SAXException cause
 * @modules java.base/jdk.internal.util.xml java.base/jdk.internal.org.xml.sax
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        String malformed = "<properties><entry key=\"a\">b</properties>";
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        Properties props = new Properties();
        boolean thrown = false;
        try {
            handler.load(props, new ByteArrayInputStream(malformed.getBytes("UTF-8")));
        } catch (InvalidPropertiesFormatException e) {
            thrown = true;
            if (!(e.getCause() instanceof SAXException)) {
                throw new RuntimeException("Expected SAXException cause, got: " + e.getCause());
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected InvalidPropertiesFormatException for malformed XML");
        }
        if (props.size() != 0) {
            throw new RuntimeException("Properties should not be populated on fatal error");
        }
    }
}
