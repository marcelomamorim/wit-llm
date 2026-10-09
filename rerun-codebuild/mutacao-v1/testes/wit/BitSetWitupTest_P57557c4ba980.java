/* @test    @summary Test for BitSet.flip(int, int) method to verify IndexOutOfBoundsException behavior.    @run main BitSetWitupTest_P57557c4ba980 */

import java.util.BitSet;

public class BitSetWitupTest_P57557c4ba980 {

    public static void main(String[] args) {
        testFlipWithToIndexGreaterThanFromIndex();
        testFlipWithFromIndexGreaterThanZeroAndToIndexLessThanZero();
        testFlipWithFromIndexLessThanZero();
    }

    private static void testFlipWithToIndexGreaterThanFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.flip(10, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for flip(10, 5)");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testFlipWithFromIndexGreaterThanZeroAndToIndexLessThanZero() {
        BitSet bs = new BitSet();
        try {
            bs.flip(5, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for flip(5, -1)");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testFlipWithFromIndexLessThanZero() {
        BitSet bs = new BitSet();
        try {
            bs.flip(-3, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for flip(-3, 10)");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
