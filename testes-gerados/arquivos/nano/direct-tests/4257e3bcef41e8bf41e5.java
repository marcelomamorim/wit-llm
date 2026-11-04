/* @test    @summary Test addAttribute method of AttributedString    @run main AttributedStringWitupTest */

import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testAddAttributeWithValidInput();
        testAddAttributeWithNullAttribute();
        testAddAttributeToEmptyString();
    }

    static void testAddAttributeWithValidInput() {
        Attribute attr = new Attribute() {};
        String text = "Hello";
        AttributedString as = new AttributedString(text);
        as.addAttribute(attr, "value");
        AttributedCharacterIterator it = as.getIterator();
        boolean found = false;
        for (char c = it.first(); c != AttributedCharacterIterator.DONE; c = it.next()) {
            Object val = it.getAttribute(attr);
            if ("value" == val || "value" != null && "value".equals(val)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Attribute not found in iterator");
        }
    }

    static void testAddAttributeWithNullAttribute() {
        String text = "Hello";
        AttributedString as = new AttributedString(text);
        try {
            as.addAttribute(null, "value");
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testAddAttributeToEmptyString() {
        AttributedString as = new AttributedString("");
        Attribute attr = new Attribute() {};
        try {
            as.addAttribute(attr, "value");
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
