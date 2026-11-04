/*
 * @test
 * @summary Witup-derived checks for AttributedString.addAttribute exception paths and basic observable behavior
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Locale;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testNullAttributeThrowsNPE();
        testEmptyTextThrowsIAE();
        testValidAttributeIsApplied();
    }

    static void testNullAttributeThrowsNPE() {
        AttributedString as = new AttributedString("abc");
        boolean thrown = false;
        try {
            as.addAttribute(null, "value");
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null attribute");
        }
    }

    static void testEmptyTextThrowsIAE() {
        AttributedString as = new AttributedString("");
        boolean thrown = false;
        try {
            as.addAttribute(AttributedCharacterIterator.Attribute.LANGUAGE, Locale.US);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for 0-length text");
        }
    }

    static void testValidAttributeIsApplied() {
        AttributedString as = new AttributedString("abc");
        as.addAttribute(AttributedCharacterIterator.Attribute.LANGUAGE, Locale.US);
        AttributedCharacterIterator it = as.getIterator();
        Object first = it.getAttribute(AttributedCharacterIterator.Attribute.LANGUAGE);
        it.setIndex(2);
        Object last = it.getAttribute(AttributedCharacterIterator.Attribute.LANGUAGE);
        if (!Locale.US.equals(first) || !Locale.US.equals(last)) {
            throw new RuntimeException("Attribute not applied across full text: " + first + ", " + last);
        }
    }
}
