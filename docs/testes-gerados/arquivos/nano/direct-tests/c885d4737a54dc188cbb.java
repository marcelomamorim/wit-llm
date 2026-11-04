/*
 * @test
 * @summary Tests the set(int, boolean) method of java.util.BitSet
 * @run main BitSetSetMethodTest
 */

import java.util.BitSet;

public class BitSetSetMethodTest {
    public static void main(String[] args) {
        testSetTrueSetsBit();
        testSetFalseClearsBit();
        testSetAtHighIndex();
        testSetAtZero();
    }

    static void testSetTrueSetsBit() {
        BitSet bs = new BitSet();
        int index = 10;
        bs.set(index, true);
        if (!bs.get(index)) {
            throw new AssertionError("Bit at index " + index + " should be set to true.");
        }
    }

    static void testSetFalseClearsBit() {
        BitSet bs = new BitSet();
        int index = 15;
        bs.set(index, true);
        bs.set(index, false);
        if (bs.get(index)) {
            throw new AssertionError("Bit at index " + index + " should be cleared to false.");
        }
    }

    static void testSetAtHighIndex() {
        BitSet bs = new BitSet();
        int index = 1000;
        bs.set(index, true);
        if (!bs.get(index)) {
            throw new AssertionError("Bit at high index " + index + " should be set to true.");
        }
    }

    static void testSetAtZero() {
        BitSet bs = new BitSet();
        int index = 0;
        bs.set(index, true);
        if (!bs.get(index)) {
            throw new AssertionError("Bit at index zero should be set to true.");
        }
    }
}
