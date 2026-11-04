/*
 * @test
 * @summary Tests observable behavior of BigInteger.nextProbablePrime()
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectEquals(BigInteger expected, BigInteger actual, String msg) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    static void testTrivialCases() {
        expectEquals(BigInteger.valueOf(2), BigInteger.ZERO.nextProbablePrime(), "0");
        expectEquals(BigInteger.valueOf(2), BigInteger.ONE.nextProbablePrime(), "1");
    }

    static void testSmallValues() {
        expectEquals(BigInteger.valueOf(3), BigInteger.valueOf(2).nextProbablePrime(), "2");
        expectEquals(BigInteger.valueOf(5), BigInteger.valueOf(3).nextProbablePrime(), "3");
        expectEquals(BigInteger.valueOf(5), BigInteger.valueOf(4).nextProbablePrime(), "4");
        expectEquals(BigInteger.valueOf(11), BigInteger.valueOf(7).nextProbablePrime(), "7");
        expectEquals(BigInteger.valueOf(11), BigInteger.valueOf(10).nextProbablePrime(), "10");
        expectEquals(BigInteger.valueOf(17), BigInteger.valueOf(13).nextProbablePrime(), "13");
        expectEquals(BigInteger.valueOf(101), BigInteger.valueOf(100).nextProbablePrime(), "100");
    }

    static void testLargerValue() {
        BigInteger start = new BigInteger("1000000");
        expectEquals(new BigInteger("1000003"), start.nextProbablePrime(), "1000000");
    }

    static void testResultIsPrimeAndGreater() {
        BigInteger start = new BigInteger("1000000000000000000000");
        BigInteger p = start.nextProbablePrime();
        check(p.compareTo(start) > 0, "result must be greater than input");
        check(p.isProbablePrime(100), "result must be probable prime");
    }

    static void testNegativeThrows() {
        boolean thrown = false;
        try {
            BigInteger.valueOf(-5).nextProbablePrime();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "negative input must throw ArithmeticException");
    }

    public static void main(String[] args) {
        testTrivialCases();
        testSmallValues();
        testLargerValue();
        testResultIsPrimeAndGreater();
        testNegativeThrows();
    }
}
