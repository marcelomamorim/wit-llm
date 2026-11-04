/*
 * @test
 * @summary Tests the BigInteger(int bitLength, int certainty, Random rnd) constructor: rejection of bitLength < 2, prime generation for small and large bit lengths, and determinism with a fixed seed.
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testRejectsBitLengthBelowTwo();
        testSmallBitLengthProducesPrime();
        testLargeBitLengthProducesPrime();
        testDeterministicWithSameSeed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRejectsBitLengthBelowTwo() {
        int[] invalid = {1, 0, -1};
        for (int bits : invalid) {
            boolean thrown = false;
            try {
                new BigInteger(bits, 100, new Random(1));
            } catch (ArithmeticException e) {
                thrown = true;
                check("bitLength < 2".equals(e.getMessage()),
                      "unexpected message for bitLength " + bits + ": " + e.getMessage());
            }
            check(thrown, "expected ArithmeticException for bitLength " + bits);
        }
    }

    static void testSmallBitLengthProducesPrime() {
        int[] sizes = {2, 3, 8, 16, 32, 64, 94};
        for (int bits : sizes) {
            BigInteger p = new BigInteger(bits, 100, new Random(7 + bits));
            check(p.bitLength() == bits, "bitLength mismatch for " + bits + ": " + p.bitLength());
            check(p.signum() == 1, "expected positive value for " + bits);
            check(p.isProbablePrime(100), "result not prime for " + bits + ": " + p);
        }
    }

    static void testLargeBitLengthProducesPrime() {
        int[] sizes = {95, 128, 256};
        for (int bits : sizes) {
            BigInteger p = new BigInteger(bits, 100, new Random(11 + bits));
            check(p.bitLength() == bits, "bitLength mismatch for " + bits + ": " + p.bitLength());
            check(p.signum() == 1, "expected positive value for " + bits);
            check(p.isProbablePrime(100), "large result not prime for " + bits);
        }
    }

    static void testDeterministicWithSameSeed() {
        BigInteger a = new BigInteger(128, 100, new Random(42));
        BigInteger b = new BigInteger(128, 100, new Random(42));
        check(a.equals(b), "same seed must produce the same prime");
    }
}
