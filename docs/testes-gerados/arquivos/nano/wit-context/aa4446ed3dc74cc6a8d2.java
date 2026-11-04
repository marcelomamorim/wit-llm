/*
 * @test
 * @summary Test for PropertiesDefaultHandler.error(SAXParseException)
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.*;
import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        testErrorMethodWithNull();
        testErrorMethodWithValidException();
    }

    private static void testErrorMethodWithNull() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        try {
            handler.error(null);
        } catch (NullPointerException npe) {
            System.out.println("Caught expected NullPointerException");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static void testErrorMethodWithValidException() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException ex = new SAXParseException("Test exception", null);
        try {
            handler.error(ex);
        } catch (SAXException saxe) {
            if (saxe.getCause() == ex) {
                System.out.println("Correctly rethrown SAXException with cause");
            } else {
                throw new AssertionError("SAXException cause mismatch");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
