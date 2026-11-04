/*
 * @test
 * @summary Test for BitSet.flip(int) method, including exception path for invalid index.
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testFlipWithValidIndex();
        testFlipWithNegativeIndex();
    }

    private static void testFlipWithValidIndex() {
        BitSet bs = new BitSet();
        int index = 10;
        bs.set(index);
        boolean beforeFlip = bs.get(index);
        bs.flip(index);
        boolean afterFlip = bs.get(index);
        if (beforeFlip == afterFlip) {
            throw new AssertionError("Flip did not change the bit state at index " + index);
        }
    }

    private static void testFlipWithNegativeIndex() {
        BitSet bs = new BitSet();
        int invalidIndex = -1;
        try {
            bs.flip(invalidIndex);
            throw new AssertionError("Expected IndexOutOfBoundsException for index " + invalidIndex);
        } catch (IndexOutOfBoundsException e) {
            // Expected exception, test passes
        }
    }
}
