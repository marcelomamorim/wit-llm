/*
 * @test
 * @summary Witup-derived checks for BigInteger(int numBits, Random rnd)
 * @run main BigIntegerWitupTest
 */

import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testNegativeNumBitsThrows();
        testZeroNumBitsGivesZero();
        testRangeAndSignForPositiveNumBits();
        testDeterministicForSameSeed();
    }

    static void testNegativeNumBitsThrows() {
        boolean thrown = false;
        try {
            new BigInteger(-1, new Random(1));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for numBits < 0");
        }
    }

    static void testZeroNumBitsGivesZero() {
        BigInteger v = new BigInteger(0, new Random(2));
        if (v.signum() != 0 || !v.equals(BigInteger.ZERO)) {
            throw new RuntimeException("numBits=0 should yield zero, got " + v);
        }
    }

    static void testRangeAndSignForPositiveNumBits() {
        Random rnd = new Random(3);
        for (int numBits = 1; numBits <= 64; numBits++) {
            for (int i = 0; i < 20; i++) {
                BigInteger v = new BigInteger(numBits, rnd);
                if (v.signum() < 0) {
                    throw new RuntimeException("negative result for numBits=" + numBits);
                }
                if (v.bitLength() > numBits) {
                    throw new RuntimeException("bitLength " + v.bitLength()
                            + " exceeds numBits " + numBits);
                }
            }
        }
    }

    static void testDeterministicForSameSeed() {
        BigInteger a = new BigInteger(64, new Random(7));
        BigInteger b = new BigInteger(64, new Random(7));
        if (!a.equals(b)) {
            throw new RuntimeException("same seed should produce equal values");
        }
    }
}
