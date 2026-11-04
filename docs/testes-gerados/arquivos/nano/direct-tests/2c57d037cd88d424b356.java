/* @test    @summary Test constructor AttributedString(String) with normal and null input    @run main AttributedStringWitupTest */

import java.text.AttributedString;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNull();
    }

    static void testConstructorWithValidString() {
        String sampleText = "Sample Text";
        AttributedString attrStr = new AttributedString(sampleText);
        if (!attrStr.text.equals(sampleText)) {
            throw new AssertionError("Expected text to be 'Sample Text', but was: " + attrStr.text);
        }
    }

    static void testConstructorWithNull() {
        boolean exceptionThrown = false;
        try {
            new AttributedString((String) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown for null input");
        }
    }
}
