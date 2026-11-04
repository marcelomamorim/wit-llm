/*
 * @test
 * @summary Tests for BitSet.flip(int) observable behavior: toggling, negative index rejection, and expansion
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testFlipTogglesBit();
        testFlipNegativeIndexThrowsAndLeavesSetUnchanged();
        testFlipExpandsBeyondCurrentSize();
        testFlipOnEmptySetSetsLength();
        testFlipAtWordBoundary();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFlipTogglesBit() {
        BitSet bs = new BitSet();
        bs.flip(3);
        check(bs.get(3), "bit 3 should be set after first flip");
        check(bs.cardinality() == 1, "cardinality should be 1 after first flip");
        bs.flip(3);
        check(!bs.get(3), "bit 3 should be clear after second flip");
        check(bs.isEmpty(), "set should be empty after flipping twice");
    }

    static void testFlipNegativeIndexThrowsAndLeavesSetUnchanged() {
        BitSet bs = new BitSet();
        bs.set(5);
        boolean thrown = false;
        try {
            bs.flip(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "flip(-1) should throw IndexOutOfBoundsException");
        check(bs.cardinality() == 1 && bs.get(5), "set must be unchanged after failed flip");
    }

    static void testFlipExpandsBeyondCurrentSize() {
        BitSet bs = new BitSet(8);
        bs.flip(200);
        check(bs.get(200), "bit 200 should be set after flip");
        check(bs.length() == 201, "length should be 201 after flip(200), was " + bs.length());
        check(bs.cardinality() == 1, "only one bit should be set");
        bs.flip(200);
        check(bs.length() == 0, "length should be 0 after clearing the only bit, was " + bs.length());
    }

    static void testFlipOnEmptySetSetsLength() {
        BitSet bs = new BitSet();
        bs.flip(0);
        check(bs.get(0), "bit 0 should be set");
        check(bs.length() == 1, "length should be 1, was " + bs.length());
    }

    static void testFlipAtWordBoundary() {
        BitSet bs = new BitSet();
        bs.flip(63);
        bs.flip(64);
        check(bs.get(63) && bs.get(64), "bits 63 and 64 should be set");
        check(bs.cardinality() == 2, "cardinality should be 2");
        check(bs.length() == 65, "length should be 65, was " + bs.length());
        bs.flip(63);
        check(!bs.get(63) && bs.get(64), "only bit 64 should remain set");
        check(bs.cardinality() == 1, "cardinality should be 1");
    }
}
