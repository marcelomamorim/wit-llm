/* @test    @summary Test for BitArray.get(int index) method to verify correct boolean retrieval and exception handling.    @run main BitArrayWitupTest_Pbe32f80675e2 */

import sun.security.util.BitArray;

public class BitArrayWitupTest_Pbe32f80675e2 {

    public static void main(String[] args) {
        testGetWithValidIndices();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToLength();
        testGetWithIndexGreaterThanLength();
    }

    private static void testGetWithValidIndices() {
        boolean[] bits = {true, false, true, false, true};
        BitArray ba = new BitArray(bits);
        for (int i = 0; i < bits.length; i++) {
            boolean result = ba.get(i);
            if (result != bits[i]) {
                throw new AssertionError("Failed at index " + i + ": expected " + bits[i] + " but got " + result);
            }
        }
    }

    private static void testGetWithNegativeIndex() {
        boolean[] bits = {true, false};
        BitArray ba = new BitArray(bits);
        try {
            ba.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexEqualToLength() {
        boolean[] bits = {true, false};
        BitArray ba = new BitArray(bits);
        try {
            ba.get(bits.length);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexGreaterThanLength() {
        boolean[] bits = {true, false};
        BitArray ba = new BitArray(bits);
        try {
            ba.get(bits.length + 1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
