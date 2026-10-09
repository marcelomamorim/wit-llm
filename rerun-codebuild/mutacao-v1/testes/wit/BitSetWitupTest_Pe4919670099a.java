/* @test    @summary Test for BitSet.clear(int, int) exception paths    @run main BitSetWitupTest_Pe4919670099a */

import java.util.BitSet;

public class BitSetWitupTest_Pe4919670099a {

    public static void main(String[] args) {
        testClearWithFromIndexGreaterThanToIndex();
        testClearWithNegativeFromIndex();
        testClearWithNegativeToIndex();
    }

    private static void testClearWithFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(10, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testClearWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(-1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testClearWithNegativeToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative toIndex");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
