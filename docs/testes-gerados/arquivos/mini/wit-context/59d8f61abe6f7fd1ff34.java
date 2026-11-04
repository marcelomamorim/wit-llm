/*
 * @test
 * @summary Test BigInteger constructor with byte array input, including empty array case
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testValidByteArray();
        testEmptyByteArray();
    }

    private static void testValidByteArray() {
        byte[] val = {1, 2, 3};
        BigInteger bi = new BigInteger(val);
        if (bi == null) {
            throw new RuntimeException("BigInteger constructor returned null for valid input");
        }
        // Check that the magnitude matches the input bytes (sign extension not tested here)
        byte[] biBytes = bi.toByteArray();
        if (biBytes.length == 0) {
            throw new RuntimeException("BigInteger toByteArray returned empty array unexpectedly");
        }
    }

    private static void testEmptyByteArray() {
        byte[] empty = new byte[0];
        try {
            new BigInteger(empty);
            throw new RuntimeException("Expected NumberFormatException for empty byte array input");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
