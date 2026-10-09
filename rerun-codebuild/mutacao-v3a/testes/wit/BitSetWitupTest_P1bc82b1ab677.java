/* @test    @summary Test for BitSet.nextClearBit behavior and exception handling    @run main BitSetWitupTest_P1bc82b1ab677*/

import java.util.BitSet;

public class BitSetWitupTest_P1bc82b1ab677 {

    public static void main(String[] args) {
        testNextClearBitWithValidInput();
        testNextClearBitWithNegativeInput();
        testNextClearBitWithEmptyBitSet();
        testNextClearBitBeyondSize();
    }

    private static void testNextClearBitWithValidInput() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        int result = bs.nextClearBit(0);
        // Expect 1, as bit 1 is clear
        if (result != 1) {
            throw new AssertionError("Expected next clear bit at 1, but got " + result);
        }
    }

    private static void testNextClearBitWithNegativeInput() {
        BitSet bs = new BitSet();
        boolean exceptionThrown = false;
        try {
            bs.nextClearBit(-1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        }
    }

    private static void testNextClearBitWithEmptyBitSet() {
        BitSet bs = new BitSet();
        int result = bs.nextClearBit(0);
        // Expect 0, since all bits are clear
        if (result != 0) {
            throw new AssertionError("Expected next clear bit at 0, but got " + result);
        }
    }

    private static void testNextClearBitBeyondSize() {
        BitSet bs = new BitSet();
        bs.set(0);
        int result = bs.nextClearBit(1);
        // Expect 1, as bit 1 is clear and beyond current set bits
        if (result != 1) {
            throw new AssertionError("Expected next clear bit at 1, but got " + result);
        }
    }
}
