/* @test    @summary Test for PropertiesDefaultHandler.characters method    @run main PropertiesDefaultHandlerWitupTest */

import java.io.*;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        testCharactersMethodWithValidInput();
        testCharactersMethodWithInvalidInput();
    }

    private static void testCharactersMethodWithValidInput() throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        char[] inputChars = "test".toCharArray();
        StringBuilder buf = new StringBuilder();
        // Simulate validEntry being true
        boolean validEntry = true;
        // Use reflection to set validEntry to true (since it's private)
        java.lang.reflect.Field validEntryField = PropertiesDefaultHandler.class.getDeclaredField("validEntry");
        validEntryField.setAccessible(true);
        validEntryField.setBoolean(handler, true);
        // Use reflection to set buf (assuming buf is private)
        java.lang.reflect.Field bufField = PropertiesDefaultHandler.class.getDeclaredField("buf");
        bufField.setAccessible(true);
        StringBuilder testBuf = new StringBuilder();
        bufField.set(handler, testBuf);
        // Call characters method
        handler.characters(inputChars, 0, inputChars.length);
        // Verify buf content
        if (!testBuf.toString().equals("test")) {
            throw new AssertionError("Expected buf to contain 'test', but got: " + testBuf.toString());
        }
    }

    private static void testCharactersMethodWithInvalidInput() throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        // Simulate validEntry being false
        java.lang.reflect.Field validEntryField = PropertiesDefaultHandler.class.getDeclaredField("validEntry");
        validEntryField.setAccessible(true);
        validEntryField.setBoolean(handler, false);
        // Prepare input
        char[] inputChars = "should not append".toCharArray();
        // Prepare buf
        java.lang.reflect.Field bufField = PropertiesDefaultHandler.class.getDeclaredField("buf");
        bufField.setAccessible(true);
        StringBuilder testBuf = new StringBuilder();
        bufField.set(handler, testBuf);
        // Call characters method
        handler.characters(inputChars, 0, inputChars.length);
        // Verify buf remains empty
        if (testBuf.length() != 0) {
            throw new AssertionError("Expected buf to be empty, but got: " + testBuf.toString());
        }
    }
}
