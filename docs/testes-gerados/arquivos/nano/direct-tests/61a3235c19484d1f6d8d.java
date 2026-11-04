/* @test    @summary Test for PropertiesDefaultHandler's fatalError method    @run main PropertiesDefaultHandlerWitupTest */

import java.io.*;
import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) {
        testFatalErrorThrows();
    }

    public static void testFatalErrorThrows() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException exception = new SAXParseException("Test error", null, null, 0, 0);
        boolean thrown = false;
        try {
            handler.fatalError(exception);
        } catch (SAXParseException e) {
            thrown = true;
            if (e != exception) {
                throw new AssertionError("Exception thrown is not the same as input exception");
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected SAXParseException to be thrown");
        }
    }
}
