/*
 * @test
 * @summary Test java.util.BitSet.previousSetBit(int) method behavior
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testNegativeFromIndex();
        testFromIndexMinusOneReturnsMinusOne();
        testFromIndexBeyondLengthReturnsLastSetBit();
        testPreviousSetBitNormalCases();
    }

    private static void testNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.previousSetBit(-2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for fromIndex < -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testFromIndexMinusOneReturnsMinusOne() {
        BitSet bs = new BitSet();
        int result = bs.previousSetBit(-1);
        if (result != -1) {
            throw new RuntimeException("Expected -1 for fromIndex == -1, got " + result);
        }
    }

    private static void testFromIndexBeyondLengthReturnsLastSetBit() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(63);
        bs.set(100);
        int fromIndex = 200; // beyond any set bit
        int expected = 100;
        int result = bs.previousSetBit(fromIndex);
        if (result != expected) {
            throw new RuntimeException("Expected " + expected + " for fromIndex " + fromIndex + ", got " + result);
        }
    }

    private static void testPreviousSetBitNormalCases() {
        BitSet bs = new BitSet();
        // Set bits at 0, 1, 63, 64, 127
        bs.set(0);
        bs.set(1);
        bs.set(63);
        bs.set(64);
        bs.set(127);

        // fromIndex exactly at a set bit
        if (bs.previousSetBit(127) != 127) {
            throw new RuntimeException("Expected 127 for fromIndex 127");
        }

        // fromIndex between set bits
        if (bs.previousSetBit(126) != 64) {
            throw new RuntimeException("Expected 64 for fromIndex 126");
        }

        // fromIndex below first set bit
        if (bs.previousSetBit(0) != 0) {
            throw new RuntimeException("Expected 0 for fromIndex 0");
        }

        // fromIndex below any set bit
        if (bs.previousSetBit(-1) != -1) {
            throw new RuntimeException("Expected -1 for fromIndex -1");
        }

        // fromIndex between low set bits
        if (bs.previousSetBit(2) != 1) {
            throw new RuntimeException("Expected 1 for fromIndex 2");
        }
    }
}
