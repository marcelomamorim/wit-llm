/* @test    @summary Test for BitSet.nextSetBit(int) method, focusing on exception path for invalid input.    @run main BitSetWitupTest_P6ac520142381 */

import java.util.BitSet;

public class BitSetWitupTest_P6ac520142381 {

    public static void main(String[] args) {
        testNextSetBitWithNegativeIndex();
    }

    private static void testNextSetBitWithNegativeIndex() {
        BitSet bs = new BitSet();
        int invalidIndex = -1;
        try {
            bs.nextSetBit(invalidIndex);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex = " + invalidIndex);
        } catch (IndexOutOfBoundsException e) {
            // Expected exception, test passes
            System.out.println("Passed: IndexOutOfBoundsException thrown as expected for fromIndex = " + invalidIndex);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
