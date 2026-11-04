/*
 * @test
 * @summary Verifies BigInteger.byteValueExact returns values in byte range and throws ArithmeticException outside it
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(BigInteger.valueOf(0).byteValueExact() == (byte) 0, "zero");
        check(BigInteger.valueOf(1).byteValueExact() == (byte) 1, "one");
        check(BigInteger.valueOf(-1).byteValueExact() == (byte) -1, "minus one");
        check(BigInteger.valueOf(Byte.MAX_VALUE).byteValueExact() == Byte.MAX_VALUE, "byte max");
        check(BigInteger.valueOf(Byte.MIN_VALUE).byteValueExact() == Byte.MIN_VALUE, "byte min");

        expectOutOfRange(BigInteger.valueOf(Byte.MAX_VALUE + 1));
        expectOutOfRange(BigInteger.valueOf(Byte.MIN_VALUE - 1));
        expectOutOfRange(BigInteger.valueOf(255));
        expectOutOfRange(BigInteger.valueOf(-129));
        expectOutOfRange(BigInteger.valueOf(Integer.MAX_VALUE));
        expectOutOfRange(BigInteger.valueOf(2147483648L));
        expectOutOfRange(BigInteger.valueOf(Long.MAX_VALUE));
        expectOutOfRange(BigInteger.valueOf(Long.MIN_VALUE));
        expectOutOfRange(new BigInteger("100000000000000000000"));
        expectOutOfRange(new BigInteger("-100000000000000000000"));
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("unexpected byteValueExact result for " + what);
        }
    }

    static void expectOutOfRange(BigInteger value) {
        try {
            byte b = value.byteValueExact();
            throw new RuntimeException("no ArithmeticException for " + value + ", got " + b);
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
