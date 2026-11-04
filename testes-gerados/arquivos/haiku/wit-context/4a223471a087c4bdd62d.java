/*
 * @test
 * @summary Witup-generated tests for BigInteger.longValueExact
 * @run main BigIntegerWitupTest
 */

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        checkExact(BigInteger.valueOf(0L), 0L, "zero");
        checkExact(BigInteger.valueOf(-1L), -1L, "minus one");
        checkExact(BigInteger.valueOf(Long.MAX_VALUE), Long.MAX_VALUE, "Long.MAX_VALUE");
        checkExact(BigInteger.valueOf(Long.MIN_VALUE), Long.MIN_VALUE, "Long.MIN_VALUE");
        checkExact(BigInteger.ONE.shiftLeft(62), 1L << 62, "2^62");
        checkExact(BigInteger.ONE.shiftLeft(63).negate(), Long.MIN_VALUE, "-2^63");

        expectArithmetic(BigInteger.ONE.shiftLeft(63), "2^63");
        expectArithmetic(BigInteger.ONE.shiftLeft(64), "2^64");
        expectArithmetic(BigInteger.ONE.shiftLeft(63).add(BigInteger.ONE), "2^63+1");
        expectArithmetic(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE), "Long.MAX_VALUE+1");
        expectArithmetic(new BigInteger("9223372036854775808"), "string 2^63");
        expectArithmetic(new BigInteger("-9223372036854775809"), "string -(2^63+1)");
        expectArithmetic(new BigInteger("-ffffffffffffffff", 16), "hex -(2^64-1)");
    }

    static void checkExact(BigInteger value, long expected, String name) {
        long actual = value.longValueExact();
        if (actual != expected) {
            throw new RuntimeException(name + ": expected " + expected + " but was " + actual);
        }
    }

    static void expectArithmetic(BigInteger value, String name) {
        try {
            long r = value.longValueExact();
            throw new RuntimeException(name + ": expected ArithmeticException but returned " + r);
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
