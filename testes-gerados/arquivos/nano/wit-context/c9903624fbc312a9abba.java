/*
 * @test
 * @summary Tests for BitSet.set(int) method, focusing on exception path for invalid index.
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testSetWithNegativeIndex();
        testSetWithZeroIndex();
    }

    private static void testSetWithNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.set(-1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testSetWithZeroIndex() {
        BitSet bs = new BitSet();
        try {
            bs.set(0);
            if (bs.get(0)) {
                System.out.println("Passed: Bit at index 0 set successfully");
            } else {
                System.err.println("Failed: Bit at index 0 not set");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for index 0: " + e);
        }
    }
}
