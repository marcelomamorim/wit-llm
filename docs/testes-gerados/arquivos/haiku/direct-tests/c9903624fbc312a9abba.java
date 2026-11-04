/*
 * @test
 * @summary Verifies observable behavior of BitSet.set(int) for valid, boundary, and negative indices
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testSetFirstBit();
        testWordBoundary();
        testNegativeIndexThrows();
        testIdempotent();
        testNoSideEffectOnOtherBits();
        testLargerIndex();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetFirstBit() {
        BitSet bs = new BitSet();
        bs.set(0);
        check(bs.get(0), "bit 0 should be set");
        check(bs.cardinality() == 1, "cardinality should be 1");
        check(bs.length() == 1, "length should be 1");
    }

    static void testWordBoundary() {
        BitSet bs = new BitSet();
        bs.set(63);
        bs.set(64);
        check(bs.get(63), "bit 63 should be set");
        check(bs.get(64), "bit 64 should be set");
        check(!bs.get(62), "bit 62 should not be set");
        check(!bs.get(65), "bit 65 should not be set");
        check(bs.cardinality() == 2, "cardinality should be 2");
        check(bs.length() == 65, "length should be 65");
    }

    static void testNegativeIndexThrows() {
        BitSet bs = new BitSet();
        boolean caught = false;
        try {
            bs.set(-1);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        check(caught, "set(-1) should throw IndexOutOfBoundsException");
        check(bs.cardinality() == 0, "failed set must not modify the bit set");
    }

    static void testIdempotent() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.set(5);
        check(bs.get(5), "bit 5 should remain set");
        check(bs.cardinality() == 1, "setting twice should keep cardinality 1");
    }

    static void testNoSideEffectOnOtherBits() {
        BitSet bs = new BitSet();
        bs.set(10);
        check(!bs.get(9), "bit 9 should not be set");
        check(!bs.get(11), "bit 11 should not be set");
        check(bs.cardinality() == 1, "cardinality should be 1");
    }

    static void testLargerIndex() {
        BitSet bs = new BitSet();
        bs.set(1000);
        check(bs.get(1000), "bit 1000 should be set");
        check(!bs.get(999), "bit 999 should not be set");
        check(bs.cardinality() == 1, "cardinality should be 1");
        check(bs.length() == 1001, "length should be 1001");
    }
}
