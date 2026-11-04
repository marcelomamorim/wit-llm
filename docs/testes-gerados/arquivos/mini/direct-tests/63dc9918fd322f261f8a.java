/*
 * @test
 * @summary Test that PropertiesDefaultHandler.warning throws the given SAXParseException
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {
    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException ex = new SAXParseException("Test warning", null);
        try {
            handler.warning(ex);
            throw new RuntimeException("Expected SAXParseException was not thrown");
        } catch (SAXParseException e) {
            if (e != ex) {
                throw new RuntimeException("Thrown exception is not the same instance as passed", e);
            }
        }
    }
}
