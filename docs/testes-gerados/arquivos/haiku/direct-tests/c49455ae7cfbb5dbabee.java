/*
 * @test
 * @summary BigInteger.byteValueExact returns values in byte range and throws ArithmeticException otherwise
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        checkValid("0", (byte) 0);
        checkValid("1", (byte) 1);
        checkValid("-1", (byte) -1);
        checkValid("127", (byte) 127);
        checkValid("-128", (byte) -128);

        checkOutOfRange("128");
        checkOutOfRange("-129");
        checkOutOfRange("256");
        checkOutOfRange("2147483647");
        checkOutOfRange("-2147483649");
        checkOutOfRange("9223372036854775807");
        checkOutOfRange("99999999999999999999999999");
        checkOutOfRange("-99999999999999999999999999");

        if (BigInteger.valueOf(-128).byteValueExact() != (byte) -128) {
            throw new RuntimeException("valueOf(-128) mismatch");
        }
        checkOutOfRange(BigInteger.valueOf(Long.MAX_VALUE).toString());
    }

    static void checkValid(String s, byte expected) {
        byte actual = new BigInteger(s).byteValueExact();
        if (actual != expected) {
            throw new RuntimeException("For " + s + " expected " + expected + " but got " + actual);
        }
    }

    static void checkOutOfRange(String s) {
        BigInteger v = new BigInteger(s);
        boolean thrown = false;
        try {
            v.byteValueExact();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ArithmeticException for " + s);
        }
    }
}
