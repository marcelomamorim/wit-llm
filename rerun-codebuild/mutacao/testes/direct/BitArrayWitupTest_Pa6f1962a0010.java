/*
 * @test
 * @summary Tests the get(int index) method of sun.security.util.BitArray.
 * @run main BitArrayWitupTest_Pa6f1962a0010
 */

import sun.security.util.BitArray;

public class BitArrayWitupTest_Pa6f1962a0010 {

    public static void main(String[] args) {
        testGetWithValidIndices();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToLength();
        testGetWithIndexGreaterThanLength();
        testGetWithAllBitsSet();
        testGetWithAllBitsClear();
    }

    private static void testGetWithValidIndices() {
        boolean[] bits = {true, false, true, false, true};
        BitArray bitArray = new BitArray(bits);
        for (int i = 0; i < bits.length; i++) {
            boolean expected = bits[i];
            boolean actual = bitArray.get(i);
            if (expected != actual) {
                throw new AssertionError("Failed at index " + i + ": expected " + expected + ", got " + actual);
            }
        }
    }

    private static void testGetWithNegativeIndex() {
        boolean[] bits = {true, false};
        BitArray bitArray = new BitArray(bits);
        try {
            bitArray.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexEqualToLength() {
        boolean[] bits = {true, false};
        BitArray bitArray = new BitArray(bits);
        try {
            bitArray.get(bits.length);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexGreaterThanLength() {
        boolean[] bits = {true, false};
        BitArray bitArray = new BitArray(bits);
        try {
            bitArray.get(bits.length + 1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithAllBitsSet() {
        boolean[] bits = {true, true, true};
        BitArray bitArray = new BitArray(bits);
        for (int i = 0; i < bits.length; i++) {
            if (!bitArray.get(i)) {
                throw new AssertionError("Expected true at index " + i);
            }
        }
    }

    private static void testGetWithAllBitsClear() {
        boolean[] bits = {false, false, false};
        BitArray bitArray = new BitArray(bits);
        for (int i = 0; i < bits.length; i++) {
            if (bitArray.get(i)) {
                throw new AssertionError("Expected false at index " + i);
            }
        }
    }
}
