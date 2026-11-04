/*
 * @test
 * @summary Verify that the UnicodeSet(int start, int end) constructor rejects out-of-range code points and accepts valid boundaries
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        expectIllegalArgument(-1, 0);
        expectIllegalArgument(0x110000, 0x110000);
        expectIllegalArgument(0, -1);
        expectIllegalArgument(0, 0x110000);

        UnicodeSet a = new UnicodeSet(0, 0);
        if (a == null) {
            throw new RuntimeException("constructor returned null for (0,0)");
        }
        UnicodeSet b = new UnicodeSet(0, 0x10FFFF);
        if (b == null) {
            throw new RuntimeException("constructor returned null for full range");
        }
        UnicodeSet c = new UnicodeSet(0x10FFFF, 0x10FFFF);
        if (c == null) {
            throw new RuntimeException("constructor returned null for max code point");
        }
    }

    private static void expectIllegalArgument(int start, int end) {
        try {
            new UnicodeSet(start, end);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for start=" + start + " end=" + end);
    }
}
