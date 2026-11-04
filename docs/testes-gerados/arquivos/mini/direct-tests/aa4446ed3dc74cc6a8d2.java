/*
 * @test
 * @summary Test jdk.internal.util.xml.PropertiesDefaultHandler.error method throws the passed SAXParseException
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {
    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException ex = new SAXParseException("Test error", null, null, 1, 1);
        try {
            handler.error(ex);
            throw new RuntimeException("Expected SAXParseException was not thrown");
        } catch (SAXParseException e) {
            if (e != ex) {
                throw new RuntimeException("Thrown exception is not the same instance as passed", e);
            }
        }
    }
}
