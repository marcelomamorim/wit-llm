/* @test    @summary Test for PropertiesDefaultHandler.error method to ensure it throws SAXException as expected.    @run main PropertiesDefaultHandlerWitupTest */

import java.io.*;
import jdk.internal.util.xml.PropertiesDefaultHandler;
import org.xml.sax.SAXParseException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) {
        testErrorMethodThrowsException();
    }

    public static void testErrorMethodThrowsException() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException exception = new SAXParseException("Test exception", null);
        boolean thrown = false;
        try {
            handler.error(exception);
        } catch (SAXParseException e) {
            thrown = true;
            if (e != exception) {
                throw new AssertionError("Caught exception is not the same as the input exception.");
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected SAXParseException to be thrown.");
        }
    }
}
