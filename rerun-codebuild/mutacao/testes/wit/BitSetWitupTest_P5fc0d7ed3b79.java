/*
 * @test
 * @summary Test for BitSet.nextClearBit(int) method, focusing on exception path for invalid input.
 * @run main BitSetWitupTest_P5fc0d7ed3b79
 */

import java.util.BitSet;

public class BitSetWitupTest_P5fc0d7ed3b79 {

    public static void main(String[] args) {
        testNextClearBitWithNegativeIndex();
        testNextClearBitWithValidIndex();
    }

    private static void testNextClearBitWithNegativeIndex() {
        BitSet bs = new BitSet();
        int invalidIndex = -1;
        try {
            bs.nextClearBit(invalidIndex);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for index " + invalidIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for index " + invalidIndex);
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception type: " + t);
        }
    }

    private static void testNextClearBitWithValidIndex() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(2);
        int index = 1;
        int result = bs.nextClearBit(index);
        if (result == 1) {
            System.out.println("PASS: nextClearBit at index " + index + " returned " + result);
        } else {
            System.out.println("FAIL: nextClearBit at index " + index + " returned " + result);
        }
    }
}
