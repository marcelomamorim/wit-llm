/*
 * @test
 * @summary Test java.text.AttributedString.addAttribute method behavior
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testAddAttributeNullAttribute();
        testAddAttributeEmptyString();
        testAddAttributeValid();
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

    private static void testAddAttributeEmptyString() {
        AttributedString as = new AttributedString("");
        try {
            as.addAttribute(Attribute.LANGUAGE, "en");
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testAddAttributeValid() {
        String text = "hello";
        AttributedString as = new AttributedString(text);
        as.addAttribute(Attribute.LANGUAGE, "en");
        // Verify length remains unchanged
        if (as.length() != text.length()) {
            throw new RuntimeException("Length changed after addAttribute");
        }
        // Verify iterator returns the attribute for the entire range
        java.text.AttributedCharacterIterator it = as.getIterator();
        Object attrValue = it.getAttribute(Attribute.LANGUAGE);
        if (!"en".equals(attrValue)) {
            throw new RuntimeException("Attribute value not set correctly");
        }
    }
}