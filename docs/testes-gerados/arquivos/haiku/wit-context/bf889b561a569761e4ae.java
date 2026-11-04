/*
 * @test
 * @summary Witup generated tests for BigInteger(int bitLength, int certainty, Random rnd)
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        checkRejected(0);
        checkRejected(1);
        checkRejected(-5);
        checkRejected(Integer.MIN_VALUE);

        int[] sizes = {2, 3, 5, 16, 94, 95, 96, 128, 256};
        Random rnd = new Random(12345L);
        for (int bits : sizes) {
            BigInteger p = new BigInteger(bits, 100, rnd);
            if (p.bitLength() != bits) {
                throw new RuntimeException("bitLength mismatch for " + bits + ": " + p.bitLength());
            }
            if (p.signum() != 1) {
                throw new RuntimeException("expected positive prime for " + bits);
            }
            if (!p.isProbablePrime(100)) {
                throw new RuntimeException("result not prime for bitLength " + bits + ": " + p);
            }
        }
    }

    static void checkRejected(int bits) {
        try {
            new BigInteger(bits, 100, new Random(1L));
            throw new RuntimeException("expected ArithmeticException for bitLength " + bits);
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
