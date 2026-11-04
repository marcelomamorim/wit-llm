/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.complement(int,int) method
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testComplementValidRange();
        testComplementStartGreaterThanEnd();
        testComplementStartBelowMinValue();
        testComplementEndAboveMaxValue();
    }

    private static void testComplementValidRange() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        set.freeze(); // freeze to test checkFrozen throws on complement
        try {
            set.complement(0x41, 0x5A); // A-Z
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected because set is frozen
        }

        // Now test complement on unfrozen set
        UnicodeSet set2 = new UnicodeSet(0, 0x10FFFF);
        set2.complement(0x41, 0x5A); // complement A-Z
        // The set should now not contain A-Z
        for (int c = 0x41; c <= 0x5A; c++) {
            if (set2.contains(c)) {
                throw new RuntimeException("complement failed to remove code point U+" + Integer.toHexString(c));
            }
        }
    }

    private static void testComplementStartGreaterThanEnd() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        // complement with start > end should do nothing but return this
        UnicodeSet result = set.complement(0x5A, 0x41);
        if (result != set) {
            throw new RuntimeException("complement should return this when start > end");
        }
        // The set should remain unchanged (contain all code points in range)
        if (!set.contains(0x41) || !set.contains(0x5A)) {
            throw new RuntimeException("Set changed unexpectedly when complement called with start > end");
        }
    }

    private static void testComplementStartBelowMinValue() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        try {
            set.complement(-1, 0x10);
            throw new RuntimeException("Expected IllegalArgumentException for start < MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testComplementEndAboveMaxValue() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        try {
            set.complement(0x10, 0x110000);
            throw new RuntimeException("Expected IllegalArgumentException for end > MAX_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
