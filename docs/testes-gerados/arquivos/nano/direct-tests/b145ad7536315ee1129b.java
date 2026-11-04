/* @test    @summary Test AttributedString.setIndex with valid and invalid indices    @run main AttributedStringWitupTest */

import java.text.AttributedString;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testSetIndexValid();
        testSetIndexInvalid();
    }

    static void testSetIndexValid() {
        AttributedString attrStr = new AttributedString("Sample Text");
        try {
            char ch = attrStr.setIndex(0);
            assert ch == 'S' : "Expected 'S' at index 0";
            ch = attrStr.setIndex(5);
            assert ch == 'T' : "Expected 'T' at index 5";
            ch = attrStr.setIndex(attrStr.text.length() - 1);
            assert ch == 'x' : "Expected 'x' at last index";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid setIndex", e);
        }
    }

    static void testSetIndexInvalid() {
        AttributedString attrStr = new AttributedString("Sample Text");
        try {
            attrStr.setIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for index -1");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            attrStr.setIndex(attrStr.text.length());
            throw new AssertionError("Expected IllegalArgumentException for index equal to length");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            attrStr.setIndex(attrStr.text.length() + 1);
            throw new AssertionError("Expected IllegalArgumentException for index greater than length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
