/* @test    @summary Test UnicodeSet.contains(int c) for various code points    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testContainsBasic();
        testContainsInvalid();
        testContainsEdgeCases();
    }

    static void testContainsBasic() {
        UnicodeSet set = new UnicodeSet("[a-z]");
        // Test a character within the set
        boolean result = set.contains('a');
        if (!result) {
            throw new AssertionError("Expected 'a' to be contained in the set");
        }
        // Test a character outside the set
        result = set.contains('0');
        if (result) {
            throw new AssertionError("Did not expect '0' to be contained in the set");
        }
    }

    static void testContainsInvalid() {
        UnicodeSet set = new UnicodeSet("[\u0000-\u10FFFF]");
        try {
            set.contains(-1);
            throw new AssertionError("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            set.contains(0x110000);
            throw new AssertionError("Expected IllegalArgumentException for code point > U+10FFFF");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testContainsEdgeCases() {
        UnicodeSet set = new UnicodeSet("[\u0000-\u0000]");
        // Test minimum code point
        boolean result = set.contains(0x0000);
        if (!result) {
            throw new AssertionError("Expected U+0000 to be contained in the set");
        }
        // Test maximum code point
        set = new UnicodeSet("[\u10FFFF-\u10FFFF]");
        result = set.contains(0x10FFFF);
        if (!result) {
            throw new AssertionError("Expected U+10FFFF to be contained in the set");
        }
    }
}
