/*
 * @test
 * @summary Tests BigInteger.longValueExact boundary values and out-of-range ArithmeticException
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(BigInteger.valueOf(0).longValueExact() == 0L, "zero");
        check(BigInteger.valueOf(-1).longValueExact() == -1L, "minus one");
        check(BigInteger.valueOf(42).longValueExact() == 42L, "small positive");
        check(BigInteger.valueOf(Long.MAX_VALUE).longValueExact() == Long.MAX_VALUE, "Long.MAX_VALUE");
        check(BigInteger.valueOf(Long.MIN_VALUE).longValueExact() == Long.MIN_VALUE, "Long.MIN_VALUE");

        byte[] maxBytes = {0x7f, -1, -1, -1, -1, -1, -1, -1};
        check(new BigInteger(maxBytes).longValueExact() == Long.MAX_VALUE, "max from bytes");

        byte[] minBytes = {(byte) 0x80, 0, 0, 0, 0, 0, 0, 0};
        check(new BigInteger(minBytes).longValueExact() == Long.MIN_VALUE, "min from bytes");

        expectArithmeticException(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.valueOf(1)), "MAX+1");
        expectArithmeticException(BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.valueOf(1)), "MIN-1");
        expectArithmeticException(new BigInteger("9223372036854775808"), "2^63");
        expectArithmeticException(new BigInteger("-9223372036854775809"), "-2^63-1");
        expectArithmeticException(new BigInteger("18446744073709551616"), "2^64");
        expectArithmeticException(new BigInteger("1234567890123456789012345678901234567890"), "large");
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }

    static void expectArithmeticException(BigInteger value, String msg) {
        try {
            long r = value.longValueExact();
            throw new RuntimeException("No ArithmeticException for " + msg + ", returned " + r);
        } catch (ArithmeticException e) {
            check("BigInteger out of long range".equals(e.getMessage()), "message for " + msg);
        }
    }
}
