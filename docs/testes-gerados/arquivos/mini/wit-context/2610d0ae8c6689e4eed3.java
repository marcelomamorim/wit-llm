/*
 * @test
 * @summary Test BigInteger(String val) constructor for empty string input and normal input
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testEmptyStringThrowsNumberFormatException();
        testValidStringConstructsBigInteger();
    }

    private static void testEmptyStringThrowsNumberFormatException() {
        try {
            new BigInteger("");
            throw new RuntimeException("Expected NumberFormatException for empty string input");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testValidStringConstructsBigInteger() {
        BigInteger bi = new BigInteger("12345");
        if (!bi.equals(BigInteger.valueOf(12345L))) {
            throw new RuntimeException("BigInteger constructed from string did not match expected value");
        }
    }
}
