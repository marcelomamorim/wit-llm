/*
 * @test
 * @summary Witup-generated tests for BigInteger.divide(BigInteger): exact quotients, sign rules, and ArithmeticException on zero divisor
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void assertEquals(BigInteger expected, BigInteger actual, String message) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(message + ": expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) {
        BigInteger zero = BigInteger.valueOf(0L);
        BigInteger one = BigInteger.valueOf(1L);
        BigInteger seven = BigInteger.valueOf(7L);
        BigInteger negSeven = BigInteger.valueOf(-7L);
        BigInteger two = BigInteger.valueOf(2L);
        BigInteger negTwo = BigInteger.valueOf(-2L);

        assertEquals(BigInteger.valueOf(3L), seven.divide(two), "7 / 2");
        assertEquals(BigInteger.valueOf(-3L), negSeven.divide(two), "-7 / 2");
        assertEquals(BigInteger.valueOf(-3L), seven.divide(negTwo), "7 / -2");
        assertEquals(BigInteger.valueOf(3L), negSeven.divide(negTwo), "-7 / -2");
        assertEquals(seven, seven.divide(one), "x / 1");
        assertEquals(zero, zero.divide(seven), "0 / x");

        BigInteger big = new BigInteger("1234567890123456789012345678901234567890");
        boolean thrownForZeroDivisor = false;
        try {
            big.divide(zero);
        } catch (ArithmeticException e) {
            thrownForZeroDivisor = true;
        }
        check(thrownForZeroDivisor, "divide by zero must throw ArithmeticException (large dividend)");

        thrownForZeroDivisor = false;
        try {
            seven.divide(zero);
        } catch (ArithmeticException e) {
            thrownForZeroDivisor = true;
        }
        check(thrownForZeroDivisor, "divide by zero must throw ArithmeticException (small dividend)");

        StringBuilder a = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            a.append("1234567890");
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            b.append("987654321");
        }
        BigInteger bigA = new BigInteger(a.toString());
        BigInteger bigB = new BigInteger(b.toString());
        assertEquals(bigA, bigA.multiply(bigB).divide(bigB), "(a*b)/b must equal a");
    }
}
