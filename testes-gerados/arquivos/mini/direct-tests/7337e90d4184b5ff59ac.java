/*
 * @test
 * @summary Test retainAll method of UnicodeSet for correct intersection behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testRetainAllWithOverlap();
        testRetainAllWithNoOverlap();
        testRetainAllWithEmptyArgument();
        testRetainAllWithSelf();
    }

    private static void testRetainAllWithOverlap() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet set2 = new UnicodeSet(0x004D, 0x0060); // M-`
        set1.retainAll(set2);
        // After retainAll, set1 should contain M-Z only
        for (int c = 0x0041; c < 0x004D; c++) {
            if (set1.contains(c)) {
                throw new RuntimeException("retainAll failed: contains char before intersection start: " + (char)c);
            }
        }
        for (int c = 0x004D; c <= 0x005A; c++) {
            if (!set1.contains(c)) {
                throw new RuntimeException("retainAll failed: missing char in intersection: " + (char)c);
            }
        }
    }

    private static void testRetainAllWithNoOverlap() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x0045); // A-E
        UnicodeSet set2 = new UnicodeSet(0x0061, 0x0065); // a-e
        set1.retainAll(set2);
        // After retainAll, set1 should be empty
        for (int c = 0; c <= 0x10FFFF; c++) {
            if (set1.contains(c)) {
                throw new RuntimeException("retainAll failed: set1 should be empty but contains: " + Integer.toHexString(c));
            }
        }
    }

    private static void testRetainAllWithEmptyArgument() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet set2 = new UnicodeSet(); // empty set
        set1.retainAll(set2);
        // After retainAll with empty set, set1 should be empty
        for (int c = 0; c <= 0x10FFFF; c++) {
            if (set1.contains(c)) {
                throw new RuntimeException("retainAll failed: set1 should be empty after retainAll with empty set");
            }
        }
    }

    private static void testRetainAllWithSelf() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet set2 = new UnicodeSet(0x0041, 0x005A); // A-Z
        set1.retainAll(set2);
        // After retainAll with self, set1 should be unchanged
        for (int c = 0x0041; c <= 0x005A; c++) {
            if (!set1.contains(c)) {
                throw new RuntimeException("retainAll failed: set1 missing char after retainAll with self: " + (char)c);
            }
        }
    }
}
