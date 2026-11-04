/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.complement(int,int) for exceptions and basic behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testComplementFrozenThrows();
        testComplementInvalidStartThrows();
        testComplementInvalidEndThrows();
        testComplementValidRange();
    }

    private static void testComplementFrozenThrows() {
        UnicodeSet set = new UnicodeSet(0x41, 0x5A); // A-Z
        set.freeze();
        try {
            set.complement(0x30, 0x39); // 0-9
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    private static void testComplementInvalidStartThrows() {
        UnicodeSet set = new UnicodeSet(0x41, 0x5A); // A-Z
        try {
            set.complement(-1, 0x5A); // invalid start < MIN_VALUE (0)
            throw new RuntimeException("Expected IllegalArgumentException for invalid start not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            set.complement(0x110000, 0x5A); // invalid start > MAX_VALUE (0x10FFFF)
            throw new RuntimeException("Expected IllegalArgumentException for invalid start not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testComplementInvalidEndThrows() {
        UnicodeSet set = new UnicodeSet(0x41, 0x5A); // A-Z
        try {
            set.complement(0x41, -1); // invalid end < MIN_VALUE
            throw new RuntimeException("Expected IllegalArgumentException for invalid end not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            set.complement(0x41, 0x110000); // invalid end > MAX_VALUE
            throw new RuntimeException("Expected IllegalArgumentException for invalid end not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testComplementValidRange() {
        UnicodeSet set = new UnicodeSet(0x41, 0x5A); // A-Z
        set.complement(0x30, 0x39); // complement 0-9
        // After complement, the set should contain 0-9 plus original minus 0-9 range
        // We test that the set contains '0' and does not contain 'A' (since complement toggles)
        if (!set.contains(0x30)) {
            throw new RuntimeException("Expected set to contain '0' after complement");
        }
        if (set.contains(0x41)) {
            throw new RuntimeException("Expected set to not contain 'A' after complement");
        }
    }
}
