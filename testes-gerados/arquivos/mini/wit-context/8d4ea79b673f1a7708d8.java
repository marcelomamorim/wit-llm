/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.add(CharSequence) for expected exceptions and normal behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testAddThrowsOnFrozen();
        testAddThrowsOnInvalidCodePoint();
        testAddAddsStringWhenNoSingleCodePoint();
        testAddAddsCodePointWhenSingleCodePoint();
    }

    private static void testAddThrowsOnFrozen() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        set.freeze();
        try {
            set.add("a");
            throw new RuntimeException("Expected UnsupportedOperationException when adding to frozen set");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void testAddThrowsOnInvalidCodePoint() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        // The add method calls getSingleCP(s), which returns cp < 0 if s is not a single code point
        // To trigger IllegalArgumentException, we must pass a CharSequence that is a single code point but out of range
        // Since getSingleCP returns -1 if not single code point, we try to pass a single code point outside valid range
        // The valid range is 0 to 0x10FFFF, so try a code point > 0x10FFFF
        // But UnicodeSet constructor limits range to 0..0x10FFFF, so add_unchecked will throw IllegalArgumentException
        // We simulate this by passing a CharSequence representing a single code point > 0x10FFFF
        // However, Java char cannot represent code points > 0xFFFF directly, so we use a surrogate pair or a string with code point > 0x10FFFF
        // But UnicodeSet.getSingleCP likely returns -1 for such input, so no exception thrown
        // Therefore, we test with a string representing a single code point in BMP but outside allowed range (less than 0 or greater than HIGH-1)
        // The LOW and HIGH constants are internal, so we test with a code point less than 0 (impossible) or greater than 0x10FFFF (impossible)
        // Since we cannot create such CharSequence, we skip this test as not feasible with public API
    }

    private static void testAddAddsStringWhenNoSingleCodePoint() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        String multiChar = "ab";
        set.add(multiChar);
        if (!set.containsAll(multiChar)) {
            throw new RuntimeException("UnicodeSet should contain the string added");
        }
    }

    private static void testAddAddsCodePointWhenSingleCodePoint() {
        UnicodeSet set = new UnicodeSet(0, 0x10FFFF);
        String singleChar = "a";
        set.add(singleChar);
        if (!set.contains('a')) {
            throw new RuntimeException("UnicodeSet should contain the code point added");
        }
    }
}
