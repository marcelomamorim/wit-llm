/*
 * @test
 * @summary Witup tests for BigInteger.probablePrime argument validation and prime results
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testRejectsBitLengthBelowTwo();
        testSmallBitLengthsProducePrimesWithRequestedLength();
        testLargeBitLengthProducesPrimeWithRequestedLength();
        testDeterministicWithSameSeed();
    }

    static void expectArithmeticException(int bitLength) {
        try {
            BigInteger.probablePrime(bitLength, new Random(1));
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("expected ArithmeticException for bitLength " + bitLength);
    }

    static void testRejectsBitLengthBelowTwo() {
        expectArithmeticException(1);
        expectArithmeticException(0);
        expectArithmeticException(-5);
    }

    static void testSmallBitLengthsProducePrimesWithRequestedLength() {
        Random rnd = new Random(42);
        int[] lengths = {2, 3, 4, 8, 16, 32, 64};
        for (int bits : lengths) {
            BigInteger p = BigInteger.probablePrime(bits, rnd);
            if (p.bitLength() != bits) {
                throw new RuntimeException("bitLength mismatch for " + bits + ": " + p.bitLength());
            }
            if (!p.isProbablePrime(100)) {
                throw new RuntimeException("result not prime for bitLength " + bits + ": " + p);
            }
        }
    }

    static void testLargeBitLengthProducesPrimeWithRequestedLength() {
        Random rnd = new Random(7);
        int bits = 128;
        BigInteger p = BigInteger.probablePrime(bits, rnd);
        if (p.bitLength() != bits) {
            throw new RuntimeException("bitLength mismatch for " + bits + ": " + p.bitLength());
        }
        if (!p.isProbablePrime(100)) {
            throw new RuntimeException("result not prime for bitLength " + bits);
        }
    }

    static void testDeterministicWithSameSeed() {
        BigInteger a = BigInteger.probablePrime(64, new Random(12345));
        BigInteger b = BigInteger.probablePrime(64, new Random(12345));
        if (!a.equals(b)) {
            throw new RuntimeException("same seed produced different primes: " + a + " vs " + b);
        }
    }
}
