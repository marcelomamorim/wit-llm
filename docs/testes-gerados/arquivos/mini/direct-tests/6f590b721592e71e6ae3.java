/*
 * @test
 * @summary Test BigInteger(int numBits, Random rnd) constructor for basic properties
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testZeroBits();
        testNegativeBits();
        testPositiveBits();
        testRandomness();
    }

    private static void testZeroBits() {
        BigInteger bi = new BigInteger(0, new Random(12345));
        if (bi.signum() != 0) {
            throw new RuntimeException("Expected zero for zero bits, got: " + bi);
        }
    }

    private static void testNegativeBits() {
        // Negative bits treated as zero bits, expect zero
        BigInteger bi = new BigInteger(-1, new Random(12345));
        if (bi.signum() != 0) {
            throw new RuntimeException("Expected zero for negative bits, got: " + bi);
        }
    }

    private static void testPositiveBits() {
        BigInteger bi = new BigInteger(8, new Random(12345));
        if (bi.signum() == 0) {
            throw new RuntimeException("Expected non-zero for positive bits, got zero");
        }
        if (bi.bitLength() > 8) {
            throw new RuntimeException("Bit length exceeds requested bits: " + bi.bitLength());
        }
    }

    private static void testRandomness() {
        Random rnd1 = new Random(12345);
        Random rnd2 = new Random(12345);
        BigInteger bi1 = new BigInteger(16, rnd1);
        BigInteger bi2 = new BigInteger(16, rnd2);
        if (!bi1.equals(bi2)) {
            throw new RuntimeException("BigInteger with same seed and bits should be equal");
        }
    }
}
