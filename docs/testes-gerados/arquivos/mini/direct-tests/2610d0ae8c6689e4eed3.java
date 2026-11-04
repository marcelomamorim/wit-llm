/*
 * @test
 * @summary Test BigInteger(String val) constructor with decimal input
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testValidDecimalString();
        testZeroString();
        testNegativeDecimalString();
        testLeadingPlusSign();
        testLeadingZeros();
        testInvalidString();
    }

    private static void testValidDecimalString() {
        BigInteger bi = new BigInteger("1234567890");
        if (!bi.toString().equals("1234567890")) {
            throw new RuntimeException("Failed: valid decimal string");
        }
    }

    private static void testZeroString() {
        BigInteger bi = new BigInteger("0");
        if (!bi.toString().equals("0")) {
            throw new RuntimeException("Failed: zero string");
        }
    }

    private static void testNegativeDecimalString() {
        BigInteger bi = new BigInteger("-987654321");
        if (!bi.toString().equals("-987654321")) {
            throw new RuntimeException("Failed: negative decimal string");
        }
    }

    private static void testLeadingPlusSign() {
        BigInteger bi = new BigInteger("+12345");
        if (!bi.toString().equals("12345")) {
            throw new RuntimeException("Failed: leading plus sign");
        }
    }

    private static void testLeadingZeros() {
        BigInteger bi = new BigInteger("0000123");
        if (!bi.toString().equals("123")) {
            throw new RuntimeException("Failed: leading zeros");
        }
    }

    private static void testInvalidString() {
        try {
            new BigInteger("12a34");
            throw new RuntimeException("Failed: invalid string did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
