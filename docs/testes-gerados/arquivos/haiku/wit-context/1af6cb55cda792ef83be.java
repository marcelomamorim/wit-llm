/*
 * @test
 * @summary WITUP-derived behavioral tests for UnicodeSet.contains(int)
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        UnicodeSet set = new UnicodeSet(0x41, 0x5A);
        check(set.contains(0x41), "A should be contained");
        check(set.contains(0x5A), "Z should be contained");
        check(!set.contains(0x40), "@ should not be contained");
        check(!set.contains(0x5B), "[ should not be contained");
        check(!set.contains(0x61), "a should not be contained");

        UnicodeSet high = new UnicodeSet(0x10000, 0x10FFFF);
        check(high.contains(0x10FFFF), "U+10FFFF should be contained");
        check(high.contains(0x10000), "U+10000 should be contained");
        check(!high.contains(0xFFFF), "U+FFFF should not be contained");

        UnicodeSet pattern = new UnicodeSet("[a-c]");
        check(pattern.contains('a'), "a should be contained by [a-c]");
        check(pattern.contains('c'), "c should be contained by [a-c]");
        check(!pattern.contains('d'), "d should not be contained by [a-c]");

        expectIllegalArgument(set, -1);
        expectIllegalArgument(set, 0x110000);
    }

    private static void expectIllegalArgument(UnicodeSet set, int c) {
        try {
            set.contains(c);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for code point " + c);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
    
    private static void check(boolean condition) {
        check(condition, "Unexpected result");
    }
}
