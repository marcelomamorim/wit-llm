/* @test    @summary Test handling of warning method in PropertiesDefaultHandler    @run main PropertiesDefaultHandlerWitupTest */

import java.io.*;
import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) {
        testWarningThrowsSAXParseException();
    }

    private static void testWarningThrowsSAXParseException() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException exception = new SAXParseException("Test warning exception", null);
        boolean exceptionThrown = false;
        try {
            handler.warning(exception);
        } catch (SAXParseException e) {
            exceptionThrown = true;
            if (e != exception) {
                throw new AssertionError("Caught exception does not match the thrown exception");
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected SAXParseException to be thrown by warning() method");
        }
    }
}
