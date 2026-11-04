/*
 * @test
 * @summary Test for warning method in PropertiesDefaultHandler.
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.*;
import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) {
        testWarningThrowsException();
    }

    public static void testWarningThrowsException() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException exception = new SAXParseException("Test exception", null);
        boolean thrown = false;
        try {
            handler.warning(exception);
        } catch (SAXException e) {
            thrown = true;
            if (e != exception) {
                throw new AssertionError("Exception thrown is not the same as input exception");
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected SAXException to be thrown");
        }
    }
}
