/*
 * @test
 * @summary Witup-derived tests for BitSet.set(int): negative index rejection and expansion
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeIndexThrows();
        testSetAndGetBasic();
        testExpansionAcrossWordBoundary();
        testSetIsIdempotent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNegativeIndexThrows() {
        int[] invalidIndexes = {-1, Integer.MIN_VALUE};
        for (int idx : invalidIndexes) {
            BitSet bs = new BitSet();
            boolean thrown = false;
            try {
                bs.set(idx);
            } catch (IndexOutOfBoundsException e) {
                thrown = true;
            }
            check(thrown, "expected IndexOutOfBoundsException for bitIndex " + idx);
            check(bs.isEmpty(), "bit set modified after rejected index " + idx);
        }
    }

    static void testSetAndGetBasic() {
        BitSet bs = new BitSet();
        bs.set(0);
        check(bs.get(0), "bit 0 should be set");
        check(!bs.get(1), "bit 1 should be clear");
        check(bs.cardinality() == 1, "cardinality should be 1");
        check(bs.length() == 1, "length should be 1");

        bs.set(5);
        check(bs.get(5), "bit 5 should be set");
        check(bs.cardinality() == 2, "cardinality should be 2");
        check(bs.length() == 6, "length should be 6");
    }

    static void testExpansionAcrossWordBoundary() {
        BitSet bs = new BitSet();
        bs.set(64);
        check(bs.get(64), "bit 64 should be set after expansion");
        check(!bs.get(63), "bit 63 should be clear");
        check(bs.cardinality() == 1, "cardinality should be 1 after set(64)");
        check(bs.length() == 65, "length should be 65 after set(64)");

        bs.set(1000);
        check(bs.get(1000), "bit 1000 should be set");
        check(bs.nextSetBit(65) == 1000, "nextSetBit(65) should be 1000");
        check(bs.cardinality() == 2, "cardinality should be 2 after set(1000)");
    }

    static void testSetIsIdempotent() {
        BitSet bs = new BitSet();
        bs.set(7);
        bs.set(7);
        check(bs.get(7), "bit 7 should remain set");
        check(bs.cardinality() == 1, "repeated set must not change cardinality");
        check(bs.length() == 8, "length should be 8");
    }
}
