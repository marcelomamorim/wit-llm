/*
 * @test
 * @summary Behavioral tests for UnicodeSet.complement(int start, int end)
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testComplementToggleRange();
        testComplementReturnsThis();
        testReversedRangeIsNoOp();
        testInvalidStartThrows();
        testInvalidEndThrows();
        testFrozenSetRejectsComplement();
    }

    static void testComplementToggleRange() {
        UnicodeSet s = new UnicodeSet(0x41, 0x43);
        check(s.contains(0x41) && s.contains(0x42) && s.contains(0x43), "initial set A-C");
        s.complement(0x42, 0x44);
        check(s.contains(0x41), "A must remain");
        check(!s.contains(0x42), "B must be removed by toggle");
        check(!s.contains(0x43), "C must be removed by toggle");
        check(s.contains(0x44), "D must be added by toggle");
    }

    static void testComplementReturnsThis() {
        UnicodeSet s = new UnicodeSet(0x41, 0x43);
        UnicodeSet r = s.complement(0x41, 0x41);
        check(r == s, "complement must return this");
    }

    static void testReversedRangeIsNoOp() {
        UnicodeSet s = new UnicodeSet(0x41, 0x43);
        s.complement(0x44, 0x41);
        check(s.contains(0x42), "B must still be present after reversed range");
        check(!s.contains(0x44), "D must not be added by reversed range");
    }

    static void testInvalidStartThrows() {
        expectIllegalArgument(-1, 5);
        expectIllegalArgument(0x110000, 0x110001);
    }

    static void testInvalidEndThrows() {
        expectIllegalArgument(5, -1);
        expectIllegalArgument(0x41, 0x110000);
    }

    static void testFrozenSetRejectsComplement() {
        UnicodeSet s = new UnicodeSet(0x41, 0x43);
        s.freeze();
        boolean thrown = false;
        try {
            s.complement(0x42, 0x42);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "frozen set must reject complement");
    }

    static void expectIllegalArgument(int start, int end) {
        UnicodeSet s = new UnicodeSet(0x41, 0x43);
        boolean thrown = false;
        try {
            s.complement(start, end);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for start=" + start + " end=" + end);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
