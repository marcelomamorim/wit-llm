/*
 * @test
 * @summary WITUP-derived behavioral tests for UnicodeSet.add(int)
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testAddThenContains();
        testAddOnFrozenSetThrowsUnsupportedOperation();
        testAddOutOfRangeCodePointThrowsIllegalArgument();
    }

    static void testAddThenContains() {
        UnicodeSet set = new UnicodeSet("[a]");
        set.add('b');
        if (!set.contains('b')) {
            throw new RuntimeException("added code point not contained");
        }
    }

    static void testAddOnFrozenSetThrowsUnsupportedOperation() {
        UnicodeSet set = new UnicodeSet("[a]");
        set.freeze();
        try {
            set.add('z');
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testAddOutOfRangeCodePointThrowsIllegalArgument() {
        UnicodeSet set = new UnicodeSet("[a]");
        try {
            set.add(-1);
            throw new RuntimeException("expected IllegalArgumentException for -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            set.add(0x110000);
            throw new RuntimeException("expected IllegalArgumentException for 0x110000");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
