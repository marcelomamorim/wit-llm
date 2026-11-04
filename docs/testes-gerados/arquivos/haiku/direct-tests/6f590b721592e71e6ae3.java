/*
 * @test
 * @summary Checks observable behavior of BigInteger(int numBits, Random rnd)
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testZeroBitsYieldsZero();
        testSameSeedYieldsEqualValues();
        testBitLengthDoesNotExceedNumBits();
        testSignumIsNeverNegative();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroBitsYieldsZero() {
        BigInteger v = new BigInteger(0, new Random(1));
        check(v.signum() == 0, "numBits=0 must produce signum 0");
        check(v.equals(BigInteger.ZERO), "numBits=0 must produce ZERO");
    }

    static void testSameSeedYieldsEqualValues() {
        BigInteger a = new BigInteger(128, new Random(42));
        BigInteger b = new BigInteger(128, new Random(42));
        check(a.equals(b), "same seed must produce equal values");
    }

    static void testBitLengthDoesNotExceedNumBits() {
        int[] sizes = {1, 7, 8, 9, 64, 100};
        for (int n : sizes) {
            for (long seed = 0; seed < 20; seed++) {
                BigInteger v = new BigInteger(n, new Random(seed));
                check(v.bitLength() <= n,
                      "bitLength " + v.bitLength() + " exceeds numBits " + n);
            }
        }
    }

    static void testSignumIsNeverNegative() {
        int[] sizes = {1, 8, 33, 256};
        for (int n : sizes) {
            for (long seed = 0; seed < 20; seed++) {
                BigInteger v = new BigInteger(n, new Random(seed));
                check(v.signum() >= 0, "constructor must not produce negative values");
            }
        }
    }
}
