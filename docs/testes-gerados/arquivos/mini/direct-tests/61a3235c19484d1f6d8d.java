/*
 * @test
 * @summary Test that PropertiesDefaultHandler.fatalError rethrows the SAXParseException
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {
    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException ex = new SAXParseException("Test fatal error", null);
        try {
            handler.fatalError(ex);
            throw new RuntimeException("Expected SAXParseException to be thrown");
        } catch (SAXParseException e) {
            if (e != ex) {
                throw new RuntimeException("Caught exception is not the same instance as thrown", e);
            }
        }
    }
}
