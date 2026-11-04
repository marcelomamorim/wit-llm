/*
 * @test
 * @summary Verify BitSet.set(int, boolean) sets or clears the bit at the given index
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        BitSet bs = new BitSet();

        bs.set(5, true);
        check(bs.get(5), "bit 5 should be set after set(5, true)");
        check(bs.cardinality() == 1, "cardinality should be 1 after setting one bit");

        bs.set(5, false);
        check(!bs.get(5), "bit 5 should be cleared after set(5, false)");
        check(bs.isEmpty(), "BitSet should be empty after clearing its only bit");

        bs.set(0, true);
        bs.set(63, true);
        bs.set(64, true);
        bs.set(63, false);
        check(bs.get(0), "bit 0 should remain set");
        check(!bs.get(63), "bit 63 should be cleared");
        check(bs.get(64), "bit 64 should remain set across word boundary");
        check(bs.cardinality() == 2, "cardinality should be 2, but was " + bs.cardinality());

        bs.set(0, true);
        check(bs.cardinality() == 2, "setting an already-set bit should not change cardinality");

        BitSet empty = new BitSet();
        empty.set(1000, false);
        check(empty.length() == 0, "clearing a bit beyond length should not grow the set");
        check(empty.isEmpty(), "set should remain empty after clearing an out-of-range bit");

        boolean thrown = false;
        try {
            new BitSet().set(-1, true);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(-1, true) should throw IndexOutOfBoundsException");

        thrown = false;
        try {
            new BitSet().set(-1, false);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(-1, false) should throw IndexOutOfBoundsException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
