/* @test    @summary Test the constructor BitArray(int length) for valid and invalid inputs    @run main BitArrayWitupTest */

import sun.security.util.BitArray;

public class BitArrayWitupTest {

    public static void main(String[] args) {
        testValidLength();
        testNegativeLength();
    }

    private static void testValidLength() {
        int length = 10;
        try {
            BitArray ba = new BitArray(length);
            assert ba != null : "BitArray should be created for valid length";
            assert ba.getClass().getSimpleName().equals("BitArray") : "Object should be of type BitArray";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid length: " + e);
        }
    }

    private static void testNegativeLength() {
        int length = -5;
        try {
            new BitArray(length);
            throw new AssertionError("Expected IllegalArgumentException for negative length") ;
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
