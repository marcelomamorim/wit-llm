/*
 * @test
 * @summary Test jdk.internal.util.xml.PropertiesDefaultHandler.characters method
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;
import jdk.internal.org.xml.sax.SAXException;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();

        // Use reflection to set validEntry to true since it is private and no public setter
        java.lang.reflect.Field validEntryField = PropertiesDefaultHandler.class.getDeclaredField("validEntry");
        validEntryField.setAccessible(true);
        validEntryField.setBoolean(handler, true);

        // Use reflection to get buf field (StringBuilder) to verify append
        java.lang.reflect.Field bufField = PropertiesDefaultHandler.class.getDeclaredField("buf");
        bufField.setAccessible(true);

        // Initially buf is null, so initialize it
        java.lang.reflect.Method initBufMethod = null;
        try {
            initBufMethod = PropertiesDefaultHandler.class.getDeclaredMethod("initBuf");
        } catch (NoSuchMethodException e) {
            // no initBuf method, so create buf manually
            bufField.set(handler, new StringBuilder());
        }

        if (initBufMethod != null) {
            initBufMethod.setAccessible(true);
            initBufMethod.invoke(handler);
        }

        // Prepare char array and call characters
        char[] chars = "HelloWorld".toCharArray();
        int start = 0;
        int length = chars.length;

        handler.characters(chars, start, length);

        // Verify that buf contains "HelloWorld"
        StringBuilder buf = (StringBuilder) bufField.get(handler);
        if (!"HelloWorld".equals(buf.toString())) {
            throw new AssertionError("Expected 'HelloWorld' in buf but found: " + buf.toString());
        }

        // Test with validEntry = false, characters should not append
        validEntryField.setBoolean(handler, false);
        buf.setLength(0); // clear buffer

        handler.characters(chars, start, length);

        if (buf.length() != 0) {
            throw new AssertionError("Expected buf to be empty when validEntry is false");
        }

        System.out.println("Test passed.");
    }
}
