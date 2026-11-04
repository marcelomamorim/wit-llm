/*
 * @test
 * @summary Verifies BigInteger.intValueExact() returns values within int range and throws ArithmeticException outside it
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testInRangeValues();
        testOutOfRangeValuesThrow();
        testStringConstructedBoundaries();
    }

    static void testInRangeValues() {
        check(BigInteger.valueOf(0).intValueExact() == 0, "zero");
        check(BigInteger.valueOf(1).intValueExact() == 1, "one");
        check(BigInteger.valueOf(-1).intValueExact() == -1, "minus one");
        check(BigInteger.valueOf(Integer.MAX_VALUE).intValueExact() == Integer.MAX_VALUE, "MAX_VALUE");
        check(BigInteger.valueOf(Integer.MIN_VALUE).intValueExact() == Integer.MIN_VALUE, "MIN_VALUE");
        check(BigInteger.valueOf(12345).intValueExact() == 12345, "positive value");
    }

    static void testOutOfRangeValuesThrow() {
        expectArithmeticException(BigInteger.valueOf((long) Integer.MAX_VALUE + 1L), "MAX_VALUE + 1");
        expectArithmeticException(BigInteger.valueOf((long) Integer.MIN_VALUE - 1L), "MIN_VALUE - 1");
        expectArithmeticException(BigInteger.valueOf(4294967296L), "2^32");
        expectArithmeticException(BigInteger.valueOf(4294967297L), "2^32 + 1");
        expectArithmeticException(BigInteger.valueOf(Long.MAX_VALUE), "Long.MAX_VALUE");
        expectArithmeticException(BigInteger.valueOf(Long.MIN_VALUE), "Long.MIN_VALUE");
    }

    static void testStringConstructedBoundaries() {
        check(new BigInteger("-2147483648").intValueExact() == Integer.MIN_VALUE, "parsed MIN_VALUE");
        check(new BigInteger("2147483647").intValueExact() == Integer.MAX_VALUE, "parsed MAX_VALUE");
        expectArithmeticException(new BigInteger("2147483648"), "parsed 2^31");
        expectArithmeticException(new BigInteger("-2147483649"), "parsed MIN_VALUE - 1");
        expectArithmeticException(new BigInteger("18446744073709551616"), "parsed 2^64");
    }

    static void expectArithmeticException(BigInteger value, String label) {
        try {
            int result = value.intValueExact();
            throw new AssertionError(label + ": expected ArithmeticException but got " + result);
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    static void check(boolean condition, String label) {
        if (!condition) {
            throw new AssertionError("Check failed: " + label);
        }
    }
}
