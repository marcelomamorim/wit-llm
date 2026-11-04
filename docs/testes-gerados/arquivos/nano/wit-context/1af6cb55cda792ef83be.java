/*
 * @test
 * @summary Tests the contains(int c) method of UnicodeSet for valid and invalid code points.
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testContainsValidCodePoint();
        testContainsBelowMinValue();
        testContainsAboveMaxValue();
    }

    private static void testContainsValidCodePoint() {
        UnicodeSet set = new UnicodeSet(0x0041, 0x005A); // A-Z
        boolean result = set.contains(0x0041); // 'A'
        if (!result) {
            throw new AssertionError("Expected true for code point U+0041");
        }
        result = set.contains(0x005A); // 'Z'
        if (!result) {
            throw new AssertionError("Expected true for code point U+005A");
        }
    }

    private static void testContainsBelowMinValue() {
        UnicodeSet set = new UnicodeSet(0x0000, 0x10FFFF);
        try {
            set.contains(-1); // Below valid range
            throw new AssertionError("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testContainsAboveMaxValue() {
        UnicodeSet set = new UnicodeSet(0x0000, 0x10FFFF);
        try {
            set.contains(0x110000); // Above valid range
            throw new AssertionError("Expected IllegalArgumentException for code point 0x110000");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
