/*
 * @test
 * @summary Witup-derived tests for BitSet.set(int, int): valid ranges, empty ranges, multi-word ranges and IndexOutOfBoundsException paths
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testEmptyRange();
        testMultiWordRange();
        testNegativeFromIndex();
        testNegativeToIndex();
        testFromGreaterThanTo();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidRange() {
        BitSet b = new BitSet();
        b.set(2, 5);
        check(!b.get(1), "bit 1 must be clear");
        check(b.get(2), "bit 2 must be set");
        check(b.get(3), "bit 3 must be set");
        check(b.get(4), "bit 4 must be set");
        check(!b.get(5), "toIndex is exclusive; bit 5 must be clear");
        check(b.cardinality() == 3, "cardinality must be 3 but was " + b.cardinality());
    }

    static void testEmptyRange() {
        BitSet b = new BitSet();
        b.set(4, 4);
        check(b.cardinality() == 0, "empty range must not set bits");
        b.set(0, 0);
        check(b.cardinality() == 0, "empty range at 0 must not set bits");
        check(b.isEmpty(), "set must be empty after empty ranges");
    }

    static void testMultiWordRange() {
        BitSet b = new BitSet();
        b.set(60, 130);
        check(b.cardinality() == 70, "cardinality must be 70 but was " + b.cardinality());
        check(!b.get(59), "bit 59 must be clear");
        check(b.get(60), "bit 60 must be set");
        check(b.get(63), "bit 63 must be set");
        check(b.get(64), "bit 64 must be set");
        check(b.get(129), "bit 129 must be set");
        check(!b.get(130), "bit 130 must be clear");
        check(b.length() == 130, "length must be 130 but was " + b.length());
    }

    static void expectIndexOutOfBounds(int fromIndex, int toIndex) {
        BitSet b = new BitSet();
        try {
            b.set(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) {
            check(b.isEmpty(), "bitset must remain unchanged after exception for "
                    + fromIndex + "," + toIndex);
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for set("
                + fromIndex + ", " + toIndex + ")");
    }

    static void testNegativeFromIndex() {
        expectIndexOutOfBounds(-1, 5);
        expectIndexOutOfBounds(-1, -1);
    }

    static void testNegativeToIndex() {
        expectIndexOutOfBounds(5, -1);
        expectIndexOutOfBounds(0, -3);
    }

    static void testFromGreaterThanTo() {
        expectIndexOutOfBounds(5, 3);
        expectIndexOutOfBounds(1, 0);
    }
}
