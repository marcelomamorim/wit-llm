/* @test    @summary Test UnicodeSet constructor with valid ranges    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidRange();
        testConstructorWithSingleCodePoint();
        testConstructorWithEmptyRange();
    }

    private static void testConstructorWithValidRange() {
        UnicodeSet set = new UnicodeSet(0x0041, 0x005A); // A-Z
        boolean containsA = set.contains('A');
        boolean containsZ = set.contains('Z');
        boolean containsB = set.contains('B');
        if (!containsA || !containsZ || containsB) {
            throw new AssertionError("Failed: Valid range did not produce expected contains results")
        }
    }

    private static void testConstructorWithSingleCodePoint() {
        UnicodeSet set = new UnicodeSet(0x1F600, 0x1F600); // 😀
        if (!set.contains(0x1F600)) {
            throw new AssertionError("Failed: Single code point not contained")
        }
        if (set.contains(0x1F601)) {
            throw new AssertionError("Failed: Code point outside single code point range")
        }
    }

    private static void testConstructorWithEmptyRange() {
        UnicodeSet set = new UnicodeSet(0x0061, 0x0060); // 'a' to '`' (empty range)
        if (set.contains('a') || set.contains('z')) {
            throw new AssertionError("Failed: Empty range should not contain any characters")
        }
    }
}
