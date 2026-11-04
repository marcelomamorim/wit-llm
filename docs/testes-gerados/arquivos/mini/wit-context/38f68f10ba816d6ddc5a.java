/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.add(int) for exception and boundary behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testAddThrowsOnFrozen();
        testAddThrowsOnOutOfRange();
        testAddSucceedsOnValidInput();
    }

    private static void testAddThrowsOnFrozen() {
        UnicodeSet set = new UnicodeSet(0, 10);
        set.freeze();
        try {
            set.add(5);
            throw new RuntimeException("Expected UnsupportedOperationException when adding to frozen set");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void testAddThrowsOnOutOfRange() {
        UnicodeSet set = new UnicodeSet(0, 10);
        // Not frozen, so add_unchecked will be called
        // Add code point below LOW (0) and above HIGH-1 (10) to trigger IllegalArgumentException
        int belowLow = -1;
        int aboveHigh = 0x110000; // above Unicode max 0x10FFFF
        try {
            set.add(belowLow);
            throw new RuntimeException("Expected IllegalArgumentException for code point below LOW");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            set.add(aboveHigh);
            throw new RuntimeException("Expected IllegalArgumentException for code point above HIGH-1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testAddSucceedsOnValidInput() {
        UnicodeSet set = new UnicodeSet(0, 10);
        // Add a valid code point within range
        set.add(5);
        if (!set.contains(5)) {
            throw new RuntimeException("Expected set to contain code point 5 after add");
        }
    }
}
