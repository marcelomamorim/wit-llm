/* @test    @summary Test for BigInteger(byte[] val) constructor, focusing on exception path for empty array.    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testEmptyByteArrayThrows();
        testValidByteArray();
    }

    private static void testEmptyByteArrayThrows() {
        boolean threw = false;
        try {
            new BigInteger(new byte[0]);
        } catch (NumberFormatException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NumberFormatException for empty byte array");
        }
    }

    private static void testValidByteArray() {
        byte[] bytes = {1, 2, 3};
        BigInteger bi = new BigInteger(bytes);
        if (bi == null) {
            throw new AssertionError("BigInteger instance should not be null for valid byte array");
        }
        // Additional observable checks can be added if needed
    }
}
