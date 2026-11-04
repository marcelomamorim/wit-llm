/*
 * @test
 * @summary Tests for java.util.BitSet.set(int, int)
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testSetWithValidRange();
        testSetWithFromIndexNegative();
        testSetWithToIndexNegative();
        testSetWithFromIndexGreaterThanToIndex();
    }

    static void testSetWithValidRange() {
        BitSet bs = new BitSet();
        bs.set(0, 10);
        boolean allSet = true;
        for (int i = 0; i < 10; i++) {
            if (!bs.get(i)) {
                allSet = false;
                break;
            }
        }
        if (!allSet) {
            throw new AssertionError("Failed: bits 0-9 should be set")
        }
    }

    static void testSetWithFromIndexNegative() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0")
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSetWithToIndexNegative() {
        BitSet bs = new BitSet();
        try {
            bs.set(0, -5);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex < 0")
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSetWithFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.set(10, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex")
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
