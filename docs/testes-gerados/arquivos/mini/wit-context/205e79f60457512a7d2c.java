/*
 * @test
 * @summary Test sun.text.normalizer.UCharacterIterator.getInstance(StringBuffer) for null and valid input
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testGetInstanceWithNull();
        testGetInstanceWithValidStringBuffer();
    }

    private static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance((StringBuffer) null);
            throw new RuntimeException("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testGetInstanceWithValidStringBuffer() {
        StringBuffer sb = new StringBuffer("abc");
        UCharacterIterator it = UCharacterIterator.getInstance(sb);
        if (it == null) {
            throw new RuntimeException("UCharacterIterator.getInstance returned null for valid input");
        }
        // Basic sanity check: the iterator should return the first character 'a' as int
        int firstChar = it.next();
        if (firstChar != 'a') {
            throw new RuntimeException("Iterator did not return expected first character 'a'");
        }
    }
}
