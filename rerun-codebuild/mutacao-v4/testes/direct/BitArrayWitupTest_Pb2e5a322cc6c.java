/* @test
   @summary Tests the get(int index) method of sun.security.util.BitArray.
   @run main BitArrayWitupTest_Pb2e5a322cc6c
 */
import java.util.Arrays;
import sun.security.util.BitArray;

public class BitArrayWitupTest_Pb2e5a322cc6c {
    public static void main(String[] args) {
        testGetWithValidIndices();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToLength();
        testGetWithIndexGreaterThanLength();
    }

    private static void testGetWithValidIndices() {
        boolean[] bits = {true, false, true, false, true};
        BitArray bitArray = new BitArray(bits);
        for (int i = 0; i < bits.length; i++) {
            boolean result = bitArray.get(i);
            if (result != bits[i]) {
                throw new AssertionError("Failed at index " + i + ": expected " + bits[i] + " but got " + result);
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
}
