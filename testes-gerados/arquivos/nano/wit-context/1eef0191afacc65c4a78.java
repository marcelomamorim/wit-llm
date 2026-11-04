/*
 * @test
 * @summary Test for jdk.internal.util.xml.PropertiesDefaultHandler.characters method.
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.*;
import java.util.Properties;
import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) {
        testCharactersWithValidEntry();
        testCharactersWithInvalidRange();
    }

    private static void testCharactersWithValidEntry() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        char[] ch = {'a', 'b', 'c', 'd'};
        int start = 1;
        int length = 2;
        try {
            handler.characters(ch, start, length);
            System.out.println("testCharactersWithValidEntry passed");
        } catch (SAXException e) {
            throw new RuntimeException("Unexpected exception in valid case", e);
        }
    }

    private static void testCharactersWithInvalidRange() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        char[] ch = {'x', 'y'};
        int start = 1;
        int length = 5; // invalid, as length exceeds array bounds
        boolean exceptionThrown = false;
        try {
            handler.characters(ch, start, length);
        } catch (SAXException e) {
            exceptionThrown = true;
            if (e.getCause() instanceof IndexOutOfBoundsException) {
                System.out.println("testCharactersWithInvalidRange passed");
            } else {
                throw new RuntimeException("Unexpected exception cause", e);
            }
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException was not thrown");
        }
    }
}
