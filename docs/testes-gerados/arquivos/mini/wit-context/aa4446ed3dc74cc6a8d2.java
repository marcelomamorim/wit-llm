/*
 * @test
 * @summary Test for jdk.internal.util.xml.PropertiesDefaultHandler.error(SAXParseException) method
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {
    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();

        // Create a SAXParseException instance with minimal required parameters
        SAXParseException ex = new SAXParseException("Test exception", null, null, 1, 1);

        try {
            handler.error(ex);
            throw new RuntimeException("Expected SAXParseException to be thrown");
        } catch (SAXParseException e) {
            if (e != ex) {
                throw new RuntimeException("Thrown exception is not the same instance as passed");
            }
            // Expected path: the method rethrows the passed SAXParseException
        }
    }
}
