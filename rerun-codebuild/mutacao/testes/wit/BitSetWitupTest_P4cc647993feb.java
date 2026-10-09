/*
 * @test
 * @summary Tests for BitSet.get(int, int) method, focusing on exception paths.
 * @run main BitSetWitupTest_P4cc647993feb
 */

import java.util.BitSet;

public class BitSetWitupTest_P4cc647993feb {

    public static void main(String[] args) {
        testGetWithNegativeFromIndex();
        testGetWithFromIndexEqualToToIndex();
        testGetWithToIndexGreaterThanLength();
    }

    private static void testGetWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.get(-1, 5);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative fromIndex");
        }
    }

    private static void testGetWithFromIndexEqualToToIndex() {
        BitSet bs = new BitSet();
        try {
            BitSet result = bs.get(5, 5);
            if (result.length() == 0) {
                System.out.println("PASS: get(5,5) returns empty BitSet as expected");
            } else {
                System.out.println("FAIL: get(5,5) did not return empty BitSet");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for get(5,5): " + e);
        }
    }

    private static void testGetWithToIndexGreaterThanLength() {
        BitSet bs = new BitSet();
        bs.set(0, 10); // set bits up to index 9
        try {
            BitSet result = bs.get(0, 20); // toIndex beyond length
            if (result.length() <= 10) {
                System.out.println("PASS: get(0,20) handled toIndex beyond length correctly");
            } else {
                System.out.println("FAIL: get(0,20) returned unexpected length");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for get(0,20): " + e);
        }
    }
}
