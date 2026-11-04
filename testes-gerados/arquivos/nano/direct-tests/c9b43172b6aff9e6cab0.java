/* @test    @summary Test StringCharacterIterator constructor with valid parameters    @run main StringCharacterIteratorWitupTest */

import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidNullText();
        testInvalidBeginNegative();
        testInvalidBeginGreaterThanEnd();
        testInvalidEndGreaterThanTextLength();
        testInvalidPosLessThanBegin();
        testInvalidPosGreaterThanEnd();
    }

    static void testValidConstructor() {
        String text = "Hello, World!";
        int begin = 0;
        int end = text.length();
        int pos = 5;
        StringCharacterIterator it = new StringCharacterIterator(text, begin, end, pos);
        assert it != null : "Constructor returned null";
    }

    static void testInvalidNullText() {
        try {
            new StringCharacterIterator(null, 0, 1, 0);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidBeginNegative() {
        String text = "abc";
        try {
            new StringCharacterIterator(text, -1, 2, 0);
            assert false : "Expected IllegalArgumentException for begin < 0";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidBeginGreaterThanEnd() {
        String text = "abc";
        try {
            new StringCharacterIterator(text, 2, 1, 1);
            assert false : "Expected IllegalArgumentException for begin > end";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidEndGreaterThanTextLength() {
        String text = "abc";
        try {
            new StringCharacterIterator(text, 0, 4, 0);
            assert false : "Expected IllegalArgumentException for end > text.length()";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidPosLessThanBegin() {
        String text = "abc";
        try {
            new StringCharacterIterator(text, 1, 3, 0);
            assert false : "Expected IllegalArgumentException for pos < begin";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidPosGreaterThanEnd() {
        String text = "abc";
        try {
            new StringCharacterIterator(text, 0, 2, 3);
            assert false : "Expected IllegalArgumentException for pos > end";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
