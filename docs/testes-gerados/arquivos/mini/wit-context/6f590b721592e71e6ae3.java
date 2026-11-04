/*
 * @test
 * @summary Test BigInteger(int numBits, Random rnd) constructor for valid and invalid inputs
 * @run main java.math.BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testNegativeNumBitsThrows();
        testZeroNumBitsProducesZero();
        testPositiveNumBitsProducesCorrectBitLength();
    }

    private static void testNegativeNumBitsThrows() {
        Random rnd = new Random(12345L);
        try {
            new BigInteger(-1, rnd);
            throw new RuntimeException("Expected IllegalArgumentException for negative numBits");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testZeroNumBitsProducesZero() {
        Random rnd = new Random(12345L);
        BigInteger bi = new BigInteger(0, rnd);
        if (bi.signum() != 0) {
            throw new RuntimeException("BigInteger with 0 bits should be zero");
        }
    }

    private static void testPositiveNumBitsProducesCorrectBitLength() {
        Random rnd = new Random(12345L);
        int bits = 64;
        BigInteger bi = new BigInteger(bits, rnd);
        if (bi.bitLength() > bits) {
            throw new RuntimeException("BigInteger bitLength should not exceed numBits");
        }
        if (bi.signum() < 0) {
            throw new RuntimeException("BigInteger generated should not be negative");
        }
    }
}
