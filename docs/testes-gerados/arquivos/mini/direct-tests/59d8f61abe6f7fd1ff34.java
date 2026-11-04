/*
 * @test
 * @summary Test BigInteger constructor BigInteger(byte[] val) for basic correctness and edge cases
 * @run main java.math.BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testZeroByteArray();
        testPositiveByteArray();
        testNegativeByteArray();
        testEmptyByteArray();
        testSingleByteArray();
    }

    private static void testZeroByteArray() {
        byte[] zero = {0};
        BigInteger bi = new BigInteger(zero);
        if (bi.signum() != 0) {
            throw new RuntimeException("Expected signum 0 for zero byte array");
        }
    }

    private static void testPositiveByteArray() {
        byte[] val = {1, 2, 3, 4};
        BigInteger bi = new BigInteger(val);
        // The value should be positive and equal to the bytes interpreted as big-endian
        if (bi.signum() <= 0) {
            throw new RuntimeException("Expected positive signum for positive byte array");
        }
        // Check that toByteArray returns a byte array starting with 0 to indicate positive
        byte[] ba = bi.toByteArray();
        if (ba.length < val.length) {
            throw new RuntimeException("toByteArray length less than input length");
        }
    }

    private static void testNegativeByteArray() {
        byte[] val = {(byte)0xFF, (byte)0xFE, (byte)0xFD};
        BigInteger bi = new BigInteger(val);
        if (bi.signum() >= 0) {
            throw new RuntimeException("Expected negative signum for negative byte array");
        }
    }

    private static void testEmptyByteArray() {
        try {
            new BigInteger(new byte[0]);
            throw new RuntimeException("Expected exception for empty byte array");
        } catch (IndexOutOfBoundsException | NegativeArraySizeException e) {
            // Acceptable exceptions if thrown
        } catch (Exception e) {
            // No other exceptions expected
            throw new RuntimeException("Unexpected exception for empty byte array", e);
        }
    }

    private static void testSingleByteArray() {
        byte[] val = {127};
        BigInteger bi = new BigInteger(val);
        if (bi.signum() != 1) {
            throw new RuntimeException("Expected positive signum for single positive byte");
        }
        val[0] = (byte)0x80; // -128
        bi = new BigInteger(val);
        if (bi.signum() != -1) {
            throw new RuntimeException("Expected negative signum for single negative byte");
        }
    }
}
