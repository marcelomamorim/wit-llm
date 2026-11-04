/*
 * @test
 * @summary Test for BitSet.get(int) method, covering normal and exceptional cases.
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
    }

    private static void testGetWithValidIndex() {
        BitSet bs = new BitSet();
        bs.set(10);
        boolean result = bs.get(10);
        if (!result) {
            throw new AssertionError("Expected true for index 10, but got false.");
        }
        System.out.println("testGetWithValidIndex passed.");
    }

    private static void testGetWithNegativeIndex() {
        BitSet bs = new BitSet();
        boolean exceptionThrown = false;
        try {
            bs.get(-1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index.");
        }
        System.out.println("testGetWithNegativeIndex passed.");
    }
}
