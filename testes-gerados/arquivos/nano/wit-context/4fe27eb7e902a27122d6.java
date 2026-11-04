/* @test    @summary Test for sun.security.util.BitArray.get(int) method to verify exception handling and normal behavior    @run main BitArrayWitupTest */

import sun.security.util.BitArray;

public class BitArrayWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToLength();
    }

    private static void testGetWithValidIndex() {
        try {
            boolean[] bits = {true, false, true, false};
            BitArray ba = new BitArray(bits);
            boolean result = ba.get(2);
            if (result != true) {
                throw new AssertionError("Expected true at index 2, but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetWithValidIndex: " + e);
        }
    }

    private static void testGetWithNegativeIndex() {
        try {
            boolean[] bits = {true, false, true, false};
            BitArray ba = new BitArray(bits);
            ba.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testGetWithIndexEqualToLength() {
        try {
            boolean[] bits = {true, false, true, false};
            BitArray ba = new BitArray(bits);
            ba.get(4);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
