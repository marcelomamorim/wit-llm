/*
 * @test
 * @summary Behavioral tests for AttributedString.addAttribute(Attribute, Object)
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testNullAttributeThrowsNPE();
        testEmptyTextThrowsIllegalArgument();
        testAddAttributeAppliesToWholeRange();
        testSameKeyReplacesValue();
        System.out.println("All tests passed");
    }

    static void testNullAttributeThrowsNPE() {
        AttributedString as = new AttributedString("abc");
        try {
            as.addAttribute(null, "en");
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyTextThrowsIllegalArgument() {
        AttributedString as = new AttributedString("");
        try {
            as.addAttribute(Attribute.LANGUAGE, "en");
            throw new RuntimeException("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testAddAttributeAppliesToWholeRange() {
        AttributedString as = new AttributedString("abc");
        as.addAttribute(Attribute.LANGUAGE, "en");
        AttributedCharacterIterator it = as.getIterator();
        check("en".equals(it.getAttribute(Attribute.LANGUAGE)) && it.getIndex() == 0,
              "value at index 0");
        it.setIndex(2);
        check("en".equals(it.getAttribute(Attribute.LANGUAGE)), "value at index 2");
        check(it.getRunLimit(Attribute.LANGUAGE) == 3, "run limit covers full text");
    }

    static void testSameKeyReplacesValue() {
        AttributedString as = new AttributedString("abc");
        as.addAttribute(Attribute.LANGUAGE, "en");
        as.addAttribute(Attribute.LANGUAGE, "fr");
        AttributedCharacterIterator it = as.getIterator();
        check("fr".equals(it.getAttribute(Attribute.LANGUAGE)), "replaced value at index 0");
        it.setIndex(2);
        check("fr".equals(it.getAttribute(Attribute.LANGUAGE)), "replaced value at index 2");
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }
}
