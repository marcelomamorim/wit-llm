/*
 * @test
 * @summary Verifies observable behavior of BitSet.clear(int)
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeIndexThrowsAndLeavesStateUnchanged();
        testClearOutOfRangeIsNoop();
        testClearSetBitMakesItClear();
        testClearOnlyTargetBit();
        testClearTopBitShrinksLength();
        testClearUnsetBitInRangeIsNoop();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeIndexThrowsAndLeavesStateUnchanged() {
        BitSet b = new BitSet();
        b.set(2);
        boolean thrown = false;
        try {
            b.clear(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "clear(-1) must throw IndexOutOfBoundsException");
        check(b.cardinality() == 1, "state must be unchanged after exception");
        check(b.get(2), "bit 2 must remain set after exception");
    }

    static void testClearOutOfRangeIsNoop() {
        BitSet b = new BitSet();
        b.set(3);
        int lengthBefore = b.length();
        b.clear(100);
        check(b.cardinality() == 1, "clearing beyond logical size must not change cardinality");
        check(b.get(3), "bit 3 must remain set");
        check(b.length() == lengthBefore, "length must not change when clearing beyond size");
    }

    static void testClearSetBitMakesItClear() {
        BitSet b = new BitSet();
        b.set(5);
        check(b.get(5), "precondition: bit 5 set");
        b.clear(5);
        check(!b.get(5), "bit 5 must be clear after clear(5)");
        check(b.isEmpty(), "bitset must be empty after clearing its only bit");
        check(b.length() == 0, "length must be 0 for empty bitset");
        check(b.cardinality() == 0, "cardinality must be 0");
    }

    static void testClearOnlyTargetBit() {
        BitSet b = new BitSet();
        b.set(1);
        b.set(7);
        b.set(70);
        b.clear(7);
        check(b.get(1), "bit 1 must remain set");
        check(!b.get(7), "bit 7 must be cleared");
        check(b.get(70), "bit 70 must remain set");
        check(b.cardinality() == 2, "cardinality must be 2");
    }

    static void testClearTopBitShrinksLength() {
        BitSet b = new BitSet();
        b.set(0);
        b.set(100);
        check(b.length() == 101, "precondition: length must be 101");
        b.clear(100);
        check(b.length() == 1, "length must shrink to 1 after clearing highest bit");
        check(b.get(0), "bit 0 must remain set");
        check(!b.get(100), "bit 100 must be clear");
    }

    static void testClearUnsetBitInRangeIsNoop() {
        BitSet b = new BitSet();
        b.set(0);
        b.set(10);
        b.clear(5);
        check(b.cardinality() == 2, "clearing an unset bit must not change cardinality");
        check(b.get(0) && b.get(10), "set bits must remain set");
        check(b.length() == 11, "length must remain 11");
    }
}
