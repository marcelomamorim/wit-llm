/*
 * @test
 * @summary Witup-derived tests for UnicodeSet.contains(int)
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

public class UnicodeSetWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIAE(UnicodeSet set, int c) {
        try {
            set.contains(c);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + c);
    }

    public static void main(String[] args) {
        UnicodeSet range = new UnicodeSet(0x41, 0x5A);
        check(range.contains(0x41), "U+0041 should be in [A-Z]");
        check(range.contains(0x5A), "U+005A should be in [A-Z]");
        check(range.contains(0x4D), "U+004D should be in [A-Z]");
        check(!range.contains(0x40), "U+0040 should not be in [A-Z]");
        check(!range.contains(0x5B), "U+005B should not be in [A-Z]");
        check(!range.contains(0x61), "U+0061 should not be in [A-Z]");

        UnicodeSet pattern = new UnicodeSet("[a-c]");
        check(pattern.contains(0x61), "'a' should be in [a-c]");
        check(pattern.contains(0x62), "'b' should be in [a-c]");
        check(pattern.contains(0x63), "'c' should be in [a-c]");
        check(!pattern.contains(0x64), "'d' should not be in [a-c]");
        check(!pattern.contains(0x60), "'`' should not be in [a-c]");

        UnicodeSet supplementary = new UnicodeSet(0x10000, 0x10FFFF);
        check(supplementary.contains(0x10000), "U+10000 should be contained");
        check(supplementary.contains(0x10FFFF), "U+10FFFF should be contained");
        check(!supplementary.contains(0xFFFF), "U+FFFF should not be contained");

        expectIAE(range, -1);
        expectIAE(range, 0x110000);
        expectIAE(pattern, -1);
        expectIAE(supplementary, 0x110000);
    }
}
