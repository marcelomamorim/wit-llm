/*
 * @test
 * @summary Test for jdk.internal.util.xml.PropertiesDefaultHandler.warning(SAXParseException) method
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {
    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException ex = new SAXParseException("Test warning exception", null, null, 1, 1);
        try {
            handler.warning(ex);
            throw new RuntimeException("Expected SAXParseException to be thrown");
        } catch (SAXParseException e) {
            if (e != ex) {
                throw new RuntimeException("Thrown exception is not the same instance as input");
            }
        }
    }
}
