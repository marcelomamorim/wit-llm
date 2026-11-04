/*
 * @test
 * @summary Test for jdk.internal.util.xml.PropertiesDefaultHandler.fatalError(SAXParseException)
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {
    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();

        // Create a SAXParseException to pass to fatalError
        SAXParseException ex = new SAXParseException("Test fatal error", null);

        try {
            handler.fatalError(ex);
            throw new AssertionError("Expected SAXParseException to be thrown");
        } catch (SAXParseException e) {
            if (e != ex) {
                throw new AssertionError("Thrown exception is not the same instance as passed");
            }
            // Expected path: fatalError rethrows the passed SAXParseException
        }
    }
}
