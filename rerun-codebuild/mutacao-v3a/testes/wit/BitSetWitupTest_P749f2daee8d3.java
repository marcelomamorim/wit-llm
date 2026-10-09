/* @test    @summary Test for BitSet.clear(int, int) exception handling and behavior   @run main BitSetWitupTest_P749f2daee8d3 */

import java.util.BitSet;

public class BitSetWitupTest_P749f2daee8d3 {

    public static void main(String[] args) {
        testClearWithInvalidRange();
        testClearWithNegativeFromIndex();
        testClearWithFromIndexGreaterThanToIndex();
    }

    private static void testClearWithInvalidRange() {
        BitSet bs = new BitSet();
        try {
            bs.clear(0, -1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for fromIndex=0, toIndex=-1");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for fromIndex=0, toIndex=-1");
        }
    }

    private static void testClearWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(-5, 10);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for fromIndex=-5, toIndex=10");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for fromIndex=-5, toIndex=10");
        }
    }

    private static void testClearWithFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(10, 5);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for fromIndex=10, toIndex=5");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for fromIndex=10, toIndex=5");
        }
    }
}
