/* @test    @summary Test for BitSet.nextClearBit(int) method, focusing on exception path for negative fromIndex    @run main BitSetWitupTest_P039c2d1ca87a */

import java.util.BitSet;

public class BitSetWitupTest_P039c2d1ca87a {

    public static void main(String[] args) {
        testNextClearBitNegativeIndex();
        testNextClearBitValidIndex();
        testNextClearBitIndexBeyondWordsInUse();
    }

    private static void testNextClearBitNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextClearBit(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }

    private static void testNextClearBitValidIndex() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        int result = bs.nextClearBit(0);
        if (result != 1) {
            throw new AssertionError("Expected next clear bit at 1, but got " + result);
        }
    }

    private static void testNextClearBitIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        // No bits set, so next clear bit from a large index should be that index
        int index = 1000;
        int result = bs.nextClearBit(index);
        if (result != index) {
            throw new AssertionError("Expected next clear bit at " + index + ", but got " + result);
        }
    }
}
