/* @test    @summary Test for sun.security.util.BitArray.get(int) handling of index bounds    @run main BitArrayWitupTest_Pbe32f80675e2 */

import sun.security.util.BitArray;

public class BitArrayWitupTest_Pbe32f80675e2 {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToLength();
        testGetWithIndexGreaterThanLength();
    }

    private static void testGetWithValidIndex() {
        try {
            BitArray ba = new BitArray(10);
            boolean result = ba.get(0);
            if (!(result == false)) {
                throw new AssertionError("Expected false for index 0 in new BitArray");
            }
            result = ba.get(9);
            if (!(result == false)) {
                throw new AssertionError("Expected false for index 9 in new BitArray");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetWithValidIndex", e);
        }
    }

    private static void testGetWithNegativeIndex() {
        try {
            BitArray ba = new BitArray(10);
            ba.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for negative index", e);
        }
    }

    private static void testGetWithIndexEqualToLength() {
        try {
            BitArray ba = new BitArray(10);
            ba.get(10);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for index equal to length", e);
        }
    }

    private static void testGetWithIndexGreaterThanLength() {
        try {
            BitArray ba = new BitArray(10);
            ba.get(11);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for index greater than length", e);
        }
    }
}
