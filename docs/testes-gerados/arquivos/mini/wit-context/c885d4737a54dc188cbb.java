/*
 * @test
 * @summary Test java.util.BitSet.set(int, boolean) for boundary and exception cases
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testSetTrueValidIndex();
        testSetFalseValidIndex();
        testSetTrueNegativeIndex();
        testSetFalseNegativeIndex();
    }

    private static void testSetTrueValidIndex() {
        BitSet bs = new BitSet();
        bs.set(0, true);
        if (!bs.get(0)) {
            throw new AssertionError("Bit 0 should be set to true");
        }
        bs.set(1000, true);
        if (!bs.get(1000)) {
            throw new AssertionError("Bit 1000 should be set to true");
        }
    }

    private static void testSetFalseValidIndex() {
        BitSet bs = new BitSet();
        bs.set(0, true);
        bs.set(0, false);
        if (bs.get(0)) {
            throw new AssertionError("Bit 0 should be cleared (false)");
        }
        bs.set(500, true);
        bs.set(500, false);
        if (bs.get(500)) {
            throw new AssertionError("Bit 500 should be cleared (false)");
        }
    }

    private static void testSetTrueNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1, true);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index with value=true");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSetFalseNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1, false);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index with value=false");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
