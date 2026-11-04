/*
 * @test
 * @summary Test PropertiesDefaultHandler.characters method for valid and invalid inputs
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();

        // Use reflection to set validEntry to true since it is private and no public setter is available
        // This is necessary to test the characters method behavior when validEntry is true
        // Reflection is justified here because validEntry is private and no public API to set it
        java.lang.reflect.Field validEntryField = PropertiesDefaultHandler.class.getDeclaredField("validEntry");
        validEntryField.setAccessible(true);

        // Set validEntry to true
        validEntryField.setBoolean(handler, true);

        // Prepare a char array with some content
        char[] chars = new char[] {'a', 'b', 'c', 'd', 'e'};

        // Test normal call with valid start and length
        try {
            handler.characters(chars, 1, 3); // should append 'b','c','d'
        } catch (SAXException e) {
            throw new RuntimeException("Unexpected SAXException for valid input", e);
        }

        // Test edge case: start = 0, length = 0 (should not throw)
        try {
            handler.characters(chars, 0, 0);
        } catch (SAXException e) {
            throw new RuntimeException("Unexpected SAXException for zero length", e);
        }

        // Test invalid inputs that should cause IndexOutOfBoundsException inside StringBuffer.append
        // According to WIT expath, if validEntry is true and (ch.length < length + start || length < 0 || start < 0)
        // then IndexOutOfBoundsException is expected

        // Case 1: start < 0
        try {
            handler.characters(chars, -1, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for start < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (SAXException e) {
            throw new RuntimeException("Unexpected SAXException instead of IndexOutOfBoundsException", e);
        }

        // Case 2: length < 0
        try {
            handler.characters(chars, 1, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for length < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (SAXException e) {
            throw new RuntimeException("Unexpected SAXException instead of IndexOutOfBoundsException", e);
        }

        // Case 3: length + start > ch.length
        try {
            handler.characters(chars, 3, 3); // 3 + 3 = 6 > 5
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for length + start > ch.length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (SAXException e) {
            throw new RuntimeException("Unexpected SAXException instead of IndexOutOfBoundsException", e);
        }

        // Test when validEntry is false: characters should do nothing and not throw
        validEntryField.setBoolean(handler, false);

        try {
            handler.characters(chars, -1, 10); // invalid indices but validEntry false disables append
        } catch (Exception e) {
            throw new RuntimeException("No exception expected when validEntry is false", e);
        }
    }
}
