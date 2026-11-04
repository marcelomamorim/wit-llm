/*
 * @test
 * @summary Test ReplaceableUCharacterIterator(StringBuffer) constructor for null argument and normal usage
 * @run main ReplaceableUCharacterIteratorWitupTest
 */

import sun.text.normalizer.ReplaceableUCharacterIterator;

public class ReplaceableUCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidStringBuffer();
    }

    private static void testConstructorWithNull() {
        try {
            new ReplaceableUCharacterIterator((StringBuffer) null);
            throw new RuntimeException("Expected IllegalArgumentException for null StringBuffer");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testConstructorWithValidStringBuffer() {
        StringBuffer sb = new StringBuffer("test");
        ReplaceableUCharacterIterator it = new ReplaceableUCharacterIterator(sb);
        int length = it.getLength();
        if (length != sb.length()) {
            throw new RuntimeException("Length mismatch: expected " + sb.length() + " but got " + length);
        }
        int currentChar = it.current();
        if (currentChar != sb.charAt(0)) {
            throw new RuntimeException("Current char mismatch: expected " + sb.charAt(0) + " but got " + currentChar);
        }
    }
}
