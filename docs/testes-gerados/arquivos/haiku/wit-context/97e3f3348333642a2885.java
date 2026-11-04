/*
 * @test
 * @summary Witup-generated tests for BigInteger.probablePrime(int, Random)
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testRejectsBitLengthBelowTwo();
        testSmallestValidBitLength();
        testSmallPrimeRange();
        testLargePrimeRange();
    }

    static void testRejectsBitLengthBelowTwo() {
        int[] bad = {1, 0, -1, -100};
        for (int b : bad) {
            boolean thrown = false;
            try {
                BigInteger.probablePrime(b, new Random(1L));
            } catch (ArithmeticException expected) {
                thrown = true;
            }
            check(thrown, "expected ArithmeticException for bitLength " + b);
        }
    }

    static void testSmallestValidBitLength() {
        for (long seed = 0; seed < 20; seed++) {
            BigInteger p = BigInteger.probablePrime(2, new Random(seed));
            check(p.bitLength() == 2, "bitLength 2 result has bitLength " + p.bitLength());
            check(p.isProbablePrime(100), "result is not prime: " + p);
        }
    }

    static void testSmallPrimeRange() {
        int[] sizes = {3, 8, 16, 31, 64, 94};
        for (int bits : sizes) {
            for (long seed = 0; seed < 3; seed++) {
                BigInteger p = BigInteger.probablePrime(bits, new Random(seed));
                check(p.bitLength() == bits, "expected bitLength " + bits + " got " + p.bitLength());
                check(p.isProbablePrime(100), "result is not prime for bits " + bits + ": " + p);
            }
        }
    }

    static void testLargePrimeRange() {
        int[] sizes = {95, 128, 256};
        for (int bits : sizes) {
            BigInteger p = BigInteger.probablePrime(bits, new Random(42L));
            check(p.bitLength() == bits, "expected bitLength " + bits + " got " + p.bitLength());
            check(p.isProbablePrime(100), "result is not prime for bits " + bits);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
