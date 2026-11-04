/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.contains(int) method
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testContainsValidCodePoint();
        testContainsInvalidCodePointBelowMin();
        testContainsInvalidCodePointAboveMax();
    }

    private static void testContainsValidCodePoint() {
        // Create a UnicodeSet with a range including 'A' (U+0041)
        UnicodeSet set = new UnicodeSet(0x0041, 0x005A); // 'A' to 'Z'
        // 'A' is in the set
        if (!set.contains(0x0041)) {
            throw new RuntimeException("Expected contains(0x0041) to be true");
        }
        // 'a' (U+0061) is not in the set
        if (set.contains(0x0061)) {
            throw new RuntimeException("Expected contains(0x0061) to be false");
        }
    }

    private static void testContainsInvalidCodePointBelowMin() {
        UnicodeSet set = new UnicodeSet(0x0000, 0x10FFFF);
        try {
            set.contains(-1); // less than MIN_VALUE (0)
            throw new RuntimeException("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("U+")) {
                throw new RuntimeException("Exception message does not contain code point info");
            }
        }
    }

    private static void testContainsInvalidCodePointAboveMax() {
        UnicodeSet set = new UnicodeSet(0x0000, 0x10FFFF);
        try {
            set.contains(0x110000); // greater than MAX_VALUE (0x10FFFF)
            throw new RuntimeException("Expected IllegalArgumentException for code point 0x110000");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("U+")) {
                throw new RuntimeException("Exception message does not contain code point info");
            }
        }
    }
}
