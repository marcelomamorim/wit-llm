/* @test    @summary Test for BigInteger.longValueExact() method to verify correct behavior and exception handling.    @run main BigIntegerWitupTest_P8dc2ecc214de */

import java.math.BigInteger;

public class BigIntegerWitupTest_P8dc2ecc214de {

    public static void main(String[] args) {
        testSmallRange();
        testLargeRange();
        testBeyondRange();
    }

    static void testSmallRange() {
        try {
            BigInteger bi = BigInteger.valueOf(12345L);
            long result = bi.longValueExact();
            if (result != 12345L) {
                throw new AssertionError("Expected 12345, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in small range test", e);
        }
    }

    static void testLargeRange() {
        // Construct a BigInteger within long range
        BigInteger bi = new BigInteger("9223372036854775807"); // Long.MAX_VALUE
        long result = bi.longValueExact();
        if (result != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE, got " + result);
        }
        BigInteger bi2 = new BigInteger("-9223372036854775808"); // Long.MIN_VALUE
        long result2 = bi2.longValueExact();
        if (result2 != Long.MIN_VALUE) {
            throw new AssertionError("Expected Long.MIN_VALUE, got " + result2);
        }
    }

    static void testBeyondRange() {
        // Construct a BigInteger beyond long range to trigger exception
        String beyondMax = "9223372036854775808"; // Long.MAX_VALUE + 1
        String beyondMin = "-9223372036854775809"; // Long.MIN_VALUE - 1
        try {
            BigInteger bi = new BigInteger(beyondMax);
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value: " + beyondMax);
        } catch (ArithmeticException e) {
            // expected
        }
        try {
            BigInteger bi2 = new BigInteger(beyondMin);
            bi2.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value: " + beyondMin);
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
