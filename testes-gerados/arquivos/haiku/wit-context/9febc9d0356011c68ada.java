/*
 * @test
 * @summary Witup-style checks for BigInteger.intValueExact range and ArithmeticException behavior
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        check(BigInteger.valueOf(0L).intValueExact() == 0, "zero");
        check(BigInteger.valueOf(1L).intValueExact() == 1, "one");
        check(BigInteger.valueOf(-1L).intValueExact() == -1, "minus one");
        check(BigInteger.valueOf(Integer.MAX_VALUE).intValueExact() == Integer.MAX_VALUE, "int max");
        check(BigInteger.valueOf(Integer.MIN_VALUE).intValueExact() == Integer.MIN_VALUE, "int min");
        check(new BigInteger("-2147483648").intValueExact() == Integer.MIN_VALUE, "int min from string");

        expectArithmetic(BigInteger.valueOf(2147483648L));
        expectArithmetic(BigInteger.valueOf(-2147483649L));
        expectArithmetic(BigInteger.valueOf(4294967295L));
        expectArithmetic(BigInteger.valueOf(4294967296L));
        expectArithmetic(BigInteger.valueOf(Long.MAX_VALUE));
        expectArithmetic(BigInteger.valueOf(Long.MIN_VALUE));
        expectArithmetic(new BigInteger("2147483648"));
        expectArithmetic(new BigInteger("123456789012345678901234567890"));
    }

    static void check(boolean condition, String label) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + label);
        }
    }

    static void expectArithmetic(BigInteger value) {
        try {
            int result = value.intValueExact();
            throw new RuntimeException("No ArithmeticException for " + value + ", returned " + result);
        } catch (ArithmeticException expected) {
            // expected: value is outside int range
        }
    }
}
