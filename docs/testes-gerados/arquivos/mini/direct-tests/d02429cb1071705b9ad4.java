/*
 * @test
 * @summary Test ReplaceableUCharacterIterator constructor with StringBuffer
 * @run main ReplaceableUCharacterIteratorWitupTest
 */

import sun.text.normalizer.ReplaceableUCharacterIterator;

public class ReplaceableUCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidStringBuffer();
        testConstructorWithNullStringBuffer();
    }

    private static void testConstructorWithValidStringBuffer() {
        StringBuffer sb = new StringBuffer("hello");
        ReplaceableUCharacterIterator it = new ReplaceableUCharacterIterator(sb);
        int length = it.getLength();
        if (length != sb.length()) {
            throw new RuntimeException("Length mismatch: expected " + sb.length() + " but got " + length);
        }
        int currentChar = it.current();
        if (currentChar != sb.charAt(0)) {
            throw new RuntimeException("Current char mismatch: expected " + (int)sb.charAt(0) + " but got " + currentChar);
        }
    }

    private static void testConstructorWithNullStringBuffer() {
        try {
            new ReplaceableUCharacterIterator((StringBuffer) null);
            throw new RuntimeException("Expected IllegalArgumentException for null StringBuffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
