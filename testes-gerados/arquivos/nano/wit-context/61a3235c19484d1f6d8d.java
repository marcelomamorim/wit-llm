/*
 * @test
 * @summary Test for jdk.internal.util.xml.PropertiesDefaultHandler.fatalError
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.*;
import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXParseException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        testFatalErrorWithNull();
        testFatalErrorWithValidException();
    }

    private static void testFatalErrorWithNull() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        try {
            handler.fatalError(null);
            // Expecting a NullPointerException or no exception, depending on implementation
            System.out.println("testFatalErrorWithNull: Passed (no exception)");
        } catch (NullPointerException npe) {
            System.out.println("testFatalErrorWithNull: Passed (NullPointerException caught)");
        } catch (Exception e) {
            System.out.println("testFatalErrorWithNull: Failed with unexpected exception: " + e);
        }
    }

    private static void testFatalErrorWithValidException() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException ex = new SAXParseException("Test exception", null);
        try {
            handler.fatalError(ex);
            System.out.println("testFatalErrorWithValidException: Failed (no exception thrown)");
        } catch (SAXException se) {
            if (se == ex) {
                System.out.println("testFatalErrorWithValidException: Passed (SAXException rethrown)");
            } else {
                System.out.println("testFatalErrorWithValidException: Failed (different exception)");
            }
        } catch (Exception e) {
            System.out.println("testFatalErrorWithValidException: Failed with unexpected exception: " + e);
        }
    }
}
