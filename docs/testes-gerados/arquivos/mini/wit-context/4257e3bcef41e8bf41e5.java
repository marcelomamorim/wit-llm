/*
 * @test
 * @summary Test addAttribute method of AttributedString for exception and normal cases
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;
import java.util.Map;

public class AttributedStringWitupTest {
    public static void main(String[] args) {
        testAddAttributeNullAttribute();
        testAddAttributeEmptyText();
        testAddAttributeNormal();
    }

    private static void testAddAttributeNullAttribute() {
        AttributedString as = new AttributedString("test");
        try {
            as.addAttribute(null, "value");
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAddAttributeEmptyText() {
        AttributedString as = new AttributedString("");
        try {
            as.addAttribute(Attribute.LANGUAGE, "en");
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testAddAttributeNormal() {
        String text = "hello world";
        AttributedString as = new AttributedString(text);
        as.addAttribute(Attribute.LANGUAGE, "en");
        // Verify that length is unchanged and no exception thrown
        if (as.length() != text.length()) {
            throw new RuntimeException("Length changed after addAttribute");
        }
    }
}
