/*
 * @test
 * @summary Verify that the UnicodeSet(int start, int end) constructor contains exactly the given code point range
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        UnicodeSet range = new UnicodeSet(0x41, 0x5A);
        check(range.contains(0x41), "start bound 'A' should be contained");
        check(range.contains(0x5A), "end bound 'Z' should be contained");
        check(range.contains(0x44), "interior 'D' should be contained");
        check(!range.contains(0x40), "code point below start should not be contained");
        check(!range.contains(0x5B), "code point above end should not be contained");

        UnicodeSet single = new UnicodeSet(0x61, 0x61);
        check(single.contains(0x61), "single-point range should contain its point");
        check(!single.contains(0x62), "single-point range should not contain next point");
        check(!single.contains(0x60), "single-point range should not contain previous point");

        UnicodeSet full = new UnicodeSet(0, 0x10FFFF);
        check(full.contains(0), "full range should contain U+0000");
        check(full.contains(0x10FFFF), "full range should contain U+10FFFF");
        check(full.contains(0x1F600), "full range should contain a supplementary code point");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
