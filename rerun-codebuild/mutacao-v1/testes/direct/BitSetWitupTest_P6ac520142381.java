/* @test    @summary Test for BitSet.nextSetBit(int fromIndex) method    @run main BitSetWitupTest_P6ac520142381 */

import java.util.BitSet;

public class BitSetWitupTest_P6ac520142381 {
    public static void main(String[] args) {
        testNextSetBitWithSetBits();
        testNextSetBitWithNoSetBits();
        testNextSetBitWithNegativeFromIndex();
        testNextSetBitWithFromIndexBeyondSize();
    }

    static void testNextSetBitWithSetBits() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(5);
        bs.set(10);
        int result1 = bs.nextSetBit(0);
        int result2 = bs.nextSetBit(3);
        int result3 = bs.nextSetBit(6);
        int result4 = bs.nextSetBit(11);
        if (result1 != 2 || result2 != 5 || result3 != 10 || result4 != -1) {
            throw new AssertionError("Failed testNextSetBitWithSetBits: " +
                "results: " + result1 + ", " + result2 + ", " + result3 + ", " + result4);
        }
    }

    static void testNextSetBitWithNoSetBits() {
        BitSet bs = new BitSet();
        int result = bs.nextSetBit(0);
        if (result != -1) {
            throw new AssertionError("Failed testNextSetBitWithNoSetBits: " + result);
        }
    }

    static void testNextSetBitWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        bs.set(1);
        try {
            bs.nextSetBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNextSetBitWithFromIndexBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(3);
        int result = bs.nextSetBit(10);
        if (result != -1) {
            throw new AssertionError("Failed testNextSetBitWithFromIndexBeyondSize: " + result);
        }
    }
}
