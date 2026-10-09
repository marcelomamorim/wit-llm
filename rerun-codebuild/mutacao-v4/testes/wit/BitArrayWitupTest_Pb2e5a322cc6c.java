/* @test
   @summary Testa o método get de sun.security.util.BitArray para comportamentos observáveis.
   @run main BitArrayWitupTest_Pb2e5a322cc6c
 */
import java.util.Objects;
public class BitArrayWitupTest_Pb2e5a322cc6c {
    public static void main(String[] args) {
        testValidIndex();
        testNegativeIndex();
        testIndexEqualToLength();
        testIndexGreaterThanLength();
    }

    private static void testValidIndex() {
        try {
            byte[] data = {(byte)0xFF};
            sun.security.util.BitArray bitArray = new sun.security.util.BitArray(8, data);
            boolean result = bitArray.get(0);
            if (result != true) {
                throw new AssertionError("Expected true at index 0");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidIndex", e);
        }
    }

    private static void testNegativeIndex() {
        try {
            byte[] data = {(byte)0xFF};
            sun.security.util.BitArray bitArray = new sun.security.util.BitArray(8, data);
            bitArray.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNegativeIndex", e);
        }
    }

    private static void testIndexEqualToLength() {
        try {
            byte[] data = {(byte)0xFF};
            sun.security.util.BitArray bitArray = new sun.security.util.BitArray(8, data);
            bitArray.get(8);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testIndexEqualToLength", e);
        }
    }

    private static void testIndexGreaterThanLength() {
        try {
            byte[] data = {(byte)0xFF};
            sun.security.util.BitArray bitArray = new sun.security.util.BitArray(8, data);
            bitArray.get(9);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testIndexGreaterThanLength", e);
        }
    }
}
