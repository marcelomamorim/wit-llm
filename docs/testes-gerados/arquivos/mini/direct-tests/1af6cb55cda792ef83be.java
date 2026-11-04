/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.contains(int) method
 * @run main UnicodeSetWitupTest
 */

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testContainsValidCodePointInRange();
        testContainsValidCodePointOutOfRange();
        testContainsBmpSet();
        testContainsStringSpan();
    }

    private static void testContainsValidCodePointInRange() {
        // Create a UnicodeSet with a range that includes 'A' (U+0041)
        sun.text.normalizer.UnicodeSet set = new sun.text.normalizer.UnicodeSet(0x0041, 0x0041);
        if (!set.contains(0x0041)) {
            throw new RuntimeException("Expected set to contain U+0041");
        }
        if (set.contains(0x0042)) {
            throw new RuntimeException("Expected set NOT to contain U+0042");
        }
    }

    private static void testContainsValidCodePointOutOfRange() {
        sun.text.normalizer.UnicodeSet set = new sun.text.normalizer.UnicodeSet(0x0000, 0x007F);
        // Test lowest valid code point
        if (!set.contains(0x0000)) {
            throw new RuntimeException("Expected set to contain U+0000");
        }
        // Test highest valid code point
        if (set.contains(0x110000)) {
            throw new RuntimeException("Expected set NOT to contain U+110000 (invalid code point)");
        }
        // Test code point just above MAX_VALUE (0x10FFFF)
        try {
            set.contains(0x110000);
            throw new RuntimeException("Expected IllegalArgumentException for code point above MAX_VALUE");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        // Test code point below MIN_VALUE (-1)
        try {
            set.contains(-1);
            throw new RuntimeException("Expected IllegalArgumentException for code point below MIN_VALUE");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testContainsBmpSet() {
        // Create a UnicodeSet with BMP range
        sun.text.normalizer.UnicodeSet set = new sun.text.normalizer.UnicodeSet(0x0000, 0xFFFF);
        // The bmpSet field is private, but contains() uses it internally
        // Test a BMP code point
        if (!set.contains(0x0061)) { // 'a'
            throw new RuntimeException("Expected set to contain U+0061");
        }
        // Test a code point outside BMP
        if (set.contains(0x10000)) {
            throw new RuntimeException("Expected set NOT to contain U+10000");
        }
    }

    private static void testContainsStringSpan() {
        // Create a UnicodeSet with a pattern that includes strings
        sun.text.normalizer.UnicodeSet set = new sun.text.normalizer.UnicodeSet("[a{ab}]");
        // 'a' is contained
        if (!set.contains('a')) {
            throw new RuntimeException("Expected set to contain 'a'");
        }
        // 'b' alone is not contained
        if (set.contains('b')) {
            throw new RuntimeException("Expected set NOT to contain 'b'");
        }
        // Code point outside BMP
        if (set.contains(0x10000)) {
            throw new RuntimeException("Expected set NOT to contain U+10000");
        }
    }
}
