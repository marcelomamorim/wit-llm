/*
 * @test
 * @summary Checks BigInteger.nextProbablePrime for negative, trivial, small and large inputs
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        checkNegativeThrows(-1L);
        checkNegativeThrows(-100L);
        checkEquals(BigInteger.ZERO.nextProbablePrime(), 2L);
        checkEquals(BigInteger.ONE.nextProbablePrime(), 2L);
        checkEquals(BigInteger.valueOf(2L).nextProbablePrime(), 3L);
        checkEquals(BigInteger.valueOf(3L).nextProbablePrime(), 5L);
        checkEquals(BigInteger.valueOf(10L).nextProbablePrime(), 11L);
        checkEquals(BigInteger.valueOf(13L).nextProbablePrime(), 17L);
        checkEquals(BigInteger.valueOf(100L).nextProbablePrime(), 101L);
        checkEquals(BigInteger.valueOf(1000L).nextProbablePrime(), 1009L);
        checkPrimeAfter(BigInteger.ONE.shiftLeft(64));
        checkPrimeAfter(BigInteger.valueOf(1000000007L));
    }

    static void checkNegativeThrows(long value) {
        try {
            BigInteger.valueOf(value).nextProbablePrime();
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("ArithmeticException expected for " + value);
    }

    static void checkEquals(BigInteger actual, long expected) {
        if (!actual.equals(BigInteger.valueOf(expected))) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void checkPrimeAfter(BigInteger start) {
        BigInteger p = start.nextProbablePrime();
        if (p.compareTo(start) <= 0) {
            throw new RuntimeException("result not greater than start: " + p);
        }
        if (!p.isProbablePrime(50)) {
            throw new RuntimeException("result is not prime: " + p);
        }
    }
}
